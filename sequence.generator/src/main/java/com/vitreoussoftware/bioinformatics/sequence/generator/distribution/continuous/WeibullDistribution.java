package com.vitreoussoftware.bioinformatics.sequence.generator.distribution.continuous;

import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.ContinuousDistribution;
import lombok.Builder;
import lombok.NonNull;

/**
 * Weibull Distribution
 * Created by John on 10/20/2016.
 */
public class WeibullDistribution implements ContinuousDistribution<Double> {
    private final org.apache.commons.math3.distribution.WeibullDistribution distribution;

    /**
     * Create an instance of {@link WeibullDistribution} that can be used to generate outputs
     *
     * @param alpha Shape parameter
     * @param beta  Scale parameter
     * @throws NullPointerException if the arguments are null or unset
     */
    @Builder
    public WeibullDistribution(@NonNull final Double alpha, @NonNull final Double beta) {
        this.distribution = new org.apache.commons.math3.distribution.WeibullDistribution(alpha, beta);
    }

    @Override
    public Double sample() {
        return distribution.sample();
    }
}
