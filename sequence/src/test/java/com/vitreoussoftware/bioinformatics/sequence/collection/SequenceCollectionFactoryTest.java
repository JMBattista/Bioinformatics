package com.vitreoussoftware.bioinformatics.sequence.collection;

import com.vitreoussoftware.bioinformatics.sequence.SequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Test the MongoDBSequenceCollection class
 * @author John
 *
 */
public abstract class SequenceCollectionFactoryTest {

	private SequenceFactory sequenceFactory;

	@Before
	public void setUp() throws Exception {
		this.sequenceFactory = new FastaSequenceFactory();
	}

    /**
     * Create a working SequenceCollectionFactory for the tests to use
     * @return the SequenceCollectionFactory
     */
    public abstract SequenceCollectionFactory getFactory();


	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 */
	@Test
	public void testCreateFactory() {
		SequenceCollectionFactory collectionFactory = getFactory();
		assertNotNull("The collectionFactory initialized to null", collectionFactory);
	}

	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 */
	@Test
	public void testGetCollection() {
		SequenceCollectionFactory collectionFactory = getFactory();
		assertNotNull("The collectionFactory initialized to null", collectionFactory);
		SequenceCollection collection = collectionFactory.getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
	}	
}
