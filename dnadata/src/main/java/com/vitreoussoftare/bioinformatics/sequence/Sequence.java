package com.vitreoussoftare.bioinformatics.sequence;

import java.util.Arrays;

/**
 * A DNA Sequence representation
 * @author John
 *
 */
public class Sequence {
	private byte[] sequence;

	private Sequence()
	{
	}
	/**
	 * Create a DNA Sequence from a string in FASTA format 
	 * @param dnaSequence The FASTA format sequence
	 * @return The Sequence from the FASTA format string
	 * @throws InvalidDnaFormatException One of the input characters is invalid
	 */
	public static Sequence fromFasta(String dnaSequence) throws InvalidDnaFormatException {
		if (dnaSequence.length() == 0) throw new InvalidDnaFormatException("The DNA sequence was empty!");
		
		Sequence seq = new Sequence();
		seq.sequence = new byte[dnaSequence.length()];
		for (int i = 0; i < dnaSequence.length(); i++)
		{
			seq.sequence[i] = BasePair.getValue(dnaSequence.charAt(i));
		}
		
		return seq;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (!Arrays.equals(sequence, other.sequence))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (byte bp : sequence) {
			sb.append(BasePair.tostring(bp));
		}
		return sb.toString();
	}
}
