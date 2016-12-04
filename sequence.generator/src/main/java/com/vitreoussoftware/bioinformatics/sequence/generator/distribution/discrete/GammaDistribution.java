package com.vitreoussoftware.bioinformatics.sequence.generator.distribution.discrete;

import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.DiscreteDistribution;
import lombok.Builder;
import lombok.NonNull;

/**
 * Discretized Gamma Distribution
 * Created by John on 10/21/2016.
 */
public class GammaDistribution implements DiscreteDistribution<Integer> {
    private final com.vitreoussoftware.bioinformatics.sequence.generator.distribution.continuous.GammaDistribution distribution;

    /**
     * Create an instance of {@link GammaDistribution} that can be used to generate outputs
     * @param shape
     * @param scale
     *
     * @throws NullPointerException if the arguments are null or unset
     */
    @Builder
    public GammaDistribution(@NonNull final Double shape, @NonNull final Double scale) {
        this.distribution = com.vitreoussoftware.bioinformatics.sequence.generator.distribution.continuous.GammaDistribution
                .builder()
                .shape(shape)
                .scale(scale)
                .build();
    }

    @Override
    public Integer sample() {
        // Discreteized distribution so we can use it for things like lengths
        return distribution.sample().intValue();
    }
}
