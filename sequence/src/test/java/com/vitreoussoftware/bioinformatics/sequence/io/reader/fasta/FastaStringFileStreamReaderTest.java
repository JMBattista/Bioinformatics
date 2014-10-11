package com.vitreoussoftware.bioinformatics.sequence.io.reader.fasta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.*;

import com.vitreoussoftware.bioinformatics.sequence.io.FastaData;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader;
import org.javatuples.Pair;
import org.junit.Test;

/**
 * Test the FastaFileStreamReader class
 * @author John
 *
 */
public class FastaStringFileStreamReaderTest {
    /**
     * The path where the FASTA test files can be found.
     */
    private static final String FASTA_PATH = "build/resources/test/Fasta/";
    /**
     * FASTA file with enough data that pages must be performed
     */
    private static final String ALTERNATE_FASTA = "alternate.fasta";
    /**
     * FASTA file with enough data that pages must be performed
     */
    private static final String SSU_PARC_GAPPED_FASTA = "SSUParc_Gapped.fasta";
    /**
     * FASTA file with enough data that pages must be performed
     */
    private static final String SSU_PARC_NOSPACE_FASTA = "SSUParc_NoSpace.fasta";
    /**
     * FASTA file with enough data that pages must be performed
     */
    private static final String SSU_PARC_BIG_FASTA = "SSUParc_Big.fasta";
    /**
     * FASTA file with enough data that pages must be performed
     */
    private static final String SSU_PARC_PAGED_FASTA = "SSUParc_Paged.fasta";
    /**
     * FASTA file with only a small amount of data
     */
    private static final String SSU_PARC_SIMPLE_FASTA = "SSUParc_Simple.fasta";
    /**
     * FASTA file with three full records
     */
    private static final String SSU_PARC_EXAMPLE_FASTA = "SSUParc_Example.fasta";
    /**
     * FASTA file with three full records
     */
    private static final String COMPLEX_FASTA = "ComplexExamples.fasta";

    /**
     * Create a StringStreamReader for the Simple FASTA test file
     * @return the StringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static StringStreamReader getSimpleFastaReader()
            throws FileNotFoundException {
        return FastaStringFileStreamReader.create(FASTA_PATH + SSU_PARC_SIMPLE_FASTA);
    }

    /**
     * Create a StringStreamReader for the Simple FASTA test file
     * @return the StringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static StringStreamReader getAlternateFastaReader()
            throws FileNotFoundException {
        return FastaStringFileStreamReader.create(FASTA_PATH + ALTERNATE_FASTA);
    }

    /**
     * Create a StringStreamReader for the Example FASTA test file
     * @return the StringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static StringStreamReader getExampleFastaReader()
            throws FileNotFoundException {
        return FastaStringFileStreamReader.create(FASTA_PATH + SSU_PARC_EXAMPLE_FASTA);
    }

    /**
     * Create a StringStreamReader for the Example FASTA test file
     * @return the StringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static StringStreamReader getComplexFastaReader()
            throws FileNotFoundException {
        return FastaStringFileStreamReader.create(FASTA_PATH + COMPLEX_FASTA);
    }

    /**
     * Create a StringStreamReader for the Paged FASTA test file
     * @return the StringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static StringStreamReader getPagedFastaReader()
            throws FileNotFoundException {
        return FastaStringFileStreamReader.create(FASTA_PATH + SSU_PARC_PAGED_FASTA);
    }

    /**
     * Create a StringStreamReader for the Paged FASTA test file
     * @return the StringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static StringStreamReader getPagedFastaReader(int pagingSize)
            throws FileNotFoundException {
        return FastaStringFileStreamReader.create(FASTA_PATH + SSU_PARC_PAGED_FASTA, pagingSize);
    }

    /**
     * Create a StringStreamReader for the Gapged FASTA test file
     * @return the StringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static StringStreamReader getGappedFastaReader()
            throws FileNotFoundException {
        return FastaStringFileStreamReader.create(FASTA_PATH + SSU_PARC_GAPPED_FASTA);
    }

    /**
     * Create a StringStreamReader for the NoSpace FASTA test file
     * @return the StringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static StringStreamReader getNoSpaceFastaReader()
            throws FileNotFoundException {
        return FastaStringFileStreamReader.create(FASTA_PATH + SSU_PARC_NOSPACE_FASTA);
    }

    /**
     * Create a StringStreamReader for the Big FASTA test file
     * @return the StringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static StringStreamReader getBigFastaReader()
            throws FileNotFoundException {
        return FastaStringFileStreamReader.create(FASTA_PATH + SSU_PARC_BIG_FASTA);
    }

    /**
	 * Create a FastaFileStreamReader
	 * @throws IOException 
	 */
	@Test
	public void testCreate() throws IOException {
//        OutputStreamWriter stream = new OutputStreamWriter(new FileOutputStream(new File("find the path")));
//        stream.write("test");
//        stream.close();
		FastaStringFileStreamReader.create(FASTA_PATH + SSU_PARC_EXAMPLE_FASTA);
	}
	
	/**
	 * Create a FastaFileStreamReader
	 * @throws FileNotFoundException 
	 */
	@Test(expected=FileNotFoundException.class)
	public void testCreate_notFound() throws FileNotFoundException {
		FastaStringFileStreamReader.create("Z:/bobtheexamplefileisnothere.fasta");
	}
	
	/**
	 * Create a FastaFileStreamReader
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testCreate_notFoundAutoCloseable() throws FileNotFoundException {
		try (StringStreamReader reader = FastaStringFileStreamReader.create("Z:/bobtheexamplefileisnothere.fasta"))
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
	 * @throws IOException 
	 */
	@Test
	public void testReadRecord_simple() throws IOException {
		StringStreamReader reader = getSimpleFastaReader();
		
		assertEquals(FastaData.getRecordSimple(), reader.next().getValue1());
	}
	
