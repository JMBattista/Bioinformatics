package com.vitreoussoftware.bioinformatics.sequence.generator.distribution;

/**
 * A Distribution for the given type {@see T} that extends {@see Number} and can take on discrete values.
 * {@link DiscreteDistribution}'s should primarily be used with Whole number types
 * <p>
 * If you have discrete values which are not Whole numbers you may want to look at {@see EnumeratedDistribution}
 * If you have continues values you may want to look at {@see ContinuousDistribution}
 * <p>
 * Created by John on 10/20/2016.
 */
public interface DiscreteDistribution<T extends Number> extends Distribution<T> {
}
