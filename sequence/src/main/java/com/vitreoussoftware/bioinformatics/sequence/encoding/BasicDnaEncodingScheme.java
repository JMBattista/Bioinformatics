package com.vitreoussoftware.bioinformatics.sequence.encoding;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import lombok.val;


/**
 * Represents a Nucleotide Base Pair
 * Uses the basic grade school scheme of A/T C/G. It does not support multiple value inputs.
 *
 * @author John
 */
public final class BasicDnaEncodingScheme implements EncodingScheme {
    // Due to a quirk in the java language we have to use the negative sign to set the 8th bit to 1
    // We will restrict usage of the 8th bit to mean 'special thing happening here
    private static final byte NUCLEOTIDE_A = 0b0_00_00_10;
    private static final byte NUCLEOTIDE_C = 0b0_00_10_00;
    private static final byte NUCLEOTIDE_G = 0b0_00_01_00;
    private static final byte NUCLEOTIDE_T = 0b0_00_00_01;

    private static final byte SHIFT_RIGHT = NUCLEOTIDE_A | NUCLEOTIDE_C;

    public static final EncodingScheme instance = new BasicDnaEncodingScheme();

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
     * Create an instance of the encoding scheme
     */
    public BasicDnaEncodingScheme() {
    }

    /**
     * Create a new base pair from the given nucleotide
     *
     * @param nucleotide the nucleotide identifier
     * @return the base pair representation
     * @throws InvalidDnaFormatException The given nucleotide was not valid
     */
    public static BasePair create(final char nucleotide) throws InvalidDnaFormatException {
        return BasePair.create(nucleotide, BasicDnaEncodingScheme.instance);
    }

    @Override
    public BasePair fromCharacter(final Character character) {
        return BasicDnaEncodingScheme.create(character);
    }

    @Override
    public BasePair complement(final BasePair basePair) {
        if (!basePair.getEncodingScheme().equals(this)) {
            return complement(fromCharacter(basePair.toChar()));
        }

        val nucleotide = basePair.getValue();
        if ((nucleotide & SHIFT_RIGHT) != 0)
            return toBasePair((byte) (nucleotide >>> 1));
        return toBasePair((byte) (nucleotide << 1));
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
    public char toChar(final byte nucleotide) throws InvalidDnaFormatException {
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
            default:
                throw new InvalidDnaFormatException("There was an invalid conversion request with byte representation " + nucleotide);
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
