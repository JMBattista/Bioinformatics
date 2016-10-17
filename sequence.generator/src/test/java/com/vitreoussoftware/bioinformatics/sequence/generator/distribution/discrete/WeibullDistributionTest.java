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
 * Test the {@link WeibullDistribution}
 * Created by John on 10/16/2016.
 */
public class WeibullDistributionTest {
    /**
     * Creating a {@link WeibullDistribution} with basic parameters
     */
    @Test
    public void testCreate() {
        val probability = WeibullDistribution.<Character>builder()
                .alpha(10.0)
                .beta(10.0)
                .build();

        assertThat(probability, is(not(nullValue())));
    }

    /**
     * Creating with null alpha fails
     */
    @Test(expected = NullPointerException.class)
    public void testCreate_fail_null_alpha() {
        WeibullDistribution.<Character>builder()
                .beta(10.0)
                .build();
    }

    /**
     * Creating with null beta fails
     */
    @Test(expected = NullPointerException.class)
    public void testCreate_fail_null_beta() {
        WeibullDistribution.<Character>builder()
                .alpha(10.0)
                .build();
    }
}
