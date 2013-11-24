package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.vitreoussoftware.bioinformatics.sequence.*;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.utilities.Tuple;



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
	public Collection<Tuple<Integer, SequenceCollection>> distance(Sequence sequence);
	
	/**
	 * Return the minimum distance from the give sequence to each of the sequences making up the SuffixTree
	 * @param sequence the DNA sequence to check distance for
	 * @param maxDistance the maximum distance before which we give up
	 * @return The collection of tuples showing the distance and the collection of sequences for that distance
	 */
	public Collection<Tuple<Integer, SequenceCollection>> distance(Sequence sequence, int maxDistance);
	
	/**
	 * Return the set of distances for each parent Sequence given the target Sequences
	 * @param sequence the target Sequence
	 * @return the tuples of parent Sequences and the distance lists
	 */
	public Collection<Tuple<Sequence, List<Integer>>> distances(Sequence sequence);

	/**
	 * Return the set of distances for each parent Sequence within the maximum for the given target Sequences
	 * @param sequence the target Sequence
	 * @param maxDistance the maximum distance that will be considered
	 * @return the tuples of parent Sequences and the distance lists@param maxDistance
	 */
	public Collection<Tuple<Sequence, List<Integer>>> distances(Sequence sequence, int maxDistance);
}
