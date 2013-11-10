package com.vitreoussoftare.bioinformatics.sequence;

/**
 * Represents a Nucleotide Base Pair
 * @author John
 *
 */
public final class BasePair {
	private final byte nucleotide;
	
	static final byte NUCLEOTIDE_A = 0b00000010;
	static final byte NUCLEOTIDE_T = 0b00000011;
	static final byte NUCLEOTIDE_C = 0b00000100;
	static final byte NUCLEOTIDE_G = 0b00000101;
	static final byte NUCLEOTIDE_U = 0b00000111;
	
	/**
	 * The A nucleotide
	 */
	public static final BasePair A = new BasePair(NUCLEOTIDE_A);
	/**
	 * The T nucleotide
	 */
	public static final BasePair T = new BasePair(NUCLEOTIDE_T);;
	/**
	 * The C nucleotide
	 */
	public static final BasePair C = new BasePair(NUCLEOTIDE_C);;
	/**
	 * The G nucleotide
	 */
	public static final BasePair G = new BasePair(NUCLEOTIDE_G);;
	/**
	 * Represents an unknown nucleotide due to processing
	 */
	public static final BasePair U = new BasePair(NUCLEOTIDE_U);


	/**
	 * Create a new base pair from the given nucleotide
	 * @param nucleotide the nucleotide identifier
	 * @throws InvalidDnaFormatException The given nucleotide was not valid
	 */
	private BasePair(byte value) {
		this.nucleotide = value;		
	}
	
	/**
	 * Create a new base pair from the given nucleotide
	 * @param nucleotide the nucleotide identifier
	 * @return the base pair representation
	 * @throws InvalidDnaFormatException The given nucleotide was not valid
	 */
	public static BasePair create(char nucleotide) throws InvalidDnaFormatException
	{	
		switch (nucleotide)
		{
			case 'a':
			case 'A':
				return new BasePair(NUCLEOTIDE_A);
			case 't':
			case 'T':
				return new BasePair(NUCLEOTIDE_T);
			case 'c':
			case 'C':
				return new BasePair(NUCLEOTIDE_C);
			case 'g':
			case 'G':
				return new BasePair(NUCLEOTIDE_G);
			case 'u':
			case 'U':
				return new BasePair(NUCLEOTIDE_U);
			default:
				throw new InvalidDnaFormatException("There was an invalid value for DnaSeqeucne " + nucleotide);
		}
	}
	
	/**
	 * Create a new base pair byte code based on the nucleotide and current encoding schema 
	 * @param nucleotide the nucleotide identifier
	 * @return the base pair byte representation
	 * @throws InvalidDnaFormatException The given nucleotide was not valid
	 */
	static byte getValue(char nucleotide) throws InvalidDnaFormatException {
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nucleotide;
		return result;
	}

	@Override
	public boolean equals(Object basepair) {
		if (this == basepair)
			return true;
		if (basepair == null)
			return false;
		if (getClass() == basepair.getClass())
		{
			BasePair other = (BasePair) basepair;
			return this.equals(other.nucleotide);
		}
		else if (basepair.getClass() == Byte.class)
		{
			return this.equals((byte)basepair);
		}
		
		return false;
	}
	
	/**
	 * Determines if the given byte representation is equal to this basepair  
	 * @param nucleotide the byte representation
	 * @return the result
	 */
	public boolean equals(byte nucleotide) {
		return nucleotide == this.nucleotide;
	}

	@Override
	public String toString() {
		return tostring(nucleotide);
	}

	/**
	 * Return the string representation of the encoded nucleotide based on current eoncoding schema.
	 * @param nucleotide the byte representation
	 * @return the string representation
	 */
	public static String tostring(byte nucleotide) {
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
