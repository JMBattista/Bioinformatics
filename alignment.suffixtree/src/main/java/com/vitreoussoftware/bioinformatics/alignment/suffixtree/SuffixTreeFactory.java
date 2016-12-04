package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import java.io.IOException;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReader;

/**
 * Create an instance of a Suffix Tree for use.
 * @author John
 *
 */
public interface SuffixTreeFactory {
    /**
     * Create a SuffixTree based on no sequence and return it
     * @return the suffix tree for that sequence
     */
    public SuffixTree create();

    /**
	 * Create a SuffixTree based on a Sequence
	 * @param text the text
	 * @return the suffix tree for that text
	 */
	default public SuffixTree create(final Sequence text) {
        final SuffixTree tree = create();
        tree.addText(text);
        return tree;
    }
	
	/**
	 * Create a SuffixTree based on a SequenceCollection
	 * @param texts the collection of sequences
	 * @return the SuffixTree for that collection of Sequences
	 */
	default public SuffixTree create(final SequenceCollection texts) {
        final SuffixTree tree = create();
        texts.forEach(tree::addText);
        return tree;
    }
	
	
	/**
	 * Create a SuffixTree based on a SequenceStreamREader.
	 * @param sequenceReader the SequenceStreamReader
	 * @return the SuffixTree
	 * @throws InvalidDnaFormatException 
	 * @throws IOException 
	 */
	default public SuffixTree create(final SequenceStreamReader sequenceReader) throws IOException, InvalidDnaFormatException
    {
        final SuffixTree tree = create();
        while (sequenceReader.hasNext())
            tree.addText(sequenceReader.next().orElseThrow(() -> new RuntimeException("TODO update this exception")));

        return tree;
    }
	
}
