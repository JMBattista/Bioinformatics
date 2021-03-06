package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import com.vitreoussoftware.bioinformatics.alignment.TextFirstAligner;


/**
 * Suffix Tree implementation for Sequence data
 *
 * @author John
 */
public interface SuffixTree extends TextFirstAligner {
    /**
     * Walk the tree to build up a result
     *
     * @param walker the walk to perform
     * @param <T>    the tracking value during the walk
     * @param <R>    the result type for the walk
     * @return the result of the walk
     */
    public <T, R> R walk(Walk<T, R> walker);
}
