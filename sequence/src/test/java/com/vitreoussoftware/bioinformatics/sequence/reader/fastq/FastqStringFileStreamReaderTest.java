package com.vitreoussoftware.bioinformatics.sequence.reader.fastq;

import com.vitreoussoftware.bioinformatics.sequence.reader.SequenceStringStreamReader;
import org.javatuples.Pair;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Test the FastqFileStreamReader class
 * @author John
 *
 */
public class FastqStringFileStreamReaderTest {
    /**
     * The path where the FASTA test files can be found.
     */
    private static final String FASTA_PATH = "target/test/sequence/Fastq/";

    /**
     * FASTA file with enough data that pages must be performed
     */
    private static final String ALTERNATE_FASTA = "alternate.fastq";

    /**
     * FASTA file with enough data that pages must be performed
     */
    private static final String SSU_PARC_GAPPED_FASTA = "SSUParc_Gapped.fastq";

    /**
     * FASTA file with enough data that pages must be performed
     */
    private static final String SSU_PARC_NOSPACE_FASTA = "SSUParc_NoSpace.fastq";

    /**
	 * FASTA file with enough data that pages must be performed
	 */
	private static final String SSU_PARC_PAGED_FASTA = "SSUParc_Paged.fastq";

	/**
	 * FASTA file with only a small amount of data
	 */
	private static final String SSU_PARC_SIMPLE_FASTA = "SSUParc_Simple.fastq";

	/**
	 * FASTA file with three full records
	 */
	private static final String SSU_PARC_EXAMPLE_FASTA = "SSUParc_Example.fastq";

    /**
     * FASTA file with three full records
     */
    private static final String COMPLEX_FASTA = "ComplexExamples.fastq";

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

    private final String fastqComplex1_metadata = "SEQ_ID";
    private final String fastqComplex1_sequence = "GATTTGGGGTTCAAAGCAGTATCGATCAAATAGTAAATCCATTTGTTCAACTCACAGTTT";
    private final String fastqComplex1_comments = "";
    private final String fastqComplex1_quality  = "!''*((((***+))%%%++)(%%%%).1***-+*''))**55CCF>>>>>>CCCCCCC65";

    private final String fastqComplex2_metadata = "HWUSI-EAS100R:6:73:941:1973#0/1";
    private final String fastqComplex2_sequence = "ACCGCTTGTGCGGGCCCCCGTCAATTCATTTGAGTTTTAGTCTTGCGACCGTACTCCCCAGGCGGTCTACTTATCGCGTTAGCTGCGCCACTAA";
    private final String fastqComplex2_comments = "";
    private final String fastqComplex2_quality  = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

    private final String fastqComplex3_metadata = "EAS139:136:FC706VJ:2:2104:15343:197393 1:Y:18:ATCACG";
    private final String fastqComplex3_sequence = "ACCGCTTGTGCGGGCCCCCGTCAATTCATTTGAGTTTTAGTCTTGCGACCGTACTCCCCAGGCGGTCTACTTATCGCGTTAGCTGCGCCACTAA";
    private final String fastqComplex3_comments = "";
    private final String fastqComplex3_quality  = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

    private final String fastqComplex4_metadata = "SRR001666.1 071112_SLXA-EAS1_s_7:5:1:817:345 length=36";
    private final String fastqComplex4_sequence = "GGGTGATGGCCGCTGCCGATGGCGTCAAATCCCACC";
    private final String fastqComplex4_comments = "SRR001666.1 071112_SLXA-EAS1_s_7:5:1:817:345 length=36";
    private final String fastqComplex4_quality  = "IIIIIIIIIIIIIIIIIIIIIIIIIIIIII9IG9IC";

    private final String fastqComplex5_metadata = "Acinetobacter_806_905_0:0:0_0:0:0_0/1";
    private final String fastqComplex5_sequence = "ACCGCTTGTGCGGGCCCCCGTCAATTCATTTGAGTTTTAGTCTTGCGACCGTACTCCCCAGGCGGTCTACTTATCGCGTTAGCTGCGCCACTAAAGCCTC";
    private final String fastqComplex5_comments = "";
    private final String fastqComplex5_quality  = "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII";

    private final String fastqComplex6_metadata = "Bacillus_359_458_0:0:0_0:0:0_100cb/1";
    private final String fastqComplex6_sequence = "GCCGCGTGAGTGATGAAGGCTTTCGGGTCGTAAAACTCTGTTGTTAGGGAAGAACAAGTGCTAGTTGAATAAGCTGGCACCTTGACGGTACCTAACCAGA";
    private final String fastqComplex6_comments = "";
    private final String fastqComplex6_quality  = "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII";

