package com.vitreoussoftware.bioinformatics.sequence.io.reader.fastq;

import com.vitreoussoftware.bioinformatics.sequence.io.FastqData;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader;
import org.javatuples.Pair;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Test the FastqFileStreamReader class
 * @author John
 *
 */
public class FastqStringFileStreamReaderTest {
    /**
     * The path where the FASTA test files can be found.
     */
    private static final String FASTA_PATH = "target/test/sequence/Fastq/";

    /**
     * FASTA file with enough data that pages must be performed
     */
    private static final String ALTERNATE_FASTA = "alternate.fastq";

    /**
     * FASTA file with enough data that pages must be performed
     */
    private static final String SSU_PARC_GAPPED_FASTA = "SSUParc_Gapped.fastq";

    /**
     * FASTA file with enough data that pages must be performed
     */
    private static final String SSU_PARC_NOSPACE_FASTA = "SSUParc_NoSpace.fastq";

    /**
	 * FASTA file with enough data that pages must be performed
	 */
	private static final String SSU_PARC_PAGED_FASTA = "SSUParc_Paged.fastq";

	/**
	 * FASTA file with only a small amount of data
	 */
	private static final String SSU_PARC_SIMPLE_FASTA = "SSUParc_Simple.fastq";

	/**
	 * FASTA file with three full records
	 */
	private static final String SSU_PARC_EXAMPLE_FASTA = "SSUParc_Example.fastq";

    /**
     * FASTA file with three full records
     */
    private static final String COMPLEX_FASTA = "ComplexExamples.fastq";

    /**
     * Create a StringStreamReader for the Simple FASTA test file
     * @return the StringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static StringStreamReader getSimpleFastqReader()
            throws FileNotFoundException {
        return FastqStringFileStreamReader.create(FastqStringFileStreamReaderTest.FASTA_PATH + FastqStringFileStreamReaderTest.SSU_PARC_SIMPLE_FASTA);
    }

    /**
     * Create a StringStreamReader for the Simple FASTA test file
     * @return the StringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static StringStreamReader getAlternateFastqReader()
            throws FileNotFoundException {
        return FastqStringFileStreamReader.create(FastqStringFileStreamReaderTest.FASTA_PATH + FastqStringFileStreamReaderTest.ALTERNATE_FASTA);
    }
    /**
     * Create a StringStreamReader for the Example FASTA test file
     * @return the StringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static StringStreamReader getExampleFastqReader()
            throws FileNotFoundException {
        return FastqStringFileStreamReader.create(FastqStringFileStreamReaderTest.FASTA_PATH + FastqStringFileStreamReaderTest.SSU_PARC_EXAMPLE_FASTA);
    }

    /**
     * Create a StringStreamReader for the Example FASTA test file
     * @return the StringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static StringStreamReader getComplexFastqReader()
            throws FileNotFoundException {
        return FastqStringFileStreamReader.create(FastqStringFileStreamReaderTest.FASTA_PATH + FastqStringFileStreamReaderTest.COMPLEX_FASTA);
    }

    /**
     * Create a StringStreamReader for the Paged FASTA test file
     * @return the StringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static StringStreamReader getPagedFastqReader()
            throws FileNotFoundException {
        return FastqStringFileStreamReader.create(FastqStringFileStreamReaderTest.FASTA_PATH + FastqStringFileStreamReaderTest.SSU_PARC_PAGED_FASTA);
    }

    /**
     * Create a StringStreamReader for the Paged FASTA test file
     * @return the StringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static StringStreamReader getPagedFastqReader(int pagingSize)
            throws FileNotFoundException {
        return FastqStringFileStreamReader.create(FastqStringFileStreamReaderTest.FASTA_PATH + FastqStringFileStreamReaderTest.SSU_PARC_PAGED_FASTA, pagingSize);
    }

    /**
     * Create a StringStreamReader for the Gapged FASTA test file
     * @return the StringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static StringStreamReader getGappedFastqReader()
            throws FileNotFoundException {
        return FastqStringFileStreamReader.create(FastqStringFileStreamReaderTest.FASTA_PATH + FastqStringFileStreamReaderTest.SSU_PARC_GAPPED_FASTA);
    }

    /**
     * Create a StringStreamReader for the NoSpace FASTA test file
     * @return the StringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static StringStreamReader getNoSpaceFastqReader()
            throws FileNotFoundException {
        return FastqStringFileStreamReader.create(FastqStringFileStreamReaderTest.FASTA_PATH + FastqStringFileStreamReaderTest.SSU_PARC_NOSPACE_FASTA);
    }


    /**
	 * Create a FastqFileStreamReader
	 * @throws java.io.IOException
	 */
	@Test
	public void testCreate() throws IOException {
//        OutputStreamWriter stream = new OutputStreamWriter(new FileOutputStream(new File("find the path")));
//        stream.write("test");
//        stream.close();
		FastqStringFileStreamReader.create(FASTA_PATH + SSU_PARC_EXAMPLE_FASTA);
	}

