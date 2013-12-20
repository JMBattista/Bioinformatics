package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;

import java.util.Optional;

/**
 * Provided Walk implementations, see the CountedWalk class to get information on how the walk is used
 * Created by John on 12/19/13.
 */
public class Walkers {

    /**
     * This class should never be instantiated, it is just a holder for a set of pre-provided walkers
     */
    private Walkers() {}

    public static Walk<Integer, Boolean> contains(Sequence sequence) {
        return new Walk<Integer, Boolean>() {
            boolean result = false;
            @Override
            public boolean isFinished(Integer value) {
                return (result = value.equals(sequence.length()));
            }

            @Override
            public Integer initialValue() {
                return 0;
            }

            @Override
            public Boolean getResult() {
                return result;
            }

            @Override
            public Optional<Integer> visit(BasePair basePair, Integer value) {
                if (sequence.get(value).equals(basePair))
                    return Optional.of(value +1);
                else
                    return Optional.empty();
            }

            @Override
            public int compare(Integer a, Integer b) {
                return -1 * (a - b);
            }
        };
    }
}
