package com.vitreoussoftare.bioinformatics.sequence.collection.basic;

import java.io.IOException;

import com.vitreoussoftare.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftare.bioinformatics.sequence.Sequence;
import com.vitreoussoftare.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftare.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftare.bioinformatics.sequence.reader.SequenceStreamReader;
import com.vitreoussoftare.bioinformatics.sequence.reader.SequenceStringStreamReader;

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

}
