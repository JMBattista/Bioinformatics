package com.vitreoussoftware.bioinformatics.sequence.io.writer.fasta;

import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.io.FastaData;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.fasta.FastaStringFileStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.writer.SequenceStreamWriter;
import com.vitreoussoftware.bioinformatics.sequence.io.writer.SequenceStreamWriterIntegrationTestBase;
import org.junit.Test;

/**
 * Test the FastaFileStreamWriter class
 * @author John
 *
 */
public class FastaFileStreamWriterIntegrationTest extends SequenceStreamWriterIntegrationTestBase<FastaData> {
    private static final String WRITER_TEST_FILE = "build/fastatestwriter.fasta";

    @Override
    protected FastaData getTestData() {
        return new FastaData();
    }

    @Override
    protected SequenceStreamWriter getWriter(String path) throws Exception {
        return FastaFileStreamWriter.create(path);
    }

    @Override
    protected SequenceStreamReader getReader(String path) throws Exception {
        final StringStreamReader reader = FastaStringFileStreamReader.create(path);
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
     * Read a third record from the writer
     */
    @Test
    public void testReadRecord_alternateStartingCharacter_1() throws Exception {
        writeAndCheckSequence(testData.getAlternateStartingCharacter1Sequence());
    }

    /**
     * Read a third record from the writer
     */
    @Test
    public void testReadRecord_alternateStartingCharacter_2() throws Exception {
        writeAndCheckSequence(testData.getAlternateStartingCharacter2Sequence());
    }

    /**
     * Read a third record from the writer
     */
    @Test
    public void testReadRecord_alternateStartingCharacter_3() throws Exception {
        writeAndCheckSequence(testData.getAlternateStartingCharacter3Sequence());
    }

    /**
     * Read a record from the writer
     */
    @Test
    public void testReadRecord_terminatedSequence() throws Exception {
        writeAndCheckSequence(testData.getFastaTerminatedSequence());
    }

    /**
     * Read a second record from the writer
     */
    @Test
    public void testReadRecord_multilineDescription() throws Exception {
        writeAndCheckSequence(testData.getFastaMultiLineDescriptionSequence());
    }

    /**
     * Read a third record from the writer
     */
    @Test
    public void testReadRecord_largeHeader() throws Exception {
        writeAndCheckSequence(testData.getFastaLargeHeaderSequence());
    }
}
