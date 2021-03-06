package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;


import com.vitreoussoftware.bioinformatics.alignment.Position;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;

import java.util.Iterator;


/**
 * Suffix Tree implementation for Sequence data where the substrings fall within a specified range of lengths.
 *
 * @author John
 */
public class BoundedSuffixTree extends BasicSuffixTree {
    private final int minLength;
    private final int maxLength;


    /**
     * Create the suffix tree
     *
     * @param factory   the factory for creating SequenceCollections
     * @param minLength the minimum length of a suffix contained in the tree.
     * @param maxLength the maximum length of a suffix contained in the tree.
     */
    BoundedSuffixTree(final SequenceCollectionFactory factory, final int minLength, final int maxLength) {
        super(factory);
        this.minLength = minLength;
        this.maxLength = maxLength;
    }


    /**
     * Adds a new sequence to the suffix tree
     *
     * @param text the sequence to add
     */
    public void addText(final Sequence text) {
        if (text == null) throw new IllegalArgumentException("Sequence cannot be null");
        final Iterator<BasePair> suffixIter = text.iterator();

        int startPos = 0;
        final int length = text.length();
        while (suffixIter.hasNext() && startPos + this.minLength < length) {
            SuffixTreeNode current = root;
            // up to max length fromCharacter or iterate the nodes and add parents.
            for (int offset = 0; offset < this.maxLength && offset + startPos < text.length(); offset++) {
                current = current.getOrCreate(text.get(startPos + offset));

                // Only add the starting position, not the position of the current node.
                if (offset >= this.minLength)
                    current.addPosition(Position.with(text, startPos));
            }

            // iterate forward and update the position
            suffixIter.next();
            startPos++;
        }
    }
}
