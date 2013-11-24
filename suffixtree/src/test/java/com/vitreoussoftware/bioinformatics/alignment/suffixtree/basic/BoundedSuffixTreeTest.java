package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTree;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTreeFactory;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTreeTest;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.reader.fasta.FastaStringFileStreamReaderTest;

/**
 * Run SuffixTreeTest for BasicSuffixTree
 * @author John
 *
 */
public class BoundedSuffixTreeTest extends SuffixTreeTest {
	@Override
	protected SuffixTreeFactory getSuffixTreeFactory() {
		return new BoundedSuffixTreeFactory(0, 200);
	}
	
	/**
	 * Find something in the suffix tree
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testFind_record1() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = getSuffixTreeFactory().create(record1);
		
		assertNotNull(tree);
		Sequence subSequence = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.record1.substring(120, 320));
		assertEquals("The length of the subsequence was wrong", 200, subSequence.length());
		SequenceCollection parents = tree.getParents(subSequence);
		assertTrue(tree.contains(subSequence));
	}
	
	/**
	 * Find something in the suffix tree
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testFind_record2() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = getSuffixTreeFactory().create(record2);
		
		assertNotNull(tree);
		Sequence subSequence = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.record2.substring(120, 320));
		assertEquals("The length of the subsequence was wrong", 200, subSequence.length());
		SequenceCollection parents = tree.getParents(subSequence);
		assertTrue(tree.contains(subSequence));
	}
	
	/**
	 * Find something in the suffix tree
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testFind_record3() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = getSuffixTreeFactory().create(record3);
		
		assertNotNull(tree);
		Sequence subSequence = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.record3.substring(120, 320));
		assertEquals("The length of the subsequence was wrong", 200, subSequence.length());
		SequenceCollection parents = tree.getParents(subSequence);
		assertTrue(tree.contains(subSequence));
	}
	
	/**
	 * Find something in the suffix tree
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testFindParents_record1() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = getSuffixTreeFactory().create(record1);
		
		assertNotNull(tree);
		Sequence subSequence = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.record1.substring(120, 320));
		assertEquals("The length of the subsequence was wrong", 200, subSequence.length());
		SequenceCollection parents = tree.getParents(subSequence);
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
		SuffixTree tree = getSuffixTreeFactory().create(record2);
		
		assertNotNull(tree);
		Sequence subSequence = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.record2.substring(120, 320));
		assertEquals("The length of the subsequence was wrong", 200, subSequence.length());
		SequenceCollection parents = tree.getParents(subSequence);
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
		SuffixTree tree = getSuffixTreeFactory().create(record3);
		
		assertNotNull(tree);
		Sequence subSequence = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.record3.substring(120, 320));
		assertEquals("The length of the subsequence was wrong", 200, subSequence.length());
		SequenceCollection parents = tree.getParents(subSequence);
		assertEquals(1, parents.size());
		assertEquals(record3, parents.iterator().next());
	}

	/**
	 * Check depth computation
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testDepth_record1() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = getSuffixTreeFactory().create(this.record1);
		
		assertNotNull(tree);
		assertEquals(200, tree.depth());
	}
	
	/**
	 * Find something in the suffix tree
	 * @throws IOException 
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testFindParentsWithMinimum_record3() throws IOException, InvalidDnaFormatException {
		SuffixTree tree = new BoundedSuffixTreeFactory(100, 200).create(record3);
		
		assertNotNull(tree);
		Sequence subSequence = this.sequenceFactory.fromString(FastaStringFileStreamReaderTest.record3.substring(120, 320));
		assertEquals("The length of the subsequence was wrong", 200, subSequence.length());
		SequenceCollection parents = tree.getParents(subSequence);
		assertEquals(1, parents.size());
		assertEquals(record3, parents.iterator().next());
	}
}
