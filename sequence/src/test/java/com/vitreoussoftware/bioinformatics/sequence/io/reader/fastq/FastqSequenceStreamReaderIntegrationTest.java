package com.vitreoussoftware.bioinformatics.sequence.io.reader.fastq;

import com.vitreoussoftware.bioinformatics.sequence.io.FastqData;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReaderIntegrationTestBase;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader;
import org.junit.Before;

import java.io.FileNotFoundException;

/**
 * Test the {@link FastqSequenceStreamReader}
 * Created by John on 12/15/13.
 */
public class FastqSequenceStreamReaderIntegrationTest extends SequenceStreamReaderIntegrationTestBase<FastqData> {
    @Override
    protected FastqData getTestData() {
        return new FastqData();
    }

    @Override
    public SequenceStreamReader getReader(StringStreamReader reader) {
        return new FastqSequenceStreamReader(reader);
    }

    @Override
    protected int getBigFileRecordCount() {
        return 100000;
    }
}
