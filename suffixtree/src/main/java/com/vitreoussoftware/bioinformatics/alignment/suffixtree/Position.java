package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;

/**
 * Created by John on 12/23/13.
 */
public class Position {
    final private Sequence sequence;
    final private int position;

    public Position(Sequence sequence, int position) {
        this.sequence = sequence;
        this.position = position;
    }

    public static Position with(Sequence sequence, int position) {
        return new Position(sequence, position);
    }

    public Sequence getSequence() {
        return this.sequence;
    }

    public int getPosition() {
        return this.position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position1 = (Position) o;

        if (position != position1.position) return false;
        if (sequence != null ? !sequence.equals(position1.sequence) : position1.sequence != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sequence != null ? sequence.hashCode() : 0;
        result = 31 * result + position;
        return result;
    }
}
