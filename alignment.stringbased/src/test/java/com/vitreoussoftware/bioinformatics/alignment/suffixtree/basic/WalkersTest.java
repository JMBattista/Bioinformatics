package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import com.vitreoussoftware.bioinformatics.alignment.Position;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTree;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTreeFactory;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.Walkers;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.reader.fasta.FastaStringFileStreamReaderTest;
import org.javatuples.Pair;
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
            Pair<Integer, Collection<Position>> distances = tree.walk(Walkers.distance(a));

            final int distance = distances.getValue0();
            final Collection<Position> positions = distances.getValue1();

            assertEquals("Distance was wrong", 0, distance);
            assertEquals("Number of positions was wrong", 22, positions.size());
            for (Position p : positions) {
                assertEquals("The base pair was wrong for " + p.getPosition(), AcceptUnknownDnaEncodingScheme.A, p.getSequence().get(p.getPosition()));
            }
            assertTrue("Simple was not the matching sequence", positions.stream().filter(p -> p.getSequence().equals(recordSimple)).findFirst().isPresent());
        }

        {
            Pair<Integer, Collection<Position>> distances = tree.walk(Walkers.distance(u));

            final int distance = distances.getValue0();
            final Collection<Position> positions = distances.getValue1();

            assertEquals("Distance was wrong", 0, distance);
            for (Position p : positions) {
                assertEquals("The base pair was wrong for " + p.getPosition(), AcceptUnknownDnaEncodingScheme.U, p.getSequence().get(p.getPosition()));
            }
            assertTrue("Simple was not the matching sequence", positions.stream().filter(p -> p.getSequence().equals(recordSimple)).findFirst().isPresent());
        }

        {
            Pair<Integer, Collection<Position>> distances = tree.walk(Walkers.distance(c));

            final int distance = distances.getValue0();
            final Collection<Position> positions = distances.getValue1();

            assertEquals("Distance was wrong", 0, distance);
            for (Position p : positions) {
                assertEquals("The base pair was wrong for " + p.getPosition(), AcceptUnknownDnaEncodingScheme.C, p.getSequence().get(p.getPosition()));
            }
            assertTrue("Simple was not the matching sequence", positions.stream().filter(p -> p.getSequence().equals(recordSimple)).findFirst().isPresent());
        }

        {
            Pair<Integer, Collection<Position>> distances = tree.walk(Walkers.distance(g));

            final int distance = distances.getValue0();
            final Collection<Position> positions = distances.getValue1();

            assertEquals("Distance was wrong", 0, distance);
            for (Position p : positions) {
                assertEquals("The base pair was wrong for " + p.getPosition(), AcceptUnknownDnaEncodingScheme.G, p.getSequence().get(p.getPosition()));
            }
            assertTrue("Simple was not the matching sequence", positions.stream().filter(p -> p.getSequence().equals(recordSimple)).findFirst().isPresent());
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

        Pair<Integer, Collection<Position>> distances = tree.walk(Walkers.distance(t));
        final int distance = distances.getValue0();
        final Collection<Position> positions = distances.getValue1();

        assertEquals("Distance was wrong", 1, distance);
        assertTrue("Did not contain first position", positions.stream().filter(x -> x.getPosition() == 0).findFirst().isPresent());
        assertTrue("Did not contain last position", positions.stream().filter(x -> x.getPosition() == recordSimple.length()-1).findFirst().isPresent());
        assertEquals("Number of positions did not match", recordSimple.length(), positions.size());
        assertTrue("Simple was not the matching sequence", positions.stream().filter(p -> p.getSequence().equals(recordSimple)).findFirst().isPresent());
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

        Pair<Integer, Collection<Position>> distances = tree.walk(Walkers.distance(n));
        final int distance = distances.getValue0();
        final Collection<Position> positions = distances.getValue1();

        assertEquals("Distance was wrong", 1, distance);
        assertEquals("Number of positions did not match", recordSimple.length(), positions.size());
        assertTrue("Simple was not the matching sequence", positions.stream().filter(p -> p.getSequence().equals(recordSimple)).findFirst().isPresent());
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

        Pair<Integer, Collection<Position>> distances = tree.walk(Walkers.distance(seq));
        final int distance = distances.getValue0();
        final Collection<Position> positions = distances.getValue1();

        assertEquals("Distance was wrong", 0, distance);
        assertEquals("Number of positions did not match", 2, positions.size());
        assertTrue("Simple was not the matching sequence", positions.stream().filter(p -> p.getSequence().equals(recordSimple)).findFirst().isPresent());
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

        Pair<Integer, Collection<Position>> distances = tree.walk(Walkers.distance(seq));
        final int distance = distances.getValue0();
        final Collection<Position> positions = distances.getValue1();

        assertEquals("Distance was wrong", 1, distance);
        assertEquals("Number of positions did not match", 6, positions.size());
        assertTrue("Simple was not the matching sequence", positions.stream().filter(p -> p.getSequence().equals(recordSimple)).findFirst().isPresent());
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

        Pair<Integer, Collection<Position>> distances = tree.walk(Walkers.distance(seq));
        final int distance = distances.getValue0();
        final Collection<Position> positions = distances.getValue1();

        assertEquals("Distance was wrong", 1, distance);
        assertEquals("Number of positions did not match", 6, positions.size());
        assertTrue("Simple was not the matching sequence", positions.stream().filter(p -> p.getSequence().equals(recordSimple)).findFirst().isPresent());
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

        Pair<Integer, Collection<Position>> distances = tree.walk(Walkers.distance(seq));
        final int distance = distances.getValue0();
        final Collection<Position> positions = distances.getValue1();

        assertEquals("Distance was wrong", 1, distance);
        assertEquals("Number of positions did not match", 3, positions.size());
        assertTrue("Simple was not the matching sequence", positions.stream().filter(p -> p.getSequence().equals(recordSimple)).findFirst().isPresent());
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

        Pair<Integer, Collection<Position>> distances = tree.walk(Walkers.distance(seq));
        final int distance = distances.getValue0();
        final Collection<Position> positions = distances.getValue1();

        assertEquals("Distance was wrong", 1, distance);
        assertEquals("Number of positions did not match", 6, positions.size());
        assertTrue("Simple was not the matching sequence", positions.stream().filter(p -> p.getSequence().equals(recordSimple)).findFirst().isPresent());
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

        Pair<Integer, Collection<Position>> distances = tree.walk(Walkers.distance(seq));
        final int distance = distances.getValue0();
        final Collection<Position> positions = distances.getValue1();

        assertEquals("Distance was wrong", 1, distance);
        assertEquals("Number of positions did not match", 102, positions.size());

        final List<Position> simplePositions = positions.stream().filter(p -> p.getSequence().equals(recordSimple)).collect(Collectors.toList());
        final List<Position> record3Positions = positions.stream().filter(p -> p.getSequence().equals(record3)).collect(Collectors.toList());

        assertEquals("The positions from simple and record 3 did not match up", 102, simplePositions.size() + record3Positions.size());
        assertEquals("Simple was not the matching sequence for the correct number of  positions", 6, simplePositions.size());
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

        Pair<Integer, Collection<Position>> distances = tree.walk(Walkers.distance(seq));
        final int distance = distances.getValue0();
        final Collection<Position> positions = distances.getValue1();

        assertEquals("Distance was wrong", 1, distance);
        assertEquals("Number of positions did not match", 69, positions.size());

        final List<Position> simplePositions = positions.stream().filter(p -> p.getSequence().equals(recordSimple)).collect(Collectors.toList());
        final List<Position> record3Positions = positions.stream().filter(p -> p.getSequence().equals(record3)).collect(Collectors.toList());

        assertEquals("The positions from simple and record 3 did not match up", 69, simplePositions.size() + record3Positions.size());
        assertEquals("Simple was not the matching sequence for the correct number of  positions", 3, simplePositions.size());
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

        Pair<Integer, Collection<Position>> distances = tree.walk(Walkers.distance(seq));
        final int distance = distances.getValue0();
        final Collection<Position> positions = distances.getValue1();

        assertEquals("Distance was wrong", 1, distance);
        assertEquals("Number of positions did not match", 102, positions.size());

        final List<Position> simplePositions = positions.stream().filter(p -> p.getSequence().equals(recordSimple)).collect(Collectors.toList());
        final List<Position> record3Positions = positions.stream().filter(p -> p.getSequence().equals(record3)).collect(Collectors.toList());

        assertEquals("The positions from simple and record 3 did not match up", 102, simplePositions.size() + record3Positions.size());
        assertEquals("Simple was not the matching sequence for the correct number of  positions", 6, simplePositions.size());
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

        Optional<Pair<Integer, Collection<Position>>> distances = tree.walk(Walkers.distance(seq, 10));
        assertTrue("There was no result", distances.isPresent());
        final int distance = distances.get().getValue0();
        final Collection<Position> positions = distances.get().getValue1();

        assertEquals("Distance was wrong", 4, distance);
        assertEquals("Number of positions did not match", 1, positions.size());
        assertTrue("Record3 was not the matching sequence", positions.stream().filter(p -> p.getSequence().equals(record3)).findFirst().isPresent());

        distances = tree.walk(Walkers.distance(seq, 2));

        assertFalse("There was a result", distances.isPresent());
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

        Optional<Pair<Integer, Collection<Position>>> distances = tree.walk(Walkers.distance(seq, 10));
        assertTrue("There was no result", distances.isPresent());
        final int distance = distances.get().getValue0();
        final Collection<Position> positions = distances.get().getValue1();

        assertEquals("Distance was wrong", 4, distance);
        assertEquals("Number of positions did not match", 3, positions.size());
        assertTrue("Record3 was not the matching sequence", positions.stream().filter(p -> p.getSequence().equals(record3)).findFirst().isPresent());

        distances = tree.walk(Walkers.distance(seq, 2));

        assertFalse("There was a result", distances.isPresent());
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

        Optional<Pair<Integer, Collection<Position>>> distances = tree.walk(Walkers.distance(seq, 10));
        assertTrue("There was no result", distances.isPresent());
        final int distance = distances.get().getValue0();
        final Collection<Position> positions = distances.get().getValue1();

        assertEquals("Distance was wrong", 5, distance);
        assertEquals("Number of positions did not match", 5, positions.size());
        assertTrue("Record 3 was not the matching sequence", positions.stream().filter(p -> p.getSequence().equals(record3)).findFirst().isPresent());

        distances = tree.walk(Walkers.distance(seq, 2));

        assertFalse("There was a result", distances.isPresent());
    }
}
