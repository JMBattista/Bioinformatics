package com.vitreoussoftware.bioinformatics.alignment.stringbased.keywordmap;

import com.vitreoussoftware.bioinformatics.alignment.Alignment;
import com.vitreoussoftware.bioinformatics.alignment.PatternFirstAligner;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import org.javatuples.Triplet;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by John on 8/27/14.
 */
public class KeywordMap implements PatternFirstAligner {
    private final KeywordNode root;

    KeywordMap() {
        root = new KeywordNode(null);
    }

    @Override
    public void addPattern(final Sequence pattern) {
        KeywordNode current = root;
        for (final BasePair bp : pattern) {
            current = current.getOrCreate(bp);
        }
        current.setTerminal(pattern);
    }

    @Override
    public boolean contained(final Sequence text) {
        for (int startPos = 0; startPos < text.length(); startPos++) {
            Optional<KeywordNode> current = Optional.of(root);
            for (int index = 0; startPos + index < text.length() && current.isPresent(); index++) {
                final int position = startPos + index;
                current = current.flatMap(n -> n.get(text.get(position)));
                if (current.map(n -> n.getTerminals().size() > 0).orElse(false))
                    return true;
            }
        }

        return false;
    }

    @Override
    public Collection<Alignment> getAlignments(final Sequence text) {
        final Collection<Alignment> alignments = new LinkedList<>();

        for (int startPos = 0; startPos < text.length(); startPos++) {
            Optional<KeywordNode> current = Optional.of(root);
            for (int index = 0; index + startPos < text.length() && current.isPresent(); index++) {
                final int position = startPos + index;
                current = current.flatMap(n -> n.get(text.get(position)));
                if (current.isPresent()) {
                    final List<Alignment> results = current.get().getTerminals().stream().map(pattern -> Alignment.with(text, pattern, position - pattern.length() + 1, 0))
                            .collect(Collectors.toList());
                    alignments.addAll(results);
                }
            }
        }

        return alignments;
    }

    @Override
    public Collection<Alignment> shortestDistance(final Sequence text, final int maxDistance) {
        final Collection<Alignment> alignments = new LinkedList<>();
        final PriorityQueue<Triplet<Integer, Integer, KeywordNode>> queue = new PriorityQueue<>(30, (a, b) -> a.getValue0() - b.getValue0());
        // Initialize the queue for each position
        for (int position = 0; position < text.length(); position++)
            queue.add(Triplet.with(0, position, root));

        while (!queue.isEmpty()) {
            final int distance = queue.peek().getValue0();
            final int position = queue.peek().getValue1();
            final KeywordNode current = queue.poll().getValue2();

            final Optional<Integer> shortestDistance = alignments.stream().findFirst().map(Alignment::getDistance);
            if (shortestDistance.map(d -> d < distance).orElse(false)) {
                return alignments;
            }

            for (final BasePair bp : current.keySet()) {
                final int totalDistance = text.get(position).distance(bp) + distance;

                if ((maxDistance == -1 || totalDistance < maxDistance)
                        && shortestDistance.map(d -> d >= totalDistance).orElse(true)) {
                    final Optional<KeywordNode> node = current.get(bp);
                    if (node.isPresent()) {
                        if (position + 1 < text.length())
                            queue.add(Triplet.with(totalDistance, position + 1, node.get()));

                        if (shortestDistance.map(d -> d > totalDistance && node.get().getTerminals().size() > 0).orElse(false))
                            alignments.clear();

                        final List<Alignment> results = node.get().getTerminals().stream().map(pattern -> Alignment.with(text, pattern, position - pattern.length() + 1, totalDistance))
                                .collect(Collectors.toList());
                        alignments.addAll(results);
                    }
                }
            }
        }

        return alignments;
    }

    @Override
    public Collection<Alignment> distances(final Sequence text, final int maxDistance) {
        final Collection<Alignment> alignments = new LinkedList<>();
        final PriorityQueue<Triplet<Integer, Integer, KeywordNode>> queue = new PriorityQueue<>(30, (a, b) -> a.getValue0() - b.getValue0());
        // Initialize the queue for each position
        for (int position = 0; position < text.length(); position++)
            queue.add(Triplet.with(0, position, root));

        while (!queue.isEmpty()) {
            final int distance = queue.peek().getValue0();
            final int position = queue.peek().getValue1();
            final KeywordNode current = queue.poll().getValue2();

            for (final BasePair bp : current.keySet()) {
                final int totalDistance = text.get(position).distance(bp) + distance;

                if (maxDistance == -1 || totalDistance < maxDistance) {
                    final Optional<KeywordNode> node = current.get(bp);
                    if (node.isPresent()) {
                        if (position + 1 < text.length())
                            queue.add(Triplet.with(totalDistance, position + 1, node.get()));

                        final List<Alignment> results = node.get().getTerminals().stream().map(pattern -> Alignment.with(text, pattern, position - pattern.length() + 1, totalDistance))
                                .collect(Collectors.toList());
                        alignments.addAll(results);
                    }
                }
            }
        }

        return alignments;

    }
}
