package com.vitreoussoftware.bioinformatics.sequence.generator.distribution.discrete;

import lombok.val;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Test the {@link PoissonDistribution}
 * Created by John on 10/16/2016.
 */
public class PoissonDistributionTest {
    /**
     * Creating a {@link PoissonDistribution} with basic parameters
     */
    @Test
    public void testCreate() {
        val probability = PoissonDistribution.<Character>builder()
                .mean(10.0)
                .epsilon(10.0)
                .build();

        assertThat(probability, is(not(nullValue())));
    }

    /**
     * Creating with null mean fails
     */
    @Test(expected = NullPointerException.class)
    public void testCreateFail_null_mean() {
        PoissonDistribution.<Character>builder()
                .epsilon(10.0)
                .build();
    }

    /**
     * Creating with null epsilon fails
     */
    @Test(expected = NullPointerException.class)
    public void testCreateFailNull_epsilon() {
        PoissonDistribution.<Character>builder()
                .mean(10.0)
                .build();
    }
}
