package com.vitreoussoftare.bioinformatics.sequence.collection.basic;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.vitreoussoftare.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftare.bioinformatics.sequence.Sequence;
import com.vitreoussoftare.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftare.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftare.bioinformatics.sequence.reader.SequenceStreamReader;
import com.vitreoussoftare.bioinformatics.sequence.reader.SequenceStringStreamReader;
import com.vitreoussoftare.bioinformatics.sequence.reader.fasta.FastaStringFileStreamReader;
import com.vitreoussoftare.bioinformatics.sequence.reader.fasta.FastaStringFileStreamReaderTest;
import com.vitreoussoftare.bioinformatics.sequence.reader.fasta.SequenceFromFastaStringStreamReader;

/**
 * Test the SequenceList basic collection so we have a baseline
 * @author John
 *
 */
public class SequenceListTest {

	private SequenceCollectionFactory factory;

	/**
	 * Setup for test execution
	 */
	@Before
	public void setup() {
		this.factory = new SequenceListFactory();
	}
	
	/**
	 * Can it be created?
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testCreation() throws InvalidDnaFormatException {
		SequenceCollection sc = this.factory.getSequenceCollection();
		
		assertNotNull(sc);
	}
	
	/**
	 * Can it be created?
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testAdd() throws InvalidDnaFormatException {
		SequenceCollection sc = this.factory.getSequenceCollection();
		
		assertNotNull(sc);
		sc.add(Sequence.fromFasta("AATTCCGGUU"));
		assertEquals(1,  sc.size());
	}
	
	/**
	 * Can it be created?
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testContains_sameRef() throws InvalidDnaFormatException {
		SequenceCollection sc = this.factory.getSequenceCollection();
		Sequence seq = Sequence.fromFasta("AATTCCGGUU");
		sc.add(seq);
		assertEquals(1,  sc.size());
		assertTrue(sc.contains(seq));
	}
	
	/**
	 * Can it be created?
	 * @throws InvalidDnaFormatException 
	 */
	@Test
	public void testContains_diffRef() throws InvalidDnaFormatException {
		SequenceCollection sc = this.factory.getSequenceCollection();
		sc.add(Sequence.fromFasta("AATTCCGGUU"));
		assertEquals(1,  sc.size());
		assertTrue(sc.contains(Sequence.fromFasta("AATTCCGGUU")));
	}

	/**
	 * Can it be created?
	 * @throws InvalidDnaFormatException 
	 * @throws IOException 
	 */
	@Test
	public void testStreamSource() throws InvalidDnaFormatException, IOException {
		SequenceStreamReader reader = new SequenceFromFastaStringStreamReader(FastaStringFileStreamReaderTest.getSimpleFastaReader());
		SequenceCollection sc = this.factory.getSequenceCollection(reader);
		
		assertNotNull(sc);
		assertEquals(1,  sc.size());
		assertTrue(sc.contains(Sequence.fromFasta(FastaStringFileStreamReaderTest.recordSimple)));
	}
}
