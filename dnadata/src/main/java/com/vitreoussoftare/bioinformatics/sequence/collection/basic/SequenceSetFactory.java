package com.vitreoussoftare.bioinformatics.sequence.collection.basic;

import java.io.IOException;

import com.vitreoussoftare.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftare.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftare.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftare.bioinformatics.sequence.reader.SequenceStreamReader;

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

}
