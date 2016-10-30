package com.vitreoussoftware.bioinformatics.sequence.encoding;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;


/**
 * Represents a Nucleotide Base Pair
 * @author John
 *
 */
    public final class AcceptUnknownDnaEncodingScheme implements EncodingScheme {
    // Due to a quirk in the java language we have to use the negative sign to set the 8th bit to 1
    // We will restrict usage of the 8th bit to mean 'special thing happening here
	private static final byte NUCLEOTIDE_A =  0b0_00_00_10;
    private static final byte NUCLEOTIDE_C =  0b0_00_10_00;
    private static final byte NUCLEOTIDE_G =  0b0_00_01_00;
    private static final byte NUCLEOTIDE_T =  0b0_00_00_01;
    private static final byte NUCLEOTIDE_U =  0b0_01_00_01;
    private static final byte NUCLEOTIDE_R =  NUCLEOTIDE_A | NUCLEOTIDE_G;
    private static final byte NUCLEOTIDE_Y =  NUCLEOTIDE_C | NUCLEOTIDE_T | NUCLEOTIDE_U;
    private static final byte NUCLEOTIDE_K =  NUCLEOTIDE_G | NUCLEOTIDE_T | NUCLEOTIDE_U;
    private static final byte NUCLEOTIDE_M =  NUCLEOTIDE_A | NUCLEOTIDE_C;
    private static final byte NUCLEOTIDE_S =  NUCLEOTIDE_C | NUCLEOTIDE_G;
    private static final byte NUCLEOTIDE_W =  NUCLEOTIDE_C | NUCLEOTIDE_G | 0b0_10_00_00;
    private static final byte NUCLEOTIDE_B =  NUCLEOTIDE_C | NUCLEOTIDE_G | NUCLEOTIDE_T | NUCLEOTIDE_U;
    private static final byte NUCLEOTIDE_D =  NUCLEOTIDE_A | NUCLEOTIDE_G | NUCLEOTIDE_T | NUCLEOTIDE_U;
    private static final byte NUCLEOTIDE_H =  NUCLEOTIDE_A | NUCLEOTIDE_C | NUCLEOTIDE_T | NUCLEOTIDE_U;
    private static final byte NUCLEOTIDE_V =  NUCLEOTIDE_A | NUCLEOTIDE_C | NUCLEOTIDE_G;
    private static final byte NUCLEOTIDE_N =  NUCLEOTIDE_A | NUCLEOTIDE_C | NUCLEOTIDE_G | NUCLEOTIDE_T | NUCLEOTIDE_U;
    private static final byte NUCLEOTIDE_X =  0b1_00_00_00;
    private static final byte NUCLEOTIDE_GAP = 0b0_00_00_00;

    public static EncodingScheme instance = new AcceptUnknownDnaEncodingScheme();
	
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
	 * Uracil (replaces T in RNA)
	 */
	public static final BasePair U = createSafe('U');

    /**
     * A or G puRine
     */
    public static final BasePair R = createSafe('R');

    /**
     * C, T, or U pYrimidines
     */
    public static final BasePair Y = createSafe('Y');

    /**
     * G, T or U bases which are Ketones
     */
    public static final BasePair K = createSafe('K');

    /**
     * A or C bases with aMino groups
     */
    public static final BasePair M = createSafe('M');

    /**
     *  C or G Strong interaction
     */
    public static final BasePair S = createSafe('S');

    /**
     * C or G Weak interaction
     */
    public static final BasePair W = createSafe('W');

    /**
     * not A (i.e. C, G, T or U)
     */
    public static final BasePair B = createSafe('B');

    /**
     * not C (i.e. A, G, T or U)
     */
    public static final BasePair D = createSafe('D');

    /**
     * not G (i.e. A, C, T or U)
     */
    public static final BasePair H = createSafe('H');

    /**
     * neither T nor U (i.e. A, C or G)
     */
    public static final BasePair V = createSafe('V');

    /**
     * A, C, G, T, or U uNknown
     */
    public static final BasePair N = createSafe('N');

    /**
     * Masked
     */
    public static final BasePair X = createSafe('X');

    /**
     * Gap of indeterminate length
     */
    public static final BasePair GAP = createSafe('-');
	
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
			throw new InvalidDnaFormatException("Bad value passed in statically inside AcceptUnknownDnaEncodingScheme");
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
            case 'r':
            case 'R':
                return NUCLEOTIDE_R;
            case 'y':
            case 'Y':
                return NUCLEOTIDE_Y;
            case 'k':
            case 'K':
                return NUCLEOTIDE_K;
            case 'm':
            case 'M':
                return NUCLEOTIDE_M;
            case 's':
            case 'S':
                return NUCLEOTIDE_S;
            case 'w':
            case 'W':
                return NUCLEOTIDE_W;
            case 'b':
            case 'B':
                return NUCLEOTIDE_B;
            case 'd':
            case 'D':
                return NUCLEOTIDE_D;
            case 'h':
            case 'H':
                return NUCLEOTIDE_H;
            case 'v':
            case 'V':
                return NUCLEOTIDE_V;
            case 'x':
            case 'X':
                return NUCLEOTIDE_X;
            case '-':
                return NUCLEOTIDE_GAP;
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
		case NUCLEOTIDE_U:
			return 'U';
		case NUCLEOTIDE_C:
			return 'C';
		case NUCLEOTIDE_G:
			return 'G';
		case NUCLEOTIDE_N:
			return 'N';
        case NUCLEOTIDE_R:
            return 'R';
        case NUCLEOTIDE_Y:
            return 'Y';
        case NUCLEOTIDE_K:
            return 'K';
        case NUCLEOTIDE_M:
            return 'M';
        case NUCLEOTIDE_S:
            return 'S';
        case NUCLEOTIDE_W:
            return 'W';
        case NUCLEOTIDE_B:
            return 'B';
        case NUCLEOTIDE_D:
            return 'D';
        case NUCLEOTIDE_H:
            return 'H';
        case NUCLEOTIDE_V:
            return 'V';
        case NUCLEOTIDE_X:
            return 'X';
        case NUCLEOTIDE_GAP:
            return '-';

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
        case NUCLEOTIDE_R:
            return R;
        case NUCLEOTIDE_Y:
            return Y;
        case NUCLEOTIDE_K:
            return K;
        case NUCLEOTIDE_M:
            return M;
        case NUCLEOTIDE_S:
            return S;
        case NUCLEOTIDE_W:
            return W;
        case NUCLEOTIDE_B:
            return B;
        case NUCLEOTIDE_D:
            return D;
        case NUCLEOTIDE_H:
            return H;
        case NUCLEOTIDE_V:
            return V;
        case NUCLEOTIDE_X:
            return X;
        case NUCLEOTIDE_GAP:
            return GAP;
		default:
			throw new InvalidDnaFormatException("There was an invalid conversion request with byte representation " + nucleotide);
		}
	}	
}
