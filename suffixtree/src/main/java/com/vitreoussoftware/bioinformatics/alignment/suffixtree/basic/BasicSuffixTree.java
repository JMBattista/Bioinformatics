package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTree;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.Walk;
import com.vitreoussoftware.bioinformatics.sequence.*;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.basic.SequenceList;
import com.vitreoussoftware.bioinformatics.sequence.collection.basic.SequenceSetFactory;
import org.javatuples.*;


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
        //TODO I don't like this.
		root = new SuffixTreeNode(null);
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
		Iterator<BasePair> iter = sequence.iterator();
		
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
	public Collection<Pair<Integer, SequenceCollection>> distance(Sequence sequence, int maxDistance) {
		Iterator<BasePair> iter = sequence.iterator();
		
		LinkedList<Pair<Integer, SuffixTreeNode>> previous = new LinkedList<Pair<Integer, SuffixTreeNode>>();
		previous.add(new Pair<Integer, SuffixTreeNode>(0, root));
		
		while (iter.hasNext())
		{
			LinkedList<Pair<Integer, SuffixTreeNode>> next = new LinkedList<Pair<Integer, SuffixTreeNode>>();
			
			BasePair bp = iter.next();
			
			for (Pair<Integer, SuffixTreeNode> tuple : previous)
			{
				if (tuple.getValue1().contains(bp)) {
					next.add(new Pair<Integer, SuffixTreeNode>(tuple.getValue0(), tuple.getValue1().get(bp)));
				}
				else if (maxDistance < 0 || tuple.getValue0().intValue()+1 < maxDistance) {
					for (SuffixTreeNode child : tuple.getValue1().getAll()) {
						next.add(new Pair<>(tuple.getValue0().intValue()+1, child));
					}
				}
			}
			
			previous = next;
		}

        Map<Sequence, List<Integer>> collected = previous.parallelStream()
                .<Pair<Sequence, Integer>>flatMap(tuple -> tuple.getValue1().getParents().stream().map(parent -> new Pair<>(parent, tuple.getValue0())))
                .collect(Collectors.groupingBy(tuple -> tuple.getValue0(), Collectors.mapping(x -> x.getValue1(), Collectors.toList())));

        Map<Integer, List<Sequence>> results = collected.keySet().parallelStream()
                .map(parent -> new Pair<>(collected.get(parent).stream().reduce((x, y) -> Math.min(x, y)).get(), parent))
                .collect(Collectors.groupingBy(pair -> pair.getValue0(), Collectors.mapping(x -> x.getValue1(), Collectors.toList())));


        List<Pair<Integer, SequenceCollection>> distances = results.keySet().parallelStream()
                .map(key -> new Pair<>(key, this.factory.getSequenceCollection(results.get(key))))
                .collect(Collectors.toList());

		// Sort distances on the distance value
		Collections.sort(distances, new Comparator<Pair<Integer, SequenceCollection>>() {

			@Override
			public int compare(Pair<Integer, SequenceCollection> arg0, Pair<Integer, SequenceCollection> arg1) {
				return arg0.getValue0() - arg1.getValue0();
			}
		});
		
		return distances;
	}

	@Override
	public Collection<Pair<Sequence, List<Integer>>> distances(Sequence sequence) {
		return distances(sequence, -1);
	}
	
	@Override
	public Collection<Pair<Sequence, List<Integer>>> distances(Sequence sequence, int maxDistance) {
		Iterator<BasePair> iter = sequence.iterator();
		
		LinkedList<Pair<Integer, SuffixTreeNode>> previous = new LinkedList<Pair<Integer, SuffixTreeNode>>();
		previous.add(new Pair<>(0, root));
		
		while (iter.hasNext())
		{
			LinkedList<Pair<Integer, SuffixTreeNode>> next = new LinkedList<Pair<Integer, SuffixTreeNode>>();
			
			BasePair bp = iter.next();
			SuffixTreeNode match = null;
			for (Pair<Integer, SuffixTreeNode> tuple : previous)
			{
				if (tuple.getValue1().contains(bp)) {
					match = tuple.getValue1().get(bp);
					next.add(new Pair<Integer, SuffixTreeNode>(tuple.getValue0().intValue(), match));
				}
					
				for (SuffixTreeNode child : tuple.getValue1().getAll()) {
					// if they match don't increase the distance
					if (!child.equals(match) && (maxDistance < 0 || tuple.getValue0().intValue() < maxDistance))
						next.add(new Pair<Integer, SuffixTreeNode>(tuple.getValue0().intValue()+1, child));
				}
				
			}
			
			previous = next;
		}

        final Map<Sequence,List<Integer>> results = previous.parallelStream()
                .flatMap(tuple -> tuple.getValue1().getParents().stream().map(parent -> new Pair<>(parent, tuple.getValue0())))
                .collect(Collectors.groupingBy(tuple -> tuple.getValue0(), Collectors.mapping(tuple -> tuple.getValue1(), Collectors.toList())));

        return results.keySet().parallelStream()
                .map(parent -> new Pair<>(parent, results.get(parent)))
                .collect(Collectors.<Pair<Sequence, List<Integer>>>toList());
	}

    @Override
    public <T, R> R walk(Walk<T, R> walker) {
        PriorityQueue<Pair<SuffixTreeNode, T>> toBeWalked = new PriorityQueue<>(100, new Comparator<Pair<SuffixTreeNode, T>>() {
            @Override
            public int compare(Pair<SuffixTreeNode, T> o1, Pair<SuffixTreeNode, T> o2) {
                return walker.compare(o1.getValue1(), o2.getValue1());
            }
        });
        toBeWalked.add(new Pair<>(root, walker.initialValue()));
        //AGGCUUAACACAUGCAAGUCGAACGAAGUUAGGAAGCUUGCUUCUGAUACUUAGUGGCGGACGGGUGAGUAAUGCUUAGG
        while (!toBeWalked.isEmpty()) {
            Pair<SuffixTreeNode, T> pair = toBeWalked.remove();
            for (SuffixTreeNode node : pair.getValue0().getAll()) {
                Optional<T> result = walker.visit(node.getBasePair(), pair.getValue1());

                // If there was a result use it, otherwise ignore
                if (result.isPresent()) {
                    if (walker.isFinished(result.get()))
                        return walker.getResult();
                    else
                        toBeWalked.add(new Pair<>(node, result.get()));
                }
            }
        }

        return walker.getResult();
    }

    @Override
	public Collection<Pair<Integer, SequenceCollection>> distance(Sequence sequence) {
		return distance(sequence, -1);
	}

	/**
	 * Adds a new sequence to the suffix tree
	 * @param sequence the sequence to add
	 */
	public void addSequence(final Sequence sequence) {
		if (sequence == null) throw new IllegalArgumentException("Sequence cannot be null");
		Iterator<BasePair> suffixIter = sequence.iterator();
		
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
