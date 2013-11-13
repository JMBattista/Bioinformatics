package com.vitreoussoftare.bioinformatics.sequence.fasta;

import com.vitreoussoftare.bioinformatics.sequence.BasePair;
import com.vitreoussoftare.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftare.bioinformatics.sequence.Sequence;
import com.vitreoussoftare.bioinformatics.sequence.SequenceFactory;
import com.vitreoussoftare.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme;
import com.vitreoussoftare.bioinformatics.sequence.encoding.EncodingScheme;

/**
 * Creates sequences from FASTA format data
 * @author John
 *
 */
public class FastaSequenceFactory implements SequenceFactory {

	private EncodingScheme encodingSheme;

	/**
	 * Initialize a new FastaSequenceFactory with default encoding scheme
	 */
	public FastaSequenceFactory() {
		this.encodingSheme = new AcceptUnknownDnaEncodingScheme();
	}
	
	/**
	 * Initialize a new FastaSequenceFactory with a custom encoding scheme
	 * @param encodingScheme the encoding scheme to use
	 */
	public FastaSequenceFactory(EncodingScheme encodingScheme)
	{
		this.encodingSheme = encodingScheme;
	}
	
	@Override
	public Sequence fromString(String sequence) throws InvalidDnaFormatException {
		if (sequence.length() == 0) throw new InvalidDnaFormatException("The DNA sequence was empty!");
		
		return Sequence.create(sequence, this.encodingSheme);
	}

	@Override
	public Sequence fromSequence(Sequence sequence) throws InvalidDnaFormatException {
		return Sequence.create(sequence.toString(), this.encodingSheme);
	}

}