	/**
	 * Create a FastqFileStreamReader
	 * @throws java.io.FileNotFoundException
	 */
	@Test(expected=FileNotFoundException.class)
	public void testCreate_notFound() throws FileNotFoundException {
		FastqStringFileStreamReader.create("Z:/bobtheexamplefileisnothere.fastq");
	}

	/**
	 * Create a FastqFileStreamReader
	 * @throws java.io.FileNotFoundException
	 */
	@Test
	public void testCreate_notFoundAutoCloseable() throws FileNotFoundException {
		try (StringStreamReader reader = FastqStringFileStreamReader.create("Z:/bobtheexamplefileisnothere.fastq"))
		{
			fail("This should not be reachable");
		}
		catch (FileNotFoundException e)
		{
			// do nothing since this means we passed
		}
		catch (Exception e)
		{
			fail("We should have been caught by the more specific exception in front of this.");
		}
	}

	/**
	 * Read a record from the reader
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_simple() throws IOException {
		StringStreamReader reader = getSimpleFastqReader();

		assertEquals(FastqData.getRecordSimple(), reader.next().getValue1());
	}

	/**
	 * Read a record from the reader
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_example1() throws IOException {
		StringStreamReader reader = getExampleFastqReader();

		assertEquals(FastqData.getRecord1(), reader.next().getValue1());
	}

	/**
	 * Read a second record from the reader
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_example2() throws IOException {
		StringStreamReader reader = getExampleFastqReader();

		assertEquals(FastqData.getRecord1(), reader.next().getValue1());
		assertEquals(FastqData.getRecord2(), reader.next().getValue1());
	}

	/**
	 * Read a third record from the reader
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_example3() throws IOException {
		StringStreamReader reader = getExampleFastqReader();

		assertEquals(FastqData.getRecord1(), reader.next().getValue1());
		assertEquals(FastqData.getRecord2(), reader.next().getValue1());
		assertEquals(FastqData.getRecord3(), reader.next().getValue1());
	}

	/**
	 * Read a third record from the reader
	 * @throws Exception
	 */
	@Test
	public void testReadRecord_autoCloseable() throws Exception {
		try (StringStreamReader reader	= getExampleFastqReader())
		{
			assertEquals(FastqData.getRecord1(), reader.next().getValue1());
			assertEquals(FastqData.getRecord2(), reader.next().getValue1());
			assertEquals(FastqData.getRecord3(), reader.next().getValue1());
		} catch (Exception e) {
			fail("Should not have hit an exception from the three");
		}
	}

    /**
     * Read a third record from the reader
     * @throws Exception
     */
    @Test
    public void testReadRecords_gapped() throws Exception {
        try (StringStreamReader reader	= getGappedFastqReader())
        {
            assertNotNull(reader.next().getValue1());
            assertNotNull(reader.next().getValue1());
            assertNotNull(reader.next().getValue1());
        } catch (Exception e) {
            fail("Should not have hit an exception from reading three records from gapped");
        }
    }

    /**
     * Read a third record from the reader
     * @throws Exception
     */
    @Test
    public void testReadRecords_noSpace() throws Exception {
        try (StringStreamReader reader	= getNoSpaceFastqReader())
        {
            assertNotNull(reader.next().getValue1());
            assertNotNull(reader.next().getValue1());
            assertNotNull(reader.next().getValue1());
        } catch (Exception e) {
            fail("Should not have hit an exception from reading three records from no space");
        }
    }

