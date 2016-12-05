package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTree;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTreeFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.basic.SequenceListFactory;

/**
 * Create suffix trees with bounded size
 *
 * @author John
 */
public class BoundedSuffixTreeFactory implements SuffixTreeFactory {

    private final SequenceCollectionFactory factory;
    private final int maxLength;
    private final int minLength;

    /**
     * Create a BasicSuffixTreeFactory with a custom SequenceCollectionFactory
     *
     * @param factory   the SequenceCollectionFactory to use with the SuffixTree
     * @param minLength the minimum length of a suffix contained in the tree.
     * @param maxLength the maximum length of a suffix contained in the tree.
     */
    public BoundedSuffixTreeFactory(final SequenceCollectionFactory factory, final int minLength, final int maxLength) {
        this.factory = factory;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    /**
     * Create a BasicSuffixTreeFactory with default configuration
     *
     * @param minlength the minimum length of a suffix contained in the tree.
     * @param maxLength the maximum length of a suffix contained in the tree.
     */
    public BoundedSuffixTreeFactory(final int minlength, final int maxLength) {
        this(new SequenceListFactory(), minlength, maxLength);
    }

    public SuffixTree create() {
        return new BoundedSuffixTree(this.factory, this.minLength, this.maxLength);
    }
}
