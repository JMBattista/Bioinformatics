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
public class BasicSuffixTree implements SuffixTree {
	
	protected SuffixTreeNode root;
	private SequenceCollectionFactory factory;

	/**
	 * Create the suffix tree
	 */
	BasicSuffixTree(SequenceCollectionFactory factory) {
		this.factory = factory;
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
	public SequenceCollection getParents(Sequence sequence) {
		Iterator<BasePair> iter = sequence.reverse().iterator();
		
		SuffixTreeNode current = root;
		while (iter.hasNext())
		{
			BasePair bp = iter.next();
			if (current.contains(bp))
				current = current.get(bp);
			else
				// return empty list
				return SequenceList.getEmpty();
		}
		
		return current.getParents();
	}
	
	

	@Override
	public Collection<Tuple<Integer, SequenceCollection>> distance(Sequence sequence, int maxDistance) {
		//TODO rewrite this method using stream operations
		Iterator<BasePair> iter = sequence.reverse().iterator();
		
		LinkedList<Tuple<Integer, SuffixTreeNode>> previous = new LinkedList<Tuple<Integer, SuffixTreeNode>>();
		previous.add(new Tuple<Integer, SuffixTreeNode>(0, root));
		
		while (iter.hasNext())
		{
			LinkedList<Tuple<Integer, SuffixTreeNode>> next = new LinkedList<Tuple<Integer, SuffixTreeNode>>();
			
			BasePair bp = iter.next();
			
			for (Tuple<Integer, SuffixTreeNode> tuple : previous)
			{
				if (tuple.getItem2().contains(bp)) {
					next.add(new Tuple<Integer, SuffixTreeNode>(tuple.getItem1(), tuple.getItem2().get(bp)));
				}
				else if (maxDistance < 0 || tuple.getItem1().intValue()+1 < maxDistance) {
					for (SuffixTreeNode child : tuple.getItem2().getAll()) {
						next.add(new Tuple<Integer, SuffixTreeNode>(tuple.getItem1().intValue()+1, child));
					}
				}
			}
			
			previous = next;
		}
		
		HashMap<Sequence, List<Integer>> results = new HashMap<Sequence, List<Integer>>();
		for (Tuple<Integer, SuffixTreeNode> tuple : previous) {
			for (Sequence parent : tuple.getItem2().getParents()) {
				List<Integer> list;
				if (results.containsKey(parent))
					list = results.get(parent);
				else 
					list = new LinkedList<Integer>();
				list.add(tuple.getItem1());
				
				results.put(parent, list);
			}
		}
		
		// Create an inverted HashMap of Sequences and their corresponding distance, keeping minimum
		HashMap<Sequence, Integer> sieve = new HashMap<Sequence, Integer>();
		for (Sequence parent : results.keySet()) {
			List<Integer> list = results.get(parent);
			Collections.sort(list);
			
			sieve.put(parent, list.get(0));
		}
		
		// Now flip to a HashMap of distances to Sequences
		HashMap<Integer, SequenceCollection> collector = new HashMap<Integer, SequenceCollection>();
		for (Sequence parent : sieve.keySet())
		{
			if (collector.containsKey(sieve.get(parent))) {
				collector.get(sieve.get(parent)).add(parent);
			}
			else
			{
				SequenceCollection collection = this.factory.getSequenceCollection();
				collection.add(parent);
				collector.put(sieve.get(parent), collection);
			}
		}
		
		// Put all of the values into the distances collection
		List<Tuple<Integer, SequenceCollection>> distances = new LinkedList<Tuple<Integer,SequenceCollection>>();
		for (Integer key : collector.keySet()) {
			distances.add(new Tuple<Integer, SequenceCollection>(key, collector.get(key)));
		}
		
		// Sort distances on the distance value
		Collections.sort(distances, new Comparator<Tuple<Integer, SequenceCollection>>() {

			@Override
			public int compare(Tuple<Integer, SequenceCollection> arg0, Tuple<Integer, SequenceCollection> arg1) {
				return arg0.getItem1() - arg1.getItem1();
			}
		});
		
		return distances;
	}	
	
	@Override
	public Collection<Tuple<Integer, SequenceCollection>> distance(Sequence sequence) {
		return distance(sequence, -1);
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
