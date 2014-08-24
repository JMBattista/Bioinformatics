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
     * @param pattern the substring to search for
     * @return if the substring exists in the tree
     */
    public boolean contains(Sequence pattern);

    /**
     * Find the set of parents for the pattern of interest
     *
     * @param pattern the pattern to find parents for
     * @return the set of parents, or empty list if no parents
     */
    public Collection<Position> getPositions(Sequence pattern);

    /**
     * Adds a new text to the suffix tree
     * @param text the text to add
     */
    public void addSequence(final Sequence text);

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
