package com.vitreoussoftware.bioinformatics.sequence;

import static org.junit.Assert.*;

import lombok.val;
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
	public void testEqualityToSelf() throws InvalidDnaFormatException {
		val bp = BasePair.create('A', scheme);
		assertEquals(bp, bp);
	}
	
	/** 
	 * Test Equality for BasePair, with same start
	 	 */
	@Test
	public void testEqualitySameStart() throws InvalidDnaFormatException {
		assertEquals(BasePair.create('A', scheme), BasePair.create('A', scheme));
	}
	
	/** 
	 * Test Equality for BasePair, with different cases
	 	 */
	@Test
	public void testEqualityDifferentCase() throws InvalidDnaFormatException {
		assertEquals(BasePair.create('A', scheme), BasePair.create('a', scheme));
	}
	
	/** 
	 * Test Equality for BasePair, different base pair
	 	 */
	public void testEqualityNotSame() throws InvalidDnaFormatException {
		assertFalse(BasePair.create('A', scheme).equals(BasePair.create('T', scheme)));
	}
	
	/** 
	 * Test Equality for BasePair, against N
	 	 */
	public void testEqualityAgainstN() throws InvalidDnaFormatException {
		assertFalse(BasePair.create('A', scheme).equals(BasePair.create('N', scheme)));
	}
	
	/**
	 	 */
	@Test
	public void testCreationA() throws InvalidDnaFormatException {
		assertEquals("A", BasePair.create('A', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreationLowerA() throws InvalidDnaFormatException {
		assertEquals("A", BasePair.create('a', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreationT() throws InvalidDnaFormatException {
		assertEquals("T", BasePair.create('T', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreationLowerT() throws InvalidDnaFormatException {
		assertEquals("T", BasePair.create('t', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreationC() throws InvalidDnaFormatException {
		assertEquals("C", BasePair.create('C', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreationLowerC() throws InvalidDnaFormatException {
		assertEquals("C", BasePair.create('c', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreationG() throws InvalidDnaFormatException {
		assertEquals("G", BasePair.create('G', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreationLowerG() throws InvalidDnaFormatException {
		assertEquals("G", BasePair.create('g', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreationU() throws InvalidDnaFormatException {
		assertEquals("U", BasePair.create('U', scheme).toString());
	}
	
	/**
	 	 */
	@Test
	public void testCreationLowerU() throws InvalidDnaFormatException {
		assertEquals("U", BasePair.create('u', scheme).toString());
	}

    /**
          */
    @Test(expected = InvalidDnaFormatException.class)
    public void testCreationCanFAil() throws InvalidDnaFormatException {
        BasePair.create('Q', scheme);
    }

}
