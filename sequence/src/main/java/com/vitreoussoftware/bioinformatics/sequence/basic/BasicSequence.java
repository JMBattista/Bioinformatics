package com.vitreoussoftware.bioinformatics.sequence.basic;

import com.google.common.base.Preconditions;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.encoding.EncodingScheme;
import lombok.NonNull;
import lombok.val;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static javax.swing.text.html.HTML.Tag.HEAD;

/**
 * A DNA Sequence representation
 *
 * @author John
 */
public final class BasicSequence implements Sequence {
	@NonNull private final byte[] sequence;
    @NonNull private final EncodingScheme encodingScheme;
    private final String metadata;

    private BasicSequence(EncodingScheme encodingSheme, int size, String metadata)
	{
        Preconditions.checkArgument(size > 0, "Cannot construct an empty Sequence");
        this.sequence = new byte[size];
        this.encodingScheme = encodingSheme;
        this.metadata = metadata;
	}

    /**
     * Create a new Sequence from a l{@link List<BasePair>}. We assume the first {@link BasePair} represents the
     * {@link EncodingScheme} of the whole {@link List<BasePair>}. If you have mixed {@link EncodingScheme} in the
     * {@link List<BasePair>} the {@link Sequence} won't work properly.
     *
     * @param sequence the {@link List<BasePair>} that make up this {@link Sequence}
     * @return the encoded {@link Sequence} created from the {@see sequence}
     */
    public static Optional<Sequence> create(final List<BasePair> sequence) {
        if (sequence == null || sequence.size() == 0)
            return Optional.empty();

		val encodingSheme = sequence.get(0).getEncodingScheme();
        val seq = new BasicSequence(encodingSheme, sequence.size(), null);

		val iter = sequence.iterator();
		for (int i = 0; i < sequence.size(); i++) {
			seq.sequence[i] = seq.encodingScheme.getValue(iter.next().toChar());
		}

        return Optional.of(seq);
    }

    /**
     * Create a new Sequence from a string and the given encoding scheme
     *
     * @param sequence      the string sequence to encode
     * @param encodingSheme the scheme to use for encoding
     * @return the encoded sequence
     */
    public static Optional<Sequence> create(final String sequence, final EncodingScheme encodingSheme) {
        return create("", sequence, encodingSheme);
    }

    /**
     * Create a new Sequence from a string and the given encoding scheme
     *
     * @param metadata      the string metadata for the sequence
     * @param sequence      the string sequence to encode
     * @param encodingSheme the scheme to use for encoding
     * @return the encoded sequence
     */
    public static Optional<Sequence> create(final String metadata, final String sequence, final EncodingScheme encodingSheme) {
        try {
            return Optional.of(createWithError(metadata, sequence, encodingSheme));
        } catch (final InvalidDnaFormatException e) {
            System.err.println(e.getMessage());
            System.err.println("\t" + sequence);
            return Optional.empty();
        }
    }

    /**
     * Create a new Sequence from a string and the given encoding scheme
     *
     * @param sequence      the string sequence to encode
     * @param encodingSheme the scheme to use for encoding
     * @return the encoded sequence
     */

    public static Sequence createWithError(final String metadata, final String sequence, final EncodingScheme encodingSheme) throws InvalidDnaFormatException {
        val seq = new BasicSequence(encodingSheme, sequence.length(), metadata);

        for (int i = 0; i < sequence.length(); i++) {
            seq.sequence[i] = encodingSheme.getValue(sequence.charAt(i));
        }

        return seq;
    }


    @Override
    public BasePair get(final int index) {
        try {
            return this.encodingScheme.toBasePair(sequence[index]);
        } catch (final InvalidDnaFormatException e) {
            // this should not have happened because we encoded everything using this scheme already
            e.printStackTrace();
            throw new InvalidDnaFormatException("We failed to decode a value that was previously encoded in by the same encoding scheme, for value: " + sequence[index] + " encoding scheme " + this.encodingScheme.getClass().getName());
        }
    }

    @Override
    public String getMetadata() {
        return this.metadata;
    }

    @Override
	public String toString() {
		val sb = new StringBuilder();
		try {
			for (final byte bp : sequence) {
				sb.append(this.encodingScheme.toString(bp));
			}
		} catch (final InvalidDnaFormatException e) {
			// this should never fail since the encoding came from the encapsulated BasePair
			e.printStackTrace();
			throw new InvalidDnaFormatException("We hit an unknown basepair encoding converting to string\n");
		}
		return sb.toString();
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + encodingScheme.getClass().getName().hashCode();
        result = prime * result + Arrays.hashCode(sequence);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Sequence))
            return false;
        if (!(obj instanceof BasicSequence)) {
            // We don't really want to hit this as it slows the system down, but it should be uncommon to mix sequence types
            return obj.toString().equals(toString());
        }
        val other = (BasicSequence) obj;
        if (this.encodingScheme == null || other.encodingScheme == null)
            return false;
        if (!encodingScheme.equals(other.encodingScheme)) {
            // We don't really want to hit this as it slows the system down, but it should be uncommon to mix encoding schemes
            return obj.toString().equals(toString());
        }

        return Arrays.equals(sequence, other.sequence);
    }

    @Override
    public int length() {
        return this.sequence.length;
    }
}
