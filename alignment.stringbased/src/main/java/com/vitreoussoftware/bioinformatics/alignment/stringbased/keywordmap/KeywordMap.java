package com.vitreoussoftware.bioinformatics.alignment.stringbased.keywordmap;

import com.vitreoussoftware.bioinformatics.alignment.Alignment;
import com.vitreoussoftware.bioinformatics.alignment.PatternFirstAligner;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import javafx.scene.layout.Priority;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by John on 8/27/14.
 */
public class KeywordMap implements PatternFirstAligner{
    private KeywordNode root;

    KeywordMap() {
        root = new KeywordNode(null);
    }

    @Override
    public void addPattern(final Sequence pattern) {
        KeywordNode current = root;
        for (BasePair bp: pattern) {
            current = current.getOrCreate(bp);
        }
        current.setTerminal(pattern);
    }

    @Override
    public boolean contained(Sequence text) {
        for (int startPos = 0; startPos < text.length(); startPos++) {
            Optional<KeywordNode> current = Optional.of(root);
            for (int index = 0; startPos + index < text.length() && current.isPresent(); index++) {
                current = current.get().get(text.get(startPos + index));
                if (current.map(n -> n.getTerminals().size() > 0).orElse(false))
                    return true;
            }
        }

        return false;
    }

    @Override
    public Collection<Alignment> getAlignments(Sequence text) {
        Collection<Alignment> alignments = new LinkedList<>();

        for (int startPos = 0; startPos < text.length(); startPos++) {
            Optional<KeywordNode> current = root.get(text.get(startPos));
            for (int index = 1; index < text.length() && current.isPresent(); index++) {
                final int position = startPos + index;
                current = current.get().get(text.get(position));
                if (current.isPresent()) {
                    List<Alignment> results = current.get().getTerminals().stream().map(pattern -> Alignment.with(text, pattern, position, 0))
                            .collect(Collectors.toList());
                    alignments.addAll(results);
                }
            }
        }

        return alignments;
    }

    @Override
    public Collection<Alignment> shortestDistance(Sequence text, int maxDistance) {
        Collection<Alignment> alignments = new LinkedList<>();
        PriorityQueue<Triplet<Integer, Integer, KeywordNode>> queue = new PriorityQueue<>(30, (a,b) -> a.getValue0() - b.getValue0());

        return new LinkedList<>();
    }

    @Override
    public Collection<Alignment> distances(Sequence text, int maxDistance) {
        return new LinkedList<>();
    }
}
