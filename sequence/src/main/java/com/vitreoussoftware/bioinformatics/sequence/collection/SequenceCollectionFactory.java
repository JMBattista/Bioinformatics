package com.vitreoussoftware.bioinformatics.sequence.collection;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
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
	public default SequenceCollection getSequenceCollection(SequenceStreamReader reader) throws IOException, InvalidDnaFormatException {
        SequenceCollection sequenceCollection = getSequenceCollection();
        while (reader.hasNext()) {
            sequenceCollection.add(reader.next().orElseThrow(() -> new RuntimeException("TODO use reason Optional")));
        }

        return sequenceCollection;
    }
	
	/**
	 * Create a SequenceCollection from another SequenceCollection
	 * @param collection the other SequenceCollection
	 * @return the new collection
	 */
	public default SequenceCollection getSequenceCollection(SequenceCollection collection) {
        return getSequenceCollection((Collection<Sequence>)collection);
    }

    /**
     * Create a SequenceCollection from another Collection<Sequence> object
     * @param collection the Collection of sequences
     * @return the new collection
     */
    public default SequenceCollection getSequenceCollection(Collection<Sequence> collection) {
        SequenceCollection sequenceCollection = getSequenceCollection();
        for (Sequence seq : collection) {
            sequenceCollection.add(seq);
        }

        return sequenceCollection;
    }
}
