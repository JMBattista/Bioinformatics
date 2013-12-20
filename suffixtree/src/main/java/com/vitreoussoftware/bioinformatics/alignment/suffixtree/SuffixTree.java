package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import java.util.Collection;
import java.util.List;

import com.vitreoussoftware.bioinformatics.sequence.*;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import org.javatuples.Pair;


/**
 * Suffix Tree implementation for Sequence data
 * @author John
 *
 */
public interface SuffixTree {
		/**
	 * Does the tree contain the specified substring?
	 * @param sequence the substring to search for
	 * @return if the substring exists in the tree
	 */
	public boolean contains(Sequence sequence);
	
	/**
	 * Returns the depth of the suffix tree.
	 * @return the depth
	 */
	public int depth();

	/**
	 * Find the set of parents for the sequence of interest
	 * @param sequence the sequence to find parents for
	 * @return the set of parents, or empty list if no parents
	 */
	public SequenceCollection getParents(Sequence sequence);
	
	/**
	 * Adds a new sequence to the suffix tree
	 * @param sequence the sequence to add
	 */
	public void addSequence(final Sequence sequence);

	/**
	 * Return the minimum distance from the give sequence to each of the sequences making up the SuffixTree
	 * @param sequence the DNA sequence to check distance for
	 * @return The collection of tuples showing the distance and the collection of sequences for that distance
	 */
	public Collection<Pair<Integer, SequenceCollection>> distance(Sequence sequence);
	
	/**
	 * Return the minimum distance from the give sequence to each of the sequences making up the SuffixTree
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

    /**
     * Walk the tree to build up a result
     * @param walker the walk to perform
     * @param <T> the tracking value during the walk
     * @param <R> the result type for the walk
     * @return the result of the walk
     */
    public <T, R> R walk(Walk<T, R> walker);
}
