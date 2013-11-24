package com.vitreoussoftware.bioinformatics.sequence.collection.basic;

import java.io.IOException;

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

	@Override
	public SequenceCollection getSequenceCollection(SequenceStreamReader reader)
			throws IOException, InvalidDnaFormatException 
	{
		SequenceCollection collection = getSequenceCollection();
		
		while (reader.hasRecord())
		{
			collection.add(reader.readRecord());
		}
		
		return collection;
	}

	@Override
	public SequenceCollection getSequenceCollection(SequenceCollection collection) {
		SequenceCollection newColleciton = getSequenceCollection();
		
		for (Sequence sequence : collection) {
			newColleciton.add(sequence);
		}
		
		return newColleciton;
	}

}
