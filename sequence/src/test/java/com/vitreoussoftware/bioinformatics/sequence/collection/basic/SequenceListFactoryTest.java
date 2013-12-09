package com.vitreoussoftware.bioinformatics.sequence.collection.basic;

import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactoryTest;

/**
 * Created by John on 12/7/13.
 */
public class SequenceListFactoryTest extends SequenceCollectionFactoryTest {
    @Override
    public SequenceCollectionFactory getFactory() {
        return new SequenceListFactory();
    }
}
