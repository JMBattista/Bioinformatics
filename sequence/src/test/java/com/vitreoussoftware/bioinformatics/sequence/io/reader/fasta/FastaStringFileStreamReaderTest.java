package com.vitreoussoftware.bioinformatics.sequence.io.reader.fasta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.*;

import org.javatuples.Pair;
import org.junit.Test;

import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStringStreamReader;

/**
 * Test the FastaFileStreamReader class
 * @author John
 *
 */
public class FastaStringFileStreamReaderTest {
    /**
     * The path where the FASTA test files can be found.
     */
    private static final String FASTA_PATH = "target/test/sequence/Fasta/";

    /**
     * FASTA file with enough data that pages must be performed
     */
    private static final String ALTERNATE_FASTA = "alternate.fasta";

    /**
     * FASTA file with enough data that pages must be performed
     */
    private static final String SSU_PARC_GAPPED_FASTA = "SSUParc_Gapped.fasta";

    /**
     * FASTA file with enough data that pages must be performed
     */
    private static final String SSU_PARC_NOSPACE_FASTA = "SSUParc_NoSpace.fasta";

    /**
     * FASTA file with enough data that pages must be performed
     */
    private static final String SSU_PARC_BIG_FASTA = "SSUParc_Big.fasta";

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
     * FASTA file with three full records
     */
    private static final String COMPLEX_FASTA = "ComplexExamples.fasta";

	/**
	 * Shortened FASTA string for basic testing
	 */
	public static final String recordSimple = "CAGGCUUAACACAUGCAAGUCGAACGAAGUUAGGAAGCUUGCUUCUGAUACUUAGUGGCGGACGGGUGAGUAAUGCUUAGG";
	
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

    private String alternate1 =
            "TGCTCAATTTTATCTAAAGAAAATCAAATTGAGCAAATATATTCACAAAAAATTATTTTT" +
            "ATAGTATTTTTGAGAAAATAATTGCTCATTTGTACTAATG";

    private String alternate2 =
            "AAACCACAAGACAAACCATATCGTAAGCTTGATGCAGATAGGCTTTATATAGAAGTCAGA" +
            "CCAAGTGGGAAAAAAGTTTGGATTCACAAATTCTCCTTAA";

    private String alternate3 =
            "GCAATTCCGACCACATAGACCGTATCAATACCACGTTCTTTTAAGTAACCCGTTAAACCT" +
            "GTCATTGTGGTGTGGTCAGCTTCCATAAAAGCCGAGTAAC";

