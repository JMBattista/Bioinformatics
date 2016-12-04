package com.vitreoussoftware.bioinformatics.sequence.encoding;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;

/**
 * Encoding Scheme for DNA sequence
 * @author John
 *
 */
public interface EncodingScheme {

	/**
	 * Create a new base pair byte code based on the nucleotide and current encoding schema
	 *
	 * @param nucleotide the nucleotide identifier
	 * @return the base pair byte representation
	 * @throws InvalidDnaFormatException The given nucleotide was not valid
	 */
	byte getValue(char nucleotide) throws InvalidDnaFormatException;

	/**
	 * Return the string representation of the encoded nucleotide based on current encoding schema.
	 *
	 * @param nucleotide the byte representation
	 * @return the string representation
	 * @throws InvalidDnaFormatException 
	 */
	default String toString(final byte nucleotide) throws InvalidDnaFormatException {
        return toChar(nucleotide) + "";
    }

	/**
	 * Return the character representation of the encoded nucleotide based on the current encoding schema.
	 *
	 * @param nucleotide the byte representation
	 * @return the character representation
	 * @throws InvalidDnaFormatException 
	 */
	char toChar(byte nucleotide);

	/**
	 * Return the BasePair representation encoded nucleotide based on the current encoding schema.
	 *
	 * @param nucleotide the byte representation
	 * @return the BasePair
	 * @throws InvalidDnaFormatException 
	 */
	BasePair toBasePair(byte nucleotide);

	/**
	 * Return the BasePair representation encoded nucleotide based on the current encoding schema.
	 * @param character The character representation of the nucleotide
	 *
	 * @return the BasePair
	 * @throws InvalidDnaFormatException
	 */
    BasePair fromCharacter(Character character);
}
