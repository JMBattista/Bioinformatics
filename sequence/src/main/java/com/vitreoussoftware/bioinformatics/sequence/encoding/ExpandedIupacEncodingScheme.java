package com.vitreoussoftware.bioinformatics.sequence.encoding;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import lombok.val;


/**
 * Represents a Nucleotide Base Pair based on the IUPAC encoding scheme with support for Gaps and X
 * {@see http://www.bioinformatics.org/sms/iupac.html}
 *
 * @author John
 */
public final class ExpandedIupacEncodingScheme implements EncodingScheme {
    // Due to a quirk in the java language we have to use the negative sign to set the 8th bit to 1
    // We will restrict usage of the 8th bit to mean 'special thing happening here
    /**
     * Indicates that we're dealing with an ambiguous base pair.
     */
    private static final byte AMBIGUITY = 0b0_10_00_00;
    /**
     * Indicates that the nucleotides complement is just its self.
     */
    private static final byte REFLECTED = 0b1_00_00_00;

    private static final byte NUCLEOTIDE_A = 0b0_00_00_10;
    private static final byte NUCLEOTIDE_T = 0b0_00_00_01;
    private static final byte NUCLEOTIDE_U = 0b0_01_00_01;
    private static final byte NUCLEOTIDE_C = 0b0_00_10_00;
    private static final byte NUCLEOTIDE_G = 0b0_00_01_00;
    private static final byte NUCLEOTIDE_R = NUCLEOTIDE_A | NUCLEOTIDE_G | AMBIGUITY;                // 0b0_10_01_10
    private static final byte NUCLEOTIDE_Y = NUCLEOTIDE_C | NUCLEOTIDE_T | NUCLEOTIDE_U | AMBIGUITY; // 0b0_11_10_01
    private static final byte NUCLEOTIDE_K = NUCLEOTIDE_G | NUCLEOTIDE_T | NUCLEOTIDE_U | AMBIGUITY; // 0b0_11_01_01
    private static final byte NUCLEOTIDE_M = NUCLEOTIDE_A | NUCLEOTIDE_C | AMBIGUITY;                // 0b0_10_10_10
    private static final byte NUCLEOTIDE_S = NUCLEOTIDE_C | NUCLEOTIDE_G | AMBIGUITY | REFLECTED;    // 0b1_10_11_00
    private static final byte NUCLEOTIDE_W = NUCLEOTIDE_A | NUCLEOTIDE_T | AMBIGUITY | REFLECTED;    // 0b1_10_00_11
    private static final byte NUCLEOTIDE_B = NUCLEOTIDE_C | NUCLEOTIDE_G | NUCLEOTIDE_T | NUCLEOTIDE_U | AMBIGUITY;    // 0b0_11_11_01
    private static final byte NUCLEOTIDE_D = NUCLEOTIDE_A | NUCLEOTIDE_G | NUCLEOTIDE_T | NUCLEOTIDE_U | AMBIGUITY;    // 0b0_11_01_11
    private static final byte NUCLEOTIDE_H = NUCLEOTIDE_A | NUCLEOTIDE_C | NUCLEOTIDE_T | NUCLEOTIDE_U | AMBIGUITY;    // 0b0_11_10_11
    private static final byte NUCLEOTIDE_V = NUCLEOTIDE_A | NUCLEOTIDE_C | NUCLEOTIDE_G | AMBIGUITY;                   // 0b0_10_11_10
    private static final byte NUCLEOTIDE_N = NUCLEOTIDE_A | NUCLEOTIDE_C | NUCLEOTIDE_G | NUCLEOTIDE_T | NUCLEOTIDE_U | AMBIGUITY | REFLECTED;
    private static final byte NUCLEOTIDE_X = REFLECTED | AMBIGUITY;
    private static final byte NUCLEOTIDE_GAP = 0b0_00_00_00;

