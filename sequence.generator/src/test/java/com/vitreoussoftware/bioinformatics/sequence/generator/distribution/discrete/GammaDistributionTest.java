package com.vitreoussoftware.bioinformatics.sequence.generator.distribution.discrete;

import lombok.val;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Test the {@link GammaDistribution}
 * Created by John on 10/16/2016.
 */
public class GammaDistributionTest {
    /**
     * Creating a {@link GammaDistribution} with basic parameters
     */
    @Test
    public void testCreate() {
        val probability = GammaDistribution.<Character>builder()
                .scale(10.0)
                .shape(10.0)
                .build();

        assertThat(probability, is(not(nullValue())));
    }

    /**
     * Creating with null scale fails
     */
    @Test(expected = NullPointerException.class)
    public void testCreateFailNullScale() {
        GammaDistribution.<Character>builder()
                .shape(10.0)
                .build();
    }

    /**
     * Creating with null shape fails
     */
    @Test(expected = NullPointerException.class)
    public void testCreateFailNullShape() {
        GammaDistribution.<Character>builder()
                .scale(10.0)
                .build();
    }
}
