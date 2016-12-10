package com.vitreoussoftware.bioinformatics.sequence.collection.basic;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Create instances of SequenceList
 *
 * @author John
 */
public class SequenceListFactory implements SequenceCollectionFactory {

    @Override
    public Collection<Sequence> getSequenceCollection() {
        return new ArrayList<>();
    }
}
