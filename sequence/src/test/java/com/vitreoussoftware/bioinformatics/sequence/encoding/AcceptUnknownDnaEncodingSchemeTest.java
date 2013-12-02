package com.vitreoussoftware.bioinformatics.sequence.encoding;

import static org.junit.Assert.*;

import org.junit.Test;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;

/**
 * Tests the AcceptUnkownDnaEncodingScheme class
 * @author John
 *
 */
public class AcceptUnknownDnaEncodingSchemeTest {

	/** 
	 * Test Equality for AcceptUnkownDnaEncodingScheme default values against byte codes
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testEqualityByte_defaultValues() throws InvalidDnaFormatException {
		EncodingScheme scheme = new AcceptUnknownDnaEncodingScheme();
		assertEquals(AcceptUnknownDnaEncodingScheme.A, scheme.getValue('A'));
		assertEquals(AcceptUnknownDnaEncodingScheme.T, scheme.getValue('T'));
		assertEquals(AcceptUnknownDnaEncodingScheme.C, scheme.getValue('C'));
		assertEquals(AcceptUnknownDnaEncodingScheme.G, scheme.getValue('G'));
		assertEquals(AcceptUnknownDnaEncodingScheme.U, scheme.getValue('U'));
	}
	
	/** 
	 * Test Equality for AcceptUnkownDnaEncodingScheme, with same start
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testEquality_toSelf() throws InvalidDnaFormatException {
		BasePair bp = AcceptUnknownDnaEncodingScheme.create('A');
		assertEquals(bp, bp);
	}
	
	/** 
	 * Test Equality for AcceptUnkownDnaEncodingScheme default values
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testEquality_defaultValues() throws InvalidDnaFormatException {
		assertEquals(AcceptUnknownDnaEncodingScheme.A, AcceptUnknownDnaEncodingScheme.create('A'));
		assertEquals(AcceptUnknownDnaEncodingScheme.T, AcceptUnknownDnaEncodingScheme.create('T'));
		assertEquals(AcceptUnknownDnaEncodingScheme.C, AcceptUnknownDnaEncodingScheme.create('C'));
		assertEquals(AcceptUnknownDnaEncodingScheme.G, AcceptUnknownDnaEncodingScheme.create('G'));
		assertEquals(AcceptUnknownDnaEncodingScheme.U, AcceptUnknownDnaEncodingScheme.create('U'));
	}
	
	/** 
	 * Test Equality for AcceptUnkownDnaEncodingScheme, with same start
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testEquality_sameStart() throws InvalidDnaFormatException {
		assertEquals(AcceptUnknownDnaEncodingScheme.create('A'), AcceptUnknownDnaEncodingScheme.create('A'));
	}
	
	/** 
	 * Test Equality for AcceptUnkownDnaEncodingScheme, with different cases
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testEquality_differentCase() throws InvalidDnaFormatException {
		assertEquals(AcceptUnknownDnaEncodingScheme.create('A'), AcceptUnknownDnaEncodingScheme.create('a'));
	}
	
	/** 
	 * Test Equality for AcceptUnkownDnaEncodingScheme, different base pair
	 * @throws InvalidDnaFormatException
	 */
	public void testEquality_notSame() throws InvalidDnaFormatException {
		assertFalse(AcceptUnknownDnaEncodingScheme.create('A').equals(AcceptUnknownDnaEncodingScheme.create('T')));
	}
	
	/** 
	 * Test Equality for AcceptUnkownDnaEncodingScheme, against N
	 * @throws InvalidDnaFormatException
	 */
	public void testEquality_againstN() throws InvalidDnaFormatException {
		assertFalse(AcceptUnknownDnaEncodingScheme.create('A').equals(AcceptUnknownDnaEncodingScheme.create('N')));
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_A() throws InvalidDnaFormatException {
		assertEquals("A", AcceptUnknownDnaEncodingScheme.create('A').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_a() throws InvalidDnaFormatException {
		assertEquals("A", AcceptUnknownDnaEncodingScheme.create('a').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_T() throws InvalidDnaFormatException {
		assertEquals("T", AcceptUnknownDnaEncodingScheme.create('T').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_t() throws InvalidDnaFormatException {
		assertEquals("T", AcceptUnknownDnaEncodingScheme.create('t').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_C() throws InvalidDnaFormatException {
		assertEquals("C", AcceptUnknownDnaEncodingScheme.create('C').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_c() throws InvalidDnaFormatException {
		assertEquals("C", AcceptUnknownDnaEncodingScheme.create('c').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_G() throws InvalidDnaFormatException {
		assertEquals("G", AcceptUnknownDnaEncodingScheme.create('G').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_g() throws InvalidDnaFormatException {
		assertEquals("G", AcceptUnknownDnaEncodingScheme.create('g').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_U() throws InvalidDnaFormatException {
		assertEquals("U", AcceptUnknownDnaEncodingScheme.create('U').toString());
	}
	
	/**
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testCreation_u() throws InvalidDnaFormatException {
		assertEquals("U", AcceptUnknownDnaEncodingScheme.create('u').toString());
	}

}
