package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import java.util.Iterator;
import java.util.function.Consumer;

import com.vitreoussoftware.bioinformatics.sequence.*;



/**
 * Suffix Tree implementation for Sequence data
 * @author John
 *
 */
public class SuffixTree {
	
	private SuffixTreeNode root;

	/**
	 * Create the suffix tree
	 * @param sequence the sequence used to generate the suffix tree
	 */
	public SuffixTree(Sequence sequence)
	{
		if (sequence == null) throw new IllegalArgumentException("Sequence cannot be null");
		root = new SuffixTreeNode();
		Iterator<BasePair> suffixIter = sequence.reverse().iterator();
		
		while (suffixIter.hasNext())
		{
			suffixIter.forEachRemaining(new Consumer<BasePair>() {
				SuffixTreeNode current = root;
				
				public void accept(BasePair bp) {
					current = current.getOrCreate(bp);
				}
			});
			suffixIter.next();
		}
	}

	/**
	 * Does the tree contain the specified substring?
	 * @param sequence the substring to search for
	 * @return if the substring exists in the tree
	 */
	public boolean contains(Sequence sequence) {
		Iterator<BasePair> iter = sequence.reverse().iterator();
		
		SuffixTreeNode current = root;
		while (iter.hasNext())
		{
			BasePair bp = iter.next();
			if (current.contains(bp))
				current = current.get(bp);
			else
				return false;
		}
		
		return true;
	}
	
}
