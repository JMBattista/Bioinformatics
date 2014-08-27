package com.vitreoussoftware.bioinformatics.sequence.collection;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Test the MongoDBSequenceCollection class
 * @author John
 *
 */
public abstract class PersistentSequenceCollectionTest extends SequenceCollectionTest {
    /**
     * Test that the data structure handles multi-point update in a persistent manner.
     * @throws java.net.UnknownHostException Failed to connect
     * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
     */
    @Test
    public void testPersistent_additionDuringIteratiion() throws UnknownHostException, InvalidDnaFormatException {
        SequenceCollection collection = getFactory().getSequenceCollection();
        assertNotNull("The collection returned was nulll", collection);
        collection.clear();
        assertEquals("The collection was not empty after a drop",  0, collection.size());

        final Sequence seq1 = this.sequenceFactory.fromString("ATCGT").get();
        final Sequence seq2 = this.sequenceFactory.fromString("ATCGA").get();
        final Sequence seq3 = this.sequenceFactory.fromString("ATCGNA").get();
        collection.add(seq1);
        collection.add(seq2);

        assertEquals("The collection did not have two elements in it", 2, collection.size());

        final Iterator<Sequence> iterator = collection.iterator();

        assertTrue("There were no elements", iterator.hasNext());
        iterator.next();

        collection.add(seq3);
        assertTrue("There were no elements", iterator.hasNext());
        iterator.next();

        assertFalse("A third element was seen by the iterator", iterator.hasNext());
    }

    /**
     * Test that the data structure handles multi-point update in a persistent manner.
     * @throws java.net.UnknownHostException Failed to connect
     * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
     */
    @Test
    public void testPersistent_deletionDuringIteration() throws UnknownHostException, InvalidDnaFormatException {
        SequenceCollection collection = getFactory().getSequenceCollection();
        assertNotNull("The collection returned was nulll", collection);
        collection.clear();
        assertEquals("The collection was not empty after a drop",  0, collection.size());

        final Sequence seq1 = this.sequenceFactory.fromString("ATCGT").get();
        final Sequence seq2 = this.sequenceFactory.fromString("ATCGA").get();
        final Sequence seq3 = this.sequenceFactory.fromString("ATCGNA").get();
        collection.add(seq1);
        collection.add(seq2);
        collection.add(seq3);

        assertEquals("The collection did not have three elements in it", 3, collection.size());

        final Iterator<Sequence> iterator = collection.iterator();

        assertTrue("There were no elements", iterator.hasNext());
        assertNotNull(iterator.next());

        collection.remove(seq3);

        assertTrue("There were no elements", iterator.hasNext());
        assertNotNull(iterator.next());

        assertTrue("There were no elements", iterator.hasNext());
        assertNotNull(iterator.next());

        assertFalse("There were still elements", iterator.hasNext());
    }

    /**
     * Test that the data structure handles multi-point update in a persistent manner.
     * @throws java.net.UnknownHostException Failed to connect
     * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
     */
    @Test
    public void testPersistent_clearDuringIteration() throws UnknownHostException, InvalidDnaFormatException {
        SequenceCollection collection = getFactory().getSequenceCollection();
        assertNotNull("The collection returned was nulll", collection);
        collection.clear();
        assertEquals("The collection was not empty after a drop",  0, collection.size());

        final Sequence seq1 = this.sequenceFactory.fromString("ATCGT").get();
        final Sequence seq2 = this.sequenceFactory.fromString("ATCGA").get();
        final Sequence seq3 = this.sequenceFactory.fromString("ATCGNA").get();
        collection.add(seq1);
        collection.add(seq2);
        collection.add(seq3);

        assertEquals("The collection did not have three elements in it", 3, collection.size());

        final Iterator<Sequence> iterator = collection.iterator();

        assertTrue("There were no elements", iterator.hasNext());
        collection.clear();

        assertNotNull(iterator.next());

        assertTrue("There were no elements", iterator.hasNext());
        assertNotNull(iterator.next());

        assertTrue("There were no elements", iterator.hasNext());
        assertNotNull(iterator.next());

        assertFalse("There were still elements", iterator.hasNext());
    }
}
