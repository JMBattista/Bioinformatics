package com.vitreoussoftware.bioinformatics.sequence.generator.distribution.continuous;

import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.ContinuousDistribution;
import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.DiscreteDistribution;
import lombok.Builder;
import lombok.NonNull;

/**
 * F Distribution
 * Created by John on 10/20/2016.
 */
public class FDistribution implements ContinuousDistribution<Double> {
    private final org.apache.commons.math3.distribution.FDistribution distribution;

    /**
     * Create an instance of {@link FDistribution} that can be used to generate outputs
     * @param numeratorDegreesOfFreedom Numberator degrees of freedom
     * @param denominatorDegreesOfFreedom Denominator degrees of freedom
     *
     * @throws NullPointerException if the arguments are null or unset
     */
    @Builder
    public FDistribution(@NonNull Double numeratorDegreesOfFreedom, @NonNull Double denominatorDegreesOfFreedom) {
        this.distribution = new org.apache.commons.math3.distribution.FDistribution(numeratorDegreesOfFreedom, denominatorDegreesOfFreedom);
    }

    @Override
    public Double sample() {
        return distribution.sample();
    }
}
