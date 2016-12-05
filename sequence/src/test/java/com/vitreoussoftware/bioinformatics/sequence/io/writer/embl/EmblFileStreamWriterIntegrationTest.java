package com.vitreoussoftware.bioinformatics.sequence.io.writer.embl;

import com.vitreoussoftware.bioinformatics.sequence.embl.EmblSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.io.EmblData;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.embl.EmblStringFileStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.writer.SequenceStreamWriter;
import com.vitreoussoftware.bioinformatics.sequence.io.writer.SequenceStreamWriterIntegrationTestBase;

/**
 * Test the {@link EmblFileStreamWriter} class
 *
 * @author John
 */
public class EmblFileStreamWriterIntegrationTest extends SequenceStreamWriterIntegrationTestBase<EmblData> {
    private static final String WRITER_TEST_FILE = "build/embltestwriter.embl";

    @Override
    protected EmblData getTestData() {
        return new EmblData();
    }

    @Override
    protected SequenceStreamWriter getWriter(final String path) throws Exception {
        return EmblFileStreamWriter.create(path);
    }

    @Override
    protected SequenceStreamReader getReader(final String path) throws Exception {
        final com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader reader = EmblStringFileStreamReader.create(path);
        return SequenceStreamReader.builder()
                .reader(reader)
                .factory(new EmblSequenceFactory())
                .build();
    }

    @Override
    protected String getTestFile() {
        return WRITER_TEST_FILE;
    }
}
