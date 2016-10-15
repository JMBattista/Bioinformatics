package com.vitreoussoftware.bioinformatics.sequence.io.reader.fasta;

import com.vitreoussoftware.bioinformatics.sequence.io.FastaData;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReaderIntegrationTestBase;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test the {@link FastaSequenceStreamReader}
 * Created by John on 12/15/13.
 */
public class FastaSequenceStreamReaderIntegrationTest extends SequenceStreamReaderIntegrationTestBase<FastaData> {
    @Override
    protected FastaData getTestData() {
        return new FastaData();
    }

    public SequenceStreamReader getReader(StringStreamReader reader) {
        return new FastaSequenceStreamReader(reader);
    }

    @Override
    protected int getBigFileRecordCount() {
        return 79002;
    }

    /**
     * Fasta files contain an optional space at the end of the sequence identifier line before the ';'
     * Test parsing when the space is missing
     * @throws Exception
     */
    @Test
    public void testReadRecords_noSpace() throws Exception {
        try (SequenceStreamReader reader = getReader(testData.getNoSpaceReader()))
        {
            assertTrue("First record could not be parsed", reader.next().isPresent());
            assertTrue("Second record could not be parsed", reader.next().isPresent());
            assertTrue("Third record could not be parsed", reader.next().isPresent());
        } catch (Exception e) {
            fail("Should not have hit an exception from reading three records from no space\n" + e.getMessage());
        }
    }
}
