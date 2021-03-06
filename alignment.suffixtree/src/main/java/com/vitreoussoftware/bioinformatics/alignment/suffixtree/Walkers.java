package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import com.vitreoussoftware.bioinformatics.alignment.Alignment;
import com.vitreoussoftware.bioinformatics.alignment.Position;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import org.javatuples.Triplet;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Provided Walk implementations, see the CountedWalk class to get information on how the walk is used
 * Created by John on 12/19/13.
 */
public final class Walkers {

    /**
     * This class should never be instantiated, it is just a holder for a set of pre-provided walkers
     */
    private Walkers() {
    }

    /**
     * Walks all nodes and keeps a count of the number that were visited.
     * This does not correspond to the underlying structure of the SuffixTree. So two implementations that store a full
     * sized SuffixTree should have the same 'count' of virtual nodes.
     * Caution: This visits every node, which can make it extremely slow!
     *
     * @return the number of 'nodes' encountered on the walk.
     */
    public static Walk<Integer, Integer> size() {
        return new Walk<Integer, Integer>() {
            final AtomicInteger size = new AtomicInteger();

            @Override
            public boolean isFinished(final Integer metadata) {
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
            public Optional<Integer> visit(final BasePair basePair, final Collection<Position> positions, final Integer metadata) {
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
     *
     * @return The depth of the longest chain of elements in the SuffixTree
     */
    public static Walk<Integer, Integer> depth() {
        return new Walk<Integer, Integer>() {
            int depth = initialValue();

            @Override
            public boolean isFinished(final Integer metadata) {
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
            public Optional<Integer> visit(final BasePair basePair, final Collection<Position> positions, final Integer metadata) {
                final int result = metadata + 1;

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
     * Check to see if the SuffixTree contained an exact match for the given pattern
     *
     * @param pattern
     * @return true if the exact match was found, false if it was not.
     */
    public static Walk<Integer, Boolean> contains(final Sequence pattern) {
        return new Walk<Integer, Boolean>() {
            boolean result = false;

            @Override
            public boolean isFinished(final Integer metadata) {
                result = metadata.equals(pattern.length());

                return result;
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
            public Optional<Integer> visit(final BasePair basePair, final Collection<Position> positions, final Integer metadata) {
                if (pattern.get(metadata).equals(basePair))
                    return Optional.of(metadata + 1);
                else
                    return Optional.empty();
            }

            @Override
            public int compare(final Integer a, final Integer b) {
                return -1 * (a - b);
            }
        };
    }

    /**
     * For the given pattern finds the alignment with the minimum shortestDistance.
     * Distance function is 1 for each mismatched BasePair and does not allow gaps.
     *
     * @param pattern The target pattern we are trying to align
     * @return The shortestDistance for the alignment and the list of sequenced position pairs that match the alignment.
     */
    public static Walk<Triplet<Integer, Integer, Collection<Position>>, Collection<Alignment>> shortestDistances(final Sequence pattern) {
        return new WalkWrapper<>(shortestDistances(pattern, 0), Optional::get);
    }

    /**
     * For the given pattern finds the alignment with the minimum shortestDistance, as long as it is less than or equal to maxDistance
     * Distance function is 1 for each mismatched BasePair and does not allow gaps.
     *
     * @param pattern The target pattern we are trying to align
     * @return The shortestDistance for the alignment and the list of sequenced position pairs that match the alignment.
     * @Param maxDistance The maximum shortestDistance a pattern can be before being rejected
     */
    public static Walk<Triplet<Integer, Integer, Collection<Position>>,
            Optional<Collection<Alignment>>> shortestDistances(final Sequence pattern, final int maxDistance) {
        return new Walk<Triplet<Integer, Integer, Collection<Position>>,
                Optional<Collection<Alignment>>>() {
            Optional<Collection<Alignment>> result = Optional.empty();

            @Override
            public Triplet<Integer, Integer, Collection<Position>> initialValue() {
                // Alignment, Distance, Parent Positions for prev
                return Triplet.with(0, 0, null);
            }

            @Override
            public Optional<Collection<Alignment>> getResult() {
                return result;
            }

            @Override
            public Optional<Triplet<Integer, Integer, Collection<Position>>> visit(final BasePair basePair, final Collection<Position> positions, final Triplet<Integer, Integer, Collection<Position>> metadata) {
                final int position = metadata.getValue0();
                final int distance = getDistance(metadata.getValue1(), basePair, pattern.get(position));

                // A max shortestDistance of 0 means infinity
                if (maxDistance != 0 && distance > maxDistance)
                    return Optional.empty();

                // Stop considering branches that are more costly than current minimum
                final Optional<Integer> resultDistance = result.flatMap(collection -> collection.stream().findFirst()).map(Alignment::getDistance);

                if (resultDistance.map(d -> distance > d).orElse(false))
                    return Optional.empty();

                if (position < pattern.length() - 1) {
                    return Optional.of(Triplet.with(position + 1, distance, positions));
                } else {
                    // If there is no result, or the result is of a higher shortestDistance use the current value
                    if (resultDistance.map(d -> d > distance).orElse(true)) {
                        result = Optional.of(positions.stream().map(p -> Alignment.with(p.getText(), pattern, p.getPosition(), distance))
                                .collect(Collectors.toCollection(HashSet::new)));
                    }
                    // If the result shortestDistance matches current shortestDistance add the positions
                    else if (resultDistance.get() == distance) {
                        final List<Alignment> alignments = positions.stream().map(p -> Alignment.with(p.getText(), pattern, p.getPosition(), distance))
                                .collect(Collectors.toList());
                        result.get().addAll(alignments);
                    }

                    // We don't need to continue the walk from this point, we've reached the end!
                    return Optional.empty();
                }
            }

            private int getDistance(final int initialDistance, final BasePair value, final BasePair target) {
                return value.equals(target) ? initialDistance : initialDistance + 1;
            }

            @Override
            public boolean isFinished(final Triplet<Integer, Integer, Collection<Position>> metadata) {
                // We only know we're finished if we found an exact match or we've exhausted all search paths
                return result.isPresent() && result.get().stream().findFirst().get().getDistance() == 0;
            }

            @Override
            public int compare(final Triplet<Integer, Integer, Collection<Position>> a, final Triplet<Integer, Integer, Collection<Position>> b) {
                // Value 1 is shortestDistance
                return a.getValue1() - b.getValue1();
            }
        };
    }
}
