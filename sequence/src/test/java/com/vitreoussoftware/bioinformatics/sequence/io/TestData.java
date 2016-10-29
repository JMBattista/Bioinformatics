package com.vitreoussoftware.bioinformatics.sequence.io;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence;
import com.vitreoussoftware.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader;

import java.io.FileNotFoundException;

/**
 * Base Class for Test Data classes to extend. Helps to generify test classes
 * Created by John on 2016/10/15
 */
public abstract class TestData {
    /**
     *  file with three full records
     */
    private static final String COMPLEX = "ComplexExamples";

    /**
     *  file with three full records
     */
    private static final String EMPTY = "Empty";

    /**
     *  file with three full records
     */
    private static final String EMPTY_WHITESPACE = "EmptyWhitespace";
    /**
     *  file with three full records
     */
    public static final String REAL_EXAMPLES = "RealExamples";

    /**
     *  file with only a small amount of data
     */
    private static final String SIMPLE_EXAMPLE = "SimpleExample";

    /**
     *  file with enough data that pages must be performed
     */
    private static final String PAGING_REQUIRED = "PagingRequired";

    /**
     *  file with enough data that pages must be performed
     */
    private static final String EXTRA_NEWLINE = "ExtraNewline";

    /**
     *  Extremely large file for testing that all records can be loaded
     */
    private static final String BIG = "Big";

    /**
     * Example metadata for use when testing reader/writer interation
     */
    private final String METADATA = "AB000263 standard; RNA; PRI; 368 BP.";

    /**
     * Shortened sequence string for basic testing
     */
    private final String RECORD_SIMPLE = "CAGGCUUAACACAUGCAAGUCGAACGAAGUUAGGAAGCUUGCUUCUGAUACUUAGUGGCGGACGGGUGAGUAAUGCUUAGG";

    /**
     * First record in the real examples file
     */
    private final String REAL_EXAMPLE_1 =
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
     * Second record in the real example file
     */
    private final String REAL_EXAMPLE_2 =
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
    private final String REAL_EXAMPLE_3 =
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
     * Get the full path for a file
     * @param fileName the name of the file
     * @return The completed path
     */
    private String getPath(String fileName) {
        return getBasePath() + fileName + getExtension();
    }

    /**
     * Get the {@link StringStreamReader} instance to use on this test data
     * @param path The data file to load
     * @return The {@link StringStreamReader}
     */
    private StringStreamReader getReader(String path) throws FileNotFoundException {
        return createReader(getPath(path));
    }
    /**
     * Get the {@link StringStreamReader} instance to use on this test data
     * @param path The data file to load
     * @param pagingSize The paging size for the {@link StringStreamReader}
     * @return The {@link StringStreamReader}
     */
    private StringStreamReader getReader(String path, int pagingSize) throws FileNotFoundException {
        return createReader(getPath(path), pagingSize);
    }

    /**
     * Get the {@link StringStreamReader} instance to use on this test data
     * @param path The data file to load
     * @return The {@link StringStreamReader}
     */
    protected abstract StringStreamReader createReader(String path) throws FileNotFoundException;

    /**
     * Get the {@link StringStreamReader} instance to use on this test data
     * @param path The data file to load
     * @param pagingSize The paging size for the {@link StringStreamReader}
     * @return The {@link StringStreamReader}
     */
    protected abstract StringStreamReader createReader(String path, int pagingSize) throws FileNotFoundException;

    /**
     * Get the extension for the files
     * @return the file extension
     */
    protected abstract String getExtension();

    /**
     * Get the base path to the files
     * @return the base path for the files
     */
    protected abstract String getBasePath();

    /**
     * Return the file path used by the {@see getSimpleExampleReader} function
     * @return The file path
     */
    public String getSimpleExamplePath() {
        return getPath(SIMPLE_EXAMPLE);
    }

    /**
     * Create a {@link StringStreamReader} for an empty test file
     * @return the StringStreamReader
     */
    public StringStreamReader getEmptyReader()
            throws FileNotFoundException {
        return getReader(EMPTY);
    }

