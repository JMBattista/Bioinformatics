package com.vitreoussoftware.bioinformatics.sequence.io.writer.fastq;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.io.writer.SequenceStreamWriter;

import java.io.FileWriter;
import java.io.IOException;

/**
 * File stream reader for FASTQ data files
 * @author John
 *
 */
public final class FastqFileStreamWriter implements SequenceStreamWriter
{

    /**
     * The FASTQ file
     */
    private FileWriter file;

    /**
     * Create a FASTQ File Stream Reader for the given file
     * @param file the file to run on
     */
    private FastqFileStreamWriter(FileWriter file)
    {
        this.file = file;
    }

    /**
     * Create an input stream for FASTQ file format
     * @param fileName the FASTQ file
     * @return the input stream
     * @throws java.io.FileNotFoundException the specified file was not found
     */
    public static SequenceStreamWriter create(String fileName) throws IOException
    {
        FileWriter file = new FileWriter(fileName);
        return new FastqFileStreamWriter(file);
    }


    @Override
    public int write(Sequence sequence) throws IOException {
        int charactersWritten = 0;
        charactersWritten += writeMetadata(sequence.getMetadata());
        charactersWritten += writeSequenceData(sequence.toString());
        charactersWritten += writeComments(sequence.getMetadata());
        charactersWritten += writeQuality(sequence.toString());

        return charactersWritten;
    }

    private int writeMetadata(String metadata) throws IOException {
        file.write("@");
        file.write(metadata);
        file.write("\n");

        return metadata.length() + 2;
    }

    private int writeSequenceData(String sequenceData) throws IOException {
        int charactersWritten = 0;

        for (int start =  0; start < sequenceData.length(); start += 80) {
            int dist = 80;
            if (start + 80 > sequenceData.length())
                dist = sequenceData.length() - start;

            String row = sequenceData.substring(start, start + dist);
            file.write(row);
            file.write("\n");
            charactersWritten += dist + 1;
        }

        return charactersWritten;
    }

    private int writeComments(String metadata) throws IOException {
        file.write("+");
        file.write(metadata);
        file.write("\n");

        return metadata.length() + 2;
    }

    private int writeQuality(String sequenceData) throws IOException {
        int charactersWritten = 0;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 80; i++)
            sb.append("I");
        String quality = sb.toString();

        for (int start =  0; start < sequenceData.length(); start += 80) {
            if (start + 80 > sequenceData.length()) {
                int dist = sequenceData.length() - start;
                file.write(quality.substring(0, dist));
                charactersWritten += dist;
            }
            else
            {
                file.write(quality);
                charactersWritten += 80;
            }

            file.write("\n");
            charactersWritten ++;
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