    /**
     * Used to test if the encoded nucleotide contains higher order bits or not
     */
    private static final byte LOWER_ORDER = 0b0_00_11_11;
    /**
     * Used to test if the byte contains non ambiguous values that can be converted by a right shift
     */
    private static final byte SHIFT_RIGHT = NUCLEOTIDE_C | NUCLEOTIDE_A;
    /**
     * Indicates that the Nucleotide is ambiguous and could be both C and G
     */
    private static final int AMBIGUOUS_CG = AMBIGUITY | NUCLEOTIDE_C | NUCLEOTIDE_G;
    /**
     * Indicates that the Nucleotide is ambiguous and could be both A and T
     */
    private static final int AMBIGUOUS_AT = AMBIGUITY | NUCLEOTIDE_A | NUCLEOTIDE_T;
    /**
     * Indicates that the Nucleotide is ambiguous and could be A/T/U
     */
    private static final int AMBIGUOUS_ATU = AMBIGUOUS_AT | NUCLEOTIDE_U;
    /**
     * Indicates that the Nucleotide is ambiguous and any of A/T/C/G
     */
    private static final int AMBIGUITY_ANY = AMBIGUITY | NUCLEOTIDE_A | NUCLEOTIDE_T | NUCLEOTIDE_C | NUCLEOTIDE_G;

    public static final EncodingScheme instance = new ExpandedIupacEncodingScheme();

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
     * Create a new base pair from the given nucleotide
     *
     * @param nucleotide the nucleotide identifier
     * @return the base pair representation
     * @throws InvalidDnaFormatException The given nucleotide was not valid
     */
    private static BasePair create(final char nucleotide) throws InvalidDnaFormatException {
        return BasePair.create(nucleotide, ExpandedIupacEncodingScheme.instance);
    }

    @Override
    public byte getValue(final char nucleotide) throws InvalidDnaFormatException {
        switch (nucleotide) {
            case 'x':
            case 'X':
                return NUCLEOTIDE_X;
            case '-':
                return NUCLEOTIDE_GAP;
            default:
                return IupacEncodingScheme.instance.getValue(nucleotide);
        }
    }

    @Override
    public char toChar(final byte nucleotide) throws InvalidDnaFormatException {
        switch (nucleotide) {
            case NUCLEOTIDE_X:
                return 'X';
            case NUCLEOTIDE_GAP:
                return '-';
            default:
                return IupacEncodingScheme.instance.toChar(nucleotide);
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
        return ExpandedIupacEncodingScheme.create(character);
    }

    @Override
    public BasePair complement(final BasePair basePair) {
        // Iupac and ExpandedIupac use the same conversion under the hood so share it.
        if (!(basePair.getEncodingScheme().equals(this)
                || basePair.getEncodingScheme().equals(IupacEncodingScheme.instance))) {
            return complement(fromCharacter(basePair.toChar()));
        }

        val nucleotide = basePair.getValue();
        if ((nucleotide & AMBIGUITY) == 0) {
            if ((nucleotide & SHIFT_RIGHT) != 0)
                return toBasePair((byte) (nucleotide >>> 1));
            return toBasePair((byte) ((nucleotide & LOWER_ORDER) << 1));
        } else if ((nucleotide & REFLECTED) != 0) {
            return basePair;
        } else if ((nucleotide & AMBIGUITY_ANY) > AMBIGUOUS_CG) {
            return toBasePair((byte) (nucleotide ^ (AMBIGUOUS_ATU ^ AMBIGUITY)));
        } else if ((nucleotide & AMBIGUOUS_AT) == AMBIGUOUS_AT) {
            return toBasePair((byte) (nucleotide ^ (AMBIGUOUS_CG ^ AMBIGUITY)));
        } else {
            return toBasePair((byte) (nucleotide ^ (LOWER_ORDER | NUCLEOTIDE_U)));
        }
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        return obj.getClass().equals(instance.getClass());
    }
}
