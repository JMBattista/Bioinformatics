package com.vitreoussoftware.bioinformatics.sequence.io.reader;

import com.vitreoussoftware.bioinformatics.sequence.fastq.FastqSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.io.FastqData;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for {@link SequenceStreamReader}. We also include tests for this in
 *   the {@link StringFileStreamReaderIntegrationTestBase}, to ensure that each implementation works with the
 *   {@link SequenceStreamReader}
 * Created by John on 2016/10/15
 */

public class SequenceStreamReaderIntegrationTest {
    protected FastqData testData;

    @Before
    public void setup() {
        testData = new FastqData();
    }

    /**
     * Test that the {@link SequenceStreamReader} can be constructed as an auto-closable
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
     * Test that we can read from some of the sample inputs
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
     * Test that we can read from some of the sample inputs that require paging
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
     * Create the {@link SequenceStreamReader} from the given {@link StringStreamReader}
     * @param reader The {@link StringStreamReader} to wrap
     * @return The {@link SequenceStreamReader} to test
     */
    private SequenceStreamReader getReader(StringStreamReader reader) {
        return SequenceStreamReader.builder()
                .reader(reader)
                .factory(new FastqSequenceFactory())
                .build();
    }
}
