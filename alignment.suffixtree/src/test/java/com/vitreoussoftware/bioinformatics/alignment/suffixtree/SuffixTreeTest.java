package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


import org.junit.Before;
import org.junit.Test;
import org.javatuples.Pair;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.reader.fasta.FastaStringFileStreamReaderTest;

/**
 * Test the FastaFileStreamReader class
 * @author John
 *
 */
public abstract class SuffixTreeTest {
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

	protected abstract SuffixTreeFactory getSuffixTreeFactory();

	/**
	 * Create a SuffixTree
	 * @throws IOException 
	 */
	@Test
	public void testCreate() throws IOException {
		SuffixTree tree = this.factory.create(recordSimple);
		
		assertNotNull(tree);
	}
	
	/**
	 * Create a SuffixTree with a null sequence
	 * @throws IllegalArgumentException 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreate_null() throws IllegalArgumentException {
		Sequence seq = null;
		this.factory.create(seq);
	}
	
	
	/**
	 * Check creation
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testCreate_simple() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(this.recordSimple);
		
		assertNotNull(tree);
	}
	
	/**
	 * Check creation
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testCreate_record1() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(this.record1);
		
		assertNotNull(tree);
	}
	
	/**
	 * Check creation
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testCreate_record2() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(this.record2);
		
		assertNotNull(tree);
	}
	
	/**
	 * Check creation
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testCreate_record3() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(this.record3);
		
		assertNotNull(tree);
	}
	
	/**
	 * Find something in the suffix tree
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testFind_trivial() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(recordSimple);
		
		assertNotNull(tree);
		assertTrue(tree.contains(this.sequenceFactory.fromString("A").get()));
		assertTrue(tree.contains(this.sequenceFactory.fromString("U").get()));
		assertTrue(tree.contains(this.sequenceFactory.fromString("C").get()));
		assertTrue(tree.contains(this.sequenceFactory.fromString("G").get()));
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
		assertFalse(tree.contains(this.sequenceFactory.fromString("T").get()));
	}


	/**
	 * Find something in the suffix tree
	 * @throws IOException
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testFind_simple() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(recordSimple);

		assertNotNull(tree);
		assertTrue(tree.contains(recordSimple));
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
		assertTrue(tree.contains(record1));
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
		assertTrue(tree.contains(record2));
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
		assertTrue(tree.contains(record3));
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
		assertTrue(tree.contains(this.sequenceFactory.fromString("GCGGAGCGUGUGGUUUAAUUCGAUGCUACACGAAGAACCUUACCAAGAUUUGACAUGCAUGUAGUAGUGAACUGAAAGGG").get()));
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
		assertFalse(tree.contains(this.sequenceFactory.fromString("GCGGAGCGUGUGGUUUAAUUCGAUGCUACACGAAGAACCUUACCAAGAUUUGACAUGCAUGUAGUAGUGAACUGAAAGGG").get()));
	}

	/**
	 * Find something in the suffix tree
	 * @throws IOException
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testFindParents_trivial() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(recordSimple);

		assertNotNull(tree);
		Set<Sequence> parents = null;

		Sequence a = this.sequenceFactory.fromString("A").get();
		parents = tree.getAlignments(a).stream().map(p -> p.getSequence()).collect(Collectors.toSet());
		assertEquals(1, parents.size());
		assertEquals(recordSimple, parents.iterator().next());

		Sequence u = this.sequenceFactory.fromString("U").get();
		parents = tree.getAlignments(u).stream().map(p -> p.getSequence()).collect(Collectors.toSet());
		assertEquals(1, parents.size());
		assertEquals(recordSimple, parents.iterator().next());

		Sequence c = this.sequenceFactory.fromString("C").get();
		parents = tree.getAlignments(c).stream().map(p -> p.getSequence()).collect(Collectors.toSet());;
		assertEquals(1, parents.size());
		assertEquals(recordSimple, parents.iterator().next());

		Sequence g = this.sequenceFactory.fromString("G").get();
		parents = tree.getAlignments(g).stream().map(p -> p.getSequence()).collect(Collectors.toSet());;
		assertEquals(1, parents.size());
		assertEquals(recordSimple, parents.iterator().next());
	}


	/**
	 * Find something in the suffix tree
	 * @throws IOException
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testFindParents_canFailWrongParent() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(recordSimple);

		assertNotNull(tree);

		Sequence g = this.sequenceFactory.fromString("G").get();
		Set<Sequence> parents = tree.getAlignments(g).stream().map(p -> p.getSequence()).collect(Collectors.toSet());;
		assertEquals(1, parents.size());
		assertFalse(g.equals(parents.iterator().next()));
	}

	/**
	 * Find something in the suffix tree
	 * @throws IOException
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testFindParents_canFailMissing() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(recordSimple);

		assertNotNull(tree);

		Sequence t = this.sequenceFactory.fromString("T").get();
		Set<Sequence> parents = tree.getAlignments(t).stream().map(p -> p.getSequence()).collect(Collectors.toSet());;
		assertTrue(parents.isEmpty());
	}

	/**
	 * Find something in the suffix tree
	 * @throws IOException
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testFindParents_simple() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(recordSimple);

		assertNotNull(tree);
		Set<Sequence> parents = tree.getAlignments(recordSimple).stream().map(p -> p.getSequence()).collect(Collectors.toSet());;
		assertEquals(1, parents.size());
		assertEquals(recordSimple, parents.iterator().next());
	}

	/**
	 * Find something in the suffix tree
	 * @throws IOException
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testFindParents_record1() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(record1);

		assertNotNull(tree);
		Set<Sequence> parents = tree.getAlignments(record1).stream().map(p -> p.getSequence()).collect(Collectors.toSet());;
		assertEquals(1, parents.size());
		assertEquals(record1, parents.iterator().next());
	}

	/**
	 * Find something in the suffix tree
	 * @throws IOException
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testFindParents_record2() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(record2);

		assertNotNull(tree);
		Set<Sequence> parents = tree.getAlignments(record2).stream().map(p -> p.getSequence()).collect(Collectors.toSet());;
		assertEquals(1, parents.size());
		assertEquals(record2, parents.iterator().next());
	}

	/**
	 * Find something in the suffix tree
	 * @throws IOException
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testFindParents_record3() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(record3);

		assertNotNull(tree);
		Set<Sequence> parents = tree.getAlignments(record3).stream().map(p -> p.getSequence()).collect(Collectors.toSet());;
		assertEquals(1, parents.size());
		assertEquals(record3, parents.iterator().next());
	}

	/**
	 * Find something in the suffix tree
	 * @throws IOException
	 */
	@Test
	public void testFindParents_record3Substring() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(record3);

