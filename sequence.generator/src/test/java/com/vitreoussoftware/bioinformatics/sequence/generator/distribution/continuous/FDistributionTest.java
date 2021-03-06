package com.vitreoussoftware.bioinformatics.sequence.generator.distribution.continuous;

import lombok.val;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Test the {@link FDistribution}
 * Created by John on 10/16/2016.
 */
public class FDistributionTest {
    /**
     * Creating a {@link FDistribution} with basic parameters
     */
    @Test
    public void testCreate() {
        val probability = FDistribution.<Character>builder()
                .denominatorDegreesOfFreedom(10.0)
                .numeratorDegreesOfFreedom(10.0)
                .build();

        assertThat(probability, is(not(nullValue())));
    }

    /**
     * Creating with null denominator fails
     */
    @Test(expected = NullPointerException.class)
    public void testCreateFailNullDenominatorDegreesOfFreedom() {
        FDistribution.<Character>builder()
                .numeratorDegreesOfFreedom(10.0)
                .build();
    }

    /**
     * Creating with null denominator fails
     */
    @Test(expected = NullPointerException.class)
    public void testCreateFailNullNumeratorDegreesOfFreedom() {
        FDistribution.<Character>builder()
                .denominatorDegreesOfFreedom(10.0)
                .build();
    }
}
