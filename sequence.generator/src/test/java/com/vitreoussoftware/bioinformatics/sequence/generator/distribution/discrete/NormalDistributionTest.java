package com.vitreoussoftware.bioinformatics.sequence.generator.distribution.discrete;

import lombok.val;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Test the {@link NormalDistribution}
 * Created by John on 10/16/2016.
 */
public class NormalDistributionTest {
    /**
     * Creating a {@link NormalDistribution} with basic parameters
     */
    @Test
    public void testCreate() {
        val probability = NormalDistribution.<Character>builder()
                .mean(10.0)
                .standardDeviation(10.0)
                .build();

        assertThat(probability, is(not(nullValue())));
    }

    /**
     * Creating with null mean fails
     */
    @Test(expected = NullPointerException.class)
    public void testCreate_fail_null_mean() {
        NormalDistribution.<Character>builder()
                .standardDeviation(10.0)
                .build();
    }

    /**
     * Creating with null standardDeviation fails
     */
    @Test(expected = NullPointerException.class)
    public void testCreate_fail_null_standardDeviation() {
        NormalDistribution.<Character>builder()
                .mean(10.0)
                .build();
    }
}