		Sequence seq = this.sequenceFactory.fromString("GCGGAGCGUGUGGUUUAAUUCGAUGCUACACGAAGAACCUUACCAAGAUUUGACAUGCAUGUAGUAGUGAACUGAAAGGG").get();
		assertNotNull(tree);
		Set<Sequence> parents = tree.getAlignments(seq).stream().map(p -> p.getSequence()).collect(Collectors.toSet());;
		assertEquals(1, parents.size());
		assertEquals(record3, parents.iterator().next());
	}

	/**
	 * Find something in the suffix tree
	 * @throws IOException
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testFindParentsMultiple_trivial() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(recordSimple);
		tree.addSequence(record1);
		tree.addSequence(record2);
		tree.addSequence(record3);

		assertNotNull(tree);
		Set<Sequence> parents = null;

		Sequence a = this.sequenceFactory.fromString("A").get();
		parents = tree.getAlignments(a).stream().map(p -> p.getSequence()).collect(Collectors.toSet());;
		assertTrue(parents.contains(recordSimple));
		assertTrue(parents.contains(record1));
		assertTrue(parents.contains(record2));
		assertTrue(parents.contains(record3));
		assertEquals(4, parents.size());

		Sequence u = this.sequenceFactory.fromString("U").get();
		parents = tree.getAlignments(u).stream().map(p -> p.getSequence()).collect(Collectors.toSet());;
		assertEquals(4, parents.size());
		assertTrue(parents.contains(recordSimple));
		assertTrue(parents.contains(record1));
		assertTrue(parents.contains(record2));
		assertTrue(parents.contains(record3));

		Sequence c = this.sequenceFactory.fromString("C").get();
		parents = tree.getAlignments(c).stream().map(p -> p.getSequence()).collect(Collectors.toSet());;
		assertEquals(4, parents.size());
		assertTrue(parents.contains(recordSimple));
		assertTrue(parents.contains(record1));
		assertTrue(parents.contains(record2));
		assertTrue(parents.contains(record3));

		Sequence g = this.sequenceFactory.fromString("G").get();
		parents = tree.getAlignments(g).stream().map(p -> p.getSequence()).collect(Collectors.toSet());;
		assertEquals(4, parents.size());
		assertTrue(parents.contains(recordSimple));
		assertTrue(parents.contains(record1));
		assertTrue(parents.contains(record2));
		assertTrue(parents.contains(record3));
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

		Collection<Pair<Integer, SequenceCollection>> distancesA = tree.shortestDistance(a);

		assertEquals("Number of tuples", 1, distancesA.size());
		assertEquals("Distance of first tuple", 0, distancesA.stream().findFirst().get().getValue0().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesA.stream().findFirst().get().getValue1().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesA.stream().findFirst().get().getValue1().stream().findFirst().get());

		Collection<Pair<Integer, SequenceCollection>> distancesU = tree.shortestDistance(u);

		assertEquals("Number of tuples", 1, distancesU.size());
		assertEquals("Distance of first tuple", 0, distancesU.stream().findFirst().get().getValue0().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesU.stream().findFirst().get().getValue1().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesU.stream().findFirst().get().getValue1().stream().findFirst().get());

		Collection<Pair<Integer, SequenceCollection>> distancesC = tree.shortestDistance(c);

		assertEquals("Number of tuples", 1, distancesC.size());
		assertEquals("Distance of first tuple", 0, distancesC.stream().findFirst().get().getValue0().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesC.stream().findFirst().get().getValue1().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesC.stream().findFirst().get().getValue1().stream().findFirst().get());

		Collection<Pair<Integer, SequenceCollection>> distancesG = tree.shortestDistance(g);

		assertEquals("Number of tuples", 1, distancesG.size());
		assertEquals("Distance of first tuple", 0, distancesG.stream().findFirst().get().getValue0().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesG.stream().findFirst().get().getValue1().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesG.stream().findFirst().get().getValue1().stream().findFirst().get());
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

		Collection<Pair<Integer, SequenceCollection>> distancesT = tree.shortestDistance(t);

		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 1, distancesT.stream().findFirst().get().getValue0().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesT.stream().findFirst().get().getValue1().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesT.stream().findFirst().get().getValue1().stream().findFirst().get());
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

		Collection<Pair<Integer, SequenceCollection>> distancesT = tree.shortestDistance(n);

		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 1, distancesT.stream().findFirst().get().getValue0().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesT.stream().findFirst().get().getValue1().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesT.stream().findFirst().get().getValue1().stream().findFirst().get());
	}

	/**
	 * Find distances in the suffix tree
	 * @throws IOException
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testDistance_multiCharacterOptions() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(recordSimple);

		assertNotNull(tree);

		Sequence seq = this.sequenceFactory.fromString("AAC").get();

		Collection<Pair<Integer, SequenceCollection>> distancesT = tree.shortestDistance(seq);

		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 0, distancesT.stream().findFirst().get().getValue0().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesT.stream().findFirst().get().getValue1().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesT.stream().findFirst().get().getValue1().stream().findFirst().get());
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

		Collection<Pair<Integer, SequenceCollection>> distancesT = tree.shortestDistance(seq);

		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 1, distancesT.stream().findFirst().get().getValue0().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesT.stream().findFirst().get().getValue1().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesT.stream().findFirst().get().getValue1().stream().findFirst().get());
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

		Collection<Pair<Integer, SequenceCollection>> distancesT = tree.shortestDistance(seq);

		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 1, distancesT.stream().findFirst().get().getValue0().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesT.stream().findFirst().get().getValue1().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesT.stream().findFirst().get().getValue1().stream().findFirst().get());
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

		Collection<Pair<Integer, SequenceCollection>> distancesT = tree.shortestDistance(seq);

		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 1, distancesT.stream().findFirst().get().getValue0().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesT.stream().findFirst().get().getValue1().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesT.stream().findFirst().get().getValue1().stream().findFirst().get());
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

		Collection<Pair<Integer, SequenceCollection>> distancesT = tree.shortestDistance(seq);

		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 1, distancesT.stream().findFirst().get().getValue0().intValue());
		assertEquals("Number of sequences on first tuple", 2, distancesT.stream().findFirst().get().getValue1().size());
		assertTrue("Simple was not a parent", distancesT.stream().findFirst().get().getValue1().contains(recordSimple));
		assertTrue("Record3 was not a parent", distancesT.stream().findFirst().get().getValue1().contains(record3));
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

		Collection<Pair<Integer, SequenceCollection>> distancesT = tree.shortestDistance(seq);

		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 1, distancesT.stream().findFirst().get().getValue0().intValue());
		assertEquals("Number of sequences on first tuple", 2, distancesT.stream().findFirst().get().getValue1().size());
		assertTrue("Simple was not a parent", distancesT.stream().findFirst().get().getValue1().contains(recordSimple));
		assertTrue("Record3 was not a parent", distancesT.stream().findFirst().get().getValue1().contains(record3));
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

		Collection<Pair<Integer, SequenceCollection>> distancesT = tree.shortestDistance(seq);

		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 1, distancesT.stream().findFirst().get().getValue0().intValue());
		assertEquals("Number of sequences on first tuple", 2, distancesT.stream().findFirst().get().getValue1().size());
		assertTrue("Simple was not a parent", distancesT.stream().findFirst().get().getValue1().contains(recordSimple));
		assertTrue("Record3 was not a parent", distancesT.stream().findFirst().get().getValue1().contains(record3));
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

		Collection<Pair<Integer, SequenceCollection>> distances = tree.shortestDistance(seq, 10);

		assertEquals("Number of tuples", 1, distances.size());
		assertEquals("Distance of first tuple", 4, distances.stream().findFirst().get().getValue0().intValue());
		assertEquals("Number of sequences on first tuple", 1, distances.stream().findFirst().get().getValue1().size());
		assertTrue("Record3 was not a parent", distances.stream().findFirst().get().getValue1().contains(record3));

		distances = tree.shortestDistance(seq, 2);

		assertEquals("Number of tuples", 0, distances.size());
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

		Collection<Pair<Integer, SequenceCollection>> distances = tree.shortestDistance(seq, 10);

		assertEquals("Number of tuples", 1, distances.size());
		assertEquals("Distance of first tuple", 4, distances.stream().findFirst().get().getValue0().intValue());
		assertEquals("Number of sequences on first tuple", 1, distances.stream().findFirst().get().getValue1().size());
		assertTrue("Simple was not a parent", distances.stream().findFirst().get().getValue1().contains(record3));

		distances = tree.shortestDistance(seq, 2);

		assertEquals("Number of tuples", 0, distances.size());
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

		Collection<Pair<Integer, SequenceCollection>> distance = tree.shortestDistance(seq, 10);

		assertEquals("Number of tuples", 1, distance.size());
		assertEquals("Distance of first tuple", 5, distance.stream().findFirst().get().getValue0().intValue());
		assertEquals("Number of sequences on first tuple", 1, distance.stream().findFirst().get().getValue1().size());
		assertTrue("Record3 was not a parent", distance.stream().findFirst().get().getValue1().contains(record3));


		distance = tree.shortestDistance(seq, 2);

		assertEquals("Number of tuples", 0, distance.size());
	}

	/**
	 * Find distances in the suffix tree
	 * @throws IOException
	 * @throws InvalidDnaFormatException
	 */
	@Test
	public void testDistances_trivial3() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(this.sequenceFactory.fromString("TATATACT").get());

		assertNotNull(tree);

		Sequence sequence = this.sequenceFactory.fromString("TAT").get();

		Collection<Pair<Sequence, List<Integer>>> distances = tree.distances(sequence, 2);

		// There is only one parent
		assertEquals("Number of tuples", 1, distances.size());

		List<Integer> list = distances.stream().findFirst().get().getValue1();
		Collections.sort(list);
		// There are 4 shortestDistance values on that parent TAT(0) TAT(0) TAC(1) ACT(2). ATA (3) is not included as it is over max shortestDistance
		assertEquals("Number of distances of first tuple", 4, list.size());
		// Contains the correct values
		assertEquals("First position", 0, list.get(0).intValue());
		assertEquals("First position", 0, list.get(1).intValue());
		assertEquals("First position", 1, list.get(2).intValue());
		assertEquals("First position", 2, list.get(3).intValue());
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
		assertEquals(1, tree.depth());
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
		assertEquals(2, tree.depth());
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
		assertEquals(this.recordSimple.length(), tree.depth());
	}
	
	/**
	 * Check depth computation
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test // Keep around for debugging
	public void testDepth_record1() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(this.record1);

		assertNotNull(tree);
		assertEquals(this.record1.length(), tree.depth());
	}

	/**
	 * Check depth computation
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	//@Test // Keep around for debugging
	public void testDepth_record2() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(this.record2);
		
		assertNotNull(tree);
		assertEquals(this.record2.length(), tree.depth());
	}
	
	/**
	 * Check depth computation
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	//@Test // Keep around for debugging 
	public void testDepth_record3() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(this.record3);
		
		assertNotNull(tree);
		assertEquals(this.record3.length(), tree.depth());
	}
}
