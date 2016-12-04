package com.vitreoussoftware.bioinformatics.sequence.io;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence;
import com.vitreoussoftware.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.fasta.FastaStringFileStreamReader;

import java.io.FileNotFoundException;

/**
 * Test data in the FASTA format uses {@link AcceptUnknownDnaEncodingScheme}
 * Created by John on 9/21/14.
 */
public class FastaData extends TestData {
    private static final String fastaMultiLineDescriptionMetadata = "LCBO - Prolactin precursor - Bovine";
    private static final String fastaMultiLineDescriptionSequence =
            "VCVYTHCGRNCYYGSYVYSTTWNTGCMVVVCTMATAGMGYVVAWGXMSGWGATVCTNVGSACAYCGTNVV" +
                    "TWCWGGGSVDKATVNRGGAGHGCVAGTMVAVAGVHVTGVHTTGSNNAVGVTSDSDKCAGHAYYTCKDGVG" +
                    "VVCVCVVVVVVAVVSADMVGDADNHMAADAVNTAVHCKATWYGVGAYACVRSVANKVGGVVAVGVSCVCV" +
                    "GVMAGVHTSKHRSMMVRAVSXAVGWTVTMDVVTVTWCGSXAVTYAYTCCGXMASCVYGSCCVAGVACAGX" +
                    "CTNY";
    private static final String fastaTerminatedMetadata = "MCHU - Calmodulin - Human, rabbit, bovine, rat, and chicken";
    private static final String fastaTerminatedSequence =
            "ADXVTTTXCATGKTAGSVGDKDGDGTCTTKTVGTVMRSVGXNATTATVXDMCNTVDADGNGTCD" +
                    "GATGVTMMARKMKDTDSTTTCRTAGRVGDKDGNGYCSAATVRHVMTNVGTKVTDTTVDTMCRTA" +
                    "DCDGDGXVNYTTGVXMMTAK";
    private static final String fastaLargeHeaderMetadata = "gi|5524211|gb|AAD44166.1| cytochrome b [Elephas maximus maximus]";
    private static final String fastaLargeHeaderSequence =
            "VCVYTHCGRNCYYGSYVYSTTWNTGCMVVVCTMATAGMGYVVAWGXMSGWGATVCTNVGSACAYCGTNVV" +
                    "TWCWGGGSVDKATVNRGGAGHGCVAGTMVAVAGVHVTGVHTTGSNNAVGVTSDSDKCAGHAYYTCKDGVG" +
                    "VVCVCVVVVVVAVVSADMVGDADNHMAADAVNTAVHCKATWYGVGAYACVRSVANKVGGVVAVGVSCVCV" +
                    "GVMAGVHTSKHRSMMVRAVSXAVGWTVTMDVVTVTWCGSXAVTYAYTCCGXMASCVYGSCCVAGVACAGX" +
                    "CTNY";

    private final String alternate1 =
            "TGCTCAATTTTATCTAAAGAAAATCAAATTGAGCAAATATATTCACAAAAAATTATTTTT" +
                    "ATAGTATTTTTGAGAAAATAATTGCTCATTTGTACTAATG";
    private final String alternate2 =
            "AAACCACAAGACAAACCATATCGTAAGCTTGATGCAGATAGGCTTTATATAGAAGTCAGA" +
                    "CCAAGTGGGAAAAAAGTTTGGATTCACAAATTCTCCTTAA";
    private final String alternate3 =
            "GCAATTCCGACCACATAGACCGTATCAATACCACGTTCTTTTAAGTAACCCGTTAAACCT" +
                    "GTCATTGTGGTGTGGTCAGCTTCCATAAAAGCCGAGTAAC";


    /**
     * The path where the FASTA test files can be found.
     */
    private static final String PATH = "build/resources/test/Fasta/";

    /**
     * The FASTA file extension
     */
    private static final String EXTENSION = ".fasta";

    /**
     * FASTA file to test alternate starting character > instead of ;
     */
    private static final String ALTERNATE_STARTING_CHARACTER = PATH + "AlternateStartingCharacter.fasta";
    /**
     * FASTA with no space between the end of metadata and terminating character
     */
    private static final String NO_ENDING_SPACE = PATH + "NoEndingSpace.fasta";

