package com.vitreoussoftare.bioinformatics.sequence;

import java.util.Arrays;

import com.vitreoussoftare.bioinformatics.sequence.encoding.EncodingScheme;

/**
 * A DNA Sequence representation
 * @author John
 *
 */
public class Sequence {		
	private byte[] sequence;
	private final EncodingScheme encodingScheme;

	private Sequence(EncodingScheme encodingSheme)
	{
		this.encodingScheme = encodingSheme;
	}
	
	/**
	 * Create a new Sequence from a string and the given encoding scheme
	 * @param sequence the string sequence to encode
	 * @param encodingSheme the scheme to use for encoding
	 * @return the encoded sequence
	 * @throws InvalidDnaFormatException if the input doesn't match the scheme
	 */
	public static Sequence create(String sequence, EncodingScheme encodingSheme) throws InvalidDnaFormatException
	{
		Sequence seq = new Sequence(encodingSheme);
		seq.sequence = new byte[sequence.length()];
		
		for (int i = 0; i < sequence.length(); i++)
		{
			seq.sequence[i] = encodingSheme.getValue(sequence.charAt(i));
		}
		
		return seq;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (byte bp : sequence) {
			sb.append(this.encodingScheme.toString(bp));
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
		Sequence other = (Sequence) obj;
		if (this.encodingScheme == null || other.encodingScheme == null)
			return false;
		if (!encodingScheme.getClass().equals(other.encodingScheme.getClass()))
			return false;
		if (!Arrays.equals(sequence, other.sequence))
			return false;
		return true;
	}

}
