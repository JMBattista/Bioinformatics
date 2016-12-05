package com.vitreoussoftware.bioinformatics.sequence.io;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence;
import com.vitreoussoftware.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.fastq.FastqStringFileStreamReader;

import java.io.FileNotFoundException;

/**
 * Created by John on 9/21/14.
 */
public class FastqData extends TestData {
    private static final String complex1_metadata = "SEQ_ID";
    private static final String complex1_sequence = "GATTTGGGGTTCAAAGCAGTATCGATCAAATAGTAAATCCATTTGTTCAACTCACAGTTT";
    private static final String complex1_comments = "";
    private static final String complex1_quality = "!''*((((***+))%%%++)(%%%%).1***-+*''))**55CCF>>>>>>CCCCCCC65";

    private static final String complex2_metadata = "HWUSI-EAS100R:6:73:941:1973#0/1";
    private static final String complex2_sequence = "ACCGCTTGTGCGGGCCCCCGTCAATTCATTTGAGTTTTAGTCTTGCGACCGTACTCCCCAGGCGGTCTACTTATCGCGTTAGCTGCGCCACTAA";
    private static final String complex2_comments = "";
    private static final String complex2_quality = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

    private static final String complex3_metadata = "EAS139:136:FC706VJ:2:2104:15343:197393 1:Y:18:ATCACG";
    private static final String complex3_sequence = "ACCGCTTGTGCGGGCCCCCGTCAATTCATTTGAGTTTTAGTCTTGCGACCGTACTCCCCAGGCGGTCTACTTATCGCGTTAGCTGCGCCACTAA";
    private static final String complex3_comments = "";
    private static final String complex3_quality = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

    private static final String complex4_metadata = "SRR001666.1 071112_SLXA-EAS1_s_7:5:1:817:345 length=36";
    private static final String complex4_sequence = "GGGTGATGGCCGCTGCCGATGGCGTCAAATCCCACC";
    private static final String complex4_comments = "SRR001666.1 071112_SLXA-EAS1_s_7:5:1:817:345 length=36";
    private static final String complex4_quality = "IIIIIIIIIIIIIIIIIIIIIIIIIIIIII9IG9IC";

    private static final String complex5_metadata = "Acinetobacter_806_905_0:0:0_0:0:0_0/1";
    private static final String complex5_sequence = "ACCGCTTGTGCGGGCCCCCGTCAATTCATTTGAGTTTTAGTCTTGCGACCGTACTCCCCAGGCGGTCTACTTATCGCGTTAGCTGCGCCACTAAAGCCTC";
    private static final String complex5_comments = "";
    private static final String complex5_quality = "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII";

    private static final String complex6_metadata = "Bacillus_359_458_0:0:0_0:0:0_100cb/1";
    private static final String complex6_sequence = "GCCGCGTGAGTGATGAAGGCTTTCGGGTCGTAAAACTCTGTTGTTAGGGAAGAACAAGTGCTAGTTGAATAAGCTGGCACCTTGACGGTACCTAACCAGA";
    private static final String complex6_comments = "";
    private static final String complex6_quality = "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII";

    private static final String complex7_metadata = "Actinomyces_79_178_0:0:0_0:0:0_4639/1";
    private static final String complex7_sequence = "GAACAAACCTTTCCACCAACCCCCATGCGAAGATCAGTGAATATCCAGTATTAGCACCCGTTTCCGGGCGTTATCCCAAAGAAGGGGGCAGGTTACTCAC";
    private static final String complex7_comments = "";
    private static final String complex7_quality = "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII";

    /**
     * The path where the FASTQ test files can be found.
     */
    private static final String PATH = "build/resources/test/Fastq/";

    /**
     * The extension for FASTQ data files
     */
    private static final String EXTENSION = ".fastq";

    @Override
    protected StringStreamReader createReader(final String path) throws FileNotFoundException {
        return FastqStringFileStreamReader.create(path);
    }

    @Override
    protected StringStreamReader createReader(final String path, final int pagingSize) throws FileNotFoundException {
        return FastqStringFileStreamReader.create(path, pagingSize);
    }

    @Override
    protected String getExtension() {
        return EXTENSION;
    }

    @Override
    protected String getBasePath() {
        return PATH;
    }

    public String getComplex1Metadata() {
        return complex1_metadata;
    }

    public String getComplex1SequenceString() {
        return complex1_sequence;
    }

    public String getComplex1Comments() {
        return complex1_comments;
    }

    public String getComplex1Quality() {
        return complex1_quality;
    }

    public String getComplex2Metadata() {
        return complex2_metadata;
    }

    public String getComplex2SequenceString() {
        return complex2_sequence;
    }

    public String getComplex2Comments() {
        return complex2_comments;
    }

    public String getComplex2Quality() {
        return complex2_quality;
    }

    public String getComplex3Metadata() {
        return complex3_metadata;
    }

    public String getComplex3SequenceString() {
        return complex3_sequence;
    }

    public String getComplex3Comments() {
        return complex3_comments;
    }

    public String getComplex3Quality() {
        return complex3_quality;
    }

    public String getComplex4Metadata() {
        return complex4_metadata;
    }

    public String getComplex4SequenceString() {
        return complex4_sequence;
    }

    public String getComplex4Comments() {
        return complex4_comments;
    }

    public String getComplex4Quality() {
        return complex4_quality;
    }

    public String getComplex5Metadata() {
        return complex5_metadata;
    }

    public String getComplex5SequenceString() {
        return complex5_sequence;
    }

    public String getComplex5Comments() {
        return complex5_comments;
    }

    public String getComplex5Quality() {
        return complex5_quality;
    }

    public String getComplex6Metadata() {
        return complex6_metadata;
    }

    public String getComplex6SequenceString() {
        return complex6_sequence;
    }

    public String getComplex6Comments() {
        return complex6_comments;
    }

    public String getComplex6Quality() {
        return complex6_quality;
    }

    public String getComplex7Metadata() {
        return complex7_metadata;
    }

    public String getComplex7SequenceString() {
        return complex7_sequence;
    }

    public String getComplex7Comments() {
        return complex7_comments;
    }

    public String getComplex7Quality() {
        return complex7_quality;
    }

    public Sequence getComplex1Sequence() {
        return BasicSequence.create(complex1_metadata, complex1_sequence, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    public Sequence getComplex2Sequence() {
        return BasicSequence.create(complex2_metadata, complex2_sequence, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    public Sequence getComplex3Sequence() {
        return BasicSequence.create(complex3_metadata, complex3_sequence, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    public Sequence getComplex4Sequence() {
        return BasicSequence.create(complex4_metadata, complex4_sequence, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    public Sequence getComplex5Sequence() {
        return BasicSequence.create(complex5_metadata, complex5_sequence, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    public Sequence getComplex6Sequence() {
        return BasicSequence.create(complex6_metadata, complex6_sequence, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    public Sequence getComplex7Sequence() {
        return BasicSequence.create(complex7_metadata, complex7_sequence, AcceptUnknownDnaEncodingScheme.instance).get();
    }
}
