package com.vitreoussoftware.bioinformatics.sequence.collection.basic;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionTest;
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReader;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Test the SequenceList basic collection so we have a baseline
 * @author John
 *
 */
public class SequenceListIntegrationTest extends SequenceCollectionTest {

    @Override
    protected SequenceCollectionFactory getFactory() {
        return new SequenceListFactory();
    }

	/**
	 * Can it be created?
	 * @throws InvalidDnaFormatException 
	 * @throws IOException 
	 */
	@Test
	public void testStreamSourceSingle() throws InvalidDnaFormatException, IOException {
        final com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader reader1 = testData.getSimpleExampleReader();
        SequenceStreamReader reader = SequenceStreamReader.builder()
			.reader(reader1)
			.factory(new FastaSequenceFactory())
			.build();
		SequenceCollection sc = this.getFactory().getSequenceCollection(reader);
		
		assertNotNull(sc);
		assertEquals(1,  sc.size());
		assertEquals(sequenceFactory.fromString(testData.getSimpleRecord()).get(), sc.iterator().next());
		assertTrue(sc.contains(sequenceFactory.fromString(testData.getSimpleRecord()).get()));
	}
	
	/**
	 * Can it be created?
	 * @throws InvalidDnaFormatException 
	 * @throws IOException 
	 */
	@Test
	public void testStreamSourceMultiple() throws InvalidDnaFormatException, IOException {
        final com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader reader1 = testData.getRealExamplesReader();
        SequenceStreamReader reader = SequenceStreamReader.builder()
				.reader(reader1)
				.factory(new FastaSequenceFactory())
				.build();
		SequenceCollection sc = this.getFactory().getSequenceCollection(reader);

		assertNotNull(sc);
		assertEquals(3,  sc.size());
		assertTrue(sc.contains(sequenceFactory.fromString(testData.getRealExample1()).get()));
		assertTrue(sc.contains(sequenceFactory.fromString(testData.getRealExample2()).get()));
		assertTrue(sc.contains(sequenceFactory.fromString(testData.getRealExample3()).get()));
	}
}
