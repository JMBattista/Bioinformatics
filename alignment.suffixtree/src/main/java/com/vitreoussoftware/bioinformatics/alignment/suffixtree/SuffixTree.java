package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import java.util.Collection;
import java.util.List;

import com.vitreoussoftware.bioinformatics.alignment.Position;
import com.vitreoussoftware.bioinformatics.alignment.TextFirstAligner;
import com.vitreoussoftware.bioinformatics.sequence.*;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import org.javatuples.Pair;


/**
 * Suffix Tree implementation for Sequence data
 * @author John
 *
 */
public interface SuffixTree extends TextFirstAligner {
    /**
     * Returns the depth of the suffix tree.
     * @return the depth
     */
    public int depth();

    /**
     * Walk the tree to build up a result
     * @param walker the walk to perform
     * @param <T> the tracking value during the walk
     * @param <R> the result type for the walk
     * @return the result of the walk
     */
    public <T, R> R walk(Walk<T, R> walker);
}
