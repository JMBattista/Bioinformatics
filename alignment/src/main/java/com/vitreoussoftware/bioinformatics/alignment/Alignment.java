package com.vitreoussoftware.bioinformatics.alignment;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;

/**
 * Created by John on 12/23/13.
 */
public class Alignment {
    final private Sequence sequence;
    final private int position;
    final private int distance;

    private Alignment(Sequence text, int position, int distance) {
        this.sequence = text;
        this.position = position;
        this.distance = distance;
    }


    public static Alignment with(Sequence sequence, int position) {
        return new Alignment(sequence, position, 0);
    }

    public static Alignment with(Sequence text, int position, int distance) {
        return new Alignment(text, position, distance);
    }

    public Sequence getSequence() {
        return this.sequence;
    }

    public int getPosition() {
        return this.position;
    }

    public int getDistance() { return this.distance; }

    @Override
    public String toString() {
        return String.format("(%d, %d, %s)", this.position, this.distance, this.sequence.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alignment alignment1 = (Alignment) o;

        if (position != alignment1.position) return false;
        if (distance != alignment1.distance) return false;
        if (sequence != null ? !sequence.equals(alignment1.sequence) : alignment1.sequence != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sequence != null ? sequence.hashCode() : 0;
        result = 31 * result + position;
        result = 51 * result + distance;
        return result;
    }
}
