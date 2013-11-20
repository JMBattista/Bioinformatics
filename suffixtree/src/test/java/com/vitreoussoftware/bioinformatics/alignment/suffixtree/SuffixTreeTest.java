package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic.BasicSuffixTreeFactory;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.reader.SequenceStringStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.reader.fasta.FastaStringFileStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.reader.fasta.FastaStringFileStreamReaderTest;
import com.vitreoussoftware.utilities.Tuple;

/**
 * Test the FastaFileStreamReader class
 * @author John
 *
 */
public abstract class SuffixTreeTest {
	private SuffixTreeFactory factory;
	private FastaSequenceFactory sequenceFactory;
	private Sequence record1;
	private Sequence record2;
	private Sequence record3;
	private Sequence recordSimple;
	
	@Before
	public void setup() throws InvalidDnaFormatException {
		this.factory = getSuffixTreeFactory();
		this.sequenceFactory = new FastaSequenceFactory();
		this.record1 = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.record1);
		this.record2 = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.record2);
		this.record3 = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.record3);
		this.recordSimple = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.recordSimple);
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
		assertTrue(tree.contains(this.sequenceFactory.fromString("A")));
		assertTrue(tree.contains(this.sequenceFactory.fromString("U")));
		assertTrue(tree.contains(this.sequenceFactory.fromString("C")));
		assertTrue(tree.contains(this.sequenceFactory.fromString("G")));
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
		assertFalse(tree.contains(this.sequenceFactory.fromString("T")));
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
		assertTrue(tree.contains(this.sequenceFactory.fromString("GCGGAGCGUGUGGUUUAAUUCGAUGCUACACGAAGAACCUUACCAAGAUUUGACAUGCAUGUAGUAGUGAACUGAAAGGG")));	
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
		assertFalse(tree.contains(this.sequenceFactory.fromString("GCGGAGCGUGUGGUUUAAUUCGAUGCUACACGAAGAACCUUACCAAGAUUUGACAUGCAUGUAGUAGUGAACUGAAAGGG")));	
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
		SequenceCollection parents = null;
		
		Sequence a = this.sequenceFactory.fromString("A");
		parents = tree.getParents(a);
		assertEquals(1, parents.size());
		assertEquals(recordSimple, parents.iterator().next());
		
		Sequence u = this.sequenceFactory.fromString("U");
		parents = tree.getParents(u);
		assertEquals(1, parents.size());
		assertEquals(recordSimple, parents.iterator().next());
		
		Sequence c = this.sequenceFactory.fromString("C");
		parents = tree.getParents(c);
		assertEquals(1, parents.size());
		assertEquals(recordSimple, parents.iterator().next());
		
		Sequence g = this.sequenceFactory.fromString("G");
		parents = tree.getParents(g);
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
		
		Sequence g = this.sequenceFactory.fromString("G");
		SequenceCollection parents = tree.getParents(g);
		assertEquals(1, parents.size());
		assertNotEquals(g, parents.iterator().next());
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
		
		Sequence t = this.sequenceFactory.fromString("T");
		SequenceCollection parents = tree.getParents(t);
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
		SequenceCollection parents = tree.getParents(recordSimple);
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
		SequenceCollection parents = tree.getParents(record1);
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
		SequenceCollection parents = tree.getParents(record2);
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
		SequenceCollection parents = tree.getParents(record3);
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
		
		Sequence seq = this.sequenceFactory.fromString("GCGGAGCGUGUGGUUUAAUUCGAUGCUACACGAAGAACCUUACCAAGAUUUGACAUGCAUGUAGUAGUGAACUGAAAGGG");
		assertNotNull(tree);
		SequenceCollection parents = tree.getParents(seq);
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
		SequenceCollection parents = null;
		
		Sequence a = this.sequenceFactory.fromString("A");
		parents = tree.getParents(a);
		assertTrue(parents.contains(recordSimple));
		assertTrue(parents.contains(record1));
		assertTrue(parents.contains(record2));
		assertTrue(parents.contains(record3));
		assertEquals(4, parents.size());
		
		Sequence u = this.sequenceFactory.fromString("U");
		parents = tree.getParents(u);
		assertEquals(4, parents.size());
		assertTrue(parents.contains(recordSimple));
		assertTrue(parents.contains(record1));
		assertTrue(parents.contains(record2));
		assertTrue(parents.contains(record3));
		
		Sequence c = this.sequenceFactory.fromString("C"); 
		parents = tree.getParents(c);
		assertEquals(4, parents.size());
		assertTrue(parents.contains(recordSimple));
		assertTrue(parents.contains(record1));
		assertTrue(parents.contains(record2));
		assertTrue(parents.contains(record3));
		
		Sequence g = this.sequenceFactory.fromString("G");
		parents = tree.getParents(g);
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
		
		Sequence a = this.sequenceFactory.fromString("A");
		Sequence u = this.sequenceFactory.fromString("U");
		Sequence c = this.sequenceFactory.fromString("C");
		Sequence g = this.sequenceFactory.fromString("G");
		
		Collection<Tuple<Integer, SequenceCollection>> distancesA = tree.distance(a);
		
		assertEquals("Number of tuples", 1, distancesA.size());
		assertEquals("Distance of first tuple", 0, distancesA.stream().findFirst().get().getItem1().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesA.stream().findFirst().get().getItem2().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesA.stream().findFirst().get().getItem2().stream().findFirst().get());
		
		Collection<Tuple<Integer, SequenceCollection>> distancesU = tree.distance(u);
		
		assertEquals("Number of tuples", 1, distancesU.size());
		assertEquals("Distance of first tuple", 0, distancesU.stream().findFirst().get().getItem1().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesU.stream().findFirst().get().getItem2().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesU.stream().findFirst().get().getItem2().stream().findFirst().get());
		
		Collection<Tuple<Integer, SequenceCollection>> distancesC = tree.distance(c);
		
		assertEquals("Number of tuples", 1, distancesC.size());
		assertEquals("Distance of first tuple", 0, distancesC.stream().findFirst().get().getItem1().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesC.stream().findFirst().get().getItem2().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesC.stream().findFirst().get().getItem2().stream().findFirst().get());
		
		Collection<Tuple<Integer, SequenceCollection>> distancesG = tree.distance(g);
		
		assertEquals("Number of tuples", 1, distancesG.size());
		assertEquals("Distance of first tuple", 0, distancesG.stream().findFirst().get().getItem1().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesG.stream().findFirst().get().getItem2().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesG.stream().findFirst().get().getItem2().stream().findFirst().get());
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
		
		Sequence t = this.sequenceFactory.fromString("T");
		
		Collection<Tuple<Integer, SequenceCollection>> distancesT = tree.distance(t);
		
		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 1, distancesT.stream().findFirst().get().getItem1().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesT.stream().findFirst().get().getItem2().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesT.stream().findFirst().get().getItem2().stream().findFirst().get());
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
		
		Sequence n = this.sequenceFactory.fromString("N");
		
		Collection<Tuple<Integer, SequenceCollection>> distancesT = tree.distance(n);
		
		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 1, distancesT.stream().findFirst().get().getItem1().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesT.stream().findFirst().get().getItem2().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesT.stream().findFirst().get().getItem2().stream().findFirst().get());
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
		
		Sequence seq = this.sequenceFactory.fromString("AAC");
		
		Collection<Tuple<Integer, SequenceCollection>> distancesT = tree.distance(seq);
		
		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 0, distancesT.stream().findFirst().get().getItem1().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesT.stream().findFirst().get().getItem2().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesT.stream().findFirst().get().getItem2().stream().findFirst().get());
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
		
		Sequence seq = this.sequenceFactory.fromString("AAN");
		
		Collection<Tuple<Integer, SequenceCollection>> distancesT = tree.distance(seq);
		
		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 1, distancesT.stream().findFirst().get().getItem1().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesT.stream().findFirst().get().getItem2().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesT.stream().findFirst().get().getItem2().stream().findFirst().get());
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
		
		Sequence seq = this.sequenceFactory.fromString("ANA");
		
		Collection<Tuple<Integer, SequenceCollection>> distancesT = tree.distance(seq);
		
		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 1, distancesT.stream().findFirst().get().getItem1().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesT.stream().findFirst().get().getItem2().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesT.stream().findFirst().get().getItem2().stream().findFirst().get());
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
		
		Sequence seq = this.sequenceFactory.fromString("NAA");
		
		Collection<Tuple<Integer, SequenceCollection>> distancesT = tree.distance(seq);
		
		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 1, distancesT.stream().findFirst().get().getItem1().intValue());
		assertEquals("Number of sequences on first tuple", 1, distancesT.stream().findFirst().get().getItem2().size());
		assertEquals("Correct first tuple sequence", recordSimple, distancesT.stream().findFirst().get().getItem2().stream().findFirst().get());
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
		
		Sequence seq = this.sequenceFactory.fromString("AAN");
		
		Collection<Tuple<Integer, SequenceCollection>> distancesT = tree.distance(seq);
		
		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 1, distancesT.stream().findFirst().get().getItem1().intValue());
		assertEquals("Number of sequences on first tuple", 2, distancesT.stream().findFirst().get().getItem2().size());
		assertTrue("Simple was not a parent", distancesT.stream().findFirst().get().getItem2().contains(recordSimple));
		assertTrue("Record3 was not a parent", distancesT.stream().findFirst().get().getItem2().contains(record3));
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
		
		Sequence seq = this.sequenceFactory.fromString("ANA");
		
		Collection<Tuple<Integer, SequenceCollection>> distancesT = tree.distance(seq);
		
		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 1, distancesT.stream().findFirst().get().getItem1().intValue());
		assertEquals("Number of sequences on first tuple", 2, distancesT.stream().findFirst().get().getItem2().size());
		assertTrue("Simple was not a parent", distancesT.stream().findFirst().get().getItem2().contains(recordSimple));
		assertTrue("Record3 was not a parent", distancesT.stream().findFirst().get().getItem2().contains(record3));
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
		
		Sequence seq = this.sequenceFactory.fromString("NAA");
		
		Collection<Tuple<Integer, SequenceCollection>> distancesT = tree.distance(seq);
		
		assertEquals("Number of tuples", 1, distancesT.size());
		assertEquals("Distance of first tuple", 1, distancesT.stream().findFirst().get().getItem1().intValue());
		assertEquals("Number of sequences on first tuple", 2, distancesT.stream().findFirst().get().getItem2().size());
		assertTrue("Simple was not a parent", distancesT.stream().findFirst().get().getItem2().contains(recordSimple));
		assertTrue("Record3 was not a parent", distancesT.stream().findFirst().get().getItem2().contains(record3));
	}
	
	/**
	 * Check depth computation
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testDepth_1() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(this.sequenceFactory.fromString("A"));
		
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
		SuffixTree tree = this.factory.create(this.sequenceFactory.fromString("AU"));
		
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
	//@Test // Keep around for debugging
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
