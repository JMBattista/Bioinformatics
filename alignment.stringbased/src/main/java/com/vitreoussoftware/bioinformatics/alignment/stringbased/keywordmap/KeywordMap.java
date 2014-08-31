package com.vitreoussoftware.bioinformatics.alignment.stringbased.keywordmap;

import com.vitreoussoftware.bioinformatics.alignment.Alignment;
import com.vitreoussoftware.bioinformatics.alignment.PatternFirstAligner;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import org.javatuples.Pair;

import java.util.Collection;
import java.util.List;

/**
 * Created by John on 8/27/14.
 */
public class KeywordMap implements PatternFirstAligner{
    KeywordMap() {
    }

    @Override
    public SequenceCollection containedIn(Sequence text) {
        return null;
    }

    @Override
    public Collection<Alignment> getAlignments(Sequence text) {
        return null;
    }

    @Override
    public void addPattern(Sequence pattern) {

    }

    @Override
    public Collection<Pair<Integer, SequenceCollection>> shortestDistance(Sequence text) {
        return null;
    }

    @Override
    public Collection<Pair<Integer, SequenceCollection>> shortestDistance(Sequence text, int maxDistance) {
        return null;
    }

    @Override
    public Collection<Pair<Sequence, List<Integer>>> distances(Sequence text) {
        return null;
    }

    @Override
    public Collection<Pair<Sequence, List<Integer>>> distances(Sequence text, int maxDistance) {
        return null;
    }
}
