package com.vitreoussoftware.bioinformatics.sequence.reader.fasta;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.vitreoussoftware.bioinformatics.sequence.reader.SequenceStringStreamReader;

/**
 * File stream reader for FASTA data files
 * @author John
 *
 */
public final class FastaStringFileStreamReader implements SequenceStringStreamReader 
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
	private FastaStringFileStreamReader(FileReader file, int pagingSize)
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
     * @throws FileNotFoundException the specified file was not found
     */
    public static SequenceStringStreamReader create(String fileName) throws FileNotFoundException
    {
        return create(fileName, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Create an input stream for FASTA file format
     * @param fileName the FASTA file
     * @param pagingSize the size of the buffer for paging data from disk
     * @return the input stream
     * @throws FileNotFoundException the specified file was not found
     */
    public static SequenceStringStreamReader create(String fileName, int pagingSize) throws FileNotFoundException {
        FileReader file = new FileReader(fileName);
        return new FastaStringFileStreamReader(file, pagingSize);
    }
	
	/**
     * Reads a record from the file
	 * @return the record
	 */
	@Override
	public String next() {
        String metadata = readMetadata();
        String data = readSequenceData();

        return data;
    }

    private String readMetadata() {
        StringBuilder sb = new StringBuilder();
        // if we are out of buffered data read more
        if (index >= length)
            bufferData();

        // spin until we find the end of the heading row
        while (index < length && buffer[index] != '\n') {
            sb.append(buffer[index]);
            index++;
        }

        // If we reached the end return the metadata
        if (index < length && buffer[index] == '\n')
            return sb.toString();

        // If we need to rebuffer do so.
        return sb.toString() + readMetadata();
    }

    private String readSequenceData() {
        StringBuilder sb = new StringBuilder();
        boolean readingSequence = true;
        do {
            // if we are out of buffered data read more
            if (index >= length)
                bufferData();

            // spin until we are out of white space
            while (index < length && Character.isWhitespace(buffer[index]))
                index++;

            // read elements from the buffer until we find the start of the next sequence or the end of file
            while (index < length && buffer[index] != '>' && ! Character.isWhitespace(buffer[index])) {
                sb.append(buffer[index]);
                index++;
            }

            if (index < length && buffer[index] == '>')
            {
                readingSequence = false;
            }
        } while (length > 0 && (readingSequence || index >= length));

        return  sb.toString();
    }

    private void bufferData() {
        try {
		    length = file.read(buffer);
		    index = 0;
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
	 * @throws IOException If the file cannot be accessed it may fail
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
