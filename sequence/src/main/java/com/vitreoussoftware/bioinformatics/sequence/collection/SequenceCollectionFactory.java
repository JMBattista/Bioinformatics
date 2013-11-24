package com.vitreoussoftware.bioinformatics.sequence.collection;

import java.io.IOException;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.reader.SequenceStreamReader;

/**
 * @author John
 *
 */
public interface SequenceCollectionFactory {
	
	/**
	 * Get a Sequence collection of the type this factory creates
	 * @return A new sequence collection
	 */
	public SequenceCollection getSequenceCollection();
	
	/**
	 * Creates a Sequence collection from the given SequenceStreamReader
	 * @param reader the sequence reader 
	 * @return the collection built from the reader
	 * @throws IOException errors reading from the stream 
	 * @throws InvalidDnaFormatException 
	 */
	public SequenceCollection getSequenceCollection(SequenceStreamReader reader) throws IOException, InvalidDnaFormatException;
	
	/**
	 * Create a SequenceCollection from another SequenceCollection
	 * @param collection the other SequenceCollection
	 * @return the new collection
	 */
	public SequenceCollection getSequenceCollection(SequenceCollection collection);
}