	/**
	 * Read a third record from the reader
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_paged() throws IOException {
		StringStreamReader reader = getPagedFastqReader();

        int index = 0;
		while (reader.hasNext())
		{
			assertEquals(index+0 + " failed to parse", FastqData.getRecord1(), reader.next().getValue1());
            assertEquals(index+1 + " failed to parse", FastqData.getRecord2(), reader.next().getValue1());
            assertEquals(index+2 + " failed to parse", FastqData.getRecord3(), reader.next().getValue1());
            index += 3;
		}

        assertEquals("The number of records was not correct", 81, index);
	}

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecords_paged1() throws IOException {
        StringStreamReader reader = getPagedFastqReader(1);

        int index = 0;
        while (reader.hasNext())
        {
            assertEquals(index+0 + " failed to parse", FastqData.getRecord1(), reader.next().getValue1());
            assertEquals(index+1 + " failed to parse", FastqData.getRecord2(), reader.next().getValue1());
            assertEquals(index+2 + " failed to parse", FastqData.getRecord3(), reader.next().getValue1());
            index += 3;
        }

        assertEquals("The number of records was not correct", 81, index);
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecords_paged10() throws IOException {
        StringStreamReader reader = getPagedFastqReader(10);

        int index = 0;
        while (reader.hasNext())
        {
            if (index >= 78)
                index = index -1 + 1;
            assertEquals(index+0 + " failed to parse", FastqData.getRecord1(), reader.next().getValue1());
            assertEquals(index+1 + " failed to parse", FastqData.getRecord2(), reader.next().getValue1());
            assertEquals(index+2 + " failed to parse", FastqData.getRecord3(), reader.next().getValue1());
            index += 3;
        }

        assertEquals("The number of records was not correct", 81, index);
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecords_paged100() throws IOException {
        StringStreamReader reader = getPagedFastqReader(100);

        int index = 0;
        while (reader.hasNext())
        {
            assertEquals(index+0 + " failed to parse", FastqData.getRecord1(), reader.next().getValue1());
            assertEquals(index+1 + " failed to parse", FastqData.getRecord2(), reader.next().getValue1());
            assertEquals(index+2 + " failed to parse", FastqData.getRecord3(), reader.next().getValue1());
            index += 3;
        }

        assertEquals("The number of records was not correct", 81, index);
    }

    /**
     * Read a record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex1() throws IOException {
        StringStreamReader reader = getComplexFastqReader();

        Pair<String, String> next = reader.next();
        assertEquals(FastqData.getComplex1_metadata(), next.getValue0());
        assertEquals(FastqData.getComplex1_sequence(), next.getValue1());
    }

    /**
     * Read a second record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex2() throws IOException {
        StringStreamReader reader = getComplexFastqReader();

        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(FastqData.getComplex2_metadata(), next.getValue0());
        assertEquals(FastqData.getComplex2_sequence(), next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex3() throws IOException {
        StringStreamReader reader = getComplexFastqReader();

        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(FastqData.getComplex3_metadata(), next.getValue0());
        assertEquals(FastqData.getComplex3_sequence(), next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex4() throws IOException {
        StringStreamReader reader = getComplexFastqReader();

        reader.next();
        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(FastqData.getComplex4_metadata(), next.getValue0());
        assertEquals(FastqData.getComplex4_sequence(), next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex5() throws IOException {
        StringStreamReader reader = getComplexFastqReader();

        reader.next();
        reader.next();
        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(FastqData.getComplex5_metadata(), next.getValue0());
        assertEquals(FastqData.getComplex5_sequence(), next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex6() throws IOException {
        StringStreamReader reader = getComplexFastqReader();

        reader.next();
        reader.next();
        reader.next();
        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(FastqData.getComplex6_metadata(), next.getValue0());
        assertEquals(FastqData.getComplex6_sequence(), next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex7() throws IOException {
        StringStreamReader reader = getComplexFastqReader();

        reader.next();
        reader.next();
        reader.next();
        reader.next();
        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(FastqData.getComplex7_metadata(), next.getValue0());
        assertEquals(FastqData.getComplex7_sequence(), next.getValue1());
    }
}
