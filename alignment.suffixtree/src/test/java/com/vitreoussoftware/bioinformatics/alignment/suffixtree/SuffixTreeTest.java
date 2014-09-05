package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import com.vitreoussoftware.bioinformatics.alignment.Alignment;
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

        Consumer<Pair<String, Collection<Alignment>>> validate = (pair) -> {
            String seqName = pair.getValue0();
            Collection<Alignment> alignments = pair.getValue1();
            assertTrue("All tuples for " +  seqName + " have distance 0", alignments.stream().allMatch(alignment -> alignment.getDistance() == 0));
            assertTrue("All tuples for " + seqName + " have the correct text sequence", alignments.stream().allMatch(alignment -> alignment.getSequence().equals(recordSimple)));
        };

        validate.accept(Pair.with("A", tree.shortestDistance(a)));
        validate.accept(Pair.with("U", tree.shortestDistance(u)));
        validate.accept(Pair.with("C", tree.shortestDistance(c)));
        validate.accept(Pair.with("G", tree.shortestDistance(g)));

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

		Collection<Alignment> alignments = tree.shortestDistance(t);

        assertTrue("All tuples for T have distance 1", alignments.stream().allMatch(alignment -> alignment.getDistance() == 1));
        assertTrue("All tuples for T have the correct text sequence", alignments.stream().allMatch(alignment -> alignment.getSequence().equals(recordSimple)));
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

		Collection<Alignment> alignments = tree.shortestDistance(n);

        assertTrue("All tuples for N have distance 0", alignments.stream().allMatch(alignment -> alignment.getDistance() == 1));
        assertTrue("All tuples for N have the correct text sequence", alignments.stream().allMatch(alignment -> alignment.getSequence().equals(recordSimple)));
        assertEquals("N is aligned to all positions in the text sequence", recordSimple.length(), alignments.size());
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

		Collection<Alignment> alignments = tree.shortestDistance(seq);

        assertTrue("All tuples for AAC have distance 0", alignments.stream().allMatch(alignment -> alignment.getDistance() == 0));
        assertTrue("All tuples for AAC have the correct text sequence", alignments.stream().allMatch(alignment -> alignment.getSequence().equals(recordSimple)));
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
