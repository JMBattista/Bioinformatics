package com.vitreoussoftware.bioinformatics.alignment.stringbased;

import com.vitreoussoftware.bioinformatics.alignment.PatternFirstAligner;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.reader.SequenceStreamReader;

import java.io.IOException;

/**
 * Created by John on 9/9/14.
 */
public interface StringBasedAlignerFactory {
    /**
     * Create a StringBasedAlignerFactory based on no sequence and return it
     * @return the suffix tree for that sequence
     */
    public PatternFirstAligner create();

    /**
     * Create a StringBasedAlignerFactory based on a Sequence
     * @param text the text
     * @return the suffix tree for that text
     */
    default public PatternFirstAligner create(Sequence text) {
        PatternFirstAligner aligner = create();
        aligner.addPattern(text);
        return aligner;
    }

    /**
     * Create a StringBasedAlignerFactory based on a SequenceCollection
     * @param texts the collection of sequences
     * @return the StringBasedAlignerFactory for that collection of Sequences
     */
    default public PatternFirstAligner create(SequenceCollection texts) {
        PatternFirstAligner aligner = create();
        texts.forEach(text -> aligner.addPattern(text));
        return aligner;
    }


    /**
     * Create a StringBasedAlignerFactory based on a SequenceStreamREader.
     * @param sequenceReader the SequenceStreamReader
     * @return the StringBasedAlignerFactory
     * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
     * @throws java.io.IOException
     */
    default public PatternFirstAligner create(SequenceStreamReader sequenceReader) throws IOException, InvalidDnaFormatException
    {
        PatternFirstAligner aligner = create();
        while (sequenceReader.hasNext())
            aligner.addPattern(sequenceReader.next().orElseThrow(() -> new RuntimeException("TODO update this exception")));

        return aligner;
    }

}
