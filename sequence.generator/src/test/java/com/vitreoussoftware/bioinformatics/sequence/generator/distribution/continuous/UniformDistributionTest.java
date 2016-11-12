package com.vitreoussoftware.bioinformatics.sequence.generator.distribution.continuous;

import lombok.val;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Test the {@link UniformDistribution}
 * Created by John on 10/16/2016.
 */
public class UniformDistributionTest {
    /**
     * Creating a {@link UniformDistribution} with basic parameters
     */
    @Test
    public void testCreate() {
        val probability = UniformDistribution.<Character>builder()
                .lower(-10.0)
                .upper(10.0)
                .build();

        assertThat(probability, is(not(nullValue())));
    }

    /**
     * Creating with null lower fails
     */
    @Test(expected = NullPointerException.class)
    public void testCreateFail_null_lower() {
        UniformDistribution.<Character>builder()
                .upper(10.0)
                .build();
    }

    /**
     * Creating with null upper fails
     */
    @Test(expected = NullPointerException.class)
    public void testCreateFailNull_upper() {
        UniformDistribution.<Character>builder()
                .lower(10.0)
                .build();
    }

    /**
     * Creating with identical upper and lower values fails
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateFailIdentical() {
        UniformDistribution.<Character>builder()
                .lower(10.0)
                .upper(10.0)
                .build();
    }
}
