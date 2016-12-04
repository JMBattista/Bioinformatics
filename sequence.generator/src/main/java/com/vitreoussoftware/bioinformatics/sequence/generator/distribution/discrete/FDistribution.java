package com.vitreoussoftware.bioinformatics.sequence.generator.distribution.discrete;

import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.DiscreteDistribution;
import lombok.NonNull;
import lombok.Builder;

/**
 * Discretized F Distribution
 * Created by John on 10/20/2016.
 */
public class FDistribution implements DiscreteDistribution<Integer> {
    private final com.vitreoussoftware.bioinformatics.sequence.generator.distribution.continuous.FDistribution distribution;

    /**
     * Create an instance of {@link FDistribution} that can be used to generate outputs
     * @param numeratorDegreesOfFreedom Numberator degrees of freedom
     * @param denominatorDegreesOfFreedom Denominator degrees of freedom
     *
     * @throws NullPointerException if the arguments are null or unset
     */
    @Builder
    public FDistribution(@NonNull final Double numeratorDegreesOfFreedom, @NonNull final Double denominatorDegreesOfFreedom) {
        this.distribution = com.vitreoussoftware.bioinformatics.sequence.generator.distribution.continuous.FDistribution
                .builder()
                .numeratorDegreesOfFreedom(numeratorDegreesOfFreedom)
                .denominatorDegreesOfFreedom(denominatorDegreesOfFreedom)
                .build();
    }

    @Override
    public Integer sample() {
        // Discreteized distribution so we can use it for things like lengths
        return distribution.sample().intValue();
    }
}
