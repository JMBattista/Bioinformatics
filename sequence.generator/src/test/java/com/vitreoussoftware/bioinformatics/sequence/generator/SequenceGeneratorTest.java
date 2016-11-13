package com.vitreoussoftware.bioinformatics.sequence.generator;

import com.google.common.collect.ImmutableList;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.encoding.EncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.Distribution;
import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.EnumeratedDistribution;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.OrderingComparison.greaterThan;

/**
 * Test the {@link SequenceGenerator}
 * Created by John on 10/16/2016.
 */
@RunWith(Theories.class)
public class SequenceGeneratorTest {
    private EnumeratedDistribution defaultDistribution;

    @DataPoints
    public static List<Character> samples = ImmutableList.of('A', 'T', 'C', 'G');

    @Before
    public void setup() {
        defaultDistribution = getDistribution()
                .probability('A', 1.0)
                .build();
    }

    /**
     * Create a {@link EnumeratedDistribution.DiscreteDistributionBuilder} with a default {@link EncodingScheme}
     * @return the {@link EnumeratedDistribution.DiscreteDistributionBuilder} instance
     */
    private EnumeratedDistribution.DiscreteDistributionBuilder<Character> getDistribution() {
        return EnumeratedDistribution.<Character>builder();
    }

    /**
     * We can create a generator
     */
    @Test
    public void testCreate() {
        SequenceGenerator.builder()
                .lengthDistribution(() -> 10)
                .encodingScheme(AcceptUnknownDnaEncodingScheme.instance)
                .basePairDistribution(defaultDistribution)
                .build();
    }

    /**
     * Attempting to create without an {@link EncodingScheme} fails
     */
    @Test(expected = NullPointerException.class)
    public void testCreateNoEncodingScheme() {
        SequenceGenerator.builder()
                .lengthDistribution(() -> 10)
                .basePairDistribution(defaultDistribution)
                .build();
    }


    /**
     * Attempting to create without a length {@link Distribution} fails.
     */
    @Test(expected = NullPointerException.class)
    public void testCreateNoLengthDistribution() {
        SequenceGenerator.builder()
                .encodingScheme(AcceptUnknownDnaEncodingScheme.instance)
                .basePairDistribution(defaultDistribution)
                .build();
    }


    /**
     * Attempting to create without a BasePair {@link Distribution}
     */
    @Test(expected = NullPointerException.class)
    public void testCreateNoBasePairDistribution() {
        SequenceGenerator.builder()
                .lengthDistribution(() -> 10)
                .encodingScheme(AcceptUnknownDnaEncodingScheme.instance)
                .build();
    }

    /**
     * If the length distribution keeps returning 0 we can't create a sequence and should fail.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSampleFailLength0() {
        val generator = SequenceGenerator.builder()
                .lengthDistribution(() -> 0)
                .encodingScheme(AcceptUnknownDnaEncodingScheme.instance)
                .basePairDistribution(defaultDistribution)
                .build();

        generator.sample();
    }

    /**
     * We can create a Random sequence generator for length 1
     */
    @Test
    public void testSampleLength1() {
        val length = 1;
        val generator = SequenceGenerator.builder()
                .lengthDistribution(() -> length)
                .encodingScheme(AcceptUnknownDnaEncodingScheme.instance)
                .basePairDistribution(defaultDistribution)
                .build();

        assertThat(generator.sample().length(), is(length));
    }

    /**
     * We can create a Random sequence generator for length 10
     */
    @Test
    public void testSampleLength10() {
        val length = 10;
        val generator = SequenceGenerator.builder()
                .lengthDistribution(() -> length)
                .encodingScheme(AcceptUnknownDnaEncodingScheme.instance)
                .basePairDistribution(defaultDistribution)
                .build();

        assertThat(generator.sample().length(), is(length));
    }


    /**
     * Test that we can generate a sequence from an {@link EnumeratedDistribution} containing only one element
     */
    @Theory
    public void theorySampleCanGenerateFromSingleCharacter(Character sample) {
        val generator = SequenceGenerator.builder()
                .lengthDistribution(() -> 10)
                .encodingScheme(AcceptUnknownDnaEncodingScheme.instance)
                .basePairDistribution(getDistribution()
                    .probability(sample, 1.0)
                    .build())
                .build();

        val sequence = generator.sample();

        sequence.stream()
                .map(BasePair::toChar)
                .forEach(bp -> assertThat(bp, is(sample)));
    }


    /**
     * Test that we can generate a sample {@link Sequence} containing different base pairs
     */
    @Test
    public void testSampleCanGenerateMany() {
        val length = 100;
        val generator = SequenceGenerator.builder()
                .lengthDistribution(() -> length)
                .encodingScheme(AcceptUnknownDnaEncodingScheme.instance)
                .basePairDistribution(getDistribution()
                        .probability('A', 0.5)
                        .probability('T', 0.5)
                        .build())
                .build();

        val sequence = generator.sample();

        assertThat("Sequence length is positive", sequence.length(), is(length));

        int aCount = (int) sequence.stream().filter(bp -> bp.toChar() == 'A').count();
        int tCount = (int) sequence.stream().filter(bp -> bp.toChar() == 'T').count();

        assertThat("No A's were found", aCount, is(greaterThan(0)));
        assertThat("No T's were found", tCount, is(greaterThan(0)));
        assertThat("There were non A/T values", aCount + tCount, is(length));
    }
}
