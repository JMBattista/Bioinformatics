package com.vitreoussoftare.bioinformatics.sequence.reader.fasta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import com.vitreoussoftare.bioinformatics.sequence.reader.SequenceStringStreamReader;

/**
 * Test the FastaFileStreamReader class
 * @author John
 *
 */
public class FastaStringFileStreamReaderTest {
	
	/**
	 * FASTA file with enough data that pages must be performed
	 */
	private static final String SSU_PARC_PAGED_FASTA = "SSUParc_Paged.fasta";

	/**
	 * FASTA file with only a small amount of data
	 */
	private static final String SSU_PARC_SIMPLE_FASTA = "SSUParc_Simple.fasta";

	/**
	 * FASTA file with three full records
	 */
	private static final String SSU_PARC_EXAMPLE_FASTA = "SSUParc_Example.fasta";

	/**
	 * The path where the FASTA test files can be found.
	 */
	private static final String FASTA_PATH = "src/test/resources/fasta/";

	/**
	 * Shortened FASTA string for basic testing
	 */
	public static final String recordSimple = "AGGCUUAACACAUGCAAGUCGAACGAAGUUAGGAAGCUUGCUUCUGAUACUUAGUGGCGGACGGGUGAGUAAUGCUUAGG";
	
	/** 
	 * First record in the example FASTA file
	 */
	public static final String record1 = 
			"AGGCUUAACACAUGCAAGUCGAACGAAGUUAGGAAGCUUGCUUCUGAUACUUAGUGGCGGACGGGUGAGUAAUGCUUAGG" +
			"AAUCUGCCUAGUAGUGGGGGAUAACUUGGGGAAACCCAAGCUAAUACCGCAUACGACCUACGGGUGAAAGGGGGCUUUUA" +
			"GCUCUCGCUAUUAGAUGAGCCUAAGUCGGAUUAGCUGGUUGGUGGGGUAAAGGCCUACCAAGGCGACGAUCUGUAGCUGG" +
			"UCUGAGAGGAUGAUCAGCCACACUGGGACUGAGACACGGCCCAGACUCCUACGGGAGGCAGCAGUGGGGAAUAUUGGACA" +
			"AUGGGCGAAAGCCUGAUCCAGCCAUGCCGCGUGUGUGAAGAAGGCCUUUUGGUUGUAAAGCACUUUAAGUGGGGAGGAAA" +
			"AGCUUAUGGUUAAUACCCAUAAGCCCUGACGUUACCCACAGAAUAAGCACCGGCUAACUCUGUGCCAGCAGCCGCGGUAA" +
			"UACAGAGGGUGCAAGCGUUAAUCGGAUUACUGGGCGUAAAGCGCGCGUAGGUGGUUAUUUAAGUCAGAUGUGAAAGCCCC" +
			"GGGCUUAACCUGGGAACUGCAUCUGAUACUGGAUAACUAGAGUAGGUGAGAGGGGNGUAGAAUUCCAGGUGUAGCGGUGA" +
			"AAUGCGUAGAGAUCUGGAGGAAUACCGAUGGCGAAGGCAGCUCCCUGGCAUCAUACUGACACUGAGGUGCGAAAGCGUGG" +
			"GUAGCAAACAGGAUUAGAUACCCUGGUAGUCCACGCCGUAAACGAUGUCUACCAGUCGUUGGGUCUUUUAAAGACUUAGU" +
			"GACGCAGUUAACGCAAUAAGUAGACCGCCUGGGGAGUACGGCCGCAAGGUUAAAACUCAAAUGAAUUGACGGGGGCCCGC" +
			"ACAAGCGGUGGAGCAUGUGGUUUAAUUCGAUGCAACGCGAAGAACCUUACCUGGUCUUGACAUAGUGAGAAUCUUGCAGA" +
			"GAUGCGAGAGUGCCUUCGGGAAUUCACAUACAGGUGCUGCAUGGCUGUCGUCAGCUCGUGUCGUGAGAUGUUGGGUUAAG" +
			"UCCCGCAACGAGCGCAACCCUUUUCCUUAGUUACCAGCGACUCGGUCGGGAACUCUAAGGAUACUGCCAGUGACAAACUG" +
			"GAGGAAGGCGGGGACGACGUCAAGUCAUCAUGGCCCUUACGACCAGGGCUACACACGUGCUACAAUGGUUGGUACAAAGG" +
			"GUUGCUACACAGCGAUGUGAUGCUAAUCUCAAAAAGCCAAUCGUAGUCCGGAUUGGAGUCUGCAACUCGACUCCAUGAAG" +
			"UCGGAAUCGCUAGUAAUCGCAGAUCAGAAUGCUGCGGUGAAUACGUUCCCGGGCCUUGUACACACCGCCCGUCACACCAU" +
			"GGGAGUUGAUCUCACCAGAAGUGGUUAGCCUAACGCAAGAGGGCGAUCACCACGGUGGGGUCGAUGACUGGGGUGAAGUC" +
			"GUAACAAGGUAGCCGUAGGGGAACUGCGGCUG";
	
