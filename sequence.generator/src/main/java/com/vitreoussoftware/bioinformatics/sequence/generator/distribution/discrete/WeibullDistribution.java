package com.vitreoussoftware.bioinformatics.sequence.generator.distribution.discrete;

import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.DiscreteDistribution;
import lombok.Builder;
import lombok.NonNull;

/**
 * Discretized Distribution
 * Created by John on 10/20/2016.
 */
public class WeibullDistribution implements DiscreteDistribution<Integer> {
    private final com.vitreoussoftware.bioinformatics.sequence.generator.distribution.continuous.WeibullDistribution distribution;

    /**
     * Create an instance of {@link WeibullDistribution} that can be used to generate outputs
     *
     * @param alpha Shape parameter
     * @param beta  Scale parameter
     * @throws NullPointerException if the arguments are null or unset
     */
    @Builder
    public WeibullDistribution(@NonNull final Double alpha, @NonNull final Double beta) {
        this.distribution = com.vitreoussoftware.bioinformatics.sequence.generator.distribution.continuous.WeibullDistribution
                .builder()
                .alpha(alpha)
                .beta(beta)
                .build();
    }

    @Override
    public Integer sample() {
        // Discreteized distribution so we can use it for things like lengths
        return distribution.sample().intValue();
    }
}
