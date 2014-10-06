package com.vitreoussoftware.bioinformatics.sequence.io.writer.fastq;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.io.FastqData;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.fastq.FastqSequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.fastq.FastqStringFileStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.writer.SequenceStreamWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Test the FastqFileStreamWriter class
 * @author John
 *
 */
public class FastqStringFileStreamWriterTest {

    private static final String WRITER_TEST_FILE = "target/Fastqtestwriter.Fastq";

    /**
     * Create a SequenceStreamWriter to write the test file to
     * @return the SequenceStreamWriter
     * @throws java.io.IOException the test file could not be found
     */
    public static SequenceStreamWriter getFastqWriter() throws Exception {
        return FastqStringFileStreamWriter.create(WRITER_TEST_FILE);
    }

    /**
     * Create a SequenceStreamWriter to write the test file to
     * @return the SequenceStreamWriter
     * @throws java.io.IOException the test file could not be found
     */
    public static SequenceStreamReader getFastqReader() throws Exception {
        return new FastqSequenceStreamReader(FastqStringFileStreamReader.create(WRITER_TEST_FILE));
    }


    /**
     * Create a SequenceStreamWriter for the Big Fastq test file
     * @return the SequenceStreamReader
     * @throws java.io.IOException the test file could not be found
     */
    public static File writeSequence(Sequence sequence) throws Exception {
        try (SequenceStreamWriter writer = getFastqWriter()) {
            writer.write(sequence);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        return new File(WRITER_TEST_FILE);
    }

    /**
     * Create a SequenceStreamWriter for the Big Fastq test file
     * @return the SequenceStreamReader
     */
    public static void writeAndCheckSequence(Sequence expected) throws Exception {
        final File written = writeSequence(expected);
        SequenceStreamReader reader = getFastqReader();

        Optional<Sequence> result = reader.next();
        assertTrue("Failed to read the written result", result.isPresent());
        assertEquals("The sequence was not the same once read back", expected, result.get());
        reader.close();
    }


    /**
     * Create a FastqFileStreamWriter
     * @throws java.io.IOException
     */
    @Test
    public void testCreate() throws Exception {
        FastqStringFileStreamWriter.create(WRITER_TEST_FILE);
    }

    /**
     * Create a FastqFileStreamWriter
     * @throws java.io.IOException
     */
    @Test(expected=IOException.class)
    public void testCreate_notFound() throws Exception {
        FastqStringFileStreamWriter.create("Z:/bobtheexamplefileisnothere.Fastq");
    }

    /**
     * Read a record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_simple() throws Exception {
        writeAndCheckSequence(FastqData.getSimpleSequence());
    }

    /**
     * Read a record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_example1() throws Exception {
        writeAndCheckSequence(FastqData.getRecord1Sequence());
    }

    /**
     * Read a second record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_example2() throws Exception {
        writeAndCheckSequence(FastqData.getRecord2Sequence());
    }

    /**
     * Read a third record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_example3() throws Exception {
        writeAndCheckSequence(FastqData.getRecord3Sequence());
    }

    /**
     * Read a third record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_alternate1() throws Exception {
        writeAndCheckSequence(FastqData.getAlternate1Sequence());
    }

    /**
     * Read a third record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_alternate2() throws Exception {
        writeAndCheckSequence(FastqData.getAlternate2Sequence());
    }

    /**
     * Read a third record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_alternate3() throws Exception {
        writeAndCheckSequence(FastqData.getAlternate3Sequence());
    }

    /**
     * Read a third record from the writer
     * @throws Exception
     */
    @Test
    public void testReadRecord_autoCloseable() throws Exception {
        try (SequenceStreamWriter writer	= getFastqWriter())
        {
            writer.write(FastqData.getRecord1Sequence());
            writer.write(FastqData.getRecord2Sequence());
            writer.write(FastqData.getRecord3Sequence());

        } catch (Exception e) {
            fail("Exception writing the files");
        }

        try (SequenceStreamReader reader = getFastqReader()) {
            assertEquals(FastqData.getRecord1Sequence(), reader.next().get());
            assertEquals(FastqData.getRecord2Sequence(), reader.next().get());
            assertEquals(FastqData.getRecord3Sequence(), reader.next().get());
        }
    }

    /**
     * Read a record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex1() throws Exception {
        writeAndCheckSequence(FastqData.getComplex1Sequence());
    }

    /**
     * Read a record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex2() throws Exception {
        writeAndCheckSequence(FastqData.getComplex2Sequence());
    }

    /**
     * Read a record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex3() throws Exception {
        writeAndCheckSequence(FastqData.getComplex3Sequence());
    }

    /**
     * Read a record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex4() throws Exception {
        writeAndCheckSequence(FastqData.getComplex4Sequence());
    }

    /**
     * Read a record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex5() throws Exception {
        writeAndCheckSequence(FastqData.getComplex5Sequence());
    }

    /**
     * Read a record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex6() throws Exception {
        writeAndCheckSequence(FastqData.getComplex6Sequence());
    }

    /**
     * Read a record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex7() throws Exception {
        writeAndCheckSequence(FastqData.getComplex7Sequence());
    }
}
