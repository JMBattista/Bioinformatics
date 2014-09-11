package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import java.util.*;
import java.util.stream.Collectors;

import com.vitreoussoftware.bioinformatics.alignment.Alignment;
import com.vitreoussoftware.bioinformatics.alignment.Position;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTree;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.Walk;
import com.vitreoussoftware.bioinformatics.sequence.*;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
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
		root = new SuffixTreeNode(null);
	}

	/**
	 * Does the tree contain the specified substring?
	 * @param pattern the substring to search for
	 * @return if the substring exists in the tree
	 */
	public boolean contains(Sequence pattern) {
		return !this.getAlignments(pattern).isEmpty();
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
	 *
     * @param pattern the sequence to find parents for
     * @return the set of parents, or empty list if no parents
	 */
	public Collection<Alignment> getAlignments(Sequence pattern) {
		Iterator<BasePair> iter = pattern.iterator();
		
		SuffixTreeNode current = root;
		while (iter.hasNext())
		{
			BasePair bp = iter.next();
			if (current.contains(bp))
				current = current.get(bp);
			else
				// return empty list
				return Collections.EMPTY_LIST;
		}

		return current.getTexts().stream().map(position -> Alignment.with(position.getText(), pattern, position.getPosition())).collect(Collectors.toCollection(LinkedList::new));
	}
	
	

	@Override
	public Collection<Alignment> shortestDistance(Sequence pattern, int maxDistance) {
		PriorityQueue<Triplet<Integer, Integer, SuffixTreeNode>> queue = new PriorityQueue<>(50, (a, b) -> a.getValue0() - b.getValue0());
		queue.add(new Triplet<>(0, 0, root));
        List<Alignment> alignments = new LinkedList<>();

		while (queue.size() > 0)
		{
            int distance = queue.peek().getValue0();

            if (alignments.size() > 0 && alignments.get(0).getDistance() < distance)
                break;

            int position = queue.peek().getValue1();
            SuffixTreeNode node = queue.poll().getValue2();

			BasePair bp = pattern.get(position);

			for (BasePair candidate: node.keySet())
			{
				int newDistance = candidate.distance(bp) + distance;
                if (maxDistance == -1 || newDistance <= maxDistance) {
                    if (position == pattern.length() -1)
                        for (Position pos: node.get(candidate).getTexts())
                        alignments.add(Alignment.with(pos.getText(), pattern, pos.getPosition(), newDistance));
                    else
                        queue.add(Triplet.with(newDistance, position+1, node.get(candidate)));
                }
			}
		}

        int shortestDistance = alignments.stream().min((a, b) -> a.getDistance() - b.getDistance()).map(a -> a.getDistance()).orElse(0);

        return alignments.stream().filter(alignment -> alignment.getDistance() == shortestDistance).collect(Collectors.toCollection(LinkedList::new));
	}
	
	@Override
	public Collection<Alignment> distances(Sequence pattern, int maxDistance) {
        PriorityQueue<Triplet<Integer, Integer, SuffixTreeNode>> queue = new PriorityQueue<>(50, (a, b) -> a.getValue0() - b.getValue0());
        queue.add(new Triplet<>(0, 0, root));
        List<Alignment> alignments = new LinkedList<>();

        while (queue.size() > 0)
        {
            int distance = queue.peek().getValue0();

            int position = queue.peek().getValue1();
            SuffixTreeNode node = queue.poll().getValue2();

            BasePair bp = pattern.get(position);

            for (BasePair candidate: node.keySet())
            {
                int newDistance = candidate.distance(bp) + distance;
                if (maxDistance == -1 || newDistance <= maxDistance) {
                    if (position == pattern.length() -1)
                        for (Position pos: node.get(candidate).getTexts())
                            alignments.add(Alignment.with(pos.getText(), pattern, pos.getPosition(), newDistance));
                    else
                        queue.add(Triplet.with(newDistance, position+1, node.get(candidate)));
                }
            }
        }

        return alignments.stream().collect(Collectors.toCollection(LinkedList::new));
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

        while (!toBeWalked.isEmpty()) {
            Pair<SuffixTreeNode, T> pair = toBeWalked.remove();
            for (SuffixTreeNode node : pair.getValue0().values()) {
                Optional<T> result = walker.visit(node.getBasePair(), node.getTexts(), pair.getValue1());

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

	/**
	 * Adds a new sequence to the suffix tree
	 * @param text the sequence to add
	 */
	public void addText(final Sequence text) {
		if (text == null) throw new IllegalArgumentException("Sequence cannot be null");

        // Start from the front of the sequence and then for each position
		for (int offset = 0; offset < text.length(); offset++)
		{
            SuffixTreeNode current = root;
            // Add the sub-sequence of elements remaining in the sequence to the SuffixTree
            for (int j = offset; j < text.length(); j++) {
                BasePair bp = text.get(j);
                current = current.getOrCreate(bp);
                // We add the position the sequence started from, not its current point
                //  to better support alignment algorithms which will be interested in finding a partial
                //  alignment and then knowing what its starting point was
                current.addPosition(text, offset);
            }
		}
	}
}
