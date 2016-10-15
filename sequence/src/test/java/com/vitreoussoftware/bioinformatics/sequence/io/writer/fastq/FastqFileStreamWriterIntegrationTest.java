package com.vitreoussoftware.bioinformatics.sequence.io.writer.fastq;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.io.FastqData;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.fastq.FastqSequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.fastq.FastqStringFileStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.writer.SequenceStreamWriter;
import com.vitreoussoftware.bioinformatics.sequence.io.writer.SequenceStreamWriterIntegrationTestBase;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Test the FastqFileStreamWriter class
 * @author John
 *
 */
public class FastqFileStreamWriterIntegrationTest extends SequenceStreamWriterIntegrationTestBase<FastqData> {
    private static final String WRITER_TEST_FILE = "build/fastqtestwriter.fastq";

    @Override
    protected FastqData getTestData() {
        return new FastqData();
    }

    @Override
    protected SequenceStreamWriter getWriter(String path) throws Exception {
        return FastqFileStreamWriter.create(path);
    }

    @Override
    protected SequenceStreamReader getReader(String path) throws Exception {
        return new FastqSequenceStreamReader(FastqStringFileStreamReader.create(path));
    }

    @Override
    protected String getTestFile() {
        return WRITER_TEST_FILE;
    }

    /**
     * Read a record from the writer
     */
    @Test
    public void testReadRecord_complex1() throws Exception {
        writeAndCheckSequence(testData.getComplex1Sequence());
    }

    /**
     * Read a record from the writer
     */
    @Test
    public void testReadRecord_complex2() throws Exception {
        writeAndCheckSequence(testData.getComplex2Sequence());
    }

    /**
     * Read a record from the writer
     */
    @Test
    public void testReadRecord_complex3() throws Exception {
        writeAndCheckSequence(testData.getComplex3Sequence());
    }

    /**
     * Read a record from the writer
     */
    @Test
    public void testReadRecord_complex4() throws Exception {
        writeAndCheckSequence(testData.getComplex4Sequence());
    }

    /**
     * Read a record from the writer
     */
    @Test
    public void testReadRecord_complex5() throws Exception {
        writeAndCheckSequence(testData.getComplex5Sequence());
    }

    /**
     * Read a record from the writer
     */
    @Test
    public void testReadRecord_complex6() throws Exception {
        writeAndCheckSequence(testData.getComplex6Sequence());
    }

    /**
     * Read a record from the writer
     */
    @Test
    public void testReadRecord_complex7() throws Exception {
        writeAndCheckSequence(testData.getComplex7Sequence());
    }
}
