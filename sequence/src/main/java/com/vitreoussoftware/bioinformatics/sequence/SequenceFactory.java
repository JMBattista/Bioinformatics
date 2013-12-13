package com.vitreoussoftware.bioinformatics.sequence;

import java.util.Optional;

/**
 * Factory for the creation of Sequence objects based on necessary parameters
 * @author John
 *
 */
public interface SequenceFactory {
	
	/**
	 * Create a Sequence object from string input
	 *
     * @param sequence the string sequence to process
     * @return the sequence object
	 * @throws InvalidDnaFormatException 
	 */
	Optional<Sequence> fromString(String sequence) throws InvalidDnaFormatException;
	
	/**
	 * Create a Sequence object from another sequence object.
	 * Used for changing encoding schemes.
	 *
     * @param sequence the sequence to re-encode
     * @return the Sequence object
	 * @throws InvalidDnaFormatException 
	 */
	public Optional<Sequence> fromSequence(Sequence sequence) throws InvalidDnaFormatException;

}