	/**
	 * Second record in the example FASTA file
	 */
	public static final String record2 = 
			"UUAAAAUGAGAGUUUGAUCCUGGCUCAGGACGAACGCUGGCGGCGUGCCUAAUACAUGCAAGUCGAACGAAACUUUCUUA" +
			"CACCGAAUGCUUGCAUUCACUCGUAAGAAUGAGUGGCGUGGACGGGUGAGUAACACGUGGGUAACCUGCCUAAAAGAAGG" +
			"GGAUAACACUUGGAAACAGGUGCUAAUACCGUAUAUCUCUAAGGAUCGCAUGAUCCUUAGAUGAAAGAUGGUUCUNGCUA" +
			"UCGCUUUUAGAUGGACCCGCGGCGUAUUAACUAGUUGGUGGGGUAACGGCCUACCAAGGUGAUGAUACGUAGCCGAACUG" +
			"AGAGGUUGAUCGGCCACAUUGGGACUGAGACACGGCCCNAACUCCUACGGGAGGCAGCAGUAGGGAAUCUUCCACAAUGG" +
			"ACGCAAGUCUGAUGGAGCAACGCCGCGUGAGUGAAGAAGGUCUUCGGAUCGUAAAACUCNGUUGUUAGAGAAGAACUCGA" +
			"GUGAGAGUAACUGUUCAUUCGAUGACGGUAUCUAACCAGCAAGUCACGGCUAACUACGUGCCAGCAGCCGCGGUAAUACG" +
			"UAGGUGGCAAGCGUUGUCCGGAUUUAUUGGGCGUAAAGGGAACGCAGGCGGUCUUUUAAGUCUGAUGUGAAAGCCUUCGG" +
			"CUUAACCGGAGUAGUGCUAUGGAAACUGGAAGACUUGAGUGCAGAAGAGGAGAGUGGAACUCCAUGUGUAGCGGUGAAAU" +
			"GCGUAGAUAUAUGGAAGAACACCAGUGGCGAAAGCGGCUCUCUGGUCUGUAACUGACGCUGAGGUUCGAAAGCGUGGGUA" +
			"GCAAACAGGAUUAGAUACCCUGGUAGUCCACGCCGUAAACGAUGAAUGCUAGGUGUUGGAGGGUUUCCGCCCUUCAGUGC" +
			"CGCAGCUAACGCAAUAAGCAUUCCGCCUGGGGAGUACGACCGCAAGGUUGAAACUCAAAGGAAUUGACGGGGGCNNGCAC" +
			"AAGCGGUGGAGCAUGUGGUUUAAUUCGAANNAACGCGAAGAACCUUACCAGGUCUUGACAUCCUUUGACCACCUAAGAGA" +
			"UUAGGCUUUCCCUUCGGGGACAAAGUGACAGGUGGNGCAUGGCUGUCGUCAGCUCGUGUCGUGAGAUGUUGGGUUAAGUC" +
			"CCGCAACGAGCGCAACCCUUGUUGUCAGUUGCCAGCAUUAAGUUGGGCACUCUGGCGAGACUGCCGGUGACAAACCGGAG" +
			"GAAGGUGGGGACGACGUCAAGUCAUCAUGCCCCUUAUGACCUGGGCUACACACGUGCUACAAUGGACGGUACAACGAGUC" +
			"GCGAGACCGCGAGGUUUAGCUAAUCUCUUAAAGCCGUUCUCAGUUCGGAUUGUAGGCUGCAACUCGCCUACAUGAAGUCG" +
			"GAAUCGCUAGUAAUCGCGA";
	
	
	public static final String record3 =
			"GAUGAACGCUAGCGGCGUGCCUUAUGCAUGCAAGUCGAACGGUCUUAAGCAAUUAAGAUAGUGGCGAACGGGUGAGUAAC" +
			"GCGUAAGUAACCUACCUCUAAGUGGGGGAUAGCUUCGGGAAACUGAAGGUAAUACCGCAUGUGGUGGGCCGACAUAUGUU" +
			"GGUUCACUAAAGCCGUAAGGCGCUUGGUGAGGGGCUUGCGUCCGAUUAGCUAGUUGGUGGGGUAAUGGCCUACCAAGGCU" +
			"UCGAUCGGUAGCUGGUCUGAGAGGAUGAUCAGCCACACUGGGACUGAGACACGGCCCAGACUCCUACGGGAGGCAGCAGC" +
			"AAGGAAUCUUGGGCAAUGGGCGAAAGCCUGACCCAGCAACGCCGCGUGAGGGAUGAAGGCUUUCGGGUUGUAAACCUCUU" +
			"UUCAUAGGGAAGAAUAAUGACGGUACCUGUGGAAUAAGCUUCGGCUAACUACGUGCCAGCAGCCGCGGUAAUACGUAGGA" +
			"AGCAAGCGUUAUCCGGAUUUAUUGGGCGUAAAGUGAGCGUAGGUGGUCUUUCAAGUUGGAUGUGAAAUUUCCCGGCUUAA" +
			"CCGGGACGAGUCAUUCAAUACUGUUGGACUAGAGUACAGCAGGAGAAAACGGAAUUCCCGGUGUAGUGGUAAAAUGCGUA" +
			"GAUAUCGGGAGGAACACCAGAGGCGAAGGCGGUUUUCUAGGUUGUCACUGACACUGAGGCUCGAAAGCGUGGGGAGCGAA" +
			"CAGAAUUAGAUACUCUGGUAGUCCACGCCUUAAACUAUGGACACUAGGUAUAGGGAGUAUCGACCCUCUCUGUGCCGAAG" +
			"CUAACGCUUUAAGUGUCCCGCCUGGGGAGUACGGUCGCAAGGCUAAAACUCAAAGGAAUUGACGGGGGCCCGCACAAGCA" +
			"GCGGAGCGUGUGGUUUAAUUCGAUGCUACACGAAGAACCUUACCAAGAUUUGACAUGCAUGUAGUAGUGAACUGAAAGGG" +
			"GAACGACCUGUUAAGUCAGGAACUUGCACAGGUGCUGCAUGGCUGUCGUCAGCUCGUGCCGUGAGGUGUUUGGUUAAGUC" +
			"CUGCAACGAGCGCAACCCUUGUUGCUAGUUAAAUUUUCUAGCGAGACUGCCCCGCGAAACGGGGAGGAAGGUGGGGAUGA" +
			"CGUCAAGUCAGCAUGGCCUUUAUAUCUUGGGCUACACACACGCUACAAUGGACAGAACAAUAGGUUGCAACAGUGUGAAC" +
			"UGGAGCUAAUCC";
	
