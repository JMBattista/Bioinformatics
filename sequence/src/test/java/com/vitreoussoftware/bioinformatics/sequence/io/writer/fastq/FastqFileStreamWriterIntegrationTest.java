package com.vitreoussoftware.bioinformatics.sequence.io.writer.fastq;

import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.io.FastqData;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.fastq.FastqStringFileStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.writer.SequenceStreamWriter;
import com.vitreoussoftware.bioinformatics.sequence.io.writer.SequenceStreamWriterIntegrationTestBase;
import org.junit.Test;

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
        final com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader reader = FastqStringFileStreamReader.create(path);
        return SequenceStreamReader.builder()
                .reader(reader)
                .factory(new FastaSequenceFactory())
                .build();
    }

    @Override
    protected String getTestFile() {
        return WRITER_TEST_FILE;
    }

    /**
     * Read a record from the writer
     */
    @Test
    public void testReadRecordComplex1() throws Exception {
        writeAndCheckSequence(testData.getComplex1Sequence());
    }

    /**
     * Read a record from the writer
     */
    @Test
    public void testReadRecordComplex2() throws Exception {
        writeAndCheckSequence(testData.getComplex2Sequence());
    }

    /**
     * Read a record from the writer
     */
    @Test
    public void testReadRecordComplex3() throws Exception {
        writeAndCheckSequence(testData.getComplex3Sequence());
    }

    /**
     * Read a record from the writer
     */
    @Test
    public void testReadRecordComplex4() throws Exception {
        writeAndCheckSequence(testData.getComplex4Sequence());
    }

    /**
     * Read a record from the writer
     */
    @Test
    public void testReadRecordComplex5() throws Exception {
        writeAndCheckSequence(testData.getComplex5Sequence());
    }

    /**
     * Read a record from the writer
     */
    @Test
    public void testReadRecordComplex6() throws Exception {
        writeAndCheckSequence(testData.getComplex6Sequence());
    }

    /**
     * Read a record from the writer
     */
    @Test
    public void testReadRecordComplex7() throws Exception {
        writeAndCheckSequence(testData.getComplex7Sequence());
    }
}
