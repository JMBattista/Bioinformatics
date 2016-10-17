package com.vitreoussoftware.bioinformatics.sequence.generator.distribution.discrete;

import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.DiscreteDistribution;
import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.EnumeratedDistribution;
import lombok.Builder;
import lombok.NonNull;

import java.math.BigInteger;

/**
 * Created by John on 10/20/2016.
 */
public class PoissonDistribution implements DiscreteDistribution<Integer> {
    private final org.apache.commons.math3.distribution.PoissonDistribution distribution;

    /**
     * Create an instance of {@link EnumeratedDistribution} that can be used to generate outputs
     * @param mean Poisson mean
     * @param epsilon Convergence factor
     *
     * @throws NullPointerException if the arguments are null or unset
     */
    @Builder
    public PoissonDistribution(@NonNull Double mean, @NonNull Double epsilon) {
        this.distribution = new org.apache.commons.math3.distribution.PoissonDistribution(mean, epsilon);
    }

    @Override
    public Integer sample() {
        return distribution.sample();
    }
}
