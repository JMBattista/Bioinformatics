package com.vitreoussoftware.bioinformatics.sequence.io.writer;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.io.TestData;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.writer.fasta.FastaFileStreamWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Base tests for the {@link SequenceStreamWriter} class
 *
 * @author John 2016/10/15
 */
public abstract class SequenceStreamWriterIntegrationTestBase<T extends TestData> {
    protected T testData;

    @Before
    public void setup() {
        testData = getTestData();
    }

    /**
     * Get the {@link TestData} instance to use with this {@link SequenceStreamWriter}
     *
     * @return The {@link TestData} instance to use
     */
    protected abstract T getTestData();

    /**
     * Get the {@link SequenceStreamWriter} instance to use for these tests
     *
     * @param path The file path to use
     * @return The {@link SequenceStreamWriter}
     */
    protected abstract SequenceStreamWriter getWriter(String path) throws Exception;

    /**
     * Get the {@link SequenceStreamReader} instance to use with this {@link SequenceStreamWriter}
     *
     * @param path The file path to use
     * @return The {@link SequenceStreamReader}
     */
    protected abstract SequenceStreamReader getReader(String path) throws Exception;

    /**
     * Get the location of the test file to be used for these tests
     *
     * @return
     */
    protected abstract String getTestFile();

    /**
     * Gets the {@link SequenceStreamWriter} for the test file
     *
     * @return The {@link SequenceStreamWriter}
     */
    private SequenceStreamWriter getWriter() throws Exception {
        return getWriter(getTestFile());
    }

    /**
     * Gets the {@link SequenceStreamReader} for the test file
     *
     * @return The {@link SequenceStreamReader}
     */
    private SequenceStreamReader getReader() throws Exception {
        return getReader(getTestFile());
    }

    /**
     * Create a SequenceStreamWriter for the Big FASTA test file
     *
     * @return the SequenceStreamReader
     * @throws java.io.IOException the test file could not be found
     */
    protected File writeSequence(final Sequence sequence) throws Exception {
        try (SequenceStreamWriter writer = getWriter()) {
            writer.write(sequence);
        } catch (final Exception e) {
            e.printStackTrace();
            fail();
        }

        return new File(getTestFile());
    }

    /**
     * Create a SequenceStreamWriter for the Big FASTA test file
     *
     * @return the SequenceStreamReader
     */
    protected void writeAndCheckSequence(final Sequence expected) throws Exception {
        writeSequence(expected);
        final SequenceStreamReader reader = getReader();

        final Optional<Sequence> result = reader.next();
        assertTrue("Failed to read the written result", result.isPresent());
        assertEquals("The sequence was not the same once read back", expected, result.get());
        reader.close();
    }

    /**
     * Create a FastaFileStreamWriter
     */
    @Test
    public void testCreate() throws Exception {
        FastaFileStreamWriter.create(getTestFile());
    }

    /**
     * Create a FastaFileStreamWriter
     */
    @Test(expected = IOException.class)
    public void testCreateNotFound() throws Exception {
        FastaFileStreamWriter.create("Z:/bobtheexamplefileisnothere.fasta");
    }

    /**
     * Read a record from the writer
     */
    @Test
    public void testReadRecordSimple() throws Exception {
        writeAndCheckSequence(testData.getSimpleSequence());
    }

    /**
     * Read a record from the writer
     *
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecordExample1() throws Exception {
        writeAndCheckSequence(testData.getRealExample1Sequence());
    }

    /**
     * Read a second record from the writer
     *
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecordExample2() throws Exception {
        writeAndCheckSequence(testData.getRealExample2Sequence());
    }

    /**
     * Read a third record from the writer
     *
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecordExample3() throws Exception {
        writeAndCheckSequence(testData.getRealExample3Sequence());
    }

    /**
     * Read a third record from the writer
     *
     * @throws Exception
     */
    @Test
    public void testReadRecordAutoCloseable() throws Exception {
        try (SequenceStreamWriter writer = getWriter()) {
            writer.write(testData.getRealExample1Sequence());
            writer.write(testData.getRealExample2Sequence());
            writer.write(testData.getRealExample3Sequence());

        } catch (final Exception e) {
            fail("Exception writing the files");
        }

        try (SequenceStreamReader reader = getReader()) {
            assertEquals(testData.getRealExample1Sequence(), reader.next().get());
            assertEquals(testData.getRealExample2Sequence(), reader.next().get());
            assertEquals(testData.getRealExample3Sequence(), reader.next().get());
        }
    }
}
