package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		
		return new SequenceSetFactory().getSequenceCollection(current.getParents());
	}
	
	

	@Override
	public Collection<Tuple<Integer, SequenceCollection>> distance(Sequence sequence, int maxDistance) {
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

        Map<Sequence, List<Integer>> collected = previous.parallelStream()
                .<Tuple<Sequence, Integer>>flatMap(tuple -> tuple.getItem2().getParents().stream().map(parent -> new Tuple<>(parent, tuple.getItem1())))
                .collect(Collectors.groupingBy(tuple -> tuple.getItem1(), Collectors.mapping(x -> x.getItem2(), Collectors.toList())));

        Map<Integer, List<Sequence>> results = collected.keySet().parallelStream()
                .map(parent -> new Tuple<>(collected.get(parent).stream().reduce((x, y) -> Math.min(x, y)).get(), parent))
                .collect(Collectors.groupingBy(tuple -> tuple.getItem1(), Collectors.mapping(x -> x.getItem2(), Collectors.toList())));


        List<Tuple<Integer, SequenceCollection>> distances = results.keySet().parallelStream()
                .map(key -> new Tuple<>(key, this.factory.getSequenceCollection(results.get(key))))
                .collect(Collectors.toList());

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
	public Collection<Tuple<Sequence, List<Integer>>> distances(Sequence sequence) {
		return distances(sequence, -1);
	}
	
	@Override
	public Collection<Tuple<Sequence, List<Integer>>> distances(Sequence sequence, int maxDistance) {
		Iterator<BasePair> iter = sequence.reverse().iterator();
		
		LinkedList<Tuple<Integer, SuffixTreeNode>> previous = new LinkedList<Tuple<Integer, SuffixTreeNode>>();
		previous.add(new Tuple<>(0, root));
		
		while (iter.hasNext())
		{
			LinkedList<Tuple<Integer, SuffixTreeNode>> next = new LinkedList<Tuple<Integer, SuffixTreeNode>>();
			
			BasePair bp = iter.next();
			SuffixTreeNode match = null;
			for (Tuple<Integer, SuffixTreeNode> tuple : previous)
			{
				if (tuple.getItem2().contains(bp)) {
					match = tuple.getItem2().get(bp);
					next.add(new Tuple<Integer, SuffixTreeNode>(tuple.getItem1().intValue(), match));
				}
					
				for (SuffixTreeNode child : tuple.getItem2().getAll()) {
					// if they match don't increase the distance
					if (!child.equals(match) && (maxDistance < 0 || tuple.getItem1().intValue() < maxDistance))
						next.add(new Tuple<Integer, SuffixTreeNode>(tuple.getItem1().intValue()+1, child));
				}
				
			}
			
			previous = next;
		}

        final Map<Sequence,List<Integer>> results = previous.parallelStream()
                .flatMap(tuple -> tuple.getItem2().getParents().stream().map(parent -> new Tuple<>(parent, tuple.getItem1())))
                .collect(Collectors.groupingBy(tuple -> tuple.getItem1(), Collectors.mapping(tuple -> tuple.getItem2(), Collectors.toList())));

        return results.keySet().parallelStream()
                .map(parent -> new Tuple<>(parent, results.get(parent)))
                .collect(Collectors.<Tuple<Sequence, List<Integer>>>toList());
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
