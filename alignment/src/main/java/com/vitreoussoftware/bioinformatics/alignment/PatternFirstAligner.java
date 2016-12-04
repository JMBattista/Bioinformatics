package com.vitreoussoftware.bioinformatics.alignment;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import org.javatuples.Pair;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * Aligners allow users to determine the locations of a set of Patterns from within a set of Texts,
 * based on a matching function.
 * <p>
 * A PatternFirstAligner pre-processes the Text that is to be aligned against, then runs the Patterns against
 * that pre-processed Text.
 * <p>
 * An Example would be creating a KeywordMap from the set of reads and then aligning them against the GreenGeenes set.
 * <p>
 * This is not the standard way of approaching alignment, as in most cases the Texts are fairly stable however the
 * read sets change on a per experiment basis. This is more useful if using an iterative approach that generates new
 * candidate sequences and needs to align against them periodically.
 * <p>
 * Created by John on 8/23/14.
 */
public interface PatternFirstAligner {
    /**
     * Adds a new pattern to the suffix tree
     *
     * @param pattern the pattern to add
     */
    public void addPattern(final Sequence pattern);

    /**
     * Does the text contain one or more of the patterns
     *
     * @param text the sequence to search in
     * @return if the text contained one of the patterns
     */
    public boolean contained(Sequence text);

    /**
     * For all texts which are have contained patterns
     *
     * @param texts collection of sequences to search in
     * @return List of (Text, contained?) pairs.
     */
    default public Collection<Pair<Sequence, Boolean>> contained(final SequenceCollection texts) {
        return texts.stream().map(pattern -> Pair.with(pattern, this.contained(pattern))).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Find the set of alignments for the text of interest
     *
     * @param text to find alignments for
     * @return the set of parents, or empty list if no parents
     */
    public Collection<Alignment> getAlignments(Sequence text);

    /**
     * Find the set of alignments for the each pattern of interest
     *
     * @param texts to find alignments for
     * @return List of (Pattern, Collection<Alignment) pairs.
     */
    default public Collection<Pair<Sequence, Collection<Alignment>>> getAlignments(final SequenceCollection texts) {
        return texts.stream().map(pattern -> Pair.with(pattern, this.getAlignments(pattern))).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Return the minimum shortestDistance from the given text to each of the sequences making up the Aligner
     *
     * @param text the DNA text to check shortestDistance for
     * @return The collection of tuples showing the shortestDistance and the collection of sequences for that shortestDistance
     */
    default public Collection<Alignment> shortestDistance(final Sequence text) {
        // Negative number used to indicate no distance cap
        return shortestDistance(text, -1);
    }

    /**
     * Return the minimum shortestDistance from the given texts to each of the sequences making up the Aligner
     *
     * @param texts the DNA texts to check shortestDistance for
     * @return The collection of tuples showing the shortestDistance and the collection of sequences for that shortestDistance
     */
    default public Collection<Pair<Sequence, Collection<Alignment>>> shortestDistance(final SequenceCollection texts) {
        // Negative number used to indicate no distance cap
        return shortestDistance(texts, -1);
    }

    /**
     * Return the minimum shortestDistance from the given text to each of the sequences making up the Aligner
     *
     * @param text        the DNA text to check shortestDistance for
     * @param maxDistance the maximum shortestDistance before which we give up
     * @return The collection of tuples showing the shortestDistance and the collection of sequences for that shortestDistance
     */
    public Collection<Alignment> shortestDistance(Sequence text, int maxDistance);

    /**
     * Return the minimum shortestDistance from the given texts to each of the sequences making up the Aligner
     *
     * @param texts       the DNA texts to check shortestDistance for
     * @param maxDistance the maximum shortestDistance before which we give up
     * @return The collection of tuples showing the shortestDistance and the collection of sequences for that shortestDistance
     */
    default public Collection<Pair<Sequence, Collection<Alignment>>> shortestDistance(final SequenceCollection texts, final int maxDistance) {
        return texts.stream().map(pattern -> Pair.with(pattern, shortestDistance(pattern, maxDistance))).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Return the set of distances for each parent Sequence given the target Sequences
     *
     * @param text the target Sequence
     * @return the tuples of parent Sequences and the shortestDistance lists
     */
    default public Collection<Alignment> distances(final Sequence text) {
        // Negative number used to indicate no distance cap
        return distances(text, -1);
    }

    /**
     * Return the set of distances for each parent Sequence given the target Sequences
     *
     * @param texts the target Sequence
     * @return the tuples of parent Sequences and the shortestDistance lists
     */
    default public Collection<Pair<Sequence, Collection<Alignment>>> distances(final SequenceCollection texts) {
        // Negative number used to indicate no distance cap
        return distances(texts, -1);
    }

    /**
     * Return the set of distances for each text Sequence within the maximum for the given text Sequence
     *
     * @param text        the target Sequence
     * @param maxDistance the maximum shortestDistance that will be considered
     * @return the tuples of parent Sequences and the shortestDistance lists
     */
    public Collection<Alignment> distances(Sequence text, int maxDistance);

    /**
     * Return the set of distances for each parent Sequence within the maximum for the given pattern Sequences
     *
     * @param texts       the target Sequences
     * @param maxDistance the maximum shortestDistance that will be considered
     * @return the tuples of parent Sequences and the shortestDistance lists
     */
    default public Collection<Pair<Sequence, Collection<Alignment>>> distances(final SequenceCollection texts, final int maxDistance) {
        return texts.stream().map(pattern -> Pair.with(pattern, distances(pattern, maxDistance))).collect(Collectors.toCollection(LinkedList::new));
    }
}
