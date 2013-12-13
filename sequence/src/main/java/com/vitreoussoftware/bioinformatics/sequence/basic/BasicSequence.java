package com.vitreoussoftware.bioinformatics.sequence.basic;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.encoding.EncodingScheme;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * A DNA Sequence representation
 * @author John
 *
 */
public class BasicSequence implements Sequence {
	private byte[] sequence;
	private final EncodingScheme encodingScheme;

	private BasicSequence(EncodingScheme encodingSheme)
	{
		this.encodingScheme = encodingSheme;
	}

	/**
	 * Create a new Sequence from a string and the given encoding scheme
	 * @param sequence the string sequence to encode
	 * @param encodingSheme the scheme to use for encoding
	 * @return the encoded sequence
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException if the input doesn't match the scheme
	 */
	public static Optional<Sequence> create(String sequence, EncodingScheme encodingSheme)
	{

        try {
            BasicSequence seq = new BasicSequence(encodingSheme);
            seq.sequence = new byte[sequence.length()];

            for (int i = 0; i < sequence.length(); i++)
            {
                seq.sequence[i] = encodingSheme.getValue(sequence.charAt(i));
            }

            return Optional.<Sequence>of(seq);
        }
        catch (InvalidDnaFormatException e) {
            e.printStackTrace();
            return Optional.empty();
        }
	}

    @Override
	public BasePair get(int index)
	{
		try {
			return this.encodingScheme.toBasePair(sequence[index]);
		}
		catch (InvalidDnaFormatException e)
		{
			// this should not have happened because we encoded everything using this scheme already
			e.printStackTrace();
			throw new RuntimeException("We failed to decode a value that was previously encoded in by the same encoding scheme, for value: " + sequence[index] + " encoding scheme " + this.encodingScheme.getClass().getName());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		try {
			for (byte bp : sequence) {
				sb.append(this.encodingScheme.toString(bp));
			}
		} catch (InvalidDnaFormatException e) {
			// this should never fail since the encoding came from the encapsulated BasePair
			e.printStackTrace();
			throw new RuntimeException("We hit an unknown basepair encoding converting to string\n");
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicSequence other = (BasicSequence) obj;
		if (this.encodingScheme == null || other.encodingScheme == null)
			return false;
		if (!encodingScheme.getClass().equals(other.encodingScheme.getClass()))
			return false;

        return Arrays.equals(sequence, other.sequence);
    }

	@Override
	public int length() {
		return this.sequence.length;
	}
}
