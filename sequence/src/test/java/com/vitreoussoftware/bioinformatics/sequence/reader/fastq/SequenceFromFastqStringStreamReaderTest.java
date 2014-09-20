package com.vitreoussoftware.bioinformatics.sequence.reader.fastq;

import com.vitreoussoftware.bioinformatics.sequence.reader.SequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.reader.fastq.SequenceFromFastqStringStreamReader;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by John on 12/15/13.
 */

public class SequenceFromFastqStringStreamReaderTest {

    /**
     * Read a third record from the reader
     * @throws Exception
     */
    @Test
    public void testReadRecord_autoCloseable() throws Exception {
        try (SequenceStreamReader reader= new SequenceFromFastqStringStreamReader(FastqStringFileStreamReaderTest.getExampleFastqReader()))
        {
            assertTrue("First record could not be parsed", reader.next().isPresent());
            assertTrue("Second record could not be parsed", reader.next().isPresent());
            assertTrue("Third record could not be parsed", reader.next().isPresent());
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
        try (SequenceStreamReader reader= new SequenceFromFastqStringStreamReader(FastqStringFileStreamReaderTest.getGappedFastqReader()))
        {
            assertTrue("First record could not be parsed", reader.next().isPresent());
            assertTrue("Second record could not be parsed", reader.next().isPresent());
            assertTrue("Third record could not be parsed", reader.next().isPresent());
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
        try (SequenceStreamReader reader= new SequenceFromFastqStringStreamReader(FastqStringFileStreamReaderTest.getNoSpaceFastqReader()))
        {
            assertTrue("First record could not be parsed", reader.next().isPresent());
            assertTrue("Second record could not be parsed", reader.next().isPresent());
            assertTrue("Third record could not be parsed", reader.next().isPresent());
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
        SequenceStreamReader reader= new SequenceFromFastqStringStreamReader(FastqStringFileStreamReaderTest.getPagedFastqReader());

        while (reader.hasNext())
        {
            assertTrue("First record could not be parsed", reader.next().isPresent());
            assertTrue("Second record could not be parsed", reader.next().isPresent());
            assertTrue("Third record could not be parsed", reader.next().isPresent());
        }
    }
}