    @Override
    protected StringStreamReader createReader(final String path) throws FileNotFoundException {
        return FastaStringFileStreamReader.create(path);
    }

    @Override
    protected StringStreamReader createReader(final String path, final int pagingSize) throws FileNotFoundException {
        return FastaStringFileStreamReader.create(path, pagingSize);
    }

    @Override
    protected String getExtension() {
        return EXTENSION;
    }

    @Override
    protected String getBasePath() {
        return PATH;
    }

    /**
     * Get the string expression matching Alternate1 in {@link AcceptUnknownDnaEncodingScheme} format
     * @return The Alternate1 string
     */
    public String getAlternateStartingCharacter1() {
        return alternate1;
    }

    /**
     * Get the string expression matching Alternate2 in {@link AcceptUnknownDnaEncodingScheme} format
     * @return The Alternate2 string
     */
    public String getAlternateStartingCharacter2() {
        return alternate2;
    }

    /**
     * Get the string expression matching Alternate3 in {@link AcceptUnknownDnaEncodingScheme} format
     * @return The Alternate3 string
     */
    public String getAlternateStartingCharacter3() {
        return alternate3;
    }

    public String getFastaMultiLineDescription() {
        return fastaMultiLineDescriptionSequence;
    }

    public String getFastaTerminated() {
        return fastaTerminatedSequence;
    }

    public String getFastaLargeHeader() {
        return fastaLargeHeaderSequence;
    }

    public String getFastaMultiLineDescriptionMetadata() {
        return fastaMultiLineDescriptionMetadata;
    }

    public String getFastaTerminatedMetadata() {
        return fastaTerminatedMetadata;
    }

    public String getFastaLargeHeaderMetadata() {
        return fastaLargeHeaderMetadata;
    }


    /**
     * Get the {@link Sequence} that corresponds to {@see getAlternateStartingCharacter1}
     * @return The sequence
     */
    public Sequence getAlternateStartingCharacter1Sequence() {
        return BasicSequence.create(alternate1, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    /**
     * Get the {@link Sequence} that corresponds to {@see getAlternateStartingCharacter2}
     * @return The sequence
     */
    public Sequence getAlternateStartingCharacter2Sequence() {
        return BasicSequence.create(alternate2, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    /**
     * Get the {@link Sequence} that corresponds to {@see getAlternateStartingCharacter3}
     * @return The sequence
     */
    public Sequence getAlternateStartingCharacter3Sequence() {
        return BasicSequence.create(alternate3, AcceptUnknownDnaEncodingScheme.instance).get();
    }

    public Sequence getFastaMultiLineDescriptionSequence() {
		return BasicSequence.create(fastaMultiLineDescriptionMetadata, fastaMultiLineDescriptionSequence, AcceptUnknownDnaEncodingScheme.instance).get();
	}

    public Sequence getFastaTerminatedSequence() {
		return BasicSequence.create(fastaTerminatedMetadata, fastaTerminatedSequence, AcceptUnknownDnaEncodingScheme.instance).get();
	}

    public Sequence getFastaLargeHeaderSequence() {
		return BasicSequence.create(fastaLargeHeaderMetadata, fastaLargeHeaderSequence, AcceptUnknownDnaEncodingScheme.instance).get();
	}

    /**
     * Create a StringStreamReader for the Simple test file
     * @return the StringStreamReader
     * @throws FileNotFoundException the test file could not be found
     */
    public StringStreamReader getAlternateStartingCharacterReader()
            throws FileNotFoundException {
        return FastaStringFileStreamReader.create(ALTERNATE_STARTING_CHARACTER);
    }

    /**
     * Create a StringStreamReader for the NoSpace FASTA test file
     * @return the StringStreamReader
     * @throws FileNotFoundException the test file could not be found
     */
    public StringStreamReader getNoSpaceReader()
            throws FileNotFoundException {
        return FastaStringFileStreamReader.create(NO_ENDING_SPACE);
    }
}
