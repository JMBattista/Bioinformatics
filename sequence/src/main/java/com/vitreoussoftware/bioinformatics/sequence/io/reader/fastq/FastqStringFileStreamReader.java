package com.vitreoussoftware.bioinformatics.sequence.io.reader.fastq;

import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader;
import org.javatuples.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Predicate;

/**
 * File stream reader for FASTA data files
 * @author John
 *
 */
public final class FastqStringFileStreamReader implements StringStreamReader
{
	private static final int DEFAULT_BUFFER_SIZE = 64 * 1024; // 64 KB read size
	
	/**
	 * The FASTA file
	 */
	private FileReader file;
	private char[] buffer;
	private int length;
	private int index;

	/**
	 * Create a FASTA File Stream Reader for the given file
	 * @param file the file to run on
	 */
	private FastqStringFileStreamReader(FileReader file, int pagingSize)
	{
		this.file = file;

		buffer = new char[pagingSize];
		length = 0;  // we don't have anything in the buffer
		index = 0;   // index starts at 0
	}

    /**
     * Create an input stream for FASTA file format
     * @param fileName the FASTA file
     * @return the input stream
     * @throws java.io.FileNotFoundException the specified file was not found
     */
    public static StringStreamReader create(String fileName) throws FileNotFoundException {
        return create(fileName, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Create an input stream for FASTA file format
     * @param file the FASTA file
     * @return the input stream
     * @throws java.io.FileNotFoundException the specified file was not found
     */
    public static StringStreamReader create(FileReader file) throws FileNotFoundException {
        return create(file, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Create an input stream for FASTA file format
     * @param file the FASTA file
     * @param pagingSize the size of the buffer for paging data from disk
     * @return the input stream
     * @throws java.io.FileNotFoundException the specified file was not found
     */
    public static StringStreamReader create(FileReader file, int pagingSize) throws FileNotFoundException {
        return new FastqStringFileStreamReader(file, pagingSize);
    }

    /**
     * Create an input stream for FASTA file format
     * @param fileName the FASTA file
     * @param pagingSize the size of the buffer for paging data from disk
     * @return the input stream
     * @throws java.io.FileNotFoundException the specified file was not found
     */
    public static StringStreamReader create(String fileName, int pagingSize) throws FileNotFoundException {
        FileReader file = new FileReader(fileName);
        return new FastqStringFileStreamReader(file, pagingSize);
    }

	/**
     * Reads a record from the file
	 * @return the record
	 */
	@Override
	public Pair<String,String> next() {
        String metadata = readMetadata();
        String data = readSequenceData();
        String comments = readComments();
        String quality = readQuality(data.length());

        return Pair.with(metadata, data);
    }

    private void dropUntil(Predicate<Character> pred) {
        boolean found = false;
        while (!found) {
            if (index >= length)
                bufferData();
            if (length == -1 || pred.test(buffer[index]))
                return;
            else
                index++;

        }
    }

    private void drop(int i) {
        index += i;
    }   

    private String takeUntil(Predicate<Character> pred) {
        StringBuilder sb = new StringBuilder();

        boolean found = false;
        while (!found) {
            if (index >= length)
                bufferData();
            if (length == -1 || pred.test(buffer[index]))
                found = true;
            else
                sb.append(buffer[index]);
            index++;
        }

        return sb.toString().trim();
    }

    private String readMetadata() {
        StringBuilder sb = new StringBuilder();

        // Find the Start symbol
        dropUntil(x -> x == '@');

        // move past the start symbol
        drop(1);

        return takeUntil(x -> x == '\n');
    }

    private String readSequenceData() {
        StringBuilder sb = new StringBuilder();
        boolean readingSequence = true;

        do {
            // read elements from the buffer takeUntil we find the start of the next sequence, terminator for current sequence
            // or white space
            sb.append(takeUntil(c -> Character.isWhitespace(c)));

            // Drop white space characters
            dropUntil(c -> !Character.isWhitespace(c));

            if (length == -1)
                throw new RuntimeException("Invalid file format, FASTQ requires quality data to accompany sequence data");

            if (index < length && buffer[index] == '+') {
                readingSequence = false;
            }
        } while (readingSequence);

        return  sb.toString();
    }

    private String readComments() {
        dropUntil(c -> c == '+');
        // move past index
        drop(1);

        return takeUntil(c -> c == '\n');
    }

    private String readQuality(int sequenceLength) {
        StringBuilder sb = new StringBuilder();
        boolean readingQuaility = true;

        do {
            if (length == -1)
                throw new RuntimeException("Invalid file format, FASTQ requires quality data to accompany sequence data");
            // read elements from the buffer takeUntil we find the start of the next sequence, terminator for current sequence
            // or white space
            sb.append(takeUntil(c -> Character.isWhitespace(c)));

            // Drop white space characters
            dropUntil(c -> !Character.isWhitespace(c));

            if (index < length && buffer[index] == '@') {
                readingQuaility = false;
            }
            if (sb.length() == sequenceLength)
                readingQuaility = false;
        } while (readingQuaility);

        assert(sequenceLength == sb.length());
        return  sb.toString();
    }

    private int bufferData() {
        try {
            index = 0;
            length = file.read(buffer);
		    return length;
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to read from file");
        }
	}

	@Override
	protected void finalize() throws Throwable {
		this.file.close();
		super.finalize();
	}

	/**
     * Does the stream reader still have a record?
	 * @return boolean indicator
	 * @throws java.io.IOException If the file cannot be accessed it may fail
	 */
	@Override
	public boolean hasNext() {
		// if we know we have data remaining say so
		if (length > 0 && index < length)
			return true;
		// if we don't know find out

		bufferData();
		
		return length > 0 && index < length;
	}

	@Override
	public void close() throws IOException {
		this.file.close();
	}


}
