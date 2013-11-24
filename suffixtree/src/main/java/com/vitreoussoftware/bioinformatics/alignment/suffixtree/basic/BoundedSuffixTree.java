package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTree;
import com.vitreoussoftware.bioinformatics.sequence.*;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.basic.SequenceList;
import com.vitreoussoftware.bioinformatics.sequence.collection.basic.SequenceSetFactory;
import com.vitreoussoftware.utilities.Tuple;



/**
 * Suffix Tree implementation for Sequence data
 * @author John
 *
 */
public class BoundedSuffixTree extends BasicSuffixTree {
	private int minLength;
	private int maxLength;


	/**
	 * Create the suffix tree
	 * @param factory the factory for creating SequenceCollections 
	 * @param minlength the minimum length of a suffix contained in the tree.
	 * @param maxLength the maximum length of a suffix contained in the tree.
	 */
	BoundedSuffixTree(SequenceCollectionFactory factory, int minLength, int maxLength) {
		super(factory);
		this.minLength = minLength;
		this.maxLength = maxLength;
	}


	/**
	 * Adds a new sequence to the suffix tree
	 * @param sequence the sequence to add
	 */
	public void addSequence(final Sequence sequence) {
		if (sequence == null) throw new IllegalArgumentException("Sequence cannot be null");
		Iterator<BasePair> suffixIter = sequence.reverse().iterator();

		int startPos = 0;
		final int length = sequence.length();
		while (suffixIter.hasNext() && startPos + this.minLength < length)
		{
			SuffixTreeNode current = root;
			// up to max length create or iterate the nodes and add parents.
			for (int offset = 0; offset < this.maxLength && offset + startPos < sequence.length(); offset++) {
				current = current.getOrCreate(sequence.get(length - startPos - offset -1));
				if (offset >= this.minLength) current.addParent(sequence);
			}
			
			// iterate forward and update the position
			suffixIter.next();
			startPos++;
		}
	}	
}
