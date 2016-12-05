package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import com.vitreoussoftware.bioinformatics.alignment.Alignment;
import com.vitreoussoftware.bioinformatics.alignment.Position;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTree;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.Walk;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Suffix Tree implementation for Sequence data
 *
 * @author John
 */
public class BasicSuffixTree implements SuffixTree {

    protected final SuffixTreeNode root;
    private final SequenceCollectionFactory factory;

    /**
     * Create the suffix tree
     */
    BasicSuffixTree(final SequenceCollectionFactory factory) {
        this.factory = factory;
        root = new SuffixTreeNode(null);
    }

    /**
     * Does the tree contain the specified substring?
     *
     * @param pattern the substring to search for
     * @return if the substring exists in the tree
     */
    public boolean contains(final Sequence pattern) {
        return !this.getAlignments(pattern).isEmpty();
    }

    /**
     * Returns the depth of the suffix tree.
     *
     * @return the depth
     */
    public int depth() {
        // The root is a null element
        return root.depth() - 1;
    }

    /**
     * Find the set of parents for the sequence of interest
     *
     * @param pattern the sequence to find parents for
     * @return the set of parents, or empty list if no parents
     */
    public Collection<Alignment> getAlignments(final Sequence pattern) {
        final Iterator<BasePair> iter = pattern.iterator();

        SuffixTreeNode current = root;
        while (iter.hasNext()) {
            final BasePair bp = iter.next();
            if (current.contains(bp))
                current = current.get(bp);
            else
                // return empty list
                return Collections.EMPTY_LIST;
        }

        return current.getTexts().stream().map(position -> Alignment.with(position.getText(), pattern, position.getPosition())).collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    public Collection<Alignment> shortestDistance(final Sequence pattern, final int maxDistance) {
        final PriorityQueue<Triplet<Integer, Integer, SuffixTreeNode>> queue = new PriorityQueue<>(50, (a, b) -> a.getValue0() - b.getValue0());
        queue.add(new Triplet<>(0, 0, root));
        final List<Alignment> alignments = new LinkedList<>();

        while (queue.size() > 0) {
            final int distance = queue.peek().getValue0();

            if (alignments.size() > 0 && alignments.get(0).getDistance() < distance)
                break;

            final int position = queue.peek().getValue1();
            final SuffixTreeNode node = queue.poll().getValue2();

            final BasePair bp = pattern.get(position);

            for (final BasePair candidate : node.keySet()) {
                final int newDistance = candidate.distance(bp) + distance;
                if (maxDistance == -1 || newDistance <= maxDistance) {
                    if (position == pattern.length() - 1)
                        for (final Position pos : node.get(candidate).getTexts())
                            alignments.add(Alignment.with(pos.getText(), pattern, pos.getPosition(), newDistance));
                    else
                        queue.add(Triplet.with(newDistance, position + 1, node.get(candidate)));
                }
            }
        }

        final int shortestDistance = alignments.stream().min((a, b) -> a.getDistance() - b.getDistance()).map(Alignment::getDistance).orElse(0);

        return alignments.stream().filter(alignment -> alignment.getDistance() == shortestDistance).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Collection<Alignment> distances(final Sequence pattern, final int maxDistance) {
        final PriorityQueue<Triplet<Integer, Integer, SuffixTreeNode>> queue = new PriorityQueue<>(50, (a, b) -> a.getValue0() - b.getValue0());
        queue.add(new Triplet<>(0, 0, root));
        final List<Alignment> alignments = new LinkedList<>();

        while (queue.size() > 0) {
            final int distance = queue.peek().getValue0();

            final int position = queue.peek().getValue1();
            final SuffixTreeNode node = queue.poll().getValue2();

            final BasePair bp = pattern.get(position);

            for (final BasePair candidate : node.keySet()) {
                final int newDistance = candidate.distance(bp) + distance;
                if (maxDistance == -1 || newDistance <= maxDistance) {
                    if (position == pattern.length() - 1)
                        for (final Position pos : node.get(candidate).getTexts())
                            alignments.add(Alignment.with(pos.getText(), pattern, pos.getPosition(), newDistance));
                    else
                        queue.add(Triplet.with(newDistance, position + 1, node.get(candidate)));
                }
            }
        }

        return alignments.stream().collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public <T, R> R walk(final Walk<T, R> walker) {
        final PriorityQueue<Pair<SuffixTreeNode, T>> toBeWalked = new PriorityQueue<>(100, new Comparator<Pair<SuffixTreeNode, T>>() {
            @Override
            public int compare(final Pair<SuffixTreeNode, T> o1, final Pair<SuffixTreeNode, T> o2) {
                return walker.compare(o1.getValue1(), o2.getValue1());
            }
        });
        toBeWalked.add(new Pair<>(root, walker.initialValue()));

        while (!toBeWalked.isEmpty()) {
            final Pair<SuffixTreeNode, T> pair = toBeWalked.remove();
            for (final SuffixTreeNode node : pair.getValue0().values()) {
                final Optional<T> result = walker.visit(node.getBasePair(), node.getTexts(), pair.getValue1());

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
     *
     * @param text the sequence to add
     */
    public void addText(final Sequence text) {
        if (text == null) throw new IllegalArgumentException("Sequence cannot be null");

        // Start from the front of the sequence and then for each position
        for (int offset = 0; offset < text.length(); offset++) {
            SuffixTreeNode current = root;
            // Add the sub-sequence of elements remaining in the sequence to the SuffixTree
            for (int j = offset; j < text.length(); j++) {
                final BasePair bp = text.get(j);
                current = current.getOrCreate(bp);
                // We add the position the sequence started from, not its current point
                //  to better support alignment algorithms which will be interested in finding a partial
                //  alignment and then knowing what its starting point was
                current.addPosition(text, offset);
            }
        }
    }
}
