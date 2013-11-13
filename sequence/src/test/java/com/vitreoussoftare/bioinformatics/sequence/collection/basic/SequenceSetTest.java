package com.vitreoussoftare.bioinformatics.sequence.collection.basic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.vitreoussoftare.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftare.bioinformatics.sequence.Sequence;
import com.vitreoussoftare.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftare.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftare.bioinformatics.sequence.fasta.FastaSequenceFactory;

public class SequenceSetTest {

	private SequenceCollectionFactory factory;
	private FastaSequenceFactory sequenceFactory;

	/**
	 * Setup the test class
	 */
	@Before
	public void setup() {
		this.sequenceFactory = new FastaSequenceFactory();
		this.factory = new SequenceSetFactory();
	}	
	/**
	 * Can it be created?
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testCreation() throws InvalidDnaFormatException {
		SequenceCollection sc = this.factory.getSequenceCollection();
		
		assertNotNull(sc);
	}
	
	/**
	 * Can it be created?
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testAdd() throws InvalidDnaFormatException {
		SequenceCollection sc = this.factory.getSequenceCollection();
		
		assertNotNull(sc);
		sc.add(sequenceFactory.fromString("AATTCCGGUU"));
		assertEquals(1,  sc.size());
	}
	
	/**
	 * Can it be created?
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testAdd_duplicate() throws InvalidDnaFormatException {
		SequenceCollection sc = this.factory.getSequenceCollection();
		Sequence seq = sequenceFactory.fromString("AATTCCGGUU");
		
		assertNotNull(sc);
		sc.add(sequenceFactory.fromString("AATTCCGGUU"));
		assertEquals(1,  sc.size());
		assertEquals(seq, sc.iterator().next());
		assertTrue(sc.contains(seq));
		sc.add(sequenceFactory.fromString("AATTCCGGUU"));
		assertEquals(1,  sc.size());
	}
	
	/**
	 * Can it be created?
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testContains_sameRef() throws InvalidDnaFormatException {
		SequenceCollection sc = this.factory.getSequenceCollection();
		Sequence seq = sequenceFactory.fromString("AATTCCGGUU");
		sc.add(seq);
		assertEquals(1,  sc.size());
		assertTrue(sc.contains(seq));
	}
	
	/**
	 * Can it be created?
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testContains_diffRef() throws InvalidDnaFormatException {
		SequenceCollection sc = this.factory.getSequenceCollection();
		sc.add(sequenceFactory.fromString("AATTCCGGUU"));
		assertEquals(1,  sc.size());
		assertTrue(sc.contains(sequenceFactory.fromString("AATTCCGGUU")));
	}
}
