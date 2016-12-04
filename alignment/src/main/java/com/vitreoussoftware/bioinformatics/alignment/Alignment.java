package com.vitreoussoftware.bioinformatics.alignment;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import lombok.Value;

/**
 * Created by John on 12/23/13.
 */
@Value
public class Alignment {
    final private Sequence text;
    final private Sequence pattern;
    final private int position;
    final private int distance;

    private Alignment(final Sequence text, final Sequence pattern, final int position, final int distance) {
        this.text = text;
        this.pattern = pattern;
        this.position = position;
        this.distance = distance;
    }


    public static Alignment with(final Sequence text, final Sequence pattern, final int position) {
        return new Alignment(text, pattern, position, 0);
    }

    public static Alignment with(final Sequence text, final Sequence pattern, final int position, final int distance) {
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
}
