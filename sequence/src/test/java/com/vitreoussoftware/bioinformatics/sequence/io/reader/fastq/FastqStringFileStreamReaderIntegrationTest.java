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
    protected StringStreamReader getReader(String path) throws Exception {
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
    public void testReadRecord_complex1() throws IOException {
        StringStreamReader reader = testData.getComplexExamplesReader();

        Pair<String, String> next = reader.next();
        assertEquals(testData.getComplex1_metadata(), next.getValue0());
        assertEquals(testData.getComplex1_sequence(), next.getValue1());
    }

    /**
     * Read a second record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex2() throws IOException {
        StringStreamReader reader = testData.getComplexExamplesReader();

        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(testData.getComplex2_metadata(), next.getValue0());
        assertEquals(testData.getComplex2_sequence(), next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex3() throws IOException {
        StringStreamReader reader = testData.getComplexExamplesReader();

        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(testData.getComplex3_metadata(), next.getValue0());
        assertEquals(testData.getComplex3_sequence(), next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex4() throws IOException {
        StringStreamReader reader = testData.getComplexExamplesReader();

        reader.next();
        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(testData.getComplex4_metadata(), next.getValue0());
        assertEquals(testData.getComplex4_sequence(), next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex5() throws IOException {
        StringStreamReader reader = testData.getComplexExamplesReader();

        reader.next();
        reader.next();
        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(testData.getComplex5_metadata(), next.getValue0());
        assertEquals(testData.getComplex5_sequence(), next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex6() throws IOException {
        StringStreamReader reader = testData.getComplexExamplesReader();

        reader.next();
        reader.next();
        reader.next();
        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(testData.getComplex6_metadata(), next.getValue0());
        assertEquals(testData.getComplex6_sequence(), next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex7() throws IOException {
        StringStreamReader reader = testData.getComplexExamplesReader();

        reader.next();
        reader.next();
        reader.next();
        reader.next();
        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(testData.getComplex7_metadata(), next.getValue0());
        assertEquals(testData.getComplex7_sequence(), next.getValue1());
    }
}
