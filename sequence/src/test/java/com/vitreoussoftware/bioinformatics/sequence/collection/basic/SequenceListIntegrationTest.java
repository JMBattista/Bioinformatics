package com.vitreoussoftware.bioinformatics.sequence.collection.basic;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionTest;
import com.vitreoussoftware.bioinformatics.sequence.io.FastaData;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.fasta.FastaSequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.fasta.FastaStringFileStreamReaderIntegrationTest;
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
		SequenceStreamReader reader = new FastaSequenceStreamReader(FastaStringFileStreamReaderIntegrationTest.getSimpleFastaReader());
		SequenceCollection sc = this.getFactory().getSequenceCollection(reader);
		
		assertNotNull(sc);
		assertEquals(1,  sc.size());
		assertEquals(sequenceFactory.fromString(FastaData.getRecordSimple()).get(), sc.iterator().next());
		assertTrue(sc.contains(sequenceFactory.fromString(FastaData.getRecordSimple()).get()));
	}
	
	/**
	 * Can it be created?
	 * @throws InvalidDnaFormatException 
	 * @throws IOException 
	 */
	@Test
	public void testStreamSource_multiple() throws InvalidDnaFormatException, IOException {
        SequenceStreamReader reader = new FastaSequenceStreamReader(FastaStringFileStreamReaderIntegrationTest.getExampleFastaReader());
		SequenceCollection sc = this.getFactory().getSequenceCollection(reader);

		assertNotNull(sc);
		assertEquals(3,  sc.size());
		assertTrue(sc.contains(sequenceFactory.fromString(FastaData.getRecord1()).get()));
		assertTrue(sc.contains(sequenceFactory.fromString(FastaData.getRecord2()).get()));
		assertTrue(sc.contains(sequenceFactory.fromString(FastaData.getRecord3()).get()));
	}
}