    private final String fastaMultiLineDescriptionMetadata = "LCBO - Prolactin precursor - Bovine";
    private final String fastaMultiLineDescriptionSequence =
            "MDSKGSSQKGSRLLLLLVVSNLLLCQGVVSTPVCPNGPGNCQVSLRDLFDRAVMVSHYIHDLSS" +
            "EMFNEFDKRYAQGKGFITMALNSCHTSSLPTPEDKEQAQQTHHEVLMSLILGLLRSWNDPLYHL" +
            "VTEVRGMKGAPDAILSRAIEIEEENKRLLEGMEMIFGQVIPGAKETEPYPVWSGLPSLQTKDED" +
            "ARYSAFYNLLHCLRRDSSKIDTYLKLLNCRIIYNNNC";
    private final String fastaTerminatedMetadata = "MCHU - Calmodulin - Human, rabbit, bovine, rat, and chicken";
    private final String fastaTerminatedSequence =
            "ADQLTEEQIAEFKEAFSLFDKDGDGTITTKELGTVMRSLGQNPTEAELQDMINEVDADGNGTID" +
            "FPEFLTMMARKMKDTDSEEEIREAFRVFDKDGNGYISAAELRHVMTNLGEKLTDEEVDEMIREA" +
            "DIDGDGQVNYEEFVQMMTAK";
    private final String fastaLargeHeaderMetadata = "gi|5524211|gb|AAD44166.1| cytochrome b [Elephas maximus maximus]";
    private final String fastaLargeHeaderSequence =
            "LCLYTHIGRNIYYGSYLYSETWNTGIMLLLITMATAFMGYVLPWGQMSFWGATVITNLFSAIPYIGTNLV" +
            "EWIWGGFSVDKATLNRFFAFHFILPFTMVALAGVHLTFLHETGSNNPLGLTSDSDKIPFHPYYTIKDFLG" +
            "LLILILLLLLLALLSPDMLGDPDNHMPADPLNTPLHIKPEWYFLFAYAILRSVPNKLGGVLALFLSIVIL" +
            "GLMPFLHTSKHRSMMLRPLSQALFWTLTMDLLTLTWIGSQPVEYPYTIIGQMASILYFSIILAFLPIAGX" +
            "IENY";

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
     * Create a SequenceStringStreamReader for the Simple FASTA test file
     * @return the SequenceStringStreamReader
     * @throws FileNotFoundException the test file could not be found
     */
    public static SequenceStringStreamReader getAlternateFastaReader()
            throws FileNotFoundException {
        return FastaStringFileStreamReader.create(FastaStringFileStreamReaderTest.FASTA_PATH + FastaStringFileStreamReaderTest.ALTERNATE_FASTA);
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
     * Create a SequenceStringStreamReader for the Example FASTA test file
     * @return the SequenceStringStreamReader
     * @throws FileNotFoundException the test file could not be found
     */
    public static SequenceStringStreamReader getComplexFastaReader()
            throws FileNotFoundException {
        return FastaStringFileStreamReader.create(FastaStringFileStreamReaderTest.FASTA_PATH + FastaStringFileStreamReaderTest.COMPLEX_FASTA);
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

    /**
     * Create a SequenceStringStreamReader for the Paged FASTA test file
     * @return the SequenceStringStreamReader
     * @throws FileNotFoundException the test file could not be found
     */
    public static SequenceStringStreamReader getPagedFastaReader(int pagingSize)
            throws FileNotFoundException {
        return FastaStringFileStreamReader.create(FastaStringFileStreamReaderTest.FASTA_PATH + FastaStringFileStreamReaderTest.SSU_PARC_PAGED_FASTA, pagingSize);
    }

    /**
     * Create a SequenceStringStreamReader for the Gapged FASTA test file
     * @return the SequenceStringStreamReader
     * @throws FileNotFoundException the test file could not be found
     */
    public static SequenceStringStreamReader getGappedFastaReader()
            throws FileNotFoundException {
        return FastaStringFileStreamReader.create(FastaStringFileStreamReaderTest.FASTA_PATH + FastaStringFileStreamReaderTest.SSU_PARC_GAPPED_FASTA);
    }

    /**
     * Create a SequenceStringStreamReader for the NoSpace FASTA test file
     * @return the SequenceStringStreamReader
     * @throws FileNotFoundException the test file could not be found
     */
    public static SequenceStringStreamReader getNoSpaceFastaReader()
            throws FileNotFoundException {
        return FastaStringFileStreamReader.create(FastaStringFileStreamReaderTest.FASTA_PATH + FastaStringFileStreamReaderTest.SSU_PARC_NOSPACE_FASTA);
    }

    /**
     * Create a SequenceStringStreamReader for the Big FASTA test file
     * @return the SequenceStringStreamReader
     * @throws FileNotFoundException the test file could not be found
     */
    public static SequenceStringStreamReader getBigFastaReader()
            throws FileNotFoundException {
        return FastaStringFileStreamReader.create(FastaStringFileStreamReaderTest.FASTA_PATH + FastaStringFileStreamReaderTest.SSU_PARC_BIG_FASTA);
    }


    /**
	 * Create a FastaFileStreamReader
	 * @throws IOException 
	 */
	@Test
	public void testCreate() throws IOException {
//        OutputStreamWriter stream = new OutputStreamWriter(new FileOutputStream(new File("find the path")));
//        stream.write("test");
//        stream.close();
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
		SequenceStringStreamReader reader = getSimpleFastaReader();
		
		assertEquals(recordSimple, reader.next().getValue1());
	}
	
	/**
	 * Read a record from the reader
	 * @throws IOException 
	 */
	@Test
	public void testReadRecord_example1() throws IOException {
		SequenceStringStreamReader reader = getExampleFastaReader();
		
		assertEquals(record1, reader.next().getValue1());
	}
	
	/**
	 * Read a second record from the reader
	 * @throws IOException 
	 */
	@Test
	public void testReadRecord_example2() throws IOException {
		SequenceStringStreamReader reader = getExampleFastaReader();
		
		assertEquals(record1, reader.next().getValue1());
		assertEquals(record2, reader.next().getValue1());
	}
	
	/**
	 * Read a third record from the reader
	 * @throws IOException 
	 */
	@Test
	public void testReadRecord_example3() throws IOException {
		SequenceStringStreamReader reader = getExampleFastaReader();
		
		assertEquals(record1, reader.next().getValue1());
		assertEquals(record2, reader.next().getValue1());
		assertEquals(record3, reader.next().getValue1());
	}

    /**
     * Read a third record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecord_alternate() throws IOException {
        SequenceStringStreamReader reader = getAlternateFastaReader();

        assertEquals(alternate1, reader.next().getValue1());
        assertEquals(alternate2, reader.next().getValue1());
        assertEquals(alternate3, reader.next().getValue1());
    }
	
	/**
	 * Read a third record from the reader
	 * @throws Exception 
	 */
	@Test
	public void testReadRecord_autoCloseable() throws Exception {
		try (SequenceStringStreamReader reader	= getExampleFastaReader())
		{
			assertEquals(record1, reader.next().getValue1());
			assertEquals(record2, reader.next().getValue1());
			assertEquals(record3, reader.next().getValue1());
		} catch (Exception e) {
			fail("Should not have hit an exception from the three");
		}
	}

    /**
     * Read a third record from the reader
     * @throws Exception
     */
    @Test
    public void testReadRecords_gapped() throws Exception {
        try (SequenceStringStreamReader reader	= getGappedFastaReader())
        {
            assertNotNull(reader.next().getValue1());
            assertNotNull(reader.next().getValue1());
            assertNotNull(reader.next().getValue1());
        } catch (Exception e) {
            fail("Should not have hit an exception from reading three records from gapped");
        }
    }

    /**
     * Read a third record from the reader
     * @throws Exception
     */
    @Test
    public void testReadRecords_noSpace() throws Exception {
        try (SequenceStringStreamReader reader	= getNoSpaceFastaReader())
        {
            assertNotNull(reader.next().getValue1());
            assertNotNull(reader.next().getValue1());
            assertNotNull(reader.next().getValue1());
        } catch (Exception e) {
            fail("Should not have hit an exception from reading three records from no space\n" + e.getMessage());
        }
    }
	
	/**
	 * Read a third record from the reader
	 * @throws IOException 
	 */
	@Test
	public void testReadRecord_paged() throws IOException {
		SequenceStringStreamReader reader = getPagedFastaReader();

        int index = 0;
		while (reader.hasNext())
		{	
			assertEquals(index+0 + " failed to parse", record1, reader.next().getValue1());
            assertEquals(index+1 + " failed to parse", record2, reader.next().getValue1());
            assertEquals(index+2 + " failed to parse", record3, reader.next().getValue1());
            index += 3;
		}

        assertEquals("The number of records was not correct", 81, index);
	}

    /**
     * Read a third record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecords_paged1() throws IOException {
        SequenceStringStreamReader reader = getPagedFastaReader(1);

        int index = 0;
        while (reader.hasNext())
        {
            assertEquals(index+0 + " failed to parse", record1, reader.next().getValue1());
            assertEquals(index+1 + " failed to parse", record2, reader.next().getValue1());
            assertEquals(index+2 + " failed to parse", record3, reader.next().getValue1());
            index += 3;
        }

        assertEquals("The number of records was not correct", 81, index);
    }

    /**
     * Read a third record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecords_paged10() throws IOException {
        SequenceStringStreamReader reader = getPagedFastaReader(10);

        int index = 0;
        while (reader.hasNext())
        {
            assertEquals(index+0 + " failed to parse", record1, reader.next().getValue1());
            assertEquals(index+1 + " failed to parse", record2, reader.next().getValue1());
            assertEquals(index+2 + " failed to parse", record3, reader.next().getValue1());
            index += 3;
        }

        assertEquals("The number of records was not correct", 81, index);
    }

    /**
     * Read a third record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecords_paged100() throws IOException {
        SequenceStringStreamReader reader = getPagedFastaReader(100);

        int index = 0;
        while (reader.hasNext())
        {
            assertEquals(index+0 + " failed to parse", record1, reader.next().getValue1());
            assertEquals(index+1 + " failed to parse", record2, reader.next().getValue1());
            assertEquals(index+2 + " failed to parse", record3, reader.next().getValue1());
            index += 3;
        }

        assertEquals("The number of records was not correct", 81, index);
    }

    /**
     * Read a record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecord_complex1() throws IOException {
        SequenceStringStreamReader reader = getComplexFastaReader();

        Pair<String, String> next = reader.next();
        assertEquals(fastaMultiLineDescriptionMetadata, next.getValue0());
        assertEquals(fastaMultiLineDescriptionSequence, next.getValue1());
    }

    /**
     * Read a second record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecord_complex2() throws IOException {
        SequenceStringStreamReader reader = getComplexFastaReader();

        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(fastaTerminatedMetadata, next.getValue0());
        assertEquals(fastaTerminatedSequence, next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws IOException
     */
    @Test
    public void testReadRecord_complex3() throws IOException {
        SequenceStringStreamReader reader = getComplexFastaReader();

        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(fastaLargeHeaderMetadata, next.getValue0());
        assertEquals(fastaLargeHeaderSequence, next.getValue1());
    }
}
