package com.vitreoussoftare.bioinformatics.sequence.encoding;

import com.vitreoussoftare.bioinformatics.sequence.BasePair;
import com.vitreoussoftare.bioinformatics.sequence.BasePairTest;
import com.vitreoussoftare.bioinformatics.sequence.InvalidDnaFormatException;

/**
 * Represents a Nucleotide Base Pair
 * @author John
 *
 */
public final class AcceptUnknownDnaEncodingScheme implements EncodingScheme {
	static final byte NUCLEOTIDE_A = 0b00000010;
	static final byte NUCLEOTIDE_T = 0b00000011;
	static final byte NUCLEOTIDE_C = 0b00000100;
	static final byte NUCLEOTIDE_G = 0b00000101;
	static final byte NUCLEOTIDE_U = 0b00000111;
	
	public static EncodingScheme instance = new AcceptUnknownDnaEncodingScheme();
	
	/**
	 * The A nucleotide
	 */
	public static final BasePair A = createSafe('A');
	/**
	 * The T nucleotide
	 */
	public static final BasePair T = createSafe('T');;
	/**
	 * The C nucleotide
	 */
	public static final BasePair C = createSafe('C');;
	/**
	 * The G nucleotide
	 */
	public static final BasePair G = createSafe('G');;
	/**
	 * Represents an unknown nucleotide due to processing
	 */
	public static final BasePair U = createSafe('U');
	
	/**
	 * Create an instance of the encoding scheme 
	 */
	public AcceptUnknownDnaEncodingScheme() {
	}

	private static BasePair createSafe(char value) {
		try {
			return create(value);
		}
		catch (InvalidDnaFormatException e)
		{
			// this should never happen because createSafe is called inside the class and controlled to only send valid values
			throw new RuntimeException("Bad value passed in statically inside AcceptUnknownDnaEncodingScheme");
		}
	}

	/**
	 * Create a new base pair from the given nucleotide
	 * @param nucleotide the nucleotide identifier
	 * @return the base pair representation
	 * @throws InvalidDnaFormatException The given nucleotide was not valid
	 */
	public static BasePair create(char nucleotide) throws InvalidDnaFormatException
	{	
		return BasePair.create(nucleotide, AcceptUnknownDnaEncodingScheme.instance);
	}
	
	@Override
	public byte getValue(char nucleotide) throws InvalidDnaFormatException {
		switch (nucleotide)
		{
			case 'a':
			case 'A':
				return NUCLEOTIDE_A;
			case 't':
			case 'T':
				return NUCLEOTIDE_T;
			case 'c':
			case 'C':
				return NUCLEOTIDE_C;
			case 'g':
			case 'G':
				return NUCLEOTIDE_G;
			case 'u':
			case 'U':
				return NUCLEOTIDE_U;
			default:
				throw new InvalidDnaFormatException("There was an invalid value for DnaSeqeucne " + nucleotide);
		}
	};
		
	/**
	 * Return the string representation of the encoded nucleotide based on current eoncoding schema.
	 * @param nucleotide the byte representation
	 * @return the string representation
	 */
	public String toString(byte nucleotide) {
		switch (nucleotide) {
		case NUCLEOTIDE_A:
			return "A";
		case NUCLEOTIDE_T:
			return "T";
		case NUCLEOTIDE_C:
			return "C";
		case NUCLEOTIDE_G:
			return "G";
		case NUCLEOTIDE_U:
			return "U";
		// we should never hit default but it is necessary for compilation
		default:
			return "";
		}	 
	}	
}
