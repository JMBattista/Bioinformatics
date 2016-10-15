package com.vitreoussoftware.bioinformatics.sequence.io.reader;

import com.vitreoussoftware.bioinformatics.sequence.io.TestData;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Base tests for the {@link StringStreamReader} class
 * @author John 2016/10/15
 *
 */
public abstract class StringFileStreamReaderIntegrationTestBase<T extends TestData> {
    protected T testData;

    @Before
    public void setup() {
        testData = getTestData();
    }

    /**
     * Get the {@link TestData} instance to use with this {@link StringStreamReader}
     * @return The {@link TestData} instance to use
     */
    protected abstract T getTestData();

    /**
     * Attempt to create a {@link StringStreamReader} from the path to test
     * @param path The path to the file
     * @return The {@link StringStreamReader} to test
     */
    protected abstract StringStreamReader getReader(String path) throws Exception;

    /**
     * Get a non-existent path for a file
     * @return a path to a file that does not exist
     */
    private String getNonExistantPath() {
        return "Z:/bobtheexamplefileisnothere.fasta";
    }

    /**
     * Test the creation of the {@link StringStreamReader} for a valid file path
	 */
	@Test
	public void testCreate() throws Exception {
		getReader(testData.getSimpleExamplePath());
	}

	/**
	 * Test the failure of creating a {@link StringStreamReader} for an invalid file path
	 */
	@Test
	public void testCreate_notFoundAutoCloseable() throws FileNotFoundException {
		try (StringStreamReader reader = getReader(getNonExistantPath()))
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
	 * Test reading a the 'simple' record value
     */
	@Test
	public void testReadRecord_simple() throws IOException {
		StringStreamReader reader = testData.getSimpleExampleReader();
		
		assertEquals(testData.getRecordSimple(), reader.next().getValue1());
	}
	
	/**
	 * Read a record from the reader
	 	 */
	@Test
	public void testReadRecord_example1() throws IOException {
		StringStreamReader reader = testData.getRealExamplesReader();
		
		assertEquals(testData.getRecord1(), reader.next().getValue1());
	}
	
	/**
	 * Read a second record from the reader
	 	 */
	@Test
	public void testReadRecord_example2() throws IOException {
		StringStreamReader reader = testData.getRealExamplesReader();
		
		assertEquals(testData.getRecord1(), reader.next().getValue1());
		assertEquals(testData.getRecord2(), reader.next().getValue1());
	}
	
	/**
	 * Read a third record from the reader
	 	 */
	@Test
	public void testReadRecord_example3() throws IOException {
		StringStreamReader reader = testData.getRealExamplesReader();
		
		assertEquals(testData.getRecord1(), reader.next().getValue1());
		assertEquals(testData.getRecord2(), reader.next().getValue1());
		assertEquals(testData.getRecord3(), reader.next().getValue1());
	}
	
	/**
	 * Read a third record from the reader
	 	 */
	@Test
	public void testReadRecord_autoCloseable() throws Exception {
		try (StringStreamReader reader	= testData.getRealExamplesReader())
		{
			assertEquals(testData.getRecord1(), reader.next().getValue1());
			assertEquals(testData.getRecord2(), reader.next().getValue1());
			assertEquals(testData.getRecord3(), reader.next().getValue1());
		} catch (Exception e) {
			fail("Should not have hit an exception from the three");
		}
	}

    /**
     * Read a third record from the reader
          */
    @Test
    public void testReadRecords_gapped() throws Exception {
        try (StringStreamReader reader	= testData.getExtraNewlineReader())
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
	 	 */
	@Test
	public void testReadRecord_paged() throws IOException {
		StringStreamReader reader = testData.getPagingRequiredReader();

        int index = 0;
		while (reader.hasNext())
		{	
			assertEquals(index+0 + " failed to parse", testData.getRecord1(), reader.next().getValue1());
            assertEquals(index+1 + " failed to parse", testData.getRecord2(), reader.next().getValue1());
            assertEquals(index+2 + " failed to parse", testData.getRecord3(), reader.next().getValue1());
            index += 3;
		}

        assertEquals("The number of records was not correct", 81, index);
	}

    /**
     * Read a third record from the reader
          */
    @Test
    public void testReadRecords_paged1() throws IOException {
        StringStreamReader reader = testData.getPagingRequiredReader(1);

        int index = 0;
        while (reader.hasNext())
        {
            assertEquals(index+0 + " failed to parse", testData.getRecord1(), reader.next().getValue1());
            assertEquals(index+1 + " failed to parse", testData.getRecord2(), reader.next().getValue1());
            assertEquals(index+2 + " failed to parse", testData.getRecord3(), reader.next().getValue1());
            index += 3;
        }

        assertEquals("The number of records was not correct", 81, index);
    }

    /**
     * Read a third record from the reader
          */
    @Test
    public void testReadRecords_paged10() throws IOException {
        StringStreamReader reader = testData.getPagingRequiredReader(10);

        int index = 0;
        while (reader.hasNext())
        {
            assertEquals(index+0 + " failed to parse", testData.getRecord1(), reader.next().getValue1());
            assertEquals(index+1 + " failed to parse", testData.getRecord2(), reader.next().getValue1());
            assertEquals(index+2 + " failed to parse", testData.getRecord3(), reader.next().getValue1());
            index += 3;
        }

        assertEquals("The number of records was not correct", 81, index);
    }

    /**
     * Read a third record from the reader
          */
    @Test
    public void testReadRecords_paged100() throws IOException {
        StringStreamReader reader = testData.getPagingRequiredReader(100);

        int index = 0;
        while (reader.hasNext())
        {
            assertEquals(index+0 + " failed to parse", testData.getRecord1(), reader.next().getValue1());
            assertEquals(index+1 + " failed to parse", testData.getRecord2(), reader.next().getValue1());
            assertEquals(index+2 + " failed to parse", testData.getRecord3(), reader.next().getValue1());
            index += 3;
        }

        assertEquals("The number of records was not correct", 81, index);
    }
}
