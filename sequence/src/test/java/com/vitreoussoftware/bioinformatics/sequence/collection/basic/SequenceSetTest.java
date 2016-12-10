package com.vitreoussoftware.bioinformatics.sequence.collection.basic;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class SequenceSetTest {

    private SequenceCollectionFactory factory;
    private FastaSequenceFactory sequenceFactory;

    /**
     * Setup the test class
     */
    @Before
    public void setup() {
        this.sequenceFactory = new FastaSequenceFactory();
        this.factory = new SequenceSetFactory();
    }

    /**
     * Can it be created?
     *
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testCreation() throws InvalidDnaFormatException {
        final Collection<Sequence> sc = this.factory.getSequenceCollection();

        assertNotNull(sc);
    }

    /**
     * Can it be created?
     *
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testAdd() throws InvalidDnaFormatException {
        final Collection<Sequence> sc = this.factory.getSequenceCollection();

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
    public void testAddDuplicate() throws InvalidDnaFormatException {
        final Collection<Sequence> sc = this.factory.getSequenceCollection();
        final Sequence seq = sequenceFactory.fromString("AATTCCGGUU").get();

        assertNotNull(sc);
        sc.add(sequenceFactory.fromString("AATTCCGGUU").get());
        assertEquals(1, sc.size());
        assertEquals(seq, sc.iterator().next());
        assertTrue(sc.contains(seq));
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
        final Collection<Sequence> sc = this.factory.getSequenceCollection();
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
        final Collection<Sequence> sc = this.factory.getSequenceCollection();
        sc.add(sequenceFactory.fromString("AATTCCGGUU").get());
        assertEquals(1, sc.size());
        assertTrue(sc.contains(sequenceFactory.fromString("AATTCCGGUU").get()));
    }
}
