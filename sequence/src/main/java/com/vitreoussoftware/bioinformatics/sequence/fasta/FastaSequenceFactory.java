package com.vitreoussoftware.bioinformatics.sequence.fasta;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.SequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence;
import com.vitreoussoftware.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.encoding.EncodingScheme;

import java.util.Optional;

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
	public FastaSequenceFactory(final EncodingScheme encodingScheme)
	{
		this.encodingSheme = encodingScheme;
	}

    @Override
    public Optional<Sequence> fromString(final String metadata, final String sequence) throws InvalidDnaFormatException {
        if (sequence.length() == 0) throw new InvalidDnaFormatException("The DNA sequence was empty!");

        return BasicSequence.create(metadata, sequence, this.encodingSheme);
    }

	@Override
	public Optional<Sequence> fromSequence(final Sequence sequence) throws InvalidDnaFormatException {
		return BasicSequence.create(sequence.toString(), this.encodingSheme);
	}
}
