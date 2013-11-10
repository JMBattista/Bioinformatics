package com.vitreoussoftare.bioinformatics.dnadata;

/**
 * A DNA Sequence representation
 * @author John
 *
 */
public class DnaSequence {
	private BasePair[] dnaSequence;

	private DnaSequence()
	{
	}
	/**
	 * Create a DNA Sequence from a string in FASTA format 
	 * @param dnaSequence The FASTA format sequence
	 * @throws InvalidDnaFormatException One of the input characters is invalid
	 */
	public static DnaSequence fromFasta(String dnaSequence) throws InvalidDnaFormatException {
		if (dnaSequence.length() == 0) throw new InvalidDnaFormatException("The DNA sequence was empty!");
		
		DnaSequence seq = new DnaSequence();
		seq.dnaSequence = new BasePair[dnaSequence.length()];
		for (int i = 0; i < dnaSequence.length(); i++)
		{
			seq.dnaSequence[i] = BasePair.create(dnaSequence.charAt(i));
		}
		
		return seq;
	}
	
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (BasePair bp : dnaSequence) {
			sb.append(bp);
		}
		return sb.toString();
	}
}
