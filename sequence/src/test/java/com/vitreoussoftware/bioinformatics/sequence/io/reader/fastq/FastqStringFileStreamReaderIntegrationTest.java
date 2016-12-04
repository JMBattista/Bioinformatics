package com.vitreoussoftware.bioinformatics.sequence.io.reader.fastq;

import com.vitreoussoftware.bioinformatics.sequence.SequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.fastq.FastqSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.io.FastqData;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringFileStreamReaderIntegrationTestBase;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader;
import org.javatuples.Pair;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Test the FastqFileStreamReader class
 * @author John
 *
 */
public class FastqStringFileStreamReaderIntegrationTest extends StringFileStreamReaderIntegrationTestBase<FastqData>{
    @Override
    protected FastqData getTestData() {
        return new FastqData();
    }

    @Override
    protected StringStreamReader getReader(final String path) throws Exception {
        return FastqStringFileStreamReader.create(path);
    }

    @Override
    protected SequenceFactory getFactory() throws Exception {
        return new FastqSequenceFactory();
    }

    /**
     * Read a record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecordComplex1() throws IOException {
        final StringStreamReader reader = testData.getComplexExamplesReader();

        final Pair<String, String> next = reader.next();
        assertEquals(testData.getComplex1Metadata(), next.getValue0());
        assertEquals(testData.getComplex1SequenceString(), next.getValue1());
    }

    /**
     * Read a second record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecordComplex2() throws IOException {
        final StringStreamReader reader = testData.getComplexExamplesReader();

        reader.next();
        final Pair<String, String> next = reader.next();
        assertEquals(testData.getComplex2Metadata(), next.getValue0());
        assertEquals(testData.getComplex2SequenceString(), next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecordComplex3() throws IOException {
        final StringStreamReader reader = testData.getComplexExamplesReader();

        reader.next();
        reader.next();
        final Pair<String, String> next = reader.next();
        assertEquals(testData.getComplex3Metadata(), next.getValue0());
        assertEquals(testData.getComplex3SequenceString(), next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecordComplex4() throws IOException {
        final StringStreamReader reader = testData.getComplexExamplesReader();

        reader.next();
        reader.next();
        reader.next();
        final Pair<String, String> next = reader.next();
        assertEquals(testData.getComplex4Metadata(), next.getValue0());
        assertEquals(testData.getComplex4SequenceString(), next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecordComplex5() throws IOException {
        final StringStreamReader reader = testData.getComplexExamplesReader();

        reader.next();
        reader.next();
        reader.next();
        reader.next();
        final Pair<String, String> next = reader.next();
        assertEquals(testData.getComplex5Metadata(), next.getValue0());
        assertEquals(testData.getComplex5SequenceString(), next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecordComplex6() throws IOException {
        final StringStreamReader reader = testData.getComplexExamplesReader();

        reader.next();
        reader.next();
        reader.next();
        reader.next();
        reader.next();
        final Pair<String, String> next = reader.next();
        assertEquals(testData.getComplex6Metadata(), next.getValue0());
        assertEquals(testData.getComplex6SequenceString(), next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecordComplex7() throws IOException {
        final StringStreamReader reader = testData.getComplexExamplesReader();

        reader.next();
        reader.next();
        reader.next();
        reader.next();
        reader.next();
        reader.next();
        final Pair<String, String> next = reader.next();
        assertEquals(testData.getComplex7Metadata(), next.getValue0());
        assertEquals(testData.getComplex7SequenceString(), next.getValue1());
    }
}
