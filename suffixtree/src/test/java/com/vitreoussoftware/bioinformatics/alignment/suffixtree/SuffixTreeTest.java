package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

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
	private FastaSequenceFactory factory;
	private Sequence record1;
	private Sequence record2;
	private Sequence record3;
	private Sequence recordSimple;
	
	@Before
	public void setup() throws InvalidDnaFormatException {
		this.factory = new FastaSequenceFactory();
		this.record1 = this.factory.fromString(FastaStringFileStreamReaderTest.record1);
		this.record2 = this.factory.fromString(FastaStringFileStreamReaderTest.record2);
		this.record3 = this.factory.fromString(FastaStringFileStreamReaderTest.record3);
		this.recordSimple = this.factory.fromString(FastaStringFileStreamReaderTest.recordSimple);
	}
	
	/**
	 * Create a SuffixTree
	 * @throws IOException 
	 */
	@Test
	public void testCreate() throws IOException {
		SuffixTree tree = new SuffixTree(recordSimple);
		
		assertNotNull(tree);
	}
	
	/**
	 * Create a SuffixTree with a null sequence
	 * @throws IllegalArgumentException 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreate_null() throws IllegalArgumentException {
		new SuffixTree(null);
	}
	
	
	/**
	 * Check creation
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testCreate_simple() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = new SuffixTree(this.recordSimple);
		
		assertNotNull(tree);
	}
	
	/**
	 * Check creation
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testCreate_record1() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = new SuffixTree(this.record1);
		
		assertNotNull(tree);
	}
	
	/**
	 * Check creation
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testCreate_record2() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = new SuffixTree(this.record2);
		
		assertNotNull(tree);
	}
	
	/**
	 * Check creation
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testCreate_record3() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = new SuffixTree(this.record3);
		
		assertNotNull(tree);
	}
	
	/**
	 * Find something in the suffix tree
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testFind_trivial() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = new SuffixTree(recordSimple);
		
		assertNotNull(tree);
		assertTrue(tree.contains(this.factory.fromString("A")));
		assertTrue(tree.contains(this.factory.fromString("U")));
		assertTrue(tree.contains(this.factory.fromString("C")));
		assertTrue(tree.contains(this.factory.fromString("G")));
	}
	
	/**
	 * Find something in the suffix tree
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testFind_canFail() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = new SuffixTree(recordSimple);
		
		assertNotNull(tree);
		assertFalse(tree.contains(this.factory.fromString("T")));
	}
	
	
	/**
	 * Find something in the suffix tree
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testFind_simple() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = new SuffixTree(recordSimple);
		
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
		SuffixTree tree = new SuffixTree(record1);
		
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
		SuffixTree tree = new SuffixTree(record2);
		
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
		SuffixTree tree = new SuffixTree(record3);
		
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
		SuffixTree tree = new SuffixTree(record3);
		
		assertNotNull(tree);
		assertTrue(tree.contains(this.factory.fromString("GCGGAGCGUGUGGUUUAAUUCGAUGCUACACGAAGAACCUUACCAAGAUUUGACAUGCAUGUAGUAGUGAACUGAAAGGG")));	
	}
	
	/**
	 * Find something in the suffix tree
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testFind_canFailLarge() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = new SuffixTree(record2);
		
		assertNotNull(tree);
		assertFalse(tree.contains(this.factory.fromString("GCGGAGCGUGUGGUUUAAUUCGAUGCUACACGAAGAACCUUACCAAGAUUUGACAUGCAUGUAGUAGUGAACUGAAAGGG")));	
	}
	
	/**
	 * Check depth computation
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testDepth_1() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = new SuffixTree(this.factory.fromString("A"));
		
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
		SuffixTree tree = new SuffixTree(this.factory.fromString("AU"));
		
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
		SuffixTree tree = new SuffixTree(this.recordSimple);
		
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
		SuffixTree tree = new SuffixTree(this.record1);
		
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
		SuffixTree tree = new SuffixTree(this.record2);
		
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
		SuffixTree tree = new SuffixTree(this.record3);
		
		assertNotNull(tree);
		assertEquals(this.record3.length(), tree.depth());
	}
}
