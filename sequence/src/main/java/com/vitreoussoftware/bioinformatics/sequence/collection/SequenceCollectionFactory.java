package com.vitreoussoftware.bioinformatics.sequence.collection;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReader;

import java.io.IOException;
import java.util.Collection;

/**
 * @author John
 */
public interface SequenceCollectionFactory {

    /**
     * Get a Sequence collection of the type this factory creates
     *
     * @return A new sequence collection
     */
    public Collection<Sequence> getSequenceCollection();

    /**
     * Creates a Sequence collection from the given SequenceStreamReader
     *
     * @param reader the sequence reader
     * @return the collection built from the reader
     * @throws IOException               errors reading from the stream
     * @throws InvalidDnaFormatException
     */
    public default Collection<Sequence> getSequenceCollection(final SequenceStreamReader reader) throws IOException, InvalidDnaFormatException {
        final Collection<Sequence> sequenceCollection = getSequenceCollection();
        while (reader.hasNext()) {
            sequenceCollection.add(reader.next().orElseThrow(() -> new RuntimeException("TODO use reason Optional")));
        }

        return sequenceCollection;
    }

    /**
     * Create a SequenceCollection from another Collection<Sequence> object
     *
     * @param collection the Collection of sequences
     * @return the new collection
     */
    public default Collection<Sequence> getSequenceCollection(final Collection<Sequence> collection) {
        final Collection<Sequence> sequenceCollection = getSequenceCollection();
        for (final Sequence seq : collection) {
            sequenceCollection.add(seq);
        }

        return sequenceCollection;
    }
}
