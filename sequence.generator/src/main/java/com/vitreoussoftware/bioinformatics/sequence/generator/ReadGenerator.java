package com.vitreoussoftware.bioinformatics.sequence.generator;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence;
import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.Distribution;
import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.discrete.UniformDistribution;
import lombok.Builder;
import lombok.NonNull;
import lombok.val;

import java.util.LinkedList;

/**
 * Generate {@link Sequence} reads from some sample {@link Sequence} based on user configuration.
 *
 * Created by John on 10/25/2016.
 */
public class ReadGenerator implements Distribution<Sequence> {
    /**
     * The maximum number of times we will try to generate a valid sample from the provided configuration before erroring out.
     */
    private final static int MAX_RETRIES = 1000;

    /**
     * The {@link Sequence} to sample reads from
     */
    private final Sequence source;

    /**
     * This {@link Distribution} is sampled to determine the length of the read
     */
    private final Distribution<Integer> lengthDistribution;

    /**
     * This {@link Distribution} is sampled to determine where to start the read
     * By default this is initialized as a {@link UniformDistribution} from 0 to sequence.length
     */
    private final Distribution<Integer> startPointDistribution;


    @Builder
    private ReadGenerator(
            @NonNull final Sequence source,
            @NonNull final Distribution<Integer> lengthDistribution,
            @NonNull final Distribution<Integer> startPointDistribution) {
        this.source = source;
        this.lengthDistribution = lengthDistribution;
        this.startPointDistribution = startPointDistribution;
    }

    @Override
    public Sequence sample() {
        int length = 0;
        int startPoint = 0;
        int attempts = 0;

        while (attempts < MAX_RETRIES &&
                (length == 0 || length + startPoint > this.source.length())) {
            length = lengthDistribution.sample();
            startPoint = startPointDistribution.sample();
            attempts++;
        }

        if (attempts == MAX_RETRIES)
            throw new IllegalArgumentException("Unable to generate a sample read. Please correct your source and/or distributions.");

        val list = new LinkedList<BasePair>();

        for (int i = startPoint; i < startPoint + length; i++) {
            list.add(this.source.get(i));
        }

        return BasicSequence.create(list).get();
    }

    public static class ReadGeneratorBuilder {
        private Sequence source;
        private Distribution<Integer> startPointDistribution;

        public ReadGenerator build() {
            // If no startPointDistirbution is provided default to a uniform distribution
            if (this.startPointDistribution == null) {
                this.startPointDistribution = UniformDistribution.builder()
                        .lower(0.0)
                        .upper((double) this.source.length())
                        .build();
            }

            return new ReadGenerator(source, lengthDistribution, startPointDistribution);
        }
    }
}
