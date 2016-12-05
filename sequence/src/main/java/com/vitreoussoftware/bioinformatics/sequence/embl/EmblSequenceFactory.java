package com.vitreoussoftware.bioinformatics.sequence.embl;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.SequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence;
import com.vitreoussoftware.bioinformatics.sequence.encoding.EncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.encoding.ExpandedIupacEncodingScheme;

import java.util.Optional;

/**
 * Creates {@Sequence} instances from EMBL format data
 *
 * @author John
 */
public class EmblSequenceFactory implements SequenceFactory {

    private final EncodingScheme encodingScheme;

    /**
     * Initialize a new {@link EmblSequenceFactory} with default {@link EncodingScheme}
     */
    public EmblSequenceFactory() {
        this(ExpandedIupacEncodingScheme.instance);
    }

    /**
     * Initialize a new {@link EmblSequenceFactory} with a custom {@link EncodingScheme}
     *
     * @param encodingScheme the encoding scheme to use
     */
    public EmblSequenceFactory(final EncodingScheme encodingScheme) {
        this.encodingScheme = encodingScheme;
    }

    @Override
    public Optional<Sequence> fromString(final String metadata, final String sequence) throws InvalidDnaFormatException {
        if (sequence.length() == 0) throw new InvalidDnaFormatException("The DNA sequence was empty!");

        return BasicSequence.create(metadata, sequence, this.encodingScheme);
    }

    @Override
    public Optional<Sequence> fromSequence(final Sequence sequence) throws InvalidDnaFormatException {
        return BasicSequence.create(sequence.toString(), this.encodingScheme);
    }
}
