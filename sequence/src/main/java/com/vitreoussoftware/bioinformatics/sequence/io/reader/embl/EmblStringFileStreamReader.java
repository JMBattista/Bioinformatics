package com.vitreoussoftware.bioinformatics.sequence.io.reader.embl;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.BufferFileStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader;
import lombok.NonNull;
import org.javatuples.Pair;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * File stream reader for EMBL data files
 *
 * @author John
 */
public final class EmblStringFileStreamReader implements StringStreamReader {
    /**
     * The EMBL file wrapped in a {@link BufferFileStreamReader}
     */
    private final BufferFileStreamReader reader;

    /**
     * Create a EMBL File Stream Reader for the given file
     *
     * @param reader the {@link BufferFileStreamReader} to read from
     */
    private EmblStringFileStreamReader(@NonNull final BufferFileStreamReader reader) {
        this.reader = reader;
    }

    /**
     * Create an input stream for EMBL file format
     *
     * @param filePath the EMBL file
     * @return the input stream
     * @throws FileNotFoundException the specified file was not found
     */
    public static StringStreamReader create(final String filePath) throws FileNotFoundException {
        return new EmblStringFileStreamReader(
                BufferFileStreamReader.builder()
                        .filePath(filePath)
                        .build());
    }

    /**
     * Create an input stream for EMBL file format
     *
     * @param filePath   the EMBL file
     * @param pagingSize the size of the buffer for paging data from disk
     * @return the input stream
     * @throws FileNotFoundException the specified file was not found
     */
    public static StringStreamReader create(final String filePath, final int pagingSize) throws FileNotFoundException {
        return new EmblStringFileStreamReader(
                BufferFileStreamReader.builder()
                        .filePath(filePath)
                        .bufferSize(pagingSize)
                        .build());
    }

    /**
     * Reads a record from the file
     *
     * @return the record
     */
    @Override
    public Pair<String, String> next() {
        final String metadata = readMetadata();
        final String data = readSequenceData();

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
        reader.dropWhileWhitespace();

        return !reader.isEof();
    }

    @Override
    protected void finalize() throws Throwable {
        this.reader.close();
        super.finalize();
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }

    private String readMetadata() {
        // Find the Start flag
        reader.dropUntil("ID");

        // Drop the whitespace after Start flag
        reader.dropWhileWhitespace();

        // Deal with the rest of the metadata
        return reader.takeUntil(x -> x == '\n');
    }

    private String readSequenceData() {
        final StringBuilder sb = new StringBuilder();
        boolean readingSequence = true;

        // Seek for the flag that starts the Sequence
        reader.dropUntil("SQ");

        // skip the line
        reader.dropUntil(x -> x == '\n');

        do {
            if (reader.isEof())
                throw new InvalidDnaFormatException("Did not find end of sequence before end of file");

            // Read Rows of data until we run out of them
            sb.append(readDataRow());


            // If our cursor is currently on the '/' character we've either at a valid termination or a fault
            if (reader.is(x -> x == '/')) {
                // Advance one character and recheck
                reader.drop();
                if (reader.is(x -> x == '/')) {
                    readingSequence = false;
                    reader.drop();
                } else
                    throw new InvalidDnaFormatException("Malformed sequence, found partial terminator '/' only, not '//'.");
            }
        } while (readingSequence);

        return sb.toString();
    }

    private String readDataRow() {
        final StringBuilder row = new StringBuilder();
        boolean readingRow = true;

        reader.dropWhileWhitespace();

        do {
            // read elements from the buffer reader.takeUntil we find the start of the next sequence, terminator for current sequence
            // or white space
            row.append(reader.takeUntil(Character::isWhitespace));

            // determine if this is the last data block on the row. To do this we need to ensure the buffer is not empty
            if (reader.is(x -> !Character.isAlphabetic(x))) {
                readingRow = false;
            }
            if (reader.isEof())
                throw new InvalidDnaFormatException("Did not find end of sequence before end of file");

        } while (readingRow);

        // Now that we've read the sequence data run to the end of the row and drop the count data
        // TODO should we check the counts?
        reader.dropUntil(Character::isDigit);
        reader.dropUntilWhiteSpace();
        reader.dropWhileWhitespace();

        return row.toString();
    }
}
