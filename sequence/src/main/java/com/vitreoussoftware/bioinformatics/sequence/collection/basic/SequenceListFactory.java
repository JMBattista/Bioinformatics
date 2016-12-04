package com.vitreoussoftware.bioinformatics.sequence.collection.basic;

import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;

/**
 * Create instances of SequenceList
 *
 * @author John
 */
public class SequenceListFactory implements SequenceCollectionFactory {

    @Override
    public SequenceCollection getSequenceCollection() {
        return new SequenceList();
    }
}
