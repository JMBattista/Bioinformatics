package com.vitreoussoftware.bioinformatics.sequence.io;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence;
import com.vitreoussoftware.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme;

/**
 * Created by John on 9/21/14.
 */
public class FastqData extends AlignerData {
    private final static String complex1_metadata = "SEQ_ID";
    private final static String complex1_sequence = "GATTTGGGGTTCAAAGCAGTATCGATCAAATAGTAAATCCATTTGTTCAACTCACAGTTT";
    private final static String complex1_comments = "";
    private final static String complex1_quality  = "!''*((((***+))%%%++)(%%%%).1***-+*''))**55CCF>>>>>>CCCCCCC65";

    private final static String complex2_metadata = "HWUSI-EAS100R:6:73:941:1973#0/1";
    private final static String complex2_sequence = "ACCGCTTGTGCGGGCCCCCGTCAATTCATTTGAGTTTTAGTCTTGCGACCGTACTCCCCAGGCGGTCTACTTATCGCGTTAGCTGCGCCACTAA";
    private final static String complex2_comments = "";
    private final static String complex2_quality  = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

    private final static String complex3_metadata = "EAS139:136:FC706VJ:2:2104:15343:197393 1:Y:18:ATCACG";
    private final static String complex3_sequence = "ACCGCTTGTGCGGGCCCCCGTCAATTCATTTGAGTTTTAGTCTTGCGACCGTACTCCCCAGGCGGTCTACTTATCGCGTTAGCTGCGCCACTAA";
    private final static String complex3_comments = "";
    private final static String complex3_quality  = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";

    private final static String complex4_metadata = "SRR001666.1 071112_SLXA-EAS1_s_7:5:1:817:345 length=36";
    private final static String complex4_sequence = "GGGTGATGGCCGCTGCCGATGGCGTCAAATCCCACC";
    private final static String complex4_comments = "SRR001666.1 071112_SLXA-EAS1_s_7:5:1:817:345 length=36";
    private final static String complex4_quality  = "IIIIIIIIIIIIIIIIIIIIIIIIIIIIII9IG9IC";

    private final static String complex5_metadata = "Acinetobacter_806_905_0:0:0_0:0:0_0/1";
    private final static String complex5_sequence = "ACCGCTTGTGCGGGCCCCCGTCAATTCATTTGAGTTTTAGTCTTGCGACCGTACTCCCCAGGCGGTCTACTTATCGCGTTAGCTGCGCCACTAAAGCCTC";
    private final static String complex5_comments = "";
    private final static String complex5_quality  = "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII";

    private final static String complex6_metadata = "Bacillus_359_458_0:0:0_0:0:0_100cb/1";
    private final static String complex6_sequence = "GCCGCGTGAGTGATGAAGGCTTTCGGGTCGTAAAACTCTGTTGTTAGGGAAGAACAAGTGCTAGTTGAATAAGCTGGCACCTTGACGGTACCTAACCAGA";
    private final static String complex6_comments = "";
    private final static String complex6_quality  = "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII";

    private final static String complex7_metadata = "Actinomyces_79_178_0:0:0_0:0:0_4639/1";
    private final static String complex7_sequence = "GAACAAACCTTTCCACCAACCCCCATGCGAAGATCAGTGAATATCCAGTATTAGCACCCGTTTCCGGGCGTTATCCCAAAGAAGGGGGCAGGTTACTCAC";
    private final static String complex7_comments = "";
    private final static String complex7_quality  = "IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII";

    public static String getRecordSimple() {
        return recordSimple;
    }

    public static String getRecord1() {
        return record1;
    }

    public static String getRecord2() {
        return record2;
    }

    public static String getRecord3() {
        return record3;
    }

    public static String getAlternate1() {
        return alternate1;
    }

    public static String getAlternate2() {
        return alternate2;
    }

    public static String getAlternate3() {
        return alternate3;
    }

