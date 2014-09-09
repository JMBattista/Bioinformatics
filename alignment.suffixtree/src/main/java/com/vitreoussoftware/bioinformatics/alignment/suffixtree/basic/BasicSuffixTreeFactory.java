package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import java.io.IOException;

import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTree;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTreeFactory;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.basic.SequenceListFactory;
import com.vitreoussoftware.bioinformatics.sequence.reader.SequenceStreamReader;

/**
 * Create a BasicSuffixTree
 * @author John
 *
 */
public class BasicSuffixTreeFactory implements SuffixTreeFactory {

	private SequenceCollectionFactory factory;

	/**
	 * Create a BasicSuffixTreeFactory with a custom SequenceCollectionFactory
	 * @param factory the SequenceCollectionFactory to use with the SuffixTree
	 */
	public BasicSuffixTreeFactory(SequenceCollectionFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * Create a BasicSuffixTreeFactory with default configuration
	 */
	public BasicSuffixTreeFactory() {
		this.factory = new SequenceListFactory();
	}

    @Override
    public SuffixTree create() {
        return new BasicSuffixTree(this.factory);
    }

}
