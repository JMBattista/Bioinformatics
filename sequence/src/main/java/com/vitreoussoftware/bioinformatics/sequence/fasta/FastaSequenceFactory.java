package com.vitreoussoftware.bioinformatics.sequence.fasta;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.SequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence;
import com.vitreoussoftware.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.encoding.EncodingScheme;

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
		
		return BasicSequence.create(sequence, this.encodingSheme);
	}

	@Override
	public Sequence fromSequence(Sequence sequence) throws InvalidDnaFormatException {
		return BasicSequence.create(sequence.toString(), this.encodingSheme);
	}
}
