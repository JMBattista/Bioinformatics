package com.vitreoussoftware.bioinformatics.sequence.io.writer.fasta;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.io.FastaData;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.fasta.FastaStringFileStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.writer.SequenceStreamWriter;
import com.vitreoussoftware.bioinformatics.sequence.io.writer.SequenceStreamWriter;
import com.vitreoussoftware.bioinformatics.sequence.io.writer.fasta.FastaStringFileStreamWriter;
import org.javatuples.Pair;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Test the FastaFileStreamWriter class
 * @author John
 *
 */
public class FastaStringFileStreamWriterTest {

    private static final String WRITER_TEST_FILE = "fastatestwriter.fasta";

    /**
     * Create a SequenceStreamWriter to write the test file to
     * @return the SequenceStreamWriter
     * @throws java.io.IOException the test file could not be found
     */
    public static SequenceStreamWriter getFastaWriter() throws IOException {
        return FastaStringFileStreamWriter.create(WRITER_TEST_FILE);
    }


    /**
     * Create a SequenceStreamWriter for the Big FASTA test file
     * @return the SequenceStreamReader
     * @throws java.io.IOException the test file could not be found
     */
    public static File writeSequence(Sequence sequence) throws IOException {
        try (SequenceStreamWriter writer = getFastaWriter()) {
            writer.write(sequence);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        return new File(WRITER_TEST_FILE);
    }


    /**
	 * Create a FastaFileStreamWriter
	 * @throws java.io.IOException
	 */
	@Test
	public void testCreate() throws IOException {
		FastaStringFileStreamWriter.create(WRITER_TEST_FILE);
	}

	/**
	 * Create a FastaFileStreamWriter
	 * @throws java.io.IOException
	 */
	@Test(expected=IOException.class)
	public void testCreate_notFound() throws IOException {
		FastaStringFileStreamWriter.create("Z:/bobtheexamplefileisnothere.fasta");
	}

	/**
	 * Create a FastaFileStreamWriter
	 * @throws java.io.IOException
	 */
	@Test
	public void testCreate_notFoundAutoCloseable() throws IOException {
		try (SequenceStreamWriter writer = FastaStringFileStreamWriter.create("Z:/bobtheexamplefileisnothere.fasta"))
		{
			fail("This should not be reachable");
		}
		catch (IOException e)
		{
			// do nothing since this means we passed
		}
		catch (Exception e)
		{
			fail("We should have been caught by the more specific exception in front of this.");
		}
	}

	/**
	 * Read a record from the writer
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_simple() throws IOException {
		writeSequence(FastaData.getSimpleSequence());
	}

	/**
	 * Read a record from the writer
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_example1() throws IOException {
		SequenceStreamWriter writer = getFastaWriter();

		assertEquals(record1, writer.next().getValue1());
	}

	/**
	 * Read a second record from the writer
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_example2() throws IOException {
		SequenceStreamWriter writer = getFastaWriter();

		assertEquals(record1, writer.next().getValue1());
		assertEquals(record2, writer.next().getValue1());
	}

	/**
	 * Read a third record from the writer
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_example3() throws IOException {
		SequenceStreamWriter writer = getFastaWriter();

		assertEquals(record1, writer.next().getValue1());
		assertEquals(record2, writer.next().getValue1());
		assertEquals(record3, writer.next().getValue1());
	}

    /**
     * Read a third record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_alternate() throws IOException {
        SequenceStreamWriter writer = getFastaWriter();

        assertEquals(alternate1, writer.next().getValue1());
        assertEquals(alternate2, writer.next().getValue1());
        assertEquals(alternate3, writer.next().getValue1());
    }

	/**
	 * Read a third record from the writer
	 * @throws Exception
	 */
	@Test
	public void testReadRecord_autoCloseable() throws Exception {
		try (SequenceStreamWriter writer	= getFastaWriter())
		{
			assertEquals(record1, writer.next().getValue1());
			assertEquals(record2, writer.next().getValue1());
			assertEquals(record3, writer.next().getValue1());
		} catch (Exception e) {
			fail("Should not have hit an exception from the three");
		}
	}

    /**
     * Read a third record from the writer
     * @throws Exception
     */
    @Test
    public void testReadRecords_gapped() throws Exception {
        try (SequenceStreamWriter writer	= getFastaWriter())
        {
            assertNotNull(writer.next().getValue1());
            assertNotNull(writer.next().getValue1());
            assertNotNull(writer.next().getValue1());
        } catch (Exception e) {
            fail("Should not have hit an exception from reading three records from gapped");
        }
    }

    /**
     * Read a third record from the writer
     * @throws Exception
     */
    @Test
    public void testReadRecords_noSpace() throws Exception {
        try (SequenceStreamWriter writer	= getFastaWriter())
        {
            assertNotNull(writer.next().getValue1());
            assertNotNull(writer.next().getValue1());
            assertNotNull(writer.next().getValue1());
        } catch (Exception e) {
            fail("Should not have hit an exception from reading three records from no space\n" + e.getMessage());
        }
    }

	/**
	 * Read a third record from the writer
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_paged() throws IOException {
		SequenceStreamWriter writer = getFastaWriter();

        int index = 0;
		while (writer.hasNext())
		{
			assertEquals(index+0 + " failed to parse", record1, writer.next().getValue1());
            assertEquals(index+1 + " failed to parse", record2, writer.next().getValue1());
            assertEquals(index+2 + " failed to parse", record3, writer.next().getValue1());
            index += 3;
		}

        assertEquals("The number of records was not correct", 81, index);
	}

    /**
     * Read a third record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecords_paged1() throws IOException {
        SequenceStreamWriter writer = getFastaWriter(1);

        int index = 0;
        while (writer.hasNext())
        {
            assertEquals(index+0 + " failed to parse", record1, writer.next().getValue1());
            assertEquals(index+1 + " failed to parse", record2, writer.next().getValue1());
            assertEquals(index+2 + " failed to parse", record3, writer.next().getValue1());
            index += 3;
        }

        assertEquals("The number of records was not correct", 81, index);
    }

    /**
     * Read a third record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecords_paged10() throws IOException {
        SequenceStreamWriter writer = getFastaWriter(10);

        int index = 0;
        while (writer.hasNext())
        {
            assertEquals(index+0 + " failed to parse", record1, writer.next().getValue1());
            assertEquals(index+1 + " failed to parse", record2, writer.next().getValue1());
            assertEquals(index+2 + " failed to parse", record3, writer.next().getValue1());
            index += 3;
        }

        assertEquals("The number of records was not correct", 81, index);
    }

    /**
     * Read a third record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecords_paged100() throws IOException {
        SequenceStreamWriter writer = getFastaWriter(100);

        int index = 0;
        while (writer.hasNext())
        {
            assertEquals(index+0 + " failed to parse", record1, writer.next().getValue1());
            assertEquals(index+1 + " failed to parse", record2, writer.next().getValue1());
            assertEquals(index+2 + " failed to parse", record3, writer.next().getValue1());
            index += 3;
        }

        assertEquals("The number of records was not correct", 81, index);
    }

    /**
     * Read a record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex1() throws IOException {
        SequenceStreamWriter writer = getFastaWriter();

        Pair<String, String> next = writer.next();
        assertEquals(fastaMultiLineDescriptionMetadata, next.getValue0());
        assertEquals(fastaMultiLineDescriptionSequence, next.getValue1());
    }

    /**
     * Read a second record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex2() throws IOException {
        SequenceStreamWriter writer = getFastaWriter();

        writer.next();
        Pair<String, String> next = writer.next();
        assertEquals(fastaTerminatedMetadata, next.getValue0());
        assertEquals(fastaTerminatedSequence, next.getValue1());
    }

    /**
     * Read a third record from the writer
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex3() throws IOException {
        SequenceStreamWriter writer = getFastaWriter();

        writer.next();
        writer.next();
        Pair<String, String> next = writer.next();
        assertEquals(fastaLargeHeaderMetadata, next.getValue0());
        assertEquals(fastaLargeHeaderSequence, next.getValue1());
    }
}
