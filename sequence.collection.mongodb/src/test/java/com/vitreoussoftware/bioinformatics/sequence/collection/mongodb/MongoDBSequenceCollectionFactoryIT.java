package com.vitreoussoftware.bioinformatics.sequence.collection.mongodb;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import com.vitreoussoftware.bioinformatics.sequence.SequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;

import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactoryTest;


/**
 * Test the MongoDBSequenceCollection class
 * @author John
 *
 */
public class MongoDBSequenceCollectionFactoryIT extends SequenceCollectionFactoryTest {

	private SequenceFactory sequenceFactory;

	@Before
	public void setUp() throws Exception {
		this.sequenceFactory = new FastaSequenceFactory();
	}

    @Override
    public SequenceCollectionFactory getFactory() {
        try {
            return new MongoDBSequenceCollectionFactory("localhost", 27017, "testSeq", "testColl");
        }
        catch (Exception e) {
            e.printStackTrace();
            fail("Exception thrown while trying to create MongoDBSequenceCollectionFactory, check that MongoDB is running");
        }

        // unreachable due to fail() call
        return null;
    }

	/**
	 * Create a SequenceCollection
	 * @throws UnknownHostException Failed to connect 
	 */
	@Test(expected=UnknownHostException.class)
	public void testCreateFactoryCanFail() throws UnknownHostException {
		SequenceCollectionFactory collectionFactory = new MongoDBSequenceCollectionFactory("SXKSAkasdglak@#@KASGlsldfs", 27017, "testSeq", "testColl");
		fail("We should not have been able to successfully initialize the factory without a connection to the DB");
	}
}
