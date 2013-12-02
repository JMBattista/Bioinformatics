package com.vitreoussoftware.bioinformatics.sequence.collection.basic;

import java.io.IOException;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.reader.SequenceStreamReader;

/**
 * Create instances of SequenceList
 *
 * @author John
 *
 */
public class SequenceListFactory implements SequenceCollectionFactory {

	@Override
	public SequenceCollection getSequenceCollection() {
		return new SequenceList();
	}

	@Override
	public SequenceCollection getSequenceCollection(SequenceStreamReader reader) throws IOException, InvalidDnaFormatException {
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
