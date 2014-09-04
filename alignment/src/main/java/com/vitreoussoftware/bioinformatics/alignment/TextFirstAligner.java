package com.vitreoussoftware.bioinformatics.alignment;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import org.javatuples.Pair;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Aligners allow users to determine the locations of a set of Patterns from within a set of Texts,
 * based on a matching function.
 *
 * A TextFirstAligner pre-processes the Text that is to be aligned against, then runs the Patterns
 * against that pre-processed Text.
 *
 * An Example would be creating a SuffixTree from the set of sequences making up GreenGenes and then
 * aligning a set of read data from an experiment.
 *
 * Created by John on 8/23/14.
 */
public interface TextFirstAligner {
    /**
     * Does the Text contain the pattern?
     * @param pattern the sequence to search for
     * @return if the substring exists in the tree
     */
    public boolean contains(Sequence pattern);

    /**
     * For all patterns which are contained in the Text
     * @param patterns collection of sequnces to search for in the Text
     * @return List of (Pattern, contained?) pairs.
     */
    default public Collection<Pair<Sequence, Boolean>> contains(SequenceCollection patterns) {
        return patterns.stream().map(pattern ->  Pair.with(pattern, this.contains(pattern))).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Find the set of alignments for the pattern of interest
     *
     * @param pattern to find alignments for
     * @return the set of parents, or empty list if no parents
     */
    public Collection<Alignment> getAlignments(Sequence pattern);

    /**
     * Find the set of alignments for the each pattern of interest
     *
     * @param patterns to find alignments for
     * @return List of (Pattern, Collection<Alignment) pairs.
     */
    default public Collection<Pair<Sequence, Collection<Alignment>>> getAlignments(SequenceCollection patterns) {
        return patterns.stream().map(pattern -> Pair.with(pattern, this.getAlignments(pattern))).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Adds a new text to the suffix tree
     * @param text the text to add
     */
    public void addSequence(final Sequence text);

    /**
     * Return the minimum shortestDistance from the given pattern to each of the sequences making up the Aligner
     * @param pattern the DNA pattern to check shortestDistance for
     * @return The collection of tuples showing the shortestDistance and the collection of sequences for that shortestDistance
     */
    default public Collection<Alignment> shortestDistance(Sequence pattern) {
        // Negative number used to indicate no distance cap
        return shortestDistance(pattern, -1);
    }

    /**
     * Return the minimum shortestDistance from the given patterns to each of the sequences making up the Aligner
     * @param patterns the DNA patterns to check shortestDistance for
     * @return The collection of tuples showing the shortestDistance and the collection of sequences for that shortestDistance
     */
    default public Collection<Pair<Sequence, Collection<Alignment>>> shortestDistance(SequenceCollection patterns) {
        // Negative number used to indicate no distance cap
        return shortestDistance(patterns, -1);
    }

    /**
     * Return the minimum shortestDistance from the given pattern to each of the sequences making up the Aligner
     * @param pattern the DNA pattern to check shortestDistance for
     * @param maxDistance the maximum shortestDistance before which we give up
     * @return The collection of tuples showing the shortestDistance and the collection of sequences for that shortestDistance
     */
    public Collection<Alignment> shortestDistance(Sequence pattern, int maxDistance);

    /**
     * Return the minimum shortestDistance from the given patterns to each of the sequences making up the Aligner
     * @param patterns the DNA patterns to check shortestDistance for
     * @param maxDistance the maximum shortestDistance before which we give up
     * @return The collection of tuples showing the shortestDistance and the collection of sequences for that shortestDistance
     */
    default public Collection<Pair<Sequence, Collection<Alignment>>> shortestDistance(SequenceCollection patterns, int maxDistance) {
        return patterns.stream().map(pattern -> Pair.with(pattern, shortestDistance(pattern, maxDistance))).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Return the set of distances for each parent Sequence given the target Sequences
     * @param pattern the target Sequence
     * @return the tuples of parent Sequences and the shortestDistance lists
     */
    default public Collection<Pair<Sequence, List<Integer>>> distances(Sequence pattern) {
        // Negative number used to indicate no distance cap
        return distances(pattern, -1);
    }

    /**
     * Return the set of distances for each parent Sequence given the target Sequences
     * @param patterns the target Sequence
     * @return the tuples of parent Sequences and the shortestDistance lists
     */
    default public Collection<Pair<Sequence, Collection<Pair<Sequence, List<Integer>>>>> distances(SequenceCollection patterns) {
        // Negative number used to indicate no distance cap
        return distances(patterns, -1);
    }

    /**
     * Return the set of distances for each text Sequence within the maximum for the given pattern Sequence
     * @param pattern the target Sequence
     * @param maxDistance the maximum shortestDistance that will be considered
     * @return the tuples of parent Sequences and the shortestDistance lists
     */
    public Collection<Pair<Sequence, List<Integer>>> distances(Sequence pattern, int maxDistance);

    /**
     * Return the set of distances for each parent Sequence within the maximum for the given pattern Sequences
     * @param patterns the target Sequences
     * @param maxDistance the maximum shortestDistance that will be considered
     * @return the tuples of parent Sequences and the shortestDistance lists
     */
    default public Collection<Pair<Sequence, Collection<Pair<Sequence, List<Integer>>>>> distances(SequenceCollection patterns, int maxDistance) {
        return patterns.stream().map(pattern -> Pair.with(pattern, distances(pattern, maxDistance))).collect(Collectors.toCollection(LinkedList::new));
    }
}
