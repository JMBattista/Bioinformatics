package com.vitreoussoftware.bioinformatics.sequence;

import java.util.Optional;

/**
 * Factory for the creation of Sequence objects based on necessary parameters
 * @author John
 *
 */
public interface SequenceFactory {
	
	/**
	 * Create a Sequence object from string input without metadata
	 *
     * @param sequence the string sequence to process
     * @return the sequence object
	 * @throws InvalidDnaFormatException 
	 */
	default Optional<Sequence> fromString(final String sequence) throws InvalidDnaFormatException {
        return fromString("", sequence);
    }

    /**
     * Create a Sequence object from string input
     *
     * @param metadata the string metadata
     * @param sequence the string sequence to process
     * @return the sequence object
          */
    Optional<Sequence> fromString(String metadata, String sequence) throws InvalidDnaFormatException;

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
