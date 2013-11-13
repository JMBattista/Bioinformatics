package com.vitreoussoftare.bioinformatics.sequence.collection.basic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.vitreoussoftare.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftare.bioinformatics.sequence.Sequence;
import com.vitreoussoftare.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftare.bioinformatics.sequence.collection.SequenceCollectionFactory;

public class SequenceSetTest {

	private SequenceCollectionFactory factory;

	/**
	 * Setup for test execution
	 */
	@Before
	public void setup() {
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
		sc.add(Sequence.fromFasta("AATTCCGGUU"));
		assertEquals(1,  sc.size());
	}
	
	/**
	 * Can it be created?
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testAdd_duplicate() throws InvalidDnaFormatException {
		SequenceCollection sc = this.factory.getSequenceCollection();
		
		assertNotNull(sc);
		sc.add(Sequence.fromFasta("AATTCCGGUU"));
		assertEquals(1,  sc.size());
		sc.add(Sequence.fromFasta("AATTCCGGUU"));
		assertEquals(1,  sc.size());
	}
	
	/**
	 * Can it be created?
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testContains_sameRef() throws InvalidDnaFormatException {
		SequenceCollection sc = this.factory.getSequenceCollection();
		Sequence seq = Sequence.fromFasta("AATTCCGGUU");
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
		sc.add(Sequence.fromFasta("AATTCCGGUU"));
		assertEquals(1,  sc.size());
		assertTrue(sc.contains(Sequence.fromFasta("AATTCCGGUU")));
	}
}
