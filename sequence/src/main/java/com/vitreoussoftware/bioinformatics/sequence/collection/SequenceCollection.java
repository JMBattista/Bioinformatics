package com.vitreoussoftware.bioinformatics.sequence.collection;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;

import java.util.Collection;

/**
 * A collection of Sequences
 *
 * @author John
 */
public interface SequenceCollection extends Collection<Sequence>, Iterable<Sequence> {
    public static SequenceCollection from(final Collection<Sequence> collection) {
        return new JavaCollectionWrapper(collection);
    }
}
