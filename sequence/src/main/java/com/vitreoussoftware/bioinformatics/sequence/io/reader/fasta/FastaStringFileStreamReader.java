package com.vitreoussoftware.bioinformatics.sequence.io.reader.fasta;

import com.vitreoussoftware.bioinformatics.sequence.io.reader.BufferFileStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader;
import lombok.NonNull;
import org.javatuples.Pair;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * File stream reader for FASTA data files
 * @author John
 *
 */
public final class FastaStringFileStreamReader implements StringStreamReader
{
    /**
     * The FASTA file wrapped in a {@link BufferFileStreamReader}
     */
    private final BufferFileStreamReader reader;

    /**
     * Create a FASTA File Stream Reader for the given file
     * @param reader the {@link BufferFileStreamReader} to read from
     */
    private FastaStringFileStreamReader(@NonNull final BufferFileStreamReader reader)
    {
        this.reader = reader;
    }

    /**
     * Create an input stream for FASTA file format
     * @param filePath the FASTA file
     * @return the input stream
     * @throws FileNotFoundException the specified file was not found
     */
    public static StringStreamReader create(String filePath) throws FileNotFoundException {
        return new FastaStringFileStreamReader(
                BufferFileStreamReader.builder()
                        .filePath(filePath)
                        .build());
    }

    /**
     * Create an input stream for FASTA file format
     * @param filePath the FASTA file
     * @param pagingSize the size of the buffer for paging data from disk
     * @return the input stream
     * @throws FileNotFoundException the specified file was not found
     */
    public static StringStreamReader create(String filePath, int pagingSize) throws FileNotFoundException {
        return new FastaStringFileStreamReader(
                BufferFileStreamReader.builder()
                        .filePath(filePath)
                        .bufferSize(pagingSize)
                        .build());
    }
	
	/**
     * Reads a record from the file
	 * @return the record
	 */
	@Override
	public Pair<String,String> next() {
        String metadata = readMetadata();
        String comments = readComments();
        String data = readSequenceData();

        return Pair.with(metadata, data);
    }

	/**
     * Does the stream reader still have a record?
	 * @return boolean indicator
	 * @throws IOException If the file cannot be accessed it may fail
	 */
	@Override
	public boolean hasNext() {
        // Dump whitespace
        reader.dropUntil(x -> !Character.isWhitespace(x));

		return !reader.isEof();
	}

	@Override
	public void close() throws IOException {
		this.reader.close();
	}

    @Override
    protected void finalize() throws Throwable {
        this.reader.close();
        super.finalize();
    }

    private String readMetadata() {
        StringBuilder sb = new StringBuilder();
        // if we are out of buffered data read more

        reader.dropUntil(x -> x == ';' || x == '>');
        reader.drop();

        sb.append(reader.takeUntil(x -> x == '\n'));

        return sb.toString().trim();
    }

    private String readComments() {
        StringBuilder sb = new StringBuilder();

        // spin until we are out of white space
        reader.dropWhileWhitespace();

        // if we have comments
        if (reader.is(';'))
            sb.append(readMetadata());

        return sb.toString();
    }

    private String readSequenceData() {
        StringBuilder sb = new StringBuilder();
        boolean readingSequence = true;

        while (readingSequence && !reader.isEof()) {
            reader.dropWhileWhitespace();

            // read elements from the buffer until we find the start of the next sequence, terminator for current sequence
            // or white space
            sb.append(reader.takeUntil(character -> character == '*' || Character.isWhitespace(character)));

            reader.dropWhileWhitespace();

            if (reader.is('*')) {
                reader.drop();
                readingSequence = false;
            }
            if (reader.is('>') || reader.is(';'))
                readingSequence = false;
        }

        return  sb.toString();
    }
}
