package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTree;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTreeFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.basic.SequenceListFactory;

/**
 * Create a BasicSuffixTree
 *
 * @author John
 */
public class BasicSuffixTreeFactory implements SuffixTreeFactory {

    private final SequenceCollectionFactory factory;

    /**
     * Create a BasicSuffixTreeFactory with a custom SequenceCollectionFactory
     *
     * @param factory the SequenceCollectionFactory to use with the SuffixTree
     */
    public BasicSuffixTreeFactory(final SequenceCollectionFactory factory) {
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
