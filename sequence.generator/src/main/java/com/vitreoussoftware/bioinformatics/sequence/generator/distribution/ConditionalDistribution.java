package com.vitreoussoftware.bioinformatics.sequence.generator.distribution;

/**
 * A Distribution for the given type {@see T} based on some variable {@see R}
 *
 * Can be used as a functional interface by supplying a method reference that returns {@see T} and takes {@see R} as a parameter
 *
 * Created by John on 10/29/2016.
 */
public interface ConditionalDistribution<T, R> {
    /**
     * Generates a random value sampled from this distribution
     * @param condition The condition {@see R} under which this value of {@see T} is sampled.
     *
     * @return The value of {@see T} sampled
     */
    T sample(R condition);
}
