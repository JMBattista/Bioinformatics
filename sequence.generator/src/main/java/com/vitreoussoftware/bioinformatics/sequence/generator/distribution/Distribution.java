package com.vitreoussoftware.bioinformatics.sequence.generator.distribution;

/**
 * A Distribution for the given type {@see T}
 *
 * Can be used as a functional interface by supplying a method reference that returns {@see T} and takes no parameters.
 *
 * Created by John on 10/19/2016.
 */
public interface Distribution<T> {
    /**
     * Generates a random value sampled from this distribution
     * @return The value of {@see T} sampled
     */
    T sample();
}
