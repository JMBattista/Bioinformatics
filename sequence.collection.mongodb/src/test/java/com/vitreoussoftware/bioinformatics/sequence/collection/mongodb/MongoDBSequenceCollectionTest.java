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
public class MongoDBSequenceCollectionTest {

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
	
	/**
	 * Create a SequenceCollection
	 * @throws UnknownHostException Failed to connect 
	 */
	@Test
	public void testClearCollection() throws UnknownHostException {
		SequenceCollectionFactory collectionFactory = new MongoDBSequenceCollectionFactory("localhost", 27017, "testSeq", "testColl");
		assertNotNull("The collectionFactory initialized to null", collectionFactory);
		SequenceCollection collection = collectionFactory.getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());
	}

	/**
	 * Create a SequenceCollection
	 * @throws UnknownHostException Failed to connect 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testAddSequence() throws UnknownHostException, InvalidDnaFormatException {
		SequenceCollectionFactory collectionFactory = new MongoDBSequenceCollectionFactory("localhost", 27017, "testSeq", "testColl");
		assertNotNull("The collectionFactory initialized to null", collectionFactory);
		SequenceCollection collection = collectionFactory.getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());
		
		Sequence seq = this.sequenceFactory.fromString("ATCGN");
		collection.add(seq);
		
		assertEquals("The collection did not have only one element in it", 1, collection.size());
	}
	
	/**
	 * Create a SequenceCollection
	 * @throws UnknownHostException Failed to connect 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testAddAllSequences() throws UnknownHostException, InvalidDnaFormatException {
		SequenceCollectionFactory collectionFactory = new MongoDBSequenceCollectionFactory("localhost", 27017, "testSeq", "testColl");
		assertNotNull("The collectionFactory initialized to null", collectionFactory);
		SequenceCollection collection = collectionFactory.getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());
		
		Sequence seq = this.sequenceFactory.fromString("ATCGN");
		
		collection.addAll(Arrays.asList(seq, seq, seq));
		
		assertEquals("The collection did not have 3 elements in it", 3, collection.size());
	}
	
	/**
	 * Create a SequenceCollection
	 * @throws UnknownHostException Failed to connect 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testAddAllSequences_many() throws UnknownHostException, InvalidDnaFormatException {
		SequenceCollectionFactory collectionFactory = new MongoDBSequenceCollectionFactory("localhost", 27017, "testSeq", "testColl");
		assertNotNull("The collectionFactory initialized to null", collectionFactory);
		SequenceCollection collection = collectionFactory.getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());
		
		Sequence seq = this.sequenceFactory.fromString("ATCGN");
		
		List<Sequence> list = new LinkedList<Sequence>();
		final int many = 99999;
		for (int i = 0; i < many; i++) list.add(seq);
		assertEquals("The list didn't have many elements", many, list.size());
		
		collection.addAll(list);
		
		assertEquals("The collection did not have many elements in it", many, collection.size());
	}
}
