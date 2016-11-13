package com.vitreoussoftware.bioinformatics.sequence.generator.distribution;

/**
 * A Distribution for the given type {@see T} that extends {@see Number} and can take on continuous values.
 * {@link ContinuousDistribution}'s should primarily be used with Real number types
 *
 * If you have discrete values which are not Whole numbers you may want to look at {@see EnumeratedDistribution}
 * If you have discrete values which are Whole numbers you may want to look at {@see DiscreteDistribution}
 *
 * Created by John on 10/20/2016.
 */
public interface ContinuousDistribution<T extends Number> extends Distribution<T> {

}
