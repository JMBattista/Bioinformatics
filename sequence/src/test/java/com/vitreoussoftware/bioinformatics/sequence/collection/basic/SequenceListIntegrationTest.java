package com.vitreoussoftware.bioinformatics.sequence.collection.basic;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionTest;
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReader;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test the SequenceList basic collection so we have a baseline
 *
 * @author John
 */
public class SequenceListIntegrationTest extends SequenceCollectionTest {

    @Override
    protected SequenceCollectionFactory getFactory() {
        return new SequenceListFactory();
    }

    /**
     * Can it be created?
     *
     * @throws InvalidDnaFormatException
     * @throws IOException
     */
    @Test
    public void testStreamSourceSingle() throws InvalidDnaFormatException, IOException {
        final com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader reader1 = testData.getSimpleExampleReader();
        final SequenceStreamReader reader = SequenceStreamReader.builder()
                .reader(reader1)
                .factory(new FastaSequenceFactory())
                .build();
        final Collection<Sequence> sc = this.getFactory().getSequenceCollection(reader);

        assertNotNull(sc);
        assertThat(sc.size(), is(1));
        assertThat(sc.iterator().next(), is(sequenceFactory.fromString(testData.getSimpleRecord()).get()));
        assertTrue(sc.contains(sequenceFactory.fromString(testData.getSimpleRecord()).get()));
    }

    /**
     * Can it be created?
     *
     * @throws InvalidDnaFormatException
     * @throws IOException
     */
    @Test
    public void testStreamSourceMultiple() throws InvalidDnaFormatException, IOException {
        final com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader reader1 = testData.getRealExamplesReader();
        final SequenceStreamReader reader = SequenceStreamReader.builder()
                .reader(reader1)
                .factory(new FastaSequenceFactory())
                .build();
        final Collection<Sequence> sc = this.getFactory().getSequenceCollection(reader);

        assertNotNull(sc);
        assertEquals(3, sc.size());
        assertTrue(sc.contains(sequenceFactory.fromString(testData.getRealExample1()).get()));
        assertTrue(sc.contains(sequenceFactory.fromString(testData.getRealExample2()).get()));
        assertTrue(sc.contains(sequenceFactory.fromString(testData.getRealExample3()).get()));
    }
}
