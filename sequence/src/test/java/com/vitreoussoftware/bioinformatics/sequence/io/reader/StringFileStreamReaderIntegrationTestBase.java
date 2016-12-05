package com.vitreoussoftware.bioinformatics.sequence.io.reader;

import com.vitreoussoftware.bioinformatics.sequence.SequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.io.TestData;
import lombok.val;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Base tests for the {@link StringStreamReader} class
 *
 * @author John 2016/10/15
 */
public abstract class StringFileStreamReaderIntegrationTestBase<T extends TestData> {
    protected T testData;

    @Before
    public void setup() {
        testData = getTestData();
    }

    /**
     * Get the {@link TestData} instance to use with this {@link StringStreamReader}
     *
     * @return The {@link TestData} instance to use
     */
    protected abstract T getTestData();

    /**
     * Attempt to fromCharacter a {@link StringStreamReader} from the path to test
     *
     * @param path The path to the file
     * @return The {@link StringStreamReader} to test
     */
    protected abstract StringStreamReader getReader(String path) throws Exception;

    /**
     * Create a {@link SequenceFactory}
     *
     * @return The {@link SequenceFactory} to test
     */
    protected abstract SequenceFactory getFactory() throws Exception;

    /**
     * Get a non-existent path for a file
     *
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
    public void testCreateNotFoundAutoCloseable() throws FileNotFoundException {
        try (StringStreamReader reader = getReader(getNonExistantPath())) {
            fail("This should not be reachable");
        } catch (final FileNotFoundException e) {
            // do nothing since this means we passed
        } catch (final Exception e) {
            fail("We should have been caught by the more specific exception in front of this.");
        }
    }

    /**
     * Test reading a the 'simple' record value
     */
    @Test
    public void testReadRecordSimple() throws IOException {
        final StringStreamReader reader = testData.getSimpleExampleReader();

        assertThat(reader.next().getValue1(), is(testData.getSimpleRecord()));
    }

    /**
     * Read a record from the reader
     */
    @Test
    public void testReadRecordExample1() throws IOException {
        final StringStreamReader reader = testData.getRealExamplesReader();

        assertThat(reader.next().getValue1(), is(testData.getRealExample1()));
    }

    /**
     * Read a second record from the reader
     */
    @Test
    public void testReadRecordExample2() throws IOException {
        final StringStreamReader reader = testData.getRealExamplesReader();

        assertThat(reader.next().getValue1(), is(testData.getRealExample1()));
        assertThat(reader.next().getValue1(), is(testData.getRealExample2()));
    }

    /**
     * Read a third record from the reader
     */
    @Test
    public void testReadRecordExample3() throws IOException {
        final StringStreamReader reader = testData.getRealExamplesReader();

        assertThat(reader.next().getValue1(), is(testData.getRealExample1()));
        assertThat(reader.next().getValue1(), is(testData.getRealExample2()));
        assertThat(reader.next().getValue1(), is(testData.getRealExample3()));
    }

    /**
     * Read a third record from the reader
     */
    @Test
    public void testReadRecordAutoCloseable() throws Exception {
        try (StringStreamReader reader = testData.getRealExamplesReader()) {
            assertThat(reader.next().getValue1(), is(testData.getRealExample1()));
            assertThat(reader.next().getValue1(), is(testData.getRealExample2()));
            assertThat(reader.next().getValue1(), is(testData.getRealExample3()));
        } catch (final Exception e) {
            fail("Should not have hit an exception from the three");
        }
    }

    /**
     * Read a third record from the reader
     */
    @Test
    public void testReadRecordsGapped() throws Exception {
        try (StringStreamReader reader = testData.getExtraNewlineReader()) {
            assertNotNull(reader.next().getValue1());
            assertNotNull(reader.next().getValue1());
            assertNotNull(reader.next().getValue1());
        } catch (final Exception e) {
            fail("Should not have hit an exception from reading three records from gapped");
        }
    }

    /**
     * Test that hasNext() returns false on an empty file
     */
    @Test
    public void testHasNextEmpty() throws Exception {
        final StringStreamReader reader = testData.getEmptyReader();

        assertThat(reader.hasNext(), is(false));
    }

