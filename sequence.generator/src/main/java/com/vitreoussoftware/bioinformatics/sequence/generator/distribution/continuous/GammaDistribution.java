package com.vitreoussoftware.bioinformatics.sequence.generator.distribution.continuous;

import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.ContinuousDistribution;
import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.DiscreteDistribution;
import lombok.Builder;
import lombok.NonNull;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;

/**
 * Gamma Distribution
 * Created by John on 10/21/2016.
 */
public class GammaDistribution implements ContinuousDistribution<Double> {
    private final org.apache.commons.math3.distribution.GammaDistribution distribution;

    /**
     * Create an instance of {@link GammaDistribution} that can be used to generate outputs
     * @param shape
     * @param scale
     *
     * @throws NullPointerException if the arguments are null or unset
     */
    @Builder
    public GammaDistribution(@NonNull Double shape, @NonNull Double scale) {
        this.distribution = new org.apache.commons.math3.distribution.GammaDistribution(shape, scale);
    }

    @Override
    public Double sample() {
        return distribution.sample();
    }
}
