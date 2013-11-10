package com.vitreoussoftare.bioinformatics.dnadata;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the BasePair class
 * @author John
 *
 */
public class BasePairTest {

	/** 
	 * Test Equality for BasePair, with same start
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testEquality_toSelf() throws InvalidDnaFormatException {
		BasePair bp = BasePair.create('A');
		assertEquals(bp, bp);
	}
	
	/** 
	 * Test Equality for BasePair default values
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testEquality_defaultValues() throws InvalidDnaFormatException {
		assertEquals(BasePair.A, BasePair.create('A'));
		assertEquals(BasePair.T, BasePair.create('T'));
		assertEquals(BasePair.C, BasePair.create('C'));
		assertEquals(BasePair.G, BasePair.create('G'));
		assertEquals(BasePair.N, BasePair.create('N'));
	}
	
	/** 
	 * Test Equality for BasePair, with same start
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testEquality_sameStart() throws InvalidDnaFormatException {
		assertEquals(BasePair.create('A'), BasePair.create('A'));
	}
	
	/** 
	 * Test Equality for BasePair, with different cases
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testEquality_differentCase() throws InvalidDnaFormatException {
		assertEquals(BasePair.create('A'), BasePair.create('a'));
	}
	
	/** 
	 * Test Equality for BasePair, different base pair
	 * @throws InvalidDnaFormatException
	 */
	public void testEquality_notSame() throws InvalidDnaFormatException {
		assertNotEquals(BasePair.create('A'), BasePair.create('T'));
	}
	
	/** 
	 * Test Equality for BasePair, against N
	 * @throws InvalidDnaFormatException
	 */
	public void testEquality_againstN() throws InvalidDnaFormatException {
		assertNotEquals(BasePair.create('A'), BasePair.create('N'));
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_A() throws InvalidDnaFormatException {
		assertEquals("A", BasePair.create('A').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_a() throws InvalidDnaFormatException {
		assertEquals("A", BasePair.create('a').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_T() throws InvalidDnaFormatException {
		assertEquals("T", BasePair.create('T').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_t() throws InvalidDnaFormatException {
		assertEquals("T", BasePair.create('t').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_C() throws InvalidDnaFormatException {
		assertEquals("C", BasePair.create('C').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_c() throws InvalidDnaFormatException {
		assertEquals("C", BasePair.create('c').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_G() throws InvalidDnaFormatException {
		assertEquals("G", BasePair.create('G').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_g() throws InvalidDnaFormatException {
		assertEquals("G", BasePair.create('g').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_N() throws InvalidDnaFormatException {
		assertEquals("N", BasePair.create('N').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_n() throws InvalidDnaFormatException {
		assertEquals("N", BasePair.create('n').toString());
	}

}
