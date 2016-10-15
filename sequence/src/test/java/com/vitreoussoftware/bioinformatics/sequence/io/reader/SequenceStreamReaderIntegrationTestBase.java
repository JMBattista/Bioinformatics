package com.vitreoussoftware.bioinformatics.sequence.io.reader;

import com.vitreoussoftware.bioinformatics.sequence.io.TestData;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Abstract base for testing SequenceStreamReader implementations
 * Created by John on 2016/10/15
 */

public abstract class SequenceStreamReaderIntegrationTestBase<T extends TestData> {
    protected T testData;

    @Before
    public void setup() {
        testData = getTestData();
    }

    protected abstract T getTestData();

    /**
     * Create the appropriate SequenceStreamReader from the given StringStreamReader
     * @param reader The StringStreamReader to wrap
     * @return The SequenceStreamReader to test
     */
    public abstract SequenceStreamReader getReader(StringStreamReader reader);

    protected abstract int getBigFileRecordCount();

    /**
     * Read a third record from the reader
     * @throws Exception
     */
    @Test
    public void testReadRecord_autoCloseable() throws Exception {
        try (SequenceStreamReader reader = getReader(testData.getRealExamplesReader()))
        {
            assertThat("First record could not be parsed", reader.next().isPresent(), is(true));
            assertThat("Second record could not be parsed", reader.next().isPresent(), is(true));
            assertThat("Third record could not be parsed", reader.next().isPresent(), is(true));
        } catch (Exception e) {
            fail("Should not have hit an exception from the three");
        }
    }

    /**
     * Test that we can read records from the file with extra blank lines
     * @throws Exception
     */
    @Test
    public void testReadRecords_gapped() throws Exception {
        try (SequenceStreamReader reader = getReader(testData.getExtraNewlineReader()))
        {
            assertThat("First record could not be parsed", reader.next().isPresent(), is(true));
            assertThat("Second record could not be parsed", reader.next().isPresent(), is(true));
            assertThat("Third record could not be parsed", reader.next().isPresent(), is(true));
        } catch (Exception e) {
            fail("Should not have hit an exception from reading three records from gapped");
        }
    }

    /**
     * Test that we can read records from a file while paging
     * @throws IOException
     */
    @Test
    public void testReadRecord_paged() throws IOException {

        SequenceStreamReader reader = getReader(testData.getPagingRequiredReader());

        int i = 0;
        while (reader.hasNext())
        {
            i++;
            assertThat("First record could not be parsed", reader.next().isPresent(), is(true));
            assertThat("Second record could not be parsed", reader.next().isPresent(), is(true));
            assertThat("Third record could not be parsed", reader.next().isPresent(), is(true));
        }

        assertThat(i, greaterThan(0));
    }

    /**
     * Test that we could read from a very large file with many records
     * @throws IOException
     */
    @Test
    public void testReadRecord_big() throws IOException {
        SequenceStreamReader reader = getReader(testData.getBigReader());

        int index = 0;
        while (reader.hasNext())
        {
            assertThat(index +"th record could not be parsed", reader.next().isPresent(), is(true));
            index++;
        }

        assertThat("There were not enough records", index, is(getBigFileRecordCount()));
    }
}
