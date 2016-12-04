package com.vitreoussoftware.bioinformatics.sequence.generator.distribution.continuous;

import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.ContinuousDistribution;
import lombok.Builder;
import lombok.NonNull;

/**
 * Normal Distribution
 * Created by John on 10/21/2016.
 */
public class NormalDistribution implements ContinuousDistribution<Double> {
    private final org.apache.commons.math3.distribution.NormalDistribution distribution;

    /**
     * Create an instance of {@link NormalDistribution} that can be used to generate outputs
     * @param mean Average value of the distribution
     * @param standardDeviation Standard Deviation of the value
     *
     * @throws NullPointerException if the arguments are null or unset
     */
    @Builder
    public NormalDistribution(@NonNull final Double mean, @NonNull final Double standardDeviation) {
        this.distribution = new org.apache.commons.math3.distribution.NormalDistribution(mean, standardDeviation);
    }

    @Override
    public Double sample() {
        return distribution.sample();
    }
}
