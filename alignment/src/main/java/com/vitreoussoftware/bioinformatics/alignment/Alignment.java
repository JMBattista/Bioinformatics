package com.vitreoussoftware.bioinformatics.alignment;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;

/**
 * Created by John on 12/23/13.
 */
public class Alignment {
    final private Sequence text;
    final private Sequence pattern;
    final private int position;
    final private int distance;

    private Alignment(Sequence text, Sequence pattern, int position, int distance) {
        this.text = text;
        this.pattern = pattern;
        this.position = position;
        this.distance = distance;
    }


    public static Alignment with(Sequence text, Sequence pattern, int position) {
        return new Alignment(text, pattern, position, 0);
    }

    public static Alignment with(Sequence text, Sequence pattern, int position, int distance) {
        return new Alignment(text, pattern, position, distance);
    }

    public Sequence getText() {
        return this.text;
    }

    public Sequence getPattern() {
        return this.pattern;
    }

    public int getPosition() { return this.position;
    }

    public int getDistance() { return this.distance; }

    @Override
    public String toString() {
        return String.format("(p:%d, d:%d, p:%s, t:%s)", this.position, this.distance, this.pattern.toString(), this.text.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alignment alignment1 = (Alignment) o;

        if (position != alignment1.position) return false;
        if (distance != alignment1.distance) return false;
        if (text != null ? !text.equals(alignment1.text) : alignment1.text != null) return false;
        if (pattern != null ? !pattern.equals(alignment1.pattern) : alignment1.pattern != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = text.hashCode();
        result = 31 * result + pattern.hashCode();
        result = 31 * result + position;
        result = 31 * result + distance;
        return result;
    }
}
