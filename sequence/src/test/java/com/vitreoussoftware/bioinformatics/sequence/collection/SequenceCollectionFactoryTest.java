package com.vitreoussoftware.bioinformatics.sequence.collection;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Test the MongoDBSequenceCollection class
 *
 * @author John
 */
public abstract class SequenceCollectionFactoryTest {
    /**
     * Create a working SequenceCollectionFactory for the tests to use
     *
     * @return the SequenceCollectionFactory
     */
    public abstract SequenceCollectionFactory getFactory();


    /**
     * Create a SequenceCollection
     *
     * @throws java.net.UnknownHostException Failed to connect
     */
    @Test
    public void testCreateFactory() {
        final SequenceCollectionFactory collectionFactory = getFactory();
        assertNotNull("The collectionFactory initialized to null", collectionFactory);
    }

    /**
     * Create a SequenceCollection
     *
     * @throws java.net.UnknownHostException Failed to connect
     */
    @Test
    public void testGetCollection() {
        final SequenceCollectionFactory collectionFactory = getFactory();
        assertNotNull("The collectionFactory initialized to null", collectionFactory);
        final SequenceCollection collection = collectionFactory.getSequenceCollection();
        assertNotNull("The collection returned was nulll", collection);
    }
}
