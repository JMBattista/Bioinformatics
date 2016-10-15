package com.vitreoussoftware.bioinformatics.sequence.encoding;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;


/**
 * Represents a Nucleotide Base Pair
 * Uses the basic grade school scheme of A/T C/G. It does not support multiple value inputs.
 * @author John
 *
 */
    public final class BasicDnaEncodingScheme implements EncodingScheme {
    // Due to a quirk in the java language we have to use the negative sign to set the 8th bit to 1
    // We will restrict usage of the 8th bit to mean 'special thing happening here
	static final byte NUCLEOTIDE_A =  0b0_00_00_10;
    static final byte NUCLEOTIDE_C =  0b0_00_10_00;
    static final byte NUCLEOTIDE_G =  0b0_00_01_00;
    static final byte NUCLEOTIDE_T =  0b0_00_00_01;

    public static EncodingScheme instance = new BasicDnaEncodingScheme();

	/**
	 * Adenine
	 */
	public static final BasePair A = createSafe('A');

    /**
     * Cytosine
     */
    public static final BasePair C = createSafe('C');

    /**
     * Guanine
     */
    public static final BasePair G = createSafe('G');

    /**
	 * Thymine
	 */
	public static final BasePair T = createSafe('T');

	/**
	 * Create an instance of the encoding scheme
	 */
	public BasicDnaEncodingScheme() {
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
		return BasePair.create(nucleotide, BasicDnaEncodingScheme.instance);
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
            default:
				throw new InvalidDnaFormatException("There was an invalid value for DnaSequecne " + nucleotide);
		}
	}

	@Override
	public char toChar(byte nucleotide) throws InvalidDnaFormatException{
		switch (nucleotide) {
		case NUCLEOTIDE_A:
			return 'A';
		case NUCLEOTIDE_T:
			return 'T';
		case NUCLEOTIDE_C:
			return 'C';
		case NUCLEOTIDE_G:
			return 'G';
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
		default:
			throw new InvalidDnaFormatException("There was an invalid conversion request with byte representation " + nucleotide);
		}
	}	
}