    /**
     * Test that hasNext() returns false on an empty file
     */
    @Test
    public void testHasNextWhiteSpace() throws Exception {
        final StringStreamReader reader = testData.getEmptyWhiteSpaceReader();

        assertThat(reader.hasNext(), is(false));
    }

    /**
     * Read a third record from the reader
     */
    @Test
    public void testReadRecordPaged() throws IOException {
        final StringStreamReader reader = testData.getPagingRequiredReader();

        int index = 0;
        while (reader.hasNext()) {
            assertThat(index + 0 + " failed to parse", reader.next().getValue1(), is(testData.getRealExample1()));
            assertThat(index + 1 + " failed to parse", reader.next().getValue1(), is(testData.getRealExample2()));
            assertThat(index + 2 + " failed to parse", reader.next().getValue1(), is(testData.getRealExample3()));
            index += 3;
        }

        assertThat("The number of records was not correct", index, is(81));
    }

    /**
     * Read a third record from the reader
     */
    @Test
    public void testReadRecordsPaged1() throws IOException {
        final StringStreamReader reader = testData.getPagingRequiredReader(1);

        int index = 0;
        while (reader.hasNext()) {
            try {
                assertThat(index + 0 + " failed to parse", reader.next().getValue1(), is(testData.getRealExample1()));
            } catch (final Exception e) {
                throw new RuntimeException("Failed at " + (index), e);
            }
            try {
                assertThat(index + 1 + " failed to parse", reader.next().getValue1(), is(testData.getRealExample2()));
            } catch (final Exception e) {
                throw new RuntimeException("Failed at " + (index + 1), e);
            }
            try {
                assertThat(index + 2 + " failed to parse", reader.next().getValue1(), is(testData.getRealExample3()));
            } catch (final Exception e) {
                throw new RuntimeException("Failed at " + (index + 2), e);
            }
            index += 3;
        }

        assertThat("The number of records was not correct", index, is(81));
    }

    /**
     * Read a third record from the reader
     */
    @Test
    public void testReadRecordsPaged10() throws IOException {
        final StringStreamReader reader = testData.getPagingRequiredReader(10);

        int index = 0;
        while (reader.hasNext()) {
            assertThat(index + 0 + " failed to parse", reader.next().getValue1(), is(testData.getRealExample1()));
            assertThat(index + 0 + " failed to parse", reader.next().getValue1(), is(testData.getRealExample2()));
            assertThat(index + 0 + " failed to parse", reader.next().getValue1(), is(testData.getRealExample3()));
            index += 3;
        }

        assertThat("The number of records was not correct", index, is(81));
    }

    /**
     * Read a third record from the reader
     */
    @Test
    public void testReadRecordsPaged100() throws IOException {
        final StringStreamReader reader = testData.getPagingRequiredReader(100);

        int index = 0;
        while (reader.hasNext()) {
            assertThat(index + 0 + " failed to parse", reader.next().getValue1(), is(testData.getRealExample1()));
            assertThat(index + 1 + " failed to parse", reader.next().getValue1(), is(testData.getRealExample2()));
            assertThat(index + 2 + " failed to parse", reader.next().getValue1(), is(testData.getRealExample3()));
            index += 3;
        }

        assertThat("The number of records was not correct", index, is(81));
    }

    /**
     * Test that this isntance of {@link StringStreamReader} works with {@link SequenceStreamReader}
     */
    @Test
    public void testReadSequences() throws Exception {
        val stringStreamReader = testData.getPagingRequiredReader(100);

        val sequenceStreamReader = SequenceStreamReader.builder()
                .reader(stringStreamReader)
                .factory(getFactory())
                .build();


        int index = 0;
        while (sequenceStreamReader.hasNext()) {
            assertThat(index + 0 + " failed to parse", sequenceStreamReader.next().get(), is(testData.getRealExample1Sequence()));
            assertThat(index + 1 + " failed to parse", sequenceStreamReader.next().get(), is(testData.getRealExample2Sequence()));
            assertThat(index + 2 + " failed to parse", sequenceStreamReader.next().get(), is(testData.getRealExample3Sequence()));
            index += 3;
        }

        assertThat("The number of records was not correct", index, is(81));
    }
}
