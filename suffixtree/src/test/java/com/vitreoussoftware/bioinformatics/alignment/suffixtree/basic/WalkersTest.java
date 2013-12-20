package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTree;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTreeFactory;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.Walkers;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.reader.fasta.FastaStringFileStreamReaderTest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test the collection of Walkers provided in the static Walkers class.
 * Created by John on 12/19/13.
 */
public class WalkersTest {
    private SuffixTreeFactory factory;
    protected FastaSequenceFactory sequenceFactory;
    protected Sequence record1;
    protected Sequence record2;
    protected Sequence record3;
    protected Sequence recordSimple;

    @Before
    public void setup() throws InvalidDnaFormatException {
        this.factory = getSuffixTreeFactory();
        this.sequenceFactory = new FastaSequenceFactory();
        this.record1 = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.record1).get();
        this.record2 = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.record2).get();
        this.record3 = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.record3).get();
        this.recordSimple = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.recordSimple).get();
    }

    protected SuffixTreeFactory getSuffixTreeFactory() {
        return new BasicSuffixTreeFactory();
    }


    /**
     * Find something in the suffix tree
     * @throws java.io.IOException
     * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
     */
    @Test
    public void testFind_trivial() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(recordSimple);

        assertNotNull(tree);
        
        assertTrue(tree.walk(Walkers.contains((this.sequenceFactory.fromString("A").get()))));
        assertTrue(tree.walk(Walkers.contains((this.sequenceFactory.fromString("U").get()))));
        assertTrue(tree.walk(Walkers.contains((this.sequenceFactory.fromString("C").get()))));
        assertTrue(tree.walk(Walkers.contains((this.sequenceFactory.fromString("G").get()))));
    }

    /**
     * Find something in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testFind_canFail() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(recordSimple);

        assertNotNull(tree);
        assertFalse(tree.walk(Walkers.contains((this.sequenceFactory.fromString("T").get()))));
    }


    /**
     * Find something in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testFind_simple() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(recordSimple);

        assertNotNull("The tree was null", tree);
        assertTrue("The simple record was not found in the tree", tree.walk(Walkers.contains((recordSimple))));
    }

    /**
     * Find something in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testFind_record1() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(record1);

        assertNotNull(tree);
        assertTrue("Record 1 not found in thre tree", tree.walk(Walkers.contains((record1))));
        assertTrue(tree.walk(Walkers.contains((record1))));
    }

    /**
     * Find something in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testFind_record2() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(record2);

        assertNotNull(tree);
        assertTrue(tree.walk(Walkers.contains((record2))));
    }

    /**
     * Find something in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testFind_record3() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(record3);

        assertNotNull(tree);
        assertTrue(tree.walk(Walkers.contains((record3))));
    }

    /**
     * Find something in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testFind_record3Substring() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(record3);

        assertNotNull(tree);
        assertTrue(tree.walk(Walkers.contains((this.sequenceFactory.fromString("GCGGAGCGUGUGGUUUAAUUCGAUGCUACACGAAGAACCUUACCAAGAUUUGACAUGCAUGUAGUAGUGAACUGAAAGGG").get()))));
    }

    /**
     * Find something in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testFind_canFailLarge() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(record2);

        assertNotNull(tree);
        assertFalse(tree.walk(Walkers.contains((this.sequenceFactory.fromString("GCGGAGCGUGUGGUUUAAUUCGAUGCUACACGAAGAACCUUACCAAGAUUUGACAUGCAUGUAGUAGUGAACUGAAAGGG").get()))));
    }
}
