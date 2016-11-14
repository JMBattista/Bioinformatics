package com.vitreoussoftware.bioinformatics.sequence.encoding;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;


/**
 * Represents a Nucleotide Base Pair
 *
 * @author John
 */
public final class AcceptUnknownDnaEncodingScheme implements EncodingScheme {
    // Due to a quirk in the java language we have to use the negative sign to set the 8th bit to 1
    // We will restrict usage of the 8th bit to mean 'special thing happening here
    private static final byte NUCLEOTIDE_A = 0b0_00_00_10;
    private static final byte NUCLEOTIDE_C = 0b0_00_10_00;
    private static final byte NUCLEOTIDE_G = 0b0_00_01_00;
    private static final byte NUCLEOTIDE_T = 0b0_00_00_01;
    private static final byte NUCLEOTIDE_U = 0b0_01_00_01;
    private static final byte NUCLEOTIDE_R = NUCLEOTIDE_A | NUCLEOTIDE_G;
    private static final byte NUCLEOTIDE_Y = NUCLEOTIDE_C | NUCLEOTIDE_T | NUCLEOTIDE_U;
    private static final byte NUCLEOTIDE_K = NUCLEOTIDE_G | NUCLEOTIDE_T | NUCLEOTIDE_U;
    private static final byte NUCLEOTIDE_M = NUCLEOTIDE_A | NUCLEOTIDE_C;
    private static final byte NUCLEOTIDE_S = NUCLEOTIDE_C | NUCLEOTIDE_G;
    private static final byte NUCLEOTIDE_W = NUCLEOTIDE_C | NUCLEOTIDE_G | 0b0_10_00_00;
    private static final byte NUCLEOTIDE_B = NUCLEOTIDE_C | NUCLEOTIDE_G | NUCLEOTIDE_T | NUCLEOTIDE_U;
    private static final byte NUCLEOTIDE_D = NUCLEOTIDE_A | NUCLEOTIDE_G | NUCLEOTIDE_T | NUCLEOTIDE_U;
    private static final byte NUCLEOTIDE_H = NUCLEOTIDE_A | NUCLEOTIDE_C | NUCLEOTIDE_T | NUCLEOTIDE_U;
    private static final byte NUCLEOTIDE_V = NUCLEOTIDE_A | NUCLEOTIDE_C | NUCLEOTIDE_G;
    private static final byte NUCLEOTIDE_N = NUCLEOTIDE_A | NUCLEOTIDE_C | NUCLEOTIDE_G | NUCLEOTIDE_T | NUCLEOTIDE_U;
    private static final byte NUCLEOTIDE_X = 0b1_00_00_00;
    private static final byte NUCLEOTIDE_GAP = 0b0_00_00_00;

    public static EncodingScheme instance = new AcceptUnknownDnaEncodingScheme();

    /**
     * Adenine
     */
    public static final BasePair A = create('A');

    /**
     * Cytosine
     */
    public static final BasePair C = create('C');

    /**
     * Guanine
     */
    public static final BasePair G = create('G');

    /**
     * Thymine
     */
    public static final BasePair T = create('T');

    /**
     * Uracil (replaces T in RNA)
     */
    public static final BasePair U = create('U');

    /**
     * A or G puRine
     */
    public static final BasePair R = create('R');

    /**
     * C, T, or U pYrimidines
     */
    public static final BasePair Y = create('Y');

    /**
     * G, T or U bases which are Ketones
     */
    public static final BasePair K = create('K');

    /**
     * A or C bases with aMino groups
     */
    public static final BasePair M = create('M');

    /**
     * C or G Strong interaction
     */
    public static final BasePair S = create('S');

    /**
     * C or G Weak interaction
     */
    public static final BasePair W = create('W');

    /**
     * not A (i.e. C, G, T or U)
     */
    public static final BasePair B = create('B');

    /**
     * not C (i.e. A, G, T or U)
     */
    public static final BasePair D = create('D');

    /**
     * not G (i.e. A, C, T or U)
     */
    public static final BasePair H = create('H');

    /**
     * neither T nor U (i.e. A, C or G)
     */
    public static final BasePair V = create('V');

    /**
     * A, C, G, T, or U uNknown
     */
    public static final BasePair N = create('N');

    /**
     * Masked
     */
    public static final BasePair X = create('X');

    /**
     * Gap of indeterminate length
     */
    public static final BasePair GAP = create('-');

    /**
     * Create an instance of the encoding scheme
     */
    public AcceptUnknownDnaEncodingScheme() {
    }

    /**
     * Create a new base pair from the given nucleotide
     *
     * @param nucleotide the nucleotide identifier
     * @return the base pair representation
     * @throws InvalidDnaFormatException The given nucleotide was not valid
     */
    private static BasePair create(final char nucleotide) throws InvalidDnaFormatException {
        return BasePair.create(nucleotide, AcceptUnknownDnaEncodingScheme.instance);
    }

    @Override
    public byte getValue(final char nucleotide) throws InvalidDnaFormatException {
        switch (nucleotide) {
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
    public char toChar(final byte nucleotide) throws InvalidDnaFormatException {
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
    public BasePair toBasePair(final byte nucleotide) throws InvalidDnaFormatException {
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

    @Override
    public BasePair fromCharacter(final Character character) {
        return AcceptUnknownDnaEncodingScheme.create(character);
    }

    @Override
    public BasePair flip(BasePair basePair) {
        return basePair;
    }
}
