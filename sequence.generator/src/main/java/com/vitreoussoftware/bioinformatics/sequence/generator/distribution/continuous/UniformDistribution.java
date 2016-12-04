package com.vitreoussoftware.bioinformatics.sequence.generator.distribution.continuous;

import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.ContinuousDistribution;
import lombok.Builder;
import lombok.NonNull;

/**
 * Uniform Distribution across reals within a range
 * Created by John on 10/21/2016.
 */
public class UniformDistribution implements ContinuousDistribution<Double> {
    private final org.apache.commons.math3.distribution.UniformRealDistribution distribution;

    /**
     * Create an instance of {@link UniformDistribution} that can be used to generate outputs
     * @param lower lower bound of values returned by this distribution [inclusive]
     * @param upper upper boudn of values returned by this distribution (exclusive)
     *
     * @throws NullPointerException if the arguments are null or unset
     * @throws IllegalArgumentException if the lower bound is >= upper bound
     */
    @Builder
    public UniformDistribution(@NonNull final Double lower, @NonNull final Double upper) {
        this.distribution = new org.apache.commons.math3.distribution.UniformRealDistribution(lower, upper);
    }

    @Override
    public Double sample() {
        return distribution.sample();
    }
}
