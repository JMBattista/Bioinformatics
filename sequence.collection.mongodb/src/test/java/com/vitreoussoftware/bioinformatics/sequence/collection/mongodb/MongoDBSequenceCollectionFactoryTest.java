package com.vitreoussoftware.bioinformatics.sequence.collection.mongodb;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.SequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;

/**
 * Test the MongoDBSequenceCollection class
 * @author John
 *
 */
public class MongoDBSequenceCollectionFactoryTest {

	private SequenceFactory sequenceFactory;

	@Before
	public void setUp() throws Exception {
		this.sequenceFactory = new FastaSequenceFactory();
	}

	/**
	 * Create a SequenceCollection
	 * @throws UnknownHostException Failed to connect 
	 */
	@Test
	public void testCreateFactory() throws UnknownHostException {
		SequenceCollectionFactory collectionFactory = new MongoDBSequenceCollectionFactory("localhost", 27017, "testSeq", "testColl");
		assertNotNull("The collectionFactory initialized to null", collectionFactory);
	}
	
	/**
	 * Create a SequenceCollection
	 * @throws UnknownHostException Failed to connect 
	 */
	@Test(expected=UnknownHostException.class)
	public void testCreateFactory_canFail() throws UnknownHostException {
		SequenceCollectionFactory collectionFactory = new MongoDBSequenceCollectionFactory("SXKSAkasdglak@#@KASGlsldfs", 27017, "testSeq", "testColl");
		fail("We should not have been able to successfully initialize the factory without a connection to the DB");
	}
	
	/**
	 * Create a SequenceCollection
	 * @throws UnknownHostException Failed to connect 
	 */
	@Test
	public void testGetCollection() throws UnknownHostException {
		SequenceCollectionFactory collectionFactory = new MongoDBSequenceCollectionFactory("localhost", 27017, "testSeq", "testColl");
		assertNotNull("The collectionFactory initialized to null", collectionFactory);
		SequenceCollection collection = collectionFactory.getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
	}	
}
