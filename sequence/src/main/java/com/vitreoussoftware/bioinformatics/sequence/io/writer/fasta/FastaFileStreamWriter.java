package com.vitreoussoftware.bioinformatics.sequence.io.writer.fasta;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.io.writer.SequenceStreamWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * File stream reader for FASTA data files
 *
 * @author John
 */
public final class FastaFileStreamWriter implements SequenceStreamWriter {

    /**
     * The FASTA file
     */
    private final FileWriter file;

    /**
     * Create a FASTA File Stream Reader for the given file
     *
     * @param file the file to run on
     */
    private FastaFileStreamWriter(final FileWriter file) {
        this.file = file;
    }

    /**
     * Create an input stream for FASTA file format
     *
     * @param file the FASTA file
     * @return the input stream
     * @throws java.io.FileNotFoundException the specified file was not found
     */
    public static SequenceStreamWriter create(final File file) throws IOException {
        return new FastaFileStreamWriter(new FileWriter(file));
    }

    /**
     * Create an input stream for FASTA file format
     *
     * @param fileName the FASTA file
     * @return the input stream
     * @throws java.io.FileNotFoundException the specified file was not found
     */
    public static SequenceStreamWriter create(final String fileName) throws IOException {
        return create(new File(fileName));
    }


    @Override
    public int write(final Sequence sequence) throws IOException {
        int charactersWritten = 0;
        charactersWritten += writeMetadata(sequence.getMetadata());
        charactersWritten += writeSequenceData(sequence.toString());

        return charactersWritten;
    }

    private int writeMetadata(final String metadata) throws IOException {
        file.write(">");
        file.write(metadata);
        file.write("\n");

        return metadata.length() + 2;
    }

    private int writeSequenceData(final String sequenceData) throws IOException {
        int charactersWritten = 0;

        for (int start = 0; start < sequenceData.length(); start += 80) {
            int dist = 80;
            if (start + 80 > sequenceData.length())
                dist = sequenceData.length() - start;

            final String row = sequenceData.substring(start, start + dist);
            file.write(row);
            file.write("\n");
            charactersWritten += dist + 1;
        }

        return charactersWritten;
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


}