	/**
	 * Create a FastaFileStreamReader
	 * @throws IOException 
	 */
	@Test
	public void testCreate() throws IOException {
		FastaStringFileStreamReader.create(FASTA_PATH + SSU_PARC_EXAMPLE_FASTA);
	}
	
	/**
	 * Create a FastaFileStreamReader
	 * @throws FileNotFoundException 
	 */
	@Test(expected=FileNotFoundException.class)
	public void testCreate_notFound() throws FileNotFoundException {
		FastaStringFileStreamReader.create("Z:/bobtheexamplefileisnothere.fasta");
	}
	
	/**
	 * Create a FastaFileStreamReader
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testCreate_notFoundAutoCloseable() throws FileNotFoundException {
		try (SequenceStringStreamReader reader = FastaStringFileStreamReader.create("Z:/bobtheexamplefileisnothere.fasta"))
		{
			fail("This should not be reachable");
		}
		catch (FileNotFoundException e)
		{
			// do nothing since this means we passed
		}
		catch (Exception e)
		{
			fail("We should have been caught by the more specific exception in front of this.");
		}
	}
	
	/**
	 * Read a record from the reader
	 * @throws IOException 
	 */
	@Test
	public void testReadRecord_simple() throws IOException {
		SequenceStringStreamReader reader = FastaStringFileStreamReader.create(FASTA_PATH + SSU_PARC_SIMPLE_FASTA);
		
		assertEquals(recordSimple, reader.readRecord());
	}
	
