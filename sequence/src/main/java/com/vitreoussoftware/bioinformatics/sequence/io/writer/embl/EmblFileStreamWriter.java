package com.vitreoussoftware.bioinformatics.sequence.io.writer.embl;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.io.writer.SequenceStreamWriter;

import java.io.FileWriter;
import java.io.IOException;

/**
 * File stream reader for EMBL data files
 * @author John 2016/10/16
 *
 */
public final class EmblFileStreamWriter implements SequenceStreamWriter {

    /**
     * Flag indicating the start of the metadata for a sequence
     */
    private static final String METADATA_ID = "ID";

    /**
     * The spacing between the 2 character Metadata identifier and the content
     */
    private static final String METADATA_SPACER = "   ";

    /**
     * Flag indicating the start of the sequence data
     */
    private static final String METADATA_SEQUENCE_START = "SQ";

    /**
     * Metadata indicating the length of the sequence data that will be written out
     */
    private static final String METADATA_SEQUENCE_LENGTH = "Sequence %d BP;\n";

    /**
     * The spacing between the 2 character Metadata identifier and the content
     */
    private static final String SEQUENCE_SPACER = "     ";

    /**
     * Maximum length of a row of sequence data in EMBL format
     */
    private static final int MAX_ROW_LENGTH = 80;
    private static final String BASE_PAIR_COUNT = "%9d";

    /**
     * The EMBL file we are writing to
     */
    private FileWriter file;

    /**
     * Create a EMBL File Stream Reader for the given file
     * @param file the file to run on
     */
    private EmblFileStreamWriter(FileWriter file)
    {
        this.file = file;
    }

    /**
     * Create an input stream for EMBL file format
     * @param fileName the EMBL file
     * @return the input stream
     * @throws java.io.FileNotFoundException the specified file was not found
     */
    public static SequenceStreamWriter create(String fileName) throws IOException
    {
        FileWriter file = new FileWriter(fileName);
        return new EmblFileStreamWriter(file);
    }

    @Override
    public int write(Sequence sequence) throws IOException {
        int charactersWritten = 0;
        String sequenceString = sequence.toString();
        charactersWritten += writeMetadata(sequence.getMetadata());
        charactersWritten += writeSequenceStart(sequenceString.length());
        charactersWritten += writeSequenceData(sequenceString);
        charactersWritten += writeTermination();

        return charactersWritten;
    }

    private int writeSequenceStart(int length) throws IOException {
        String sequenceLength = String.format(METADATA_SEQUENCE_LENGTH, length);
        file.write(METADATA_SEQUENCE_START);
        file.write(METADATA_SPACER);
        file.write(sequenceLength);

        return METADATA_SEQUENCE_START.length()
                + METADATA_SPACER.length()
                + sequenceLength.length();
    }

    @Override
    protected void finalize() throws Throwable {
        this.file.close();
        super.finalize();
    }

    @Override
    public void close() throws IOException {
        this.file.close();
    }

    private int writeMetadata(String metadata) throws IOException {
        file.write(METADATA_ID);
        file.write(METADATA_SPACER);
        file.write(metadata);
        file.write("\n");

        return metadata.length() + 2;
    }

    private int writeSequenceData(String sequenceData) throws IOException {
        int charactersWritten = 0;
        int start = 0;
        // Write the rows
        while (start < sequenceData.length()) {
            // Pad the sequence data start position over by the spacer
            file.write(SEQUENCE_SPACER);

            // cache the current value of start so we know how many characters were written this time
            final int priorStart = start;
            int block = 0;
            // Write each of the (max 6) blocks in the row
            for (; block < 6 && start < sequenceData.length(); block++) {
                int dist = 10;
                if (start + 10 > sequenceData.length())
                    dist = sequenceData.length() - start;

                String blockString = sequenceData.substring(start, start + dist);
                file.write(blockString);
                file.write(' ');

                start += dist;
            }

            // determine how many characters were written for this row to write the count
            final int written = start - priorStart;
            // the cumulative count of items written
            String cumulativeCount = String.format(BASE_PAIR_COUNT, start);

            // Pad out the row for missing characters
            final int gap = MAX_ROW_LENGTH - written - block - SEQUENCE_SPACER.length() - cumulativeCount.length();
            for (int i = 0; i < gap; i++)
                file.write(' ');

            file.write(cumulativeCount);

            file.write('\n');
            charactersWritten += MAX_ROW_LENGTH + 1;
        }

        return charactersWritten;
    }


    private int writeTermination() throws IOException {
        file.write("//\n");
        return 3;
    }
}
