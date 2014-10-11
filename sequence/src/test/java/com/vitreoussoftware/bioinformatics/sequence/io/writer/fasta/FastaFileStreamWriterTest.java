package com.vitreoussoftware.bioinformatics.sequence.io.writer.fasta;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.io.FastaData;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.fasta.FastaSequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.fasta.FastaStringFileStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.writer.SequenceStreamWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Test the FastaFileStreamWriter class
 * @author John
 *
 */
public class FastaFileStreamWriterTest {

    private static final String WRITER_TEST_FILE = "build/fastatestwriter.fasta";

    /**
     * Create a SequenceStreamWriter to write the test file to
     * @return the SequenceStreamWriter
     * @throws java.io.IOException the test file could not be found
     */
    public static SequenceStreamWriter getFastaWriter() throws Exception {
        return FastaFileStreamWriter.create(WRITER_TEST_FILE);
    }

    /**
     * Create a SequenceStreamWriter to write the test file to
     * @return the SequenceStreamWriter
     * @throws java.io.IOException the test file could not be found
     */
    public static SequenceStreamReader getFastaReader() throws Exception {
        return new FastaSequenceStreamReader(FastaStringFileStreamReader.create(WRITER_TEST_FILE));
    }


    /**
     * Create a SequenceStreamWriter for the Big FASTA test file
     * @return the SequenceStreamReader
     * @throws java.io.IOException the test file could not be found
     */
    public static File writeSequence(Sequence sequence) throws Exception {
        try (SequenceStreamWriter writer = getFastaWriter()) {
            writer.write(sequence);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        return new File(WRITER_TEST_FILE);
    }

    /**
     * Create a SequenceStreamWriter for the Big FASTA test file
     * @return the SequenceStreamReader
     */
    public static void writeAndCheckSequence(Sequence expected) throws Exception {
        writeSequence(expected);
        SequenceStreamReader reader = getFastaReader();

        Optional<Sequence> result = reader.next();
        assertTrue("Failed to read the written result", result.isPresent());
        assertEquals("The sequence was not the same once read back", expected, result.get());
        reader.close();
    }


    /**
	 * Create a FastaFileStreamWriter
	 * @throws java.io.IOException
	 */
	@Test
	public void testCreate() throws Exception {
		FastaFileStreamWriter.create(WRITER_TEST_FILE);
	}

	/**
	 * Create a FastaFileStreamWriter
	 * @throws java.io.IOException
	 */
	@Test(expected=IOException.class)
	public void testCreate_notFound() throws Exception {
		FastaFileStreamWriter.create("Z:/bobtheexamplefileisnothere.fasta");
	}

	/**
	 * Read a record from the writer
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_simple() throws Exception {
		writeAndCheckSequence(FastaData.getSimpleSequence());
	}

	/**
	 * Read a record from the writer
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_example1() throws Exception {
		writeAndCheckSequence(FastaData.getRecord1Sequence());
	}

	/**
	 * Read a second record from the writer
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_example2() throws Exception {
        writeAndCheckSequence(FastaData.getRecord2Sequence());
	}

	/**
	 * Read a third record from the writer
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_example3() throws Exception {
        writeAndCheckSequence(FastaData.getRecord3Sequence());
	}

    /**
     * Read a third record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_alternate1() throws Exception {
        writeAndCheckSequence(FastaData.getAlternate1Sequence());
    }

    /**
     * Read a third record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_alternate2() throws Exception {
        writeAndCheckSequence(FastaData.getAlternate2Sequence());
    }

    /**
     * Read a third record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_alternate3() throws Exception {
        writeAndCheckSequence(FastaData.getAlternate3Sequence());
    }

	/**
	 * Read a third record from the writer
	 * @throws Exception
	 */
	@Test
	public void testReadRecord_autoCloseable() throws Exception {
		try (SequenceStreamWriter writer	= getFastaWriter())
		{
            writer.write(FastaData.getRecord1Sequence());
            writer.write(FastaData.getRecord2Sequence());
            writer.write(FastaData.getRecord3Sequence());

		} catch (Exception e) {
			fail("Exception writing the files");
		}

        try (SequenceStreamReader reader = getFastaReader()) {
            assertEquals(FastaData.getRecord1Sequence(), reader.next().get());
            assertEquals(FastaData.getRecord2Sequence(), reader.next().get());
            assertEquals(FastaData.getRecord3Sequence(), reader.next().get());
        }
	}

    /**
     * Read a record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex1() throws Exception {
        writeAndCheckSequence(FastaData.getFastaTerminatedSequence());
    }

    /**
     * Read a second record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex2() throws Exception {
        writeAndCheckSequence(FastaData.getFastaMultiLineDescriptionSequence());
    }

    /**
     * Read a third record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex3() throws Exception {
        writeAndCheckSequence(FastaData.getFastaLargeHeaderSequence());
    }
}
