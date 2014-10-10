package com.vitreoussoftware.bioinformatics.sequence.io;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence;
import com.vitreoussoftware.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme;

/**
 * Created by John on 9/21/14.
 */
public class FastaData extends AlignerData {
    protected static final String fastaMultiLineDescriptionMetadata = "LCBO - Prolactin precursor - Bovine";
    protected static final String fastaMultiLineDescriptionSequence =
            "VCVYTHCGRNCYYGSYVYSTTWNTGCMVVVCTMATAGMGYVVAWGXMSGWGATVCTNVGSACAYCGTNVV" +
                    "TWCWGGGSVDKATVNRGGAGHGCVAGTMVAVAGVHVTGVHTTGSNNAVGVTSDSDKCAGHAYYTCKDGVG" +
                    "VVCVCVVVVVVAVVSADMVGDADNHMAADAVNTAVHCKATWYGVGAYACVRSVANKVGGVVAVGVSCVCV" +
                    "GVMAGVHTSKHRSMMVRAVSXAVGWTVTMDVVTVTWCGSXAVTYAYTCCGXMASCVYGSCCVAGVACAGX" +
                    "CTNY";
    protected static final String fastaTerminatedMetadata = "MCHU - Calmodulin - Human, rabbit, bovine, rat, and chicken";
    protected static final String fastaTerminatedSequence =
            "ADXVTTTXCATGKTAGSVGDKDGDGTCTTKTVGTVMRSVGXNATTATVXDMCNTVDADGNGTCD" +
                    "GATGVTMMARKMKDTDSTTTCRTAGRVGDKDGNGYCSAATVRHVMTNVGTKVTDTTVDTMCRTA" +
                    "DCDGDGXVNYTTGVXMMTAK";
    protected static final String fastaLargeHeaderMetadata = "gi|5524211|gb|AAD44166.1| cytochrome b [Elephas maximus maximus]";
    protected static final String fastaLargeHeaderSequence =
            "VCVYTHCGRNCYYGSYVYSTTWNTGCMVVVCTMATAGMGYVVAWGXMSGWGATVCTNVGSACAYCGTNVV" +
                    "TWCWGGGSVDKATVNRGGAGHGCVAGTMVAVAGVHVTGVHTTGSNNAVGVTSDSDKCAGHAYYTCKDGVG" +
                    "VVCVCVVVVVVAVVSADMVGDADNHMAADAVNTAVHCKATWYGVGAYACVRSVANKVGGVVAVGVSCVCV" +
                    "GVMAGVHTSKHRSMMVRAVSXAVGWTVTMDVVTVTWCGSXAVTYAYTCCGXMASCVYGSCCVAGVACAGX" +
                    "CTNY";

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

    public static String getFastaMultiLineDescription() {
        return fastaMultiLineDescriptionSequence;
    }

    public static String getFastaTerminated() {
        return fastaTerminatedSequence;
    }

    public static String getFastaLargeHeader() {
        return fastaLargeHeaderSequence;
    }


    public static String getFastaMultiLineDescriptionMetadata() {
        return fastaMultiLineDescriptionMetadata;
    }

    public static String getFastaTerminatedMetadata() {
        return fastaTerminatedMetadata;
    }

    public static String getFastaLargeHeaderMetadata() {
        return fastaLargeHeaderMetadata;
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

    public static Sequence getFastaMultiLineDescriptionSequence() {
		return BasicSequence.create(fastaMultiLineDescriptionMetadata, fastaMultiLineDescriptionSequence, AcceptUnknownDnaEncodingScheme.instance).get();
	}

    public static Sequence getFastaTerminatedSequence() {
		return BasicSequence.create(fastaTerminatedMetadata, fastaTerminatedSequence, AcceptUnknownDnaEncodingScheme.instance).get();
	}

    public static Sequence getFastaLargeHeaderSequence() {
		return BasicSequence.create(fastaLargeHeaderMetadata, fastaLargeHeaderSequence, AcceptUnknownDnaEncodingScheme.instance).get();
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
