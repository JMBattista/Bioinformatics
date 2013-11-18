package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTree;
import com.vitreoussoftware.bioinformatics.sequence.*;



/**
 * Suffix Tree implementation for Sequence data
 * @author John
 *
 */
public class BasicSuffixTree implements SuffixTree {
	
	private SuffixTreeNode root;

	/**
	 * Create the suffix tree
	 * @param sequence the sequence used to generate the suffix tree
	 */
	BasicSuffixTree(Sequence sequence)
	{
		this();
		this.addSequence(sequence);
	}

	/**
	 * Create the suffix tree
	 */
	BasicSuffixTree() {
		root = new SuffixTreeNode();
	}

	/**
	 * Does the tree contain the specified substring?
	 * @param sequence the substring to search for
	 * @return if the substring exists in the tree
	 */
	public boolean contains(Sequence sequence) {
		return !this.getParents(sequence).isEmpty();
	}
	
	/**
	 * Returns the depth of the suffix tree.
	 * @return the depth
	 */
	public int depth() {
		// The root is a null element
		return root.depth() -1;	
	}

	/**
	 * Find the set of parents for the sequence of interest
	 * @param sequence the sequence to find parents for
	 * @return the set of parents, or empty list if no parents
	 */
	public Collection<Sequence> getParents(Sequence sequence) {
		Iterator<BasePair> iter = sequence.reverse().iterator();
		
		SuffixTreeNode current = root;
		while (iter.hasNext())
		{
			BasePair bp = iter.next();
			if (current.contains(bp))
				current = current.get(bp);
			else
				// return empty list
				return new LinkedList<Sequence>();
		}
		
		return current.getParents();
	}

	/**
	 * Adds a new sequence to the suffix tree
	 * @param sequence the sequence to add
	 */
	public void addSequence(final Sequence sequence) {
		//TODO this takes n^2 can probably be reduced dramatically
		if (sequence == null) throw new IllegalArgumentException("Sequence cannot be null");
		Iterator<BasePair> suffixIter = sequence.reverse().iterator();
		
		while (suffixIter.hasNext())
		{
			suffixIter.forEachRemaining(new Consumer<BasePair>() {
				SuffixTreeNode current = root;
				
				public void accept(BasePair bp) {
					current = current.getOrCreate(bp);
					current.addParent(sequence);
				}
			});
			suffixIter.next();
		}
	}
	
}