    private final String fastqComplex7_metadata = "Actinomyces_79_178_0:0:0_0:0:0_4639/1";
    private final String fastqComplex7_sequence = "GAACAAACCTTTCCACCAACCCCCATGCGAAGATCAGTGAATATCCAGTATTAGCACCCGTTTCCGGGCGTTATCCCAAAGAAGGGGGCAGGTTACTCAC";
    private final String fastqComplex7_comments = "";
    private final String fastqComplex7_quality  = "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII";

    /**
     * Create a SequenceStringStreamReader for the Simple FASTA test file
     * @return the SequenceStringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static SequenceStringStreamReader getSimpleFastqReader()
            throws FileNotFoundException {
        return FastqStringFileStreamReader.create(FastqStringFileStreamReaderTest.FASTA_PATH + FastqStringFileStreamReaderTest.SSU_PARC_SIMPLE_FASTA);
    }

    /**
     * Create a SequenceStringStreamReader for the Simple FASTA test file
     * @return the SequenceStringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static SequenceStringStreamReader getAlternateFastqReader()
            throws FileNotFoundException {
        return FastqStringFileStreamReader.create(FastqStringFileStreamReaderTest.FASTA_PATH + FastqStringFileStreamReaderTest.ALTERNATE_FASTA);
    }
    /**
     * Create a SequenceStringStreamReader for the Example FASTA test file
     * @return the SequenceStringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static SequenceStringStreamReader getExampleFastqReader()
            throws FileNotFoundException {
        return FastqStringFileStreamReader.create(FastqStringFileStreamReaderTest.FASTA_PATH + FastqStringFileStreamReaderTest.SSU_PARC_EXAMPLE_FASTA);
    }

    /**
     * Create a SequenceStringStreamReader for the Example FASTA test file
     * @return the SequenceStringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static SequenceStringStreamReader getComplexFastqReader()
            throws FileNotFoundException {
        return FastqStringFileStreamReader.create(FastqStringFileStreamReaderTest.FASTA_PATH + FastqStringFileStreamReaderTest.COMPLEX_FASTA);
    }

    /**
     * Create a SequenceStringStreamReader for the Paged FASTA test file
     * @return the SequenceStringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static SequenceStringStreamReader getPagedFastqReader()
            throws FileNotFoundException {
        return FastqStringFileStreamReader.create(FastqStringFileStreamReaderTest.FASTA_PATH + FastqStringFileStreamReaderTest.SSU_PARC_PAGED_FASTA);
    }

    /**
     * Create a SequenceStringStreamReader for the Paged FASTA test file
     * @return the SequenceStringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static SequenceStringStreamReader getPagedFastqReader(int pagingSize)
            throws FileNotFoundException {
        return FastqStringFileStreamReader.create(FastqStringFileStreamReaderTest.FASTA_PATH + FastqStringFileStreamReaderTest.SSU_PARC_PAGED_FASTA, pagingSize);
    }

    /**
     * Create a SequenceStringStreamReader for the Gapged FASTA test file
     * @return the SequenceStringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static SequenceStringStreamReader getGappedFastqReader()
            throws FileNotFoundException {
        return FastqStringFileStreamReader.create(FastqStringFileStreamReaderTest.FASTA_PATH + FastqStringFileStreamReaderTest.SSU_PARC_GAPPED_FASTA);
    }

    /**
     * Create a SequenceStringStreamReader for the NoSpace FASTA test file
     * @return the SequenceStringStreamReader
     * @throws java.io.FileNotFoundException the test file could not be found
     */
    public static SequenceStringStreamReader getNoSpaceFastqReader()
            throws FileNotFoundException {
        return FastqStringFileStreamReader.create(FastqStringFileStreamReaderTest.FASTA_PATH + FastqStringFileStreamReaderTest.SSU_PARC_NOSPACE_FASTA);
    }


    /**
	 * Create a FastqFileStreamReader
	 * @throws java.io.IOException
	 */
	@Test
	public void testCreate() throws IOException {
//        OutputStreamWriter stream = new OutputStreamWriter(new FileOutputStream(new File("find the path")));
//        stream.write("test");
//        stream.close();
		FastqStringFileStreamReader.create(FASTA_PATH + SSU_PARC_EXAMPLE_FASTA);
	}

	/**
	 * Create a FastqFileStreamReader
	 * @throws java.io.FileNotFoundException
	 */
	@Test(expected=FileNotFoundException.class)
	public void testCreate_notFound() throws FileNotFoundException {
		FastqStringFileStreamReader.create("Z:/bobtheexamplefileisnothere.fastq");
	}

