package com.vitreoussoftware.bioinformatics.sequence.collection.mongodb;

import static junit.framework.Assert.*;

import java.net.UnknownHostException;

import com.vitreoussoftware.test.type.IntegrationTests;
import com.vitreoussoftware.bioinformatics.sequence.collection.*;
import org.junit.experimental.categories.Category;

/**
 * Test the MongoDBSequenceCollection class
 * @author John
 *
 */
@Category({ IntegrationTests.class })
public class MongoDBSequenceCollectionTest extends PersistentSequenceCollectionTest {

    @Override
    protected SequenceCollectionFactory getFactory() {
        try {
            return new MongoDBSequenceCollectionFactory("localhost", 27017, "testSeq", "testColl");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            fail("We were unable to create the MongoDBSequenceCollectionFactory, ensure that MongoDB is running");
        }
        // unreachable because of fail()
        return null;
    }
}