    public static String getComplex1_metadata() {
        return complex1_metadata;
    }

    public static String getComplex1_sequence() {
        return complex1_sequence;
    }

    public static String getComplex1_comments() {
        return complex1_comments;
    }

    public static String getComplex1_quality() {
        return complex1_quality;
    }

    public static String getComplex2_metadata() {
        return complex2_metadata;
    }

    public static String getComplex2_sequence() {
        return complex2_sequence;
    }

    public static String getComplex2_comments() {
        return complex2_comments;
    }

    public static String getComplex2_quality() {
        return complex2_quality;
    }

    public static String getComplex3_metadata() {
        return complex3_metadata;
    }

    public static String getComplex3_sequence() {
        return complex3_sequence;
    }

    public static String getComplex3_comments() {
        return complex3_comments;
    }

    public static String getComplex3_quality() {
        return complex3_quality;
    }

    public static String getComplex4_metadata() {
        return complex4_metadata;
    }

    public static String getComplex4_sequence() {
        return complex4_sequence;
    }

    public static String getComplex4_comments() {
        return complex4_comments;
    }

    public static String getComplex4_quality() {
        return complex4_quality;
    }

    public static String getComplex5_metadata() {
        return complex5_metadata;
    }

    public static String getComplex5_sequence() {
        return complex5_sequence;
    }

    public static String getComplex5_comments() {
        return complex5_comments;
    }

    public static String getComplex5_quality() {
        return complex5_quality;
    }

    public static String getComplex6_metadata() {
        return complex6_metadata;
    }

    public static String getComplex6_sequence() {
        return complex6_sequence;
    }

    public static String getComplex6_comments() {
        return complex6_comments;
    }

    public static String getComplex6_quality() {
        return complex6_quality;
    }

    public static String getComplex7_metadata() {
        return complex7_metadata;
    }

    public static String getComplex7_sequence() {
        return complex7_sequence;
    }

    public static String getComplex7_comments() {
        return complex7_comments;
    }

    public static String getComplex7_quality() {
        return complex7_quality;
    }

    public static Sequence getSimpleSequence() {
        return BasicSequence.create(recordSimple, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    public static Sequence getRecord1Sequence() {
		return BasicSequence.create(record1, AcceptUnknownDnaEncodingScheme.instance).get();
	}

    public static Sequence getRecord2Sequence() {
		return BasicSequence.create(record2, AcceptUnknownDnaEncodingScheme.instance).get();
	}

    public static Sequence getRecord3Sequence() {
		return BasicSequence.create(record3, AcceptUnknownDnaEncodingScheme.instance).get();
	}

    public static Sequence getComplex1Sequence() {
        return BasicSequence.create(complex1_metadata, complex1_sequence, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    public static Sequence getComplex2Sequence() {
        return BasicSequence.create(complex2_metadata, complex2_sequence, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    public static Sequence getComplex3Sequence() {
        return BasicSequence.create(complex3_metadata, complex3_sequence, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    public static Sequence getComplex4Sequence() {
        return BasicSequence.create(complex4_metadata, complex4_sequence, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    public static Sequence getComplex5Sequence() {
        return BasicSequence.create(complex5_metadata, complex5_sequence, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    public static Sequence getComplex6Sequence() {
        return BasicSequence.create(complex6_metadata, complex6_sequence, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    public static Sequence getComplex7Sequence() {
        return BasicSequence.create(complex7_metadata, complex7_sequence, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    public static Sequence getAlternate1Sequence() {
		return BasicSequence.create(alternate1, AcceptUnknownDnaEncodingScheme.instance).get();
	}

    public static Sequence getAlternate2Sequence() {
		return BasicSequence.create(alternate2, AcceptUnknownDnaEncodingScheme.instance).get();
	}

    public static Sequence getAlternate3Sequence() {
		return BasicSequence.create(alternate3, AcceptUnknownDnaEncodingScheme.instance).get();
	}
}