	/**
	 * Create a FastqFileStreamReader
	 * @throws java.io.FileNotFoundException
	 */
	@Test
	public void testCreate_notFoundAutoCloseable() throws FileNotFoundException {
		try (SequenceStringStreamReader reader = FastqStringFileStreamReader.create("Z:/bobtheexamplefileisnothere.fastq"))
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
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_simple() throws IOException {
		SequenceStringStreamReader reader = getSimpleFastqReader();

		assertEquals(recordSimple, reader.next().getValue1());
	}

	/**
	 * Read a record from the reader
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_example1() throws IOException {
		SequenceStringStreamReader reader = getExampleFastqReader();

		assertEquals(record1, reader.next().getValue1());
	}

	/**
	 * Read a second record from the reader
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_example2() throws IOException {
		SequenceStringStreamReader reader = getExampleFastqReader();

		assertEquals(record1, reader.next().getValue1());
		assertEquals(record2, reader.next().getValue1());
	}

	/**
	 * Read a third record from the reader
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_example3() throws IOException {
		SequenceStringStreamReader reader = getExampleFastqReader();

		assertEquals(record1, reader.next().getValue1());
		assertEquals(record2, reader.next().getValue1());
		assertEquals(record3, reader.next().getValue1());
	}

	/**
	 * Read a third record from the reader
	 * @throws Exception
	 */
	@Test
	public void testReadRecord_autoCloseable() throws Exception {
		try (SequenceStringStreamReader reader	= getExampleFastqReader())
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
        try (SequenceStringStreamReader reader	= getGappedFastqReader())
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
        try (SequenceStringStreamReader reader	= getNoSpaceFastqReader())
        {
            assertNotNull(reader.next().getValue1());
            assertNotNull(reader.next().getValue1());
            assertNotNull(reader.next().getValue1());
        } catch (Exception e) {
            fail("Should not have hit an exception from reading three records from no space");
        }
    }

	/**
	 * Read a third record from the reader
	 * @throws java.io.IOException
	 */
	@Test
	public void testReadRecord_paged() throws IOException {
		SequenceStringStreamReader reader = getPagedFastqReader();

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
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecords_paged1() throws IOException {
        SequenceStringStreamReader reader = getPagedFastqReader(1);

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
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecords_paged10() throws IOException {
        SequenceStringStreamReader reader = getPagedFastqReader(10);

        int index = 0;
        while (reader.hasNext())
        {
            if (index >= 78)
                index = index -1 + 1;
            assertEquals(index+0 + " failed to parse", record1, reader.next().getValue1());
            assertEquals(index+1 + " failed to parse", record2, reader.next().getValue1());
            assertEquals(index+2 + " failed to parse", record3, reader.next().getValue1());
            index += 3;
        }

        assertEquals("The number of records was not correct", 81, index);
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecords_paged100() throws IOException {
        SequenceStringStreamReader reader = getPagedFastqReader(100);

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
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex1() throws IOException {
        SequenceStringStreamReader reader = getComplexFastqReader();

        Pair<String, String> next = reader.next();
        assertEquals(fastqComplex1_metadata, next.getValue0());
        assertEquals(fastqComplex1_sequence, next.getValue1());
    }

    /**
     * Read a second record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex2() throws IOException {
        SequenceStringStreamReader reader = getComplexFastqReader();

        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(fastqComplex2_metadata, next.getValue0());
        assertEquals(fastqComplex2_sequence, next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex3() throws IOException {
        SequenceStringStreamReader reader = getComplexFastqReader();

        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(fastqComplex3_metadata, next.getValue0());
        assertEquals(fastqComplex3_sequence, next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex4() throws IOException {
        SequenceStringStreamReader reader = getComplexFastqReader();

        reader.next();
        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(fastqComplex4_metadata, next.getValue0());
        assertEquals(fastqComplex4_sequence, next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex5() throws IOException {
        SequenceStringStreamReader reader = getComplexFastqReader();

        reader.next();
        reader.next();
        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(fastqComplex5_metadata, next.getValue0());
        assertEquals(fastqComplex5_sequence, next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex6() throws IOException {
        SequenceStringStreamReader reader = getComplexFastqReader();

        reader.next();
        reader.next();
        reader.next();
        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(fastqComplex6_metadata, next.getValue0());
        assertEquals(fastqComplex6_sequence, next.getValue1());
    }

    /**
     * Read a third record from the reader
     * @throws java.io.IOException
     */
    @Test
    public void testReadRecord_complex7() throws IOException {
        SequenceStringStreamReader reader = getComplexFastqReader();

        reader.next();
        reader.next();
        reader.next();
        reader.next();
        reader.next();
        reader.next();
        Pair<String, String> next = reader.next();
        assertEquals(fastqComplex7_metadata, next.getValue0());
        assertEquals(fastqComplex7_sequence, next.getValue1());
    }
}
