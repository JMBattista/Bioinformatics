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
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.reader.SequenceStringStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.reader.fasta.FastaStringFileStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.reader.fasta.FastaStringFileStreamReaderTest;

/**
 * Test the FastaFileStreamReader class
 * @author John
 *
 */
public class SuffixTreeTest {
	private SuffixTreeFactory factory;
	private FastaSequenceFactory sequenceFactory;
	private Sequence record1;
	private Sequence record2;
	private Sequence record3;
	private Sequence recordSimple;
	
	@Before
	public void setup() throws InvalidDnaFormatException {
		this.factory = new BasicSuffixTreeFactory();
		this.sequenceFactory = new FastaSequenceFactory();
		this.record1 = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.record1);
		this.record2 = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.record2);
		this.record3 = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.record3);
		this.recordSimple = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.recordSimple);
	}
	
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
		Collection<Sequence> parents = null;
		
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
		Collection<Sequence> parents = tree.getParents(g);
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
		Collection<Sequence> parents = tree.getParents(t);
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
		Collection<Sequence> parents = tree.getParents(recordSimple);
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
		Collection<Sequence> parents = tree.getParents(record1);
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
		Collection<Sequence> parents = tree.getParents(record2);
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
		Collection<Sequence> parents = tree.getParents(record3);
		assertEquals(1, parents.size());
		assertEquals(record3, parents.iterator().next());
	}
	
	/**
	 * Find something in the suffix tree
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testFindParents_record3Substring() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(record3);
		
		Sequence seq = this.sequenceFactory.fromString("GCGGAGCGUGUGGUUUAAUUCGAUGCUACACGAAGAACCUUACCAAGAUUUGACAUGCAUGUAGUAGUGAACUGAAAGGG");
		assertNotNull(tree);
		Collection<Sequence> parents = tree.getParents(seq);
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
		Collection<Sequence> parents = null;
		
		Sequence a = this.sequenceFactory.fromString("A");
		parents = tree.getParents(a);
//		assertTrue(parents.contains(recordSimple));
//		assertTrue(parents.contains(record1));
//		assertTrue(parents.contains(record2));
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
	@Test
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
	@Test
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
	@Test
	public void testDepth_record3() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = this.factory.create(this.record3);
		
		assertNotNull(tree);
		assertEquals(this.record3.length(), tree.depth());
	}
}
