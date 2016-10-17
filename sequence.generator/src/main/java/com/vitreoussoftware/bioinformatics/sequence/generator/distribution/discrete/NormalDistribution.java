package com.vitreoussoftware.bioinformatics.sequence.generator.distribution.discrete;

import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.DiscreteDistribution;
import lombok.Builder;
import lombok.NonNull;

/**
 * Discretized Normal Distribution
 * Created by John on 10/20/2016.
 */
public class NormalDistribution implements DiscreteDistribution<Integer> {
    private final com.vitreoussoftware.bioinformatics.sequence.generator.distribution.continuous.NormalDistribution distribution;

    /**
     * Create an instance of {@link NormalDistribution} that can be used to generate outputs
     * @param mean Average value of the distribution
     * @param standardDeviation Standard Deviation of the value
     *
     * @throws NullPointerException if the arguments are null or unset
     */
    @Builder
    public NormalDistribution(@NonNull Double mean, @NonNull Double standardDeviation) {
        this.distribution = com.vitreoussoftware.bioinformatics.sequence.generator.distribution.continuous.NormalDistribution
                .builder()
                .mean(mean)
                .standardDeviation(standardDeviation)
                .build();
    }

    @Override
    public Integer sample() {
        // Discreteized distribution so we can use it for things like lengths
        return distribution.sample().intValue();
    }
}
