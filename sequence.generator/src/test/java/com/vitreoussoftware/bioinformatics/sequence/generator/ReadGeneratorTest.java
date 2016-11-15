package com.vitreoussoftware.bioinformatics.sequence.generator;

import com.google.common.collect.ImmutableList;
import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence;
import com.vitreoussoftware.bioinformatics.sequence.encoding.BasicDnaEncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.encoding.EncodingScheme;
import lombok.val;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Test the {@link ReadGenerator}
 * <p>
 * Created by John on 10/27/2016.
 */
@RunWith(Theories.class)
public class ReadGeneratorTest {

    public static final EncodingScheme SCHEME = BasicDnaEncodingScheme.instance;
    @DataPoints
    public static List<Character> samples = ImmutableList.of('A', 'T', 'C', 'G');

    /**
     * Test that we can sample length 1 reads from a length 1 sequence
     */
    @Theory
    public void theorySampleFromSingle(final Character sample) {
        val source = BasicSequence.create(sample.toString(), SCHEME).get();
        val generator = ReadGenerator.builder()
                .lengthDistribution(() -> 1)
                .startPointDistribution(() -> 0)
                .build();

        val read = generator.sample(source);

        assertThat(read, is(not(nullValue())));
        assertThat(read.length(), is(1));
        assertThat(read.get(0).toChar(), is(sample));
    }

    /**
     * Test that we can sample length 1 reads from a length 1 sequence
     */
    @Theory
    public void theorySampleFromSingleFlipped(final Character sample) {
        val basePair = SCHEME.fromCharacter(sample);
        val source = BasicSequence.create(sample.toString(), SCHEME).get();
        val generator = ReadGenerator.builder()
                .flipDistribution(() -> true)
                .lengthDistribution(() -> 1)
                .startPointDistribution(() -> 0)
                .build();

        val read = generator.sample(source);

        assertThat(read, is(not(nullValue())));
        assertThat(read.length(), is(1));
        assertThat(read.get(0), is(basePair.complement()));
    }

    /**
     * Test that we can sample repeatedly for a set of valid distributions
     */
    @Test
    public void testSampleRepeatInBounds() {
        val source = BasicSequence.create("TT", SCHEME).get();
        val generator = ReadGenerator.builder()
                .lengthDistribution(() -> (int) (Math.random() * 2))
                .startPointDistribution(() -> (int) (Math.random() * 2))
                .build();

        for (int i = 0; i < 100; i++) {
            val read = generator.sample(source);
            assertThat(read.toString(), is("T"));
        }
    }

    /**
     * Test that we can sample repeatedly for a set of distributions that can contain invalid values
     */
    @Test
    public void testSampleRepeat() {
        val source = BasicSequence.create("TT", SCHEME).get();
        val generator = ReadGenerator.builder()
                .lengthDistribution(() -> (int) (Math.random() * 4))
                .startPointDistribution(() -> (int) (Math.random() * 4))
                .build();


        for (int i = 0; i < 100; i++) {
            val read = generator.sample(source);
            assertThat(read.toString(), anyOf(is("T"), is("TT")));
        }
    }

    /**
     * Test that we can fail when attempting to sample from invalid distributions
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSampleFailsInvalidLength() {
        val source = BasicSequence.create("TT", SCHEME).get();
        val generator = ReadGenerator.builder()
                .lengthDistribution(() -> 0)
                .startPointDistribution(() -> 1)
                .build();

        generator.sample(source);
    }

    /**
     * Test that we can fail when attempting to sample from invalid distributions
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSampleFailsInvalidStartPoint() {
        val source = BasicSequence.create("TT", SCHEME).get();
        val generator = ReadGenerator.builder()
                .lengthDistribution(() -> 1)
                .startPointDistribution(() -> 5)
                .build();

        generator.sample(source);
    }
}
