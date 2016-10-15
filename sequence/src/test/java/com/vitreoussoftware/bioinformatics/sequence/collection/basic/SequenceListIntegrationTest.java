package com.vitreoussoftware.bioinformatics.sequence.collection.basic;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionTest;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.fasta.FastaSequenceStreamReader;
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
	public void testStreamSource_single() throws InvalidDnaFormatException, IOException {
		SequenceStreamReader reader = new FastaSequenceStreamReader(testData.getSimpleExampleReader());
		SequenceCollection sc = this.getFactory().getSequenceCollection(reader);
		
		assertNotNull(sc);
		assertEquals(1,  sc.size());
		assertEquals(sequenceFactory.fromString(testData.getRecordSimple()).get(), sc.iterator().next());
		assertTrue(sc.contains(sequenceFactory.fromString(testData.getRecordSimple()).get()));
	}
	
	/**
	 * Can it be created?
	 * @throws InvalidDnaFormatException 
	 * @throws IOException 
	 */
	@Test
	public void testStreamSource_multiple() throws InvalidDnaFormatException, IOException {
        SequenceStreamReader reader = new FastaSequenceStreamReader(testData.getRealExamplesReader());
		SequenceCollection sc = this.getFactory().getSequenceCollection(reader);

		assertNotNull(sc);
		assertEquals(3,  sc.size());
		assertTrue(sc.contains(sequenceFactory.fromString(testData.getRecord1()).get()));
		assertTrue(sc.contains(sequenceFactory.fromString(testData.getRecord2()).get()));
		assertTrue(sc.contains(sequenceFactory.fromString(testData.getRecord3()).get()));
	}
}
