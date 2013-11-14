package com.vitreoussoftware.bioinformatics.sequence.encoding;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;


/**
 * Represents a Nucleotide Base Pair
 * @author John
 *
 */
public final class AcceptUnknownDnaEncodingScheme implements EncodingScheme {
	static final byte NUCLEOTIDE_A = 0b00000010;
	static final byte NUCLEOTIDE_T = 0b00000001;
	static final byte NUCLEOTIDE_U = 0b00010000;
	static final byte NUCLEOTIDE_C = 0b00001000;
	static final byte NUCLEOTIDE_G = 0b00000100;
	static final byte NUCLEOTIDE_N = 0b00011111;
	
	public static EncodingScheme instance = new AcceptUnknownDnaEncodingScheme();
	
	/**
	 * The A nucleotide
	 */
	public static final BasePair A = createSafe('A');
	/**
	 * The T nucleotide
	 */
	public static final BasePair T = createSafe('T');
	/**
	 * The U nucleotide for use in RNA
	 */
	public static final BasePair U = createSafe('U');
	/**
	 * The C nucleotide
	 */
	public static final BasePair C = createSafe('C');
	/**
	 * The G nucleotide
	 */
	public static final BasePair G = createSafe('G');
	/**
	 * Represents an unknown nucleotide due to processing
	 */
	public static final BasePair N = createSafe('N');
	
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
			case 'u':
			case 'U':
				return NUCLEOTIDE_U;
			case 'c':
			case 'C':
				return NUCLEOTIDE_C;
			case 'g':
			case 'G':
				return NUCLEOTIDE_G;
			case 'n':
			case 'N':
				return NUCLEOTIDE_N;
			default:
				throw new InvalidDnaFormatException("There was an invalid value for DnaSeqeucne " + nucleotide);
		}
	};
		
	/**
	 * Return the string representation of the encoded nucleotide based on current encoding schema.
	 * @param nucleotide the byte representation
	 * @return the string representation
	 * @throws InvalidDnaFormatException 
	 */
	public String toString(byte nucleotide) throws InvalidDnaFormatException {
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
		case NUCLEOTIDE_N:
			return "N";
		default:
			throw new InvalidDnaFormatException("There was an invalid conversion request with byte representation " + nucleotide);
		}	 
	}

	@Override
	public char toChar(byte nucleotide) throws InvalidDnaFormatException{
		switch (nucleotide) {
		case NUCLEOTIDE_A:
			return 'A';
		case NUCLEOTIDE_T:
			return 'T';
		case NUCLEOTIDE_U:
			return 'U';
		case NUCLEOTIDE_C:
			return 'C';
		case NUCLEOTIDE_G:
			return 'G';
		case NUCLEOTIDE_N:
			return 'N';
		default:
			throw new InvalidDnaFormatException("There was an invalid conversion request with byte representation " + nucleotide);
		}	 
	}

	@Override
	public BasePair toBasePair(byte nucleotide) throws InvalidDnaFormatException {
		switch (nucleotide) {
		case NUCLEOTIDE_A:
			return A;
		case NUCLEOTIDE_T:
			return T;
		case NUCLEOTIDE_C:
			return C;
		case NUCLEOTIDE_G:
			return G;
		case NUCLEOTIDE_U:
			return U;
		case NUCLEOTIDE_N:
			return N;
		default:
			throw new InvalidDnaFormatException("There was an invalid conversion request with byte representation " + nucleotide);
		}
	}	
}
