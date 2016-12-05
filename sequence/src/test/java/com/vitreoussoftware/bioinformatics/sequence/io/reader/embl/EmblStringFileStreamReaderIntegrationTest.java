package com.vitreoussoftware.bioinformatics.sequence.io.reader.embl;

import com.vitreoussoftware.bioinformatics.sequence.SequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.embl.EmblSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.io.EmblData;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringFileStreamReaderIntegrationTestBase;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader;

/**
 * Test the FastqFileStreamReader class
 *
 * @author John
 */
public class EmblStringFileStreamReaderIntegrationTest extends StringFileStreamReaderIntegrationTestBase<EmblData> {
    @Override
    protected EmblData getTestData() {
        return new EmblData();
    }

    @Override
    protected StringStreamReader getReader(final String path) throws Exception {
        return EmblStringFileStreamReader.create(path);
    }

    @Override
    protected SequenceFactory getFactory() throws Exception {
        return new EmblSequenceFactory();
    }
}
