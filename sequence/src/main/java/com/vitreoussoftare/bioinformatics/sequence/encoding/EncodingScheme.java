package com.vitreoussoftare.bioinformatics.sequence.encoding;

import com.vitreoussoftare.bioinformatics.sequence.InvalidDnaFormatException;

/**
 * Encoding Scheme for DNA sequence
 * @author John
 *
 */
public interface EncodingScheme {

	/**
	 * Create a new base pair byte code based on the nucleotide and current encoding schema 
	 * @param nucleotide the nucleotide identifier
	 * @return the base pair byte representation
	 * @throws InvalidDnaFormatException The given nucleotide was not valid
	 */
	byte getValue(char nucleotide) throws InvalidDnaFormatException;

	/**
	 * Return the string representation of the encoded nucleotide based on current eoncoding schema.
	 * @param nucleotide the byte representation
	 * @return the string representation
	 */
	String toString(byte nucleotide);

}