	/**
	 * Read a record from the reader
	 * @throws IOException 
	 */
	@Test
	public void testReadRecord_example1() throws IOException {
		StringStreamReader reader = getExampleFastaReader();
		
		assertEquals(FastaData.getRecord1(), reader.next().getValue1());
	}
	
	/**
	 * Read a second record from the reader
	 * @throws IOException 
	 */
	@Test
	public void testReadRecord_example2() throws IOException {
		StringStreamReader reader = getExampleFastaReader();
		
		assertEquals(FastaData.getRecord1(), reader.next().getValue1());
		assertEquals(FastaData.getRecord2(), reader.next().getValue1());
	}
	
	/**
	 * Read a third record from the reader
	 * @throws IOException 
	 */
	@Test
	public void testReadRecord_example3() throws IOException {
		StringStreamReader reader = getExampleFastaReader();
		
		assertEquals(FastaData.getRecord1(), reader.next().getValue1());
		assertEquals(FastaData.getRecord2(), reader.next().getValue1());
		assertEquals(FastaData.getRecord3(), reader.next().getValue1());
	}

    /**
     * Read a third record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecord_alternate() throws IOException {
        StringStreamReader reader = getAlternateFastaReader();

        assertEquals(FastaData.getAlternate1(), reader.next().getValue1());
        assertEquals(FastaData.getAlternate2(), reader.next().getValue1());
        assertEquals(FastaData.getAlternate3(), reader.next().getValue1());
    }
	
	/**
	 * Read a third record from the reader
	 * @throws Exception 
	 */
	@Test
	public void testReadRecord_autoCloseable() throws Exception {
		try (StringStreamReader reader	= getExampleFastaReader())
		{
			assertEquals(FastaData.getRecord1(), reader.next().getValue1());
			assertEquals(FastaData.getRecord2(), reader.next().getValue1());
			assertEquals(FastaData.getRecord3(), reader.next().getValue1());
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
        try (StringStreamReader reader	= getGappedFastaReader())
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
        try (StringStreamReader reader	= getNoSpaceFastaReader())
        {
            assertNotNull(reader.next().getValue1());
            assertNotNull(reader.next().getValue1());
            assertNotNull(reader.next().getValue1());
        } catch (Exception e) {
            fail("Should not have hit an exception from reading three records from no space\n" + e.getMessage());
        }
    }
	
	/**
	 * Read a third record from the reader
	 * @throws IOException 
	 */
	@Test
	public void testReadRecord_paged() throws IOException {
		StringStreamReader reader = getPagedFastaReader();

        int index = 0;
		while (reader.hasNext())
		{	
			assertEquals(index+0 + " failed to parse", FastaData.getRecord1(), reader.next().getValue1());
            assertEquals(index+1 + " failed to parse", FastaData.getRecord2(), reader.next().getValue1());
            assertEquals(index+2 + " failed to parse", FastaData.getRecord3(), reader.next().getValue1());
            index += 3;
		}

        assertEquals("The number of records was not correct", 81, index);
	}

    /**
     * Read a third record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecords_paged1() throws IOException {
        StringStreamReader reader = getPagedFastaReader(1);

        int index = 0;
        while (reader.hasNext())
        {
            assertEquals(index+0 + " failed to parse", FastaData.getRecord1(), reader.next().getValue1());
            assertEquals(index+1 + " failed to parse", FastaData.getRecord2(), reader.next().getValue1());
            assertEquals(index+2 + " failed to parse", FastaData.getRecord3(), reader.next().getValue1());
            index += 3;
        }

        assertEquals("The number of records was not correct", 81, index);
    }

    /**
     * Read a third record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecords_paged10() throws IOException {
        StringStreamReader reader = getPagedFastaReader(10);

        int index = 0;
        while (reader.hasNext())
        {
            assertEquals(index+0 + " failed to parse", FastaData.getRecord1(), reader.next().getValue1());
            assertEquals(index+1 + " failed to parse", FastaData.getRecord2(), reader.next().getValue1());
            assertEquals(index+2 + " failed to parse", FastaData.getRecord3(), reader.next().getValue1());
            index += 3;
        }

        assertEquals("The number of records was not correct", 81, index);
    }

    /**
     * Read a third record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecords_paged100() throws IOException {
        StringStreamReader reader = getPagedFastaReader(100);

        int index = 0;
        while (reader.hasNext())
        {
            assertEquals(index+0 + " failed to parse", FastaData.getRecord1(), reader.next().getValue1());
            assertEquals(index+1 + " failed to parse", FastaData.getRecord2(), reader.next().getValue1());
            assertEquals(index+2 + " failed to parse", FastaData.getRecord3(), reader.next().getValue1());
            index += 3;
        }

        assertEquals("The number of records was not correct", 81, index);
    }

    /**
     * Read a record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecord_complex1() throws IOException {
        StringStreamReader reader = getComplexFastaReader();

        Pair<String, String> next = reader.next();
        assertEquals(FastaData.getFastaMultiLineDescriptionMetadata(), next.getValue0());
        assertEquals(FastaData.getFastaMultiLineDescription(), next.getValue1());
    }

    /**
     * Read a second record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecord_complex2() throws IOException {
        StringStreamReader reader = getComplexFastaReader();

        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(FastaData.getFastaTerminatedMetadata(), next.getValue0());
        assertEquals(FastaData.getFastaTerminated(), next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecord_complex3() throws IOException {
        StringStreamReader reader = getComplexFastaReader();

        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(FastaData.getFastaLargeHeaderMetadata(), next.getValue0());
        assertEquals(FastaData.getFastaLargeHeader(), next.getValue1());
    }
}
