package com.vitreoussoftare.bioinformatics.dnadata;

/**
 * Represents a Nucleotide Base Pair
 * @author John
 *
 */
public final class BasePair {
	private static final byte NUCLEOTIDE_A = 0x00;
	private static final byte NUCLEOTIDE_T = 0x01;
	private static final byte NUCLEOTIDE_C = 0x02;
	private static final byte NUCLEOTIDE_G = 0x03;
	private static final byte NUCLEOTIDE_N = 0x08;
	
	private final byte nucleotide;
	

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
			case 'n':
			case 'N':
				return new BasePair(NUCLEOTIDE_N);
			default:
				throw new InvalidDnaFormatException("There was an invalid value for DnaSeqeucne " + nucleotide);
		}
	}
	
	/**
	 * The A nucleotide
	 */
	public static final BasePair A = new BasePair((byte) 0x00);
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
	public static final BasePair N = new BasePair(NUCLEOTIDE_N);


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nucleotide;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasePair other = (BasePair) obj;
		if (nucleotide != other.nucleotide)
			return false;
		return true;
	}

	@Override
	public String toString() {
		switch (nucleotide) {
			case NUCLEOTIDE_A:
				return "A";
			case NUCLEOTIDE_T:
				return "T";
			case NUCLEOTIDE_C:
				return "C";
			case NUCLEOTIDE_G:
				return "G";
			case NUCLEOTIDE_N:
				return "N";
			// we should never hit default but it is necessary for compilation
			default:
				return "";
		}
	};
	
	
}
