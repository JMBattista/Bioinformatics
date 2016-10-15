package com.vitreoussoftware.bioinformatics.sequence;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.encoding.EncodingScheme;

/**
 * Tests the BasePair class
 * @author John
 *
 */
public class BasePairTest {
	private EncodingScheme scheme;
	
	/**
	 * Setup the test object
	 */
	@Before
	public void setup()
	{
		this.scheme = new AcceptUnknownDnaEncodingScheme();
	}

	/** 
	 * Test Equality for BasePair, with same start
	 	 */
	@Test
	public void testEquality_toSelf() throws InvalidDnaFormatException {
		BasePair bp = BasePair.create('A', scheme);
		assertEquals(bp, bp);
	}
	
	/** 
	 * Test Equality for BasePair, with same start
	 	 */
	@Test
	public void testEquality_sameStart() throws InvalidDnaFormatException {
		assertEquals(BasePair.create('A', scheme), BasePair.create('A', scheme));
	}
	
	/** 
	 * Test Equality for BasePair, with different cases
	 	 */
	@Test
	public void testEquality_differentCase() throws InvalidDnaFormatException {
		assertEquals(BasePair.create('A', scheme), BasePair.create('a', scheme));
	}
	
	/** 
	 * Test Equality for BasePair, different base pair
	 	 */
	public void testEquality_notSame() throws InvalidDnaFormatException {
		assertFalse(BasePair.create('A', scheme).equals(BasePair.create('T', scheme)));
	}
	
	/** 
	 * Test Equality for BasePair, against N
	 	 */
	public void testEquality_againstN() throws InvalidDnaFormatException {
		assertFalse(BasePair.create('A', scheme).equals(BasePair.create('N', scheme)));
	}
	
	/**
	 	 */
	@Test
	public void testCreation_A() throws InvalidDnaFormatException {
		assertEquals("A", BasePair.create('A', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreation_a() throws InvalidDnaFormatException {
		assertEquals("A", BasePair.create('a', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreation_T() throws InvalidDnaFormatException {
		assertEquals("T", BasePair.create('T', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreation_t() throws InvalidDnaFormatException {
		assertEquals("T", BasePair.create('t', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreation_C() throws InvalidDnaFormatException {
		assertEquals("C", BasePair.create('C', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreation_c() throws InvalidDnaFormatException {
		assertEquals("C", BasePair.create('c', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreation_G() throws InvalidDnaFormatException {
		assertEquals("G", BasePair.create('G', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreation_g() throws InvalidDnaFormatException {
		assertEquals("G", BasePair.create('g', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreation_U() throws InvalidDnaFormatException {
		assertEquals("U", BasePair.create('U', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreation_u() throws InvalidDnaFormatException {
		assertEquals("U", BasePair.create('u', scheme).toString());
	}

    /**
          */
    @Test(expected = InvalidDnaFormatException.class)
    public void testCreation_canFAil() throws InvalidDnaFormatException {
        BasePair.create('Q', scheme);
    }

}
