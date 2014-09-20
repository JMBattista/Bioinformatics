package com.vitreoussoftware.bioinformatics.sequence.collection.basic;

import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;

/**
 * Creates instances of SequenceSet collections
 * @author John
 *
 */
public class SequenceSetFactory implements SequenceCollectionFactory {

	@Override
	public SequenceCollection getSequenceCollection() {
		return new SequenceSet();
	}
}
