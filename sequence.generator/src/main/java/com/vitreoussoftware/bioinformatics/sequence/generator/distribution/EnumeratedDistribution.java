package com.vitreoussoftware.bioinformatics.sequence.generator.distribution;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import lombok.NonNull;
import lombok.Singular;
import lombok.val;
import org.apache.commons.math3.util.Pair;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Map;

/**
 * A Distribution for the given type {@see T} that and can take on discrete values.
 * Each discrete value is assigned its own probability for sampling.
 * {@link EnumeratedDistribution}'s should primarily be used with non Number types and restricted sets of numbers.
 * <p>
 * If you have discrete values which are Whole numbers you may want to look at {@see DiscreteDistribution}
 * If you have continues values you may want to look at {@see ContinuousDistribution}
 * <p>
 * Created by John on 10/16/2016.
 */
public class EnumeratedDistribution<T> implements Distribution<T> {
    private final org.apache.commons.math3.distribution.EnumeratedDistribution<T> distribution;

    /**
     * Create an instance of {@link EnumeratedDistribution} that can be used to generate outputs
     *
     * @param probabilities          Probabilities for the elements of the distribution
     * @param normalizeProbabilities EnumeratedDistribution values for BP values we want to accept
     * @throws NullPointerException     If the encoding scheme is not set
     * @throws IllegalArgumentException If the probabilities values are not set
     */
    EnumeratedDistribution(
            @Singular @NonNull final ImmutableMap<T, Double> probabilities,
            final boolean normalizeProbabilities) {
        Preconditions.checkArgument(probabilities.size() > 0, "Cannot create an empty EnumeratedDistribution");
        val list = new LinkedList<Pair<T, Double>>();

        // if the flag is set then we allow the underlying distribution to automatically normalize the probabilities.
        // if the flag is not set then we check that the probabilities sum to 1 or throw an exception
        if (!normalizeProbabilities) {
            val sum = new BigDecimal(probabilities.values().stream().mapToDouble(x -> x).sum()).setScale(10, BigDecimal.ROUND_HALF_UP);
            if (sum.compareTo(BigDecimal.ONE) != 0)
                throw new IllegalArgumentException("Normalize probabilities was not enabled and sum of probabilitites was not 1, was " + sum.toString());
        }

        probabilities.entrySet().forEach(entry -> list.add(new Pair<>(entry.getKey(), entry.getValue())));

        distribution = new org.apache.commons.math3.distribution.EnumeratedDistribution<>(list);
    }

    public static <T> DiscreteDistributionBuilder<T> builder() {
        return new DiscreteDistributionBuilder<T>();
    }

    /**
     * The sample randomly selected {@link BasePair} character based on the given Liklihoods
     *
     * @return the {@link BasePair} character
     */
    public T sample() {
        return distribution.sample();
    }

    /**
     * This {@link Distribution} builder has been de-lomboked to avoid issues with the IntelliJ lombok plugin where
     * it will generate invalid builders when a generic class inherits from another generic class.
     * https://github.com/mplushnikov/lombok-intellij-plugin/issues/127
     */
    public static class DiscreteDistributionBuilder<T> {
        private ImmutableMap.Builder<T, Double> probabilities;
        private boolean normalizeProbabilities;

        DiscreteDistributionBuilder() {
        }

        public DiscreteDistributionBuilder<T> probability(final T key, final Double value) {
            if (this.probabilities == null) this.probabilities = ImmutableMap.builder();
            this.probabilities.put(key, value);
            return this;
        }

        public DiscreteDistributionBuilder<T> probabilities(final Map<? extends T, ? extends Double> probabilities) {
            if (this.probabilities == null) this.probabilities = ImmutableMap.builder();
            this.probabilities.putAll(probabilities);
            return this;
        }

        public DiscreteDistributionBuilder<T> clearProbabilities() {
            this.probabilities = null;

            return this;
        }

        public DiscreteDistributionBuilder<T> normalizeProbabilities(final boolean normalizeProbabilities) {
            this.normalizeProbabilities = normalizeProbabilities;
            return this;
        }

        public EnumeratedDistribution<T> build() {
            final ImmutableMap<T, Double> probabilities = this.probabilities == null ? ImmutableMap.of() : this.probabilities.build();

            return new EnumeratedDistribution<>(probabilities, normalizeProbabilities);
        }

        public String toString() {
            return "com.vitreoussoftware.bioinformatics.sequence.generator.DiscreteDistributionBuilder(probabilities=" + this.probabilities + ", normalizeProbabilities=" + this.normalizeProbabilities + ")";
        }
    }
}
