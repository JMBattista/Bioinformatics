package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import com.vitreoussoftware.bioinformatics.alignment.Alignment;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTree;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTreeFactory;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.Walkers;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.reader.fasta.FastaStringFileStreamReaderTest;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

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

    /**
     * Check depth computation
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testDepth_1() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(this.sequenceFactory.fromString("A").get());

        assertNotNull(tree);
        assertEquals(1, tree.walk(Walkers.depth()).intValue());
    }

    /**
     * Check depth computation
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testDepth_2() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(this.sequenceFactory.fromString("AU").get());

        assertNotNull(tree);
        assertEquals(2, tree.walk(Walkers.depth()).intValue());
    }

    /**
     * Check depth computation
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testDepth_simple() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(this.recordSimple);

        assertNotNull(tree);
        assertEquals(this.recordSimple.length(), tree.walk(Walkers.depth()).intValue());
    }

    /**
     * Check depth computation
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testSize_1() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(this.sequenceFactory.fromString("A").get());

        assertNotNull(tree);
        assertEquals(1, tree.walk(Walkers.size()).intValue());
    }

    /**
     * Check depth computation
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testSize_2() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(this.sequenceFactory.fromString("AU").get());

        assertNotNull(tree);
        assertEquals(3, tree.walk(Walkers.size()).intValue());
    }

    /**
     * Check depth computation
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testSize_simple() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(this.recordSimple);

        assertNotNull(tree);
        assertEquals("The expected sequence length was wrong", 80, this.recordSimple.length());
        assertEquals("The tree size was wrong", 3049, tree.walk(Walkers.size()).intValue());
    }

    /**
     * Find distances in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testDistance_trivial0() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(recordSimple);

        assertNotNull(tree);

        Sequence a = this.sequenceFactory.fromString("A").get();
        Sequence u = this.sequenceFactory.fromString("U").get();
        Sequence c = this.sequenceFactory.fromString("C").get();
        Sequence g = this.sequenceFactory.fromString("G").get();

        {
            Collection<Alignment> alignments = tree.walk(Walkers.distance(a));

            assertEquals("Not all alignments had distance 0", alignments.stream().allMatch(alignment -> alignment.getDistance() == 0));
            assertEquals("Number of alignments was wrong", 22, alignments.size());
            for (Alignment p : alignments) {
                assertEquals("The base pair was wrong for " + p.getPosition(), AcceptUnknownDnaEncodingScheme.A, p.getText().get(p.getPosition()));
            }
            assertTrue("Simple was not the matching sequence", alignments.stream().filter(p -> p.getText().equals(recordSimple)).findFirst().isPresent());
        }

        {
            Collection<Alignment> alignments = tree.walk(Walkers.distance(u));

            assertEquals("Not all alignments had distance 0", alignments.stream().allMatch(alignment -> alignment.getDistance() == 0));
            for (Alignment p : alignments) {
                assertEquals("The base pair was wrong for " + p.getPosition(), AcceptUnknownDnaEncodingScheme.U, p.getText().get(p.getPosition()));
            }
            assertTrue("Simple was not the matching sequence", alignments.stream().filter(p -> p.getText().equals(recordSimple)).findFirst().isPresent());
        }

        {
            Collection<Alignment> alignments = tree.walk(Walkers.distance(c));

            assertEquals("Not all alignments had distance 0", alignments.stream().allMatch(alignment -> alignment.getDistance() == 0));
            for (Alignment p : alignments) {
                assertEquals("The base pair was wrong for " + p.getPosition(), AcceptUnknownDnaEncodingScheme.C, p.getText().get(p.getPosition()));
            }
            assertTrue("Simple was not the matching sequence", alignments.stream().filter(p -> p.getText().equals(recordSimple)).findFirst().isPresent());
        }

        {
            Collection<Alignment> alignments = tree.walk(Walkers.distance(g));

            assertEquals("Not all alignments had distance 0", alignments.stream().allMatch(alignment -> alignment.getDistance() == 0));
            for (Alignment p : alignments) {
                assertEquals("The base pair was wrong for " + p.getPosition(), AcceptUnknownDnaEncodingScheme.G, p.getText().get(p.getPosition()));
            }
            assertTrue("Simple was not the matching sequence", alignments.stream().filter(p -> p.getText().equals(recordSimple)).findFirst().isPresent());
        }
    }

    /**
     * Find distances in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testDistance_trivial1() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(recordSimple);

        assertNotNull(tree);

        Sequence t = this.sequenceFactory.fromString("T").get();

        Collection<Alignment> alignments = tree.walk(Walkers.distance(t));

        assertEquals("Not all alignments had distance 1", alignments.stream().allMatch(a -> a.getDistance() == 1));
        assertTrue("Did not contain first position", alignments.stream().filter(x -> x.getPosition() == 0).findFirst().isPresent());
        assertTrue("Did not contain last position", alignments.stream().filter(x -> x.getPosition() == recordSimple.length()-1).findFirst().isPresent());
        assertEquals("Number of alignments did not match", recordSimple.length(), alignments.size());
        assertTrue("Simple was not the matching sequence", alignments.stream().filter(p -> p.getText().equals(recordSimple)).findFirst().isPresent());
    }

    /**
     * Find distances in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testDistance_trivialN() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(recordSimple);

        assertNotNull(tree);

        Sequence n = this.sequenceFactory.fromString("N").get();

        Collection<Alignment> alignments = tree.walk(Walkers.distance(n));

        assertEquals("Not all alignments had correct", alignments.stream().allMatch(a -> a.getDistance() == 1));
        assertEquals("Number of alignments did not match", recordSimple.length(), alignments.size());
        assertTrue("Simple was not the matching sequence", alignments.stream().filter(p -> p.getText().equals(recordSimple)).findFirst().isPresent());
    }

    /**
     * Find distances in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testDistance_ExactMatch() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(recordSimple);

        assertNotNull(tree);

        Sequence seq = this.sequenceFactory.fromString("AAC").get();

        Collection<Alignment> alignments = tree.walk(Walkers.distance(seq));

        assertEquals("Number of alignments did not match", 2, alignments.size());
        assertTrue("Simple was not the matching sequence", alignments.stream().filter(p -> p.getText().equals(recordSimple)).findFirst().isPresent());
    }

    /**
     * Find distances in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testDistance_multiCharacterMissing() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(recordSimple);

        assertNotNull(tree);

        Sequence seq = this.sequenceFactory.fromString("AAT").get();

        Collection<Alignment> alignments = tree.walk(Walkers.distance(seq));

        assertEquals("Not all alignments had correct", alignments.stream().allMatch(a -> a.getDistance() == 1));
        assertEquals("Number of alignments did not match", 6, alignments.size());
        assertTrue("Simple was not the matching sequence", alignments.stream().filter(p -> p.getText().equals(recordSimple)).findFirst().isPresent());
    }

    /**
     * Find distances in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testDistance_offByEndN() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(recordSimple);

        assertNotNull(tree);

        Sequence seq = this.sequenceFactory.fromString("AAN").get();

        Collection<Alignment> alignments = tree.walk(Walkers.distance(seq));

        assertEquals("Not all alignments had correct", alignments.stream().allMatch(a -> a.getDistance() == 1));
        assertEquals("Number of alignments did not match", 6, alignments.size());
        assertTrue("Simple was not the matching sequence", alignments.stream().filter(p -> p.getText().equals(recordSimple)).findFirst().isPresent());
    }

    /**
     * Find distances in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testDistance_offByMiddleN() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(recordSimple);

        assertNotNull(tree);

        Sequence seq = this.sequenceFactory.fromString("ANA").get();

        Collection<Alignment> alignments = tree.walk(Walkers.distance(seq));

        assertEquals("Not all alignments had correct", alignments.stream().allMatch(a -> a.getDistance() == 1));
        assertEquals("Number of alignments did not match", 3, alignments.size());
        assertTrue("Simple was not the matching sequence", alignments.stream().filter(p -> p.getText().equals(recordSimple)).findFirst().isPresent());
    }

    /**
     * Find distances in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testDistance_offByStartN() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(recordSimple);

        assertNotNull(tree);

        Sequence seq = this.sequenceFactory.fromString("NAA").get();

        Collection<Alignment> alignments = tree.walk(Walkers.distance(seq));

        assertEquals("Not all alignments had correct", alignments.stream().allMatch(a -> a.getDistance() == 1));
        assertEquals("Number of alignments did not match", 6, alignments.size());
        assertTrue("Simple was not the matching sequence", alignments.stream().filter(p -> p.getText().equals(recordSimple)).findFirst().isPresent());
    }

    /**
     * Find distances in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testDistanceMultiple_offByEndN() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(recordSimple);
        tree.addSequence(record3);

        assertNotNull(tree);

        Sequence seq = this.sequenceFactory.fromString("AAN").get();

        Collection<Alignment> alignments = tree.walk(Walkers.distance(seq));

        assertEquals("Not all alignments had correct", alignments.stream().allMatch(a -> a.getDistance() == 1));
        assertEquals("Number of alignments did not match", 102, alignments.size());

        final List<Alignment> simpleAlignments = alignments.stream().filter(p -> p.getText().equals(recordSimple)).collect(Collectors.toList());
        final List<Alignment> record3Alignments = alignments.stream().filter(p -> p.getText().equals(record3)).collect(Collectors.toList());

        assertEquals("The alignments from simple and record 3 did not match up", 102, simpleAlignments.size() + record3Alignments.size());
        assertEquals("Simple was not the matching sequence for the correct number of  alignments", 6, simpleAlignments.size());
    }

    /**
     * Find distances in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testDistanceMultiple_offByMiddleN() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(recordSimple);
        tree.addSequence(record3);

        assertNotNull(tree);

        Sequence seq = this.sequenceFactory.fromString("ANA").get();

        Collection<Alignment> alignments = tree.walk(Walkers.distance(seq));

        assertEquals("Not all alignments had correct", alignments.stream().allMatch(a -> a.getDistance() == 1));
        assertEquals("Number of alignments did not match", 69, alignments.size());

        final List<Alignment> simpleAlignments = alignments.stream().filter(p -> p.getText().equals(recordSimple)).collect(Collectors.toList());
        final List<Alignment> record3Alignments = alignments.stream().filter(p -> p.getText().equals(record3)).collect(Collectors.toList());

        assertEquals("The alignments from simple and record 3 did not match up", 69, simpleAlignments.size() + record3Alignments.size());
        assertEquals("Simple was not the matching sequence for the correct number of  alignments", 3, simpleAlignments.size());
    }

    /**
     * Find distances in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testDistanceMultiple_offByStartN() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(recordSimple);
        tree.addSequence(record3);

        assertNotNull(tree);

        Sequence seq = this.sequenceFactory.fromString("NAA").get();

        Collection<Alignment> alignments = tree.walk(Walkers.distance(seq));

        assertEquals("Not all alignments had correct", alignments.stream().allMatch(a -> a.getDistance() == 1));
        assertEquals("Number of alignments did not match", 102, alignments.size());

        final List<Alignment> simpleAlignments = alignments.stream().filter(p -> p.getText().equals(recordSimple)).collect(Collectors.toList());
        final List<Alignment> record3Alignments = alignments.stream().filter(p -> p.getText().equals(record3)).collect(Collectors.toList());

        assertEquals("The alignments from simple and record 3 did not match up", 102, simpleAlignments.size() + record3Alignments.size());
        assertEquals("Simple was not the matching sequence for the correct number of  alignments", 6, simpleAlignments.size());
    }

    /**
     * Find distances in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testMaxDistanceMultiple_offByEndN() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(recordSimple);
        tree.addSequence(record3);

        assertNotNull(tree);

        Sequence seq = this.sequenceFactory.fromString("AAAAAAAAAAN").get();

        Optional<Collection<Alignment>> alignments = tree.walk(Walkers.distance(seq, 10));
        assertTrue("There was no result", alignments.isPresent());

        assertTrue("Distance was wrong", alignments.map(as -> as.stream().allMatch(a -> a.getDistance() == 4)).orElse(false));
        assertEquals("Number of alignments did not match", 1, (int) alignments.map(a -> a.size()).orElse(-1));
        assertTrue("Record3 was not the matching sequence", alignments.map(as -> as.stream().filter(p -> p.getText().equals(record3)).findFirst().isPresent()).orElse(false));

        alignments = tree.walk(Walkers.distance(seq, 2));

        assertFalse("There was a result", alignments.isPresent());
    }

    /**
     * Find distances in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testMaxDistanceMultiple_offByMiddleN() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(recordSimple);
        tree.addSequence(record3);

        assertNotNull(tree);

        Sequence seq = this.sequenceFactory.fromString("AAAAANAAAAA").get();

        Optional<Collection<Alignment>> alignments = tree.walk(Walkers.distance(seq, 10));
        assertTrue("There was no result", alignments.isPresent());

        assertTrue("Distance was wrong", alignments.map(as -> as.stream().allMatch(a -> a.getDistance() == 4)).orElse(false));
        assertEquals("Number of alignments did not match", 3, (int) alignments.map(a -> a.size()).orElse(-1));
        assertTrue("Record3 was not the matching sequence", alignments.map(as -> as.stream().filter(p -> p.getText().equals(record3)).findFirst().isPresent()).orElse(false));

        alignments = tree.walk(Walkers.distance(seq, 2));

        assertFalse("There was a result", alignments.isPresent());
    }

    /**
     * Find distances in the suffix tree
     * @throws IOException
     * @throws InvalidDnaFormatException
     */
    @Test
    public void testMaxDistanceMultiple_offByStartN() throws IOException, InvalidDnaFormatException {
        SuffixTree tree = this.factory.create(recordSimple);
        tree.addSequence(record3);

        assertNotNull(tree);

        Sequence seq = this.sequenceFactory.fromString("NAAAAAAAAAAA").get();

        Optional<Collection<Alignment>> alignments = tree.walk(Walkers.distance(seq, 10));
        assertTrue("There was no result", alignments.isPresent());

        assertTrue("Distance was wrong", alignments.map(as -> as.stream().allMatch(a -> a.getDistance() == 5)).orElse(false));
        assertEquals("Number of alignments did not match", 5, (int) alignments.map(a -> a.size()).orElse(-1));
        assertTrue("Record3 was not the matching sequence", alignments.map(as -> as.stream().filter(p -> p.getText().equals(record3)).findFirst().isPresent()).orElse(false));

        alignments = tree.walk(Walkers.distance(seq, 2));

        assertFalse("There was a result", alignments.isPresent());
    }
}