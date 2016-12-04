package com.vitreoussoftware.bioinformatics.sequence.io.reader.fastq;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
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
public final class FastqStringFileStreamReader implements StringStreamReader
{
    /**
     * The FASTQ file wrapped in a {@link BufferFileStreamReader}
     */
    private final BufferFileStreamReader reader;

    /**
     * Create a FASTQ File Stream Reader for the given file
     * @param reader the {@link BufferFileStreamReader} to read from
     */
    private FastqStringFileStreamReader(@NonNull final BufferFileStreamReader reader)
    {
        this.reader = reader;
    }

    /**
     * Create an input stream for FASTQ file format
     * @param filePath the FASTQ file
     * @return the input stream
     * @throws FileNotFoundException the specified file was not found
     */
    public static StringStreamReader create(final String filePath) throws FileNotFoundException {
        return new FastqStringFileStreamReader(
                BufferFileStreamReader.builder()
                        .filePath(filePath)
                        .build());
    }

    /**
     * Create an input stream for FASTQ file format
     * @param filePath the FASTQ file
     * @param pagingSize the size of the buffer for paging data from disk
     * @return the input stream
     * @throws FileNotFoundException the specified file was not found
     */
    public static StringStreamReader create(final String filePath, final int pagingSize) throws FileNotFoundException {
        return new FastqStringFileStreamReader(
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
        final String metadata = readMetadata();
        final String data = readSequenceData();
        final String comments = readComments();
        final String quality = readQuality(data.length());

        return Pair.with(metadata, data);
    }

    /**
     * Does the stream reader still have a record?
     *
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

    private String readMetadata() {
        // Find the Start symbol
        reader.dropUntil(x -> x == '@');
        // move past the start symbol
        reader.drop();

        return reader.takeUntil(x -> x == '\n' || x == '\r');
    }

    private String readSequenceData() {
        final StringBuilder sb = new StringBuilder();
        boolean readingSequence = true;

        do {
            // read elements from the buffer reader.takeUntil we find the start of the next sequence, terminator for current sequence
            // or white space
            sb.append(reader.takeUntilWhitespace());

            // Drop white space characters
            reader.dropWhileWhitespace();

            if (reader.isEof())
                throw new InvalidDnaFormatException("Invalid file format, FASTQ requires quality data to accompany sequence data");

            if (reader.is(x -> x  == '+')) {
                readingSequence = false;
            }
        } while (readingSequence);

        return  sb.toString();
    }

    private String readComments() {
        reader.dropUntil(c -> c == '+');
        // move past index
        reader.drop();

        return reader.takeUntil(c -> c == '\n' || c == '\r');
    }

    private String readQuality(final int sequenceLength) {
        final StringBuilder sb = new StringBuilder();
        boolean readingQuaility = true;

        do {
            if (reader.isEof())
                throw new InvalidDnaFormatException("Invalid file format, FASTQ requires quality data to accompany sequence data");
            // read elements from the buffer reader.takeUntil we find the start of the next sequence, terminator for current sequence
            // or white space
            sb.append(reader.takeUntilWhitespace());

            // Drop white space characters
            reader.dropWhileWhitespace();

            if (reader.is(x -> x == '@'))
                readingQuaility = false;

            if (sb.length() == sequenceLength)
                readingQuaility = false;
        } while (readingQuaility);

        assert(sequenceLength == sb.length());
        return  sb.toString();
    }

	@Override
	protected void finalize() throws Throwable {
		this.reader.close();
		super.finalize();
	}
}
