package com.vitreoussoftware.bioinformatics.sequence.collection.basic;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;

import java.util.Collection;

/**
 * Creates instances of SequenceSet collections
 *
 * @author John
 */
public class SequenceSetFactory implements SequenceCollectionFactory {

    @Override
    public Collection<Sequence> getSequenceCollection() {
        return new SequenceSet();
    }
}
