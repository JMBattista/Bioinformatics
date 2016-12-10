package com.vitreoussoftware.bioinformatics.sequence.collection.basic;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;

import java.util.Collection;
import java.util.HashSet;

/**
 * A HashSet based SequenceCollection intended for testing and prototyping.
 * This collection is not safe for use with large data sets.
 *
 * @author John
 */
public class SequenceSet extends HashSet<Sequence> implements Collection<Sequence> {

    /**
     *
     */
    private static final long serialVersionUID = 9119483062067567584L;

    SequenceSet() {
        super();
    }
}
