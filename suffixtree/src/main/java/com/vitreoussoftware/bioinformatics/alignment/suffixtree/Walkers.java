package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import com.vitreoussoftware.bioinformatics.alignment.Position;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Provided Walk implementations, see the CountedWalk class to get information on how the walk is used
 * Created by John on 12/19/13.
 */
public class Walkers {

    /**
     * This class should never be instantiated, it is just a holder for a set of pre-provided walkers
     */
    private Walkers() {}

    /**
     * Walks all nodes and keeps a count of the number that were visited.
     * This does not correspond to the underlying structure of the SuffixTree. So two implementations that store a full
     * sized SuffixTree should have the same 'count' of virtual nodes.
     * Caution: This visits every node, which can make it extremely slow!
     * @return the number of 'nodes' encountered on the walk.
     */
    public static Walk<Integer, Integer> size() {
        return new Walk<Integer, Integer>() {
            AtomicInteger size = new AtomicInteger();

            @Override
            public boolean isFinished(Integer metadata) {
                return false;
            }

            @Override
            public Integer initialValue() {
                return 0;
            }

            @Override
            public Integer getResult() {
                return size.get();
            }

            @Override
            public Optional<Integer> visit(BasePair basePair, Collection<Position> positions, Integer metadata) {
                size.incrementAndGet();
                return Optional.of(0);
            }
        };
    }

    /**
     * Walks all nodes in the SuffixTree to find the length of the longest path.
     * This does not rely on the underlying structure of the SuffixTree, so two implementations should return the same
     * value for depth, unless the SuffixTree is bounded somehow.
     * Caution: This visits every node, which can make it extremely slow!
     * @return The depth of the longest chain of elements in the SuffixTree
     */
    public static Walk<Integer, Integer> depth() {
        return new Walk<Integer, Integer>() {
            int depth = initialValue();

            @Override
            public boolean isFinished(Integer metadata) {
                return false;
            }

            @Override
            public Integer initialValue() {
                return 0;
            }

            @Override
            public Integer getResult() {
                // Needs to be syncronized to make sure we have the right value for depth
                synchronized (this) {
                    return depth;
                }
            }

            @Override
            public Optional<Integer> visit(BasePair basePair, Collection<Position> positions, Integer metadata) {
                int result = metadata + 1;

                // No need to enter syncronized block unless there is a chance it will be changed
                if (result > depth) {
                    synchronized (this) {
                        depth = Math.max(depth, result);
                    }
                }

                return Optional.of(result);
            }
        };
    }

    /**
     * Check to see if the SuffixTree contains an exact match for the given sequence
     * @param sequence
     * @return true if the exact match was found, false if it was not.
     */
    public static Walk<Integer, Boolean> contains(Sequence sequence) {
        return new Walk<Integer, Boolean>() {
            boolean result = false;
            @Override
            public boolean isFinished(Integer metadata) {
                return (result = metadata.equals(sequence.length()));
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
            public Optional<Integer> visit(BasePair basePair, Collection<Position> positions, Integer metadata) {
                if (sequence.get(metadata).equals(basePair))
                    return Optional.of(metadata +1);
                else
                    return Optional.empty();
            }

            @Override
            public int compare(Integer a, Integer b) {
                return -1 * (a - b);
            }
        };
    }

    /**
     * For the given sequence finds the alignment with the minimum distance.
     * Distance function is 1 for each mismatched BasePair and does not allow gaps.
     * @param sequence The target sequence we are trying to align
     * @return The distance for the alignment and the list of sequenced position pairs that match the alignment.
     */
    public static Walk<Triplet<Integer, Integer, Collection<Position>>,
                    Pair<Integer, Collection<Position>>> distance(Sequence sequence)
    {
        return new WalkWrapper<>(distance(sequence, 0), x -> x.get());
    }

    /**
     * For the given sequence finds the alignment with the minimum distance, as long as it is less than or equal to maxDistance
     * Distance function is 1 for each mismatched BasePair and does not allow gaps.
     * @param sequence The target sequence we are trying to align
     * @Param maxDistance The maximum distance a sequence can be before being rejected
     * @return The distance for the alignment and the list of sequenced position pairs that match the alignment.
     */
    public static Walk<Triplet<Integer, Integer, Collection<Position>>,
            Optional<Pair<Integer, Collection<Position>>>> distance(Sequence sequence, int maxDistance)
    {
        return new Walk<Triplet<Integer, Integer, Collection<Position>>,
                Optional<Pair<Integer, Collection<Position>>>>() {
            Optional<Pair<Integer, Collection<Position>>> result = Optional.empty();

            @Override
            public Triplet<Integer, Integer, Collection<Position>> initialValue() {
                // Position, Distance, Parent Positions for prev
                return Triplet.with(0, 0, null);
            }

            @Override
            public Optional<Pair<Integer,Collection<Position>>> getResult() {
                return result;
            }

            @Override
            public Optional<Triplet<Integer, Integer, Collection<Position>>> visit(BasePair basePair, Collection<Position> positions, Triplet<Integer, Integer, Collection<Position>> metadata) {
                final int position = metadata.getValue0();
                final int distance = getDistance(metadata.getValue1(), basePair, sequence.get(position));

                // A max distance of 0 means infinity
                if (maxDistance != 0 && distance > maxDistance)
                    return Optional.empty();

                // Stop considering branches that are more costly than current minimum
                if (result.isPresent() && distance > result.get().getValue0())
                    return Optional.empty();

                if (position < sequence.length() - 1) {
                    return Optional.of(Triplet.with(position +1, distance, positions));
                }
                else {
                    // If there is no result, or the result is of a higher distance use the current value
                    if (!result.isPresent() || (result.isPresent() && result.get().getValue0() > distance)) {
                            result = Optional.of(Pair.<Integer, Collection<Position>>with(distance, new HashSet<>(positions)));
                    }
                        // If the result distance matches current distance add the positions
                    else if (result.get().getValue0().intValue() == distance) {
                            result.get().getValue1().addAll(positions);
                    }

                    // We don't need to continue the walk from this point, we've reached the end!
                    return Optional.empty();
                }
            }

            private int getDistance(int initialDistance, BasePair value, BasePair target) {
                return value.equals(target) ? initialDistance : initialDistance + 1;
            }

            @Override
            public boolean isFinished(Triplet<Integer, Integer, Collection<Position>> metadata) {
                // We only know we're finished if we found an exact match or we've exhausted all search paths
                return result.isPresent() && result.get().getValue0().intValue() == 0;
            }

            @Override
            public int compare(Triplet<Integer, Integer, Collection<Position>> a, Triplet<Integer, Integer, Collection<Position>> b) {
                // Value 1 is distance
                return a.getValue1() - b.getValue1();
            }
        };
    }
}
