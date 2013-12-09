package com.vitreoussoftware.bioinformatics.sequence.collection.mongodb;

import static junit.framework.Assert.*;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.vitreoussoftware.bioinformatics.sequence.collection.*;
import org.junit.Before;
import org.junit.Test;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.SequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;

/**
 * Test the MongoDBSequenceCollection class
 * @author John
 *
 */
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
