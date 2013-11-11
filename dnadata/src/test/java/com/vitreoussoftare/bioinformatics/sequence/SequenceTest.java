package com.vitreoussoftare.bioinformatics.sequence;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import com.vitreoussoftare.bioinformatics.sequence.Sequence;
import com.vitreoussoftare.bioinformatics.sequence.InvalidDnaFormatException;

/**
 * Test the Sequence class
 * @author John
 *
 */
public class SequenceTest {

	/**
	 * Test that we can create a new DnaSequence
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testCreation_nominal() throws InvalidDnaFormatException {
		final String basis = "AATT";
		
		Sequence seq = Sequence.fromFasta(basis);
		
		assertEquals(basis, seq.toString());
	}
	
	/**
	 * Test that we can handle the full range of valid input
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testCreation_full() throws InvalidDnaFormatException {
		final String basis = "AATTCCGGUU";
		
		Sequence seq = Sequence.fromFasta(basis);
		
		assertEquals(basis, seq.toString());
	}
	
	/**
	 * Test that some basic invalid input is rejected
	 * @throws InvalidDnaFormatException 
	 */
	@Test(expected=InvalidDnaFormatException.class)
	public void testCreation_badData() throws InvalidDnaFormatException {
		final String basis = "AATT132";
		
		// We expect an error here so don't do anything about it!
		Sequence.fromFasta(basis);
	}
	
	
	/**
	 * Test that empty input is rejected
	 * @throws InvalidDnaFormatException 
	 */
	@Test(expected=InvalidDnaFormatException.class)
	public void testCreation_empty() throws InvalidDnaFormatException {
		final String basis = "";
		
		// We expect an error here so don't do anything about it!
		Sequence.fromFasta(basis);
	}
}
