package com.vitreoussoftware.bioinformatics.sequence.generator.distribution.discrete;

import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.DiscreteDistribution;
import lombok.Builder;
import lombok.NonNull;

/**
 * Uniform Distribution across whole numbers within a range
 * Created by John on 10/20/2016.
 */
public class UniformDistribution implements DiscreteDistribution<Integer> {
    private final com.vitreoussoftware.bioinformatics.sequence.generator.distribution.continuous.UniformDistribution distribution;

    /**
     * Create an instance of {@link UniformDistribution} that can be used to generate outputs
     * @param lower lower bound of values returned by this distribution [inclusive]
     * @param upper upper boudn of values returned by this distribution (exclusive)
     *
     * @throws NullPointerException if the arguments are null or unset
     * @throws IllegalArgumentException if the lower bound is >= upper bound
     */
    @Builder
    public UniformDistribution(@NonNull Double lower, @NonNull Double upper) {
        this.distribution = com.vitreoussoftware.bioinformatics.sequence.generator.distribution.continuous.UniformDistribution
                .builder()
                .lower(lower)
                .upper(upper)
                .build();
    }

    @Override
    public Integer sample() {
        // Discreteized distribution so we can use it for things like lengths
        return distribution.sample().intValue();
    }
}
