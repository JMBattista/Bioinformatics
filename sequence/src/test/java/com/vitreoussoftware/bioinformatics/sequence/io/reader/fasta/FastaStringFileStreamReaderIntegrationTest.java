package com.vitreoussoftware.bioinformatics.sequence.io.reader.fasta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.*;

import com.vitreoussoftware.bioinformatics.sequence.SequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.io.FastaData;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringFileStreamReaderIntegrationTestBase;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader;
import org.javatuples.Pair;
import org.junit.Test;

/**
 * Test the FastaFileStreamReader class
 * @author John
 *
 */
public class FastaStringFileStreamReaderIntegrationTest extends StringFileStreamReaderIntegrationTestBase<FastaData> {

    @Override
    protected FastaData getTestData() {
        return new FastaData();
    }

    @Override
    protected StringStreamReader getReader(String path) throws Exception {
        return FastaStringFileStreamReader.create(path);
    }

    @Override
    protected SequenceFactory getFactory() throws Exception {
        return new FastaSequenceFactory();
    }

    /**
     * Read a third record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecordAlternate() throws IOException {
        StringStreamReader reader = testData.getAlternateStartingCharacterReader();

        assertEquals(testData.getAlternateStartingCharacter1(), reader.next().getValue1());
        assertEquals(testData.getAlternateStartingCharacter2(), reader.next().getValue1());
        assertEquals(testData.getAlternateStartingCharacter3(), reader.next().getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws Exception
     */
    @Test
    public void testReadRecordsNoSpace() throws Exception {
        try (StringStreamReader reader	= testData.getNoSpaceReader())
        {
            assertNotNull(reader.next().getValue1());
            assertNotNull(reader.next().getValue1());
            assertNotNull(reader.next().getValue1());
        } catch (Exception e) {
            fail("Should not have hit an exception from reading three records from no space\n" + e.getMessage());
        }
    }

    /**
     * Read a record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecordComplex1() throws IOException {
        StringStreamReader reader = testData.getComplexExamplesReader();

        Pair<String, String> next = reader.next();
        assertEquals(testData.getFastaMultiLineDescriptionMetadata(), next.getValue0());
        assertEquals(testData.getFastaMultiLineDescription(), next.getValue1());
    }

    /**
     * Read a second record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecordComplex2() throws IOException {
        StringStreamReader reader = testData.getComplexExamplesReader();

        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(testData.getFastaTerminatedMetadata(), next.getValue0());
        assertEquals(testData.getFastaTerminated(), next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecordComplex3() throws IOException {
        StringStreamReader reader = testData.getComplexExamplesReader();

        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(testData.getFastaLargeHeaderMetadata(), next.getValue0());
        assertEquals(testData.getFastaLargeHeader(), next.getValue1());
    }
}
