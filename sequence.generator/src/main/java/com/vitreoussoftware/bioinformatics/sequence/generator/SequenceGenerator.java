package com.vitreoussoftware.bioinformatics.sequence.generator;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence;
import com.vitreoussoftware.bioinformatics.sequence.encoding.EncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.Distribution;
import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.EnumeratedDistribution;
import lombok.NonNull;
import lombok.Builder;
import lombok.val;

/**
 * Generate sample {@link Sequence} instances based on user configuration values
 *
 *
 * Created by John on 10/16/2016.
 */
@Builder
public class SequenceGenerator implements Distribution<Sequence> {
    /**
     * The maximum number of attempts to get a random sample that fits our criterion.
     */
    private static final int MAX_ATTEMPTS = 1000;

    /**
     * The basePairDistribution of lengths for the generated {@link Sequence}
     */
    @NonNull
    private final Distribution<Integer> lengthDistribution;

    /**
     * The {@link EnumeratedDistribution} of appearance for the BasePairs to use when generating this {@link Sequence}
     */
    @NonNull
    private final EnumeratedDistribution basePairDistribution;

    /**
     * The {@link EncodingScheme} used in constructing the sequences
     */
    @NonNull
    private final EncodingScheme encodingScheme;

    /**
     * Partial implementation for {@link Builder} to support default values
     */
    @SuppressWarnings("unused")
    public static class SequenceGeneratorBuilder {
    }

    /**
     * Get the sample {@link Sequence}
     * @return a generated {@link Sequence}
     */
    public Sequence sample() {
        val length = getLength();

        val sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(basePairDistribution.sample());
        }
        return BasicSequence.create(sb.toString(), encodingScheme).get();
    }

    private int getLength() {
        int length = 0;
        int counter = 0;

        while(length == 0 && counter < MAX_ATTEMPTS) {
            length = lengthDistribution.sample();
            counter ++;
        }

        if (length == 0)
            throw new IllegalArgumentException("Unable to generate a non zero sequence length. Please correct your lengthDistribution.");

        return length;
    }
}
