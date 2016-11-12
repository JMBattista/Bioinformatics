package com.vitreoussoftware.bioinformatics.sequence.generator.distribution;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableMap;
import lombok.val;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Test the {@link EnumeratedDistribution}
 * Created by John on 10/16/2016.
 */
public class EnumeratedDistributionTest {
    /**
     * Creating a {@link EnumeratedDistribution} without any probability mappings fails
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateNoProbability() {
        EnumeratedDistribution.builder()
                .build();
    }

    /**
     * Creating a {@link EnumeratedDistribution} with only a single value works
     */
    @Test
    public void testCreateSingleLiklihood() {
        val probability = EnumeratedDistribution.<Character>builder()
                .probability('A', 1.0)
                .build();

        assertThat(probability, is(not(nullValue())));
    }

    /**
     * Creating a {@link EnumeratedDistribution} with only a multiple values works
     */
    @Test
    public void testCreateMultipleLiklihoods() {
        val distribution = EnumeratedDistribution.<Character>builder()
                .probability('A', 0.5)
                .probability('B', 0.5)
                .build();

        assertThat(distribution, is(not(nullValue())));
    }

    /**
     * Creating a {@link EnumeratedDistribution} with only a multiple values works
     */
    @Test
    public void testCreateMultipleSmallLiklihoods() {
        val distribution = EnumeratedDistribution.<Character>builder()
                .probability('A', 0.8888889)
                .probability('C', 0.01)
                .probability('G', 0.01)
                .probability('T', 0.01)
                .probability('R', 0.01)
                .probability('Y', 0.01)
                .probability('K', 0.01)
                .probability('M', 0.01)
                .probability('S', 0.01)
                .probability('W', 0.01)
                .probability('B', 0.01)
                .probability('D', 0.01)
                .probability('H', 0.001)
                .probability('V', 0.0001)
                .probability('N', 0.00001)
                .probability('X', 0.000001)
                .probability('-', 0.0000001)
                .build();

        assertThat(distribution, is(not(nullValue())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFailCumulativeProbabilityTooHigh() {
        EnumeratedDistribution.<Character>builder()
                .probability('A', 0.5)
                .probability('B', 0.5)
                .probability('C', 0.5)
                .build();
    }

    @Test
    public void testCreateOverSizedNormalized() {
        EnumeratedDistribution.<Character>builder()
                .probability('A', 0.5)
                .probability('B', 0.5)
                .probability('C', 0.5)
                .normalizeProbabilities(true)
                .build();
    }

    /**
     * Exception is thrown for an empty list of probabilities if clear is called after adding an element
     */
    @Test(expected = IllegalArgumentException.class)
    public void testBuildClearEndsEmpty() {
        EnumeratedDistribution.<Character>builder()
                .probability('A', 1.0)
                .clearProbabilities()
                .build();
    }

    /**
     * Can add a new element after clearing the probabilities
     */
    @Test
    public void testBuildClearAdds() {
        val distribution = EnumeratedDistribution.<Character>builder()
                .probability('A', 1.0)
                .clearProbabilities()
                .probability('T', 1.0)
                .build();

        assertThat(distribution.sample(), is('T'));
    }

    /**
     * Can add a map of elements via probabilities
     */
    @Test
    public void testBuildProbabilitiyMap() {
        val distribution = EnumeratedDistribution.<Character>builder()
                .probabilities(ImmutableMap.of('A', 1.0))
                .build();

        assertThat(distribution.sample(), is('A'));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFailCumulativeProbabilityTooLow() {
        EnumeratedDistribution.<Character>builder()
                .probability('A', 0.5)
                .build();
    }

    /**
     * Use the sample method many times and check if we are within a decent range
     * //TODO make it a 99.9 confidence interval specifically
     */
    @Test
    public void testNextMixed() {
        val trials = 10000; // 1,000
        EnumeratedDistribution<Character> distribution = EnumeratedDistribution.<Character>builder()
                .probability('A', 0.5)
                .probability('T', 0.5)
                .build();

        val multiset = HashMultiset.create();
        IntStream.range(0, trials)
                .mapToObj(x -> distribution.sample())
                .forEach(c -> multiset.add(c));


        assertThat(multiset.elementSet().toString(), multiset.elementSet().size(), is(2));
        assertThat(multiset.count('A'), is(both(greaterThan((int)(trials*.45))).and(lessThan((int)(trials*.55)))));
        assertThat(multiset.count('T'), is(both(greaterThan((int)(trials*.45))).and(lessThan((int)(trials*.55)))));
    }
}