	/**
	 * Read a record from the reader
	 * @throws IOException 
	 */
	@Test
	public void testReadRecord_example1() throws IOException {
		SequenceStringStreamReader reader = FastaStringFileStreamReader.create(FASTA_PATH + SSU_PARC_EXAMPLE_FASTA);
		
		assertEquals(record1, reader.readRecord());
	}
	
	/**
	 * Read a second record from the reader
	 * @throws IOException 
	 */
	@Test
	public void testReadRecord_example2() throws IOException {
		SequenceStringStreamReader reader = FastaStringFileStreamReader.create(FASTA_PATH + SSU_PARC_EXAMPLE_FASTA);
		
		assertEquals(record1, reader.readRecord());
		assertEquals(record2, reader.readRecord());
	}
	
	/**
	 * Read a third record from the reader
	 * @throws IOException 
	 */
	@Test
	public void testReadRecord_example3() throws IOException {
		SequenceStringStreamReader reader = FastaStringFileStreamReader.create(FASTA_PATH + SSU_PARC_EXAMPLE_FASTA);
		
		assertEquals(record1, reader.readRecord());
		assertEquals(record2, reader.readRecord());
		assertEquals(record3, reader.readRecord());
	}
	
	/**
	 * Read a third record from the reader
	 * @throws Exception 
	 */
	@Test
	public void testReadRecord_autoCloseable() throws Exception {
		try (SequenceStringStreamReader reader 
				= FastaStringFileStreamReader.create(FASTA_PATH + SSU_PARC_EXAMPLE_FASTA))
		{
			assertEquals(record1, reader.readRecord());
			assertEquals(record2, reader.readRecord());
			assertEquals(record3, reader.readRecord());	
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Read a third record from the reader
	 * @throws IOException 
	 */
	@Test
	public void testReadRecord_paged() throws IOException {
		SequenceStringStreamReader reader = FastaStringFileStreamReader.create(FASTA_PATH + SSU_PARC_PAGED_FASTA);
		
		while (reader.hasRecord())
		{	
			assertEquals(record1, reader.readRecord());
			assertEquals(record2, reader.readRecord());
			assertEquals(record3, reader.readRecord());
		}
	}

	/**
	 * Create a SequenceStringStreamReader for the Simple FASTA test file
	 * @return the SequenceStringStreamReader
	 * @throws FileNotFoundException the test file could not be found
	 */
	public static SequenceStringStreamReader getSimpleFastaReader()
			throws FileNotFoundException {
		return FastaStringFileStreamReader.create(FastaStringFileStreamReaderTest.FASTA_PATH + FastaStringFileStreamReaderTest.SSU_PARC_SIMPLE_FASTA);
	}
	

	/**
	 * Create a SequenceStringStreamReader for the Example FASTA test file
	 * @return the SequenceStringStreamReader
	 * @throws FileNotFoundException the test file could not be found
	 */
	public static SequenceStringStreamReader getExampleFastaReader()
			throws FileNotFoundException {
		return FastaStringFileStreamReader.create(FastaStringFileStreamReaderTest.FASTA_PATH + FastaStringFileStreamReaderTest.SSU_PARC_EXAMPLE_FASTA);
	}
	

	/**
	 * Create a SequenceStringStreamReader for the Paged FASTA test file
	 * @return the SequenceStringStreamReader
	 * @throws FileNotFoundException the test file could not be found
	 */
	public static SequenceStringStreamReader getPagedFastaReader()
			throws FileNotFoundException {
		return FastaStringFileStreamReader.create(FastaStringFileStreamReaderTest.FASTA_PATH + FastaStringFileStreamReaderTest.SSU_PARC_PAGED_FASTA);
	}
	
}
