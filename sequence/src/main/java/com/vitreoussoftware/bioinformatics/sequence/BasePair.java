package com.vitreoussoftware.bioinformatics.sequence;

import com.vitreoussoftware.bioinformatics.sequence.encoding.EncodingScheme;
import lombok.Getter;
import lombok.val;

/**
 * Represents a Nucleotide Base Pair
 *
 * @author John
 */
public class BasePair {
    private final byte nucleotide;

    @Getter
    private EncodingScheme encodingScheme;

    /**
     * Create a new base pair from the given nucleotide
     *
     * @param encodingScheme the character representation
     * @param value          the nucleotide identifier
     * @throws InvalidDnaFormatException The given nucleotide was not valid
     */
    protected BasePair(final byte value, final EncodingScheme encodingScheme) {
        this.nucleotide = value;
        this.encodingScheme = encodingScheme;
    }

    /**
     * Create a new base pair from the given nucleotide
     *
     * @param nucleotide the nucleotide identifier
     * @return the base pair representation
     * @throws InvalidDnaFormatException The given nucleotide was not valid
     */
    public static BasePair create(final char nucleotide, final EncodingScheme encodingScheme) throws InvalidDnaFormatException {
        final byte value = encodingScheme.getValue(nucleotide);
        return new BasePair(value, encodingScheme);
    }

	/**
	 * Return the complement of the current {@link BasePair}
	 * @return the complementary {@link BasePair}
	 */
	public BasePair complement() {
		return encodingScheme.complement(this);
	}

    public int distance(final BasePair bp) {
        if (this.equals(bp))
            return 0;
        else
            return 1;
    }

    /**
     * Determines if the given byte representation is equal to this basepair
     *
     * @param nucleotide the byte representation
     * @return the result
     */
    public boolean equals(final byte nucleotide) {
        return nucleotide == this.nucleotide;
    }

    public char toChar() {
        try {
            return this.encodingScheme.toChar(this.nucleotide);
        } catch (final InvalidDnaFormatException e) {
            // this should never fail since the encoding came from the encapsulated BasePair
            throw new InvalidDnaFormatException("We hit an unknown basepair encoding converting to string\n");
        }

    }

	/**
	 * Return the underlying byte representation of the BasePair
	 *
	 * @return the byte representation
	 */
	public byte getValue() {
		return nucleotide;
	}



    @Override
    public String toString() {
        try {
            return this.encodingScheme.toString(this.nucleotide);
        } catch (InvalidDnaFormatException e) {
            // this should never fail since the encoding came from the encapsulated BasePair
            e.printStackTrace();
            throw new InvalidDnaFormatException("We hit an unknown basepair encoding converting to string\n");
        }
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + nucleotide;
        return result;
    }

    @Override
    public boolean equals(final Object basepair) {
        if (this == basepair)
            return true;
        if (basepair == null)
            return false;
        if (getClass() == basepair.getClass())
        {
            val other = (BasePair) basepair;
            return this.equals(other.nucleotide);
        }
        else if (basepair.getClass() == Byte.class)
        {
            return this.equals((byte)basepair);
        }

        return false;
    }
}
