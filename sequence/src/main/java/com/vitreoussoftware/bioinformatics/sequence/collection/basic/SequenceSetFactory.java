package com.vitreoussoftware.bioinformatics.sequence.collection.basic;

import java.io.IOException;
import java.util.Collection;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.reader.SequenceStreamReader;

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