    /**
     * Create a {@link StringStreamReader} for a test file containing only whitespace
     * @return the StringStreamReader
     */
    public StringStreamReader getEmptyWhiteSpaceReader()
            throws FileNotFoundException {
        return getReader(EMPTY_WHITESPACE);
    }

    /**
     * Create a StringStreamReader for the Simple test file
     * @return the StringStreamReader
     * @throws FileNotFoundException the test file could not be found
     */
    public StringStreamReader getSimpleExampleReader()
            throws FileNotFoundException {
        return getReader(SIMPLE_EXAMPLE);
    }

    /**
     * Create a StringStreamReader for the Example test file
     * @return the StringStreamReader
     * @throws FileNotFoundException the test file could not be found
     */
    public StringStreamReader getRealExamplesReader()
            throws FileNotFoundException {
        return getReader(REAL_EXAMPLES);
    }

    /**
     * Create a StringStreamReader for the Example test file
     * @return the StringStreamReader
     * @throws FileNotFoundException the test file could not be found
     */
    public StringStreamReader getComplexExamplesReader()
            throws FileNotFoundException {
        return getReader(COMPLEX);
    }

    /**
     * Create a StringStreamReader for the Paged test file
     * @return the StringStreamReader
     * @throws FileNotFoundException the test file could not be found
     */
    public StringStreamReader getPagingRequiredReader()
            throws FileNotFoundException {
        return getReader(PAGING_REQUIRED);
    }
    /**
     * Create a StringStreamReader for the Paged test file
     * @return the StringStreamReader
     * @throws FileNotFoundException the test file could not be found
     */
    public StringStreamReader getPagingRequiredReader(int pagingSize)
            throws FileNotFoundException {
        return getReader(PAGING_REQUIRED, pagingSize);
    }

    /**
     * Create a StringStreamReader for the test file with extra blank lines
     * @return the StringStreamReader
     * @throws FileNotFoundException the test file could not be found
     */
    public StringStreamReader getExtraNewlineReader()
            throws FileNotFoundException {
        return getReader(EXTRA_NEWLINE);
    }

    /**
     * Create a StringStreamReader for the Big test file
     * @return the StringStreamReader
     * @throws FileNotFoundException the test file could not be found
     */
    public StringStreamReader getBigReader()
            throws FileNotFoundException {
        return getReader(BIG);
    }

    /**
     * Get the string expression matching the simple record in {@link AcceptUnknownDnaEncodingScheme} format
     * @return The simple record string
     */
    public String getSimpleRecord() {
        return RECORD_SIMPLE;
    }

    /**
     * Get the string expression matching Record1 in {@link AcceptUnknownDnaEncodingScheme} format
     * @return The Record1 string
     */
    public String getRealExample1() {
        return REAL_EXAMPLE_1;
    }

    /**
     * Get the string expression matching Record2 in {@link AcceptUnknownDnaEncodingScheme} format
     * @return The Record2 string
     */
    public String getRealExample2() {
        return REAL_EXAMPLE_2;
    }

    /**
     * Get the string expression matching Record2 in {@link AcceptUnknownDnaEncodingScheme} format
     * @return The Record2 string
     */
    public String getRealExample3() {
        return REAL_EXAMPLE_3;
    }

    /**
     * Get the {@link Sequence} that corresponds to {@see getSimpleRecord}
     * @return The sequence
     */
    public Sequence getSimpleSequence() {
        return BasicSequence.create(METADATA, RECORD_SIMPLE, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    /**
     * Get the {@link Sequence} that corresponds to {@see getRealExample1}
     * @return The sequence
     */
    public Sequence getRealExample1Sequence() {
        return BasicSequence.create(METADATA, REAL_EXAMPLE_1, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    /**
     * Get the {@link Sequence} that corresponds to {@see getRealExample2}
     * @return The sequence
     */
    public Sequence getRealExample2Sequence() {
        return BasicSequence.create(METADATA, REAL_EXAMPLE_2, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    /**
     * Get the {@link Sequence} that corresponds to {@see getRealExample3}
     * @return The sequence
     */
    public Sequence getRealExample3Sequence() {
        return BasicSequence.create(METADATA, REAL_EXAMPLE_3, AcceptUnknownDnaEncodingScheme.instance).get();
    }
}
