package com.vitreoussoftware.bioinformatics.alignment;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import org.javatuples.Pair;

import java.util.Collection;
import java.util.List;

/**
 * Aligners allow users to determine the locations of a set of Patterns from within a set of Texts,
 * based on a matching function.
 *
 * A PatternFirstAligner pre-processes the Text that is to be aligned against, then runs the Patterns against
 * that pre-processed Text.
 *
 * An Example would be creating a KeywordMap from the set of reads and then aligning them against the GreenGeenes set.
 *
 * This is not the standard way of approaching alignment, as in most cases the Texts are fairly stable however the
 * read sets change on a per experiment basis. This is more useful if using an iterative approach that generates new
 * candidate sequences and needs to align against them periodically.
 *
 * Created by John on 8/23/14.
 */
public interface PatternFirstAligner {
    /**
     * Does the Text contain the pattern?
     * @param pattern the substring to search for
     * @return if the substring exists in the tree
     */
    public boolean contains(Sequence pattern);

    /**
     * Find the set of positions for the processed reads in the text
     *
     * @param text the pattern to find parents for
     * @return the set of parents, or empty list if no parents
     */
    public Collection<Position> getPositions(Sequence text);

    /**
     * Adds a new pattern to the suffix tree
     * @param pattern the pattern to add
     */
    public void addSequence(final Sequence pattern);

    /**
     * Return the minimum distance from the give sequence to each of the sequences making up the Aligner
     * @param sequence the DNA sequence to check distance for
     * @return The collection of tuples showing the distance and the collection of sequences for that distance
     */
    public Collection<Pair<Integer, SequenceCollection>> distance(Sequence sequence);

    /**
     * Return the minimum distance from the give sequence to each of the sequences making up the Aligner
     * @param sequence the DNA sequence to check distance for
     * @param maxDistance the maximum distance before which we give up
     * @return The collection of tuples showing the distance and the collection of sequences for that distance
     */
    public Collection<Pair<Integer, SequenceCollection>> distance(Sequence sequence, int maxDistance);

    /**
     * Return the set of distances for each parent Sequence given the target Sequences
     * @param sequence the target Sequence
     * @return the tuples of parent Sequences and the distance lists
     */
    public Collection<Pair<Sequence, List<Integer>>> distances(Sequence sequence);

    /**
     * Return the set of distances for each parent Sequence within the maximum for the given target Sequences
     * @param sequence the target Sequence
     * @param maxDistance the maximum distance that will be considered
     * @return the tuples of parent Sequences and the distance lists@param maxDistance
     */
    public Collection<Pair<Sequence, List<Integer>>> distances(Sequence sequence, int maxDistance);
}
