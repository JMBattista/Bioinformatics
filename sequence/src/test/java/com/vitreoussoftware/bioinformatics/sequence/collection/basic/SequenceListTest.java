package com.vitreoussoftware.bioinformatics.sequence.collection.basic;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionTest;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Test the SequenceList basic collection so we have a baseline
 *
 * @author John
 */
public class SequenceListTest extends SequenceCollectionTest {

    @Override
    protected SequenceCollectionFactory getFactory() {
        return new SequenceListFactory();
    }

    /**
     * Can it be created?
     *
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testCreation() throws InvalidDnaFormatException {
        final Collection<Sequence> sc = this.getFactory().getSequenceCollection();

        assertNotNull(sc);
    }

    /**
     * Can it be created?
     *
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testAdd() throws InvalidDnaFormatException {
        final Collection<Sequence> sc = this.getFactory().getSequenceCollection();

        assertNotNull(sc);
        sc.add(sequenceFactory.fromString("AATTCCGGUU").get());
        assertEquals(1, sc.size());
    }

    /**
     * Can it be created?
     *
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testContainsSameRef() throws InvalidDnaFormatException {
        final Collection<Sequence> sc = this.getFactory().getSequenceCollection();
        final Sequence seq = sequenceFactory.fromString("AATTCCGGUU").get();
        sc.add(seq);
        assertEquals(1, sc.size());
        assertTrue(sc.contains(seq));
    }

    /**
     * Can it be created?
     *
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testContainsDiffRef() throws InvalidDnaFormatException {
        final Collection<Sequence> sc = this.getFactory().getSequenceCollection();
        sc.add(sequenceFactory.fromString("AATTCCGGUU").get());
        assertEquals(1, sc.size());
        assertTrue(sc.contains(sequenceFactory.fromString("AATTCCGGUU").get()));
    }
}
