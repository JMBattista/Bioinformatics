package com.vitreoussoftware.bioinformatics.alignment;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import lombok.Value;

/**
 * Represents an {@link Alignment} between two {@link Sequence} objects, one the Text, the other the Pattern.
 * Where the Text represents the full {@link Sequence} and the Pattern represents a Read from some {@link Sequence}
 *
 * Created by John on 12/23/13.
 */
@Value
public final class Alignment {
    final private Sequence text;
    final private Sequence pattern;
    final private int position;
    final private int distance;

    /**
     * Create an Alignment between the {@see pattern} and the {@see text} at {@see position} with {@see distance}
     *
     * @param text the Text this {@link Alignment} references
     * @param pattern the Pattern this {@link Alignment} references
     * @param position the index into the {@see text} at which the {@see pattern} starts
     * @param distance the distance between the {@see pattern} and {@see text} for this starting position
     */
    private Alignment(final Sequence text, final Sequence pattern, final int position, final int distance) {
        this.text = text;
        this.pattern = pattern;
        this.position = position;
        this.distance = distance;
    }

    /**
     * Create an Alignment between the {@see pattern} and the {@see text} at {@see position}
     *
     * @param text the Text this {@link Alignment} references
     * @param pattern the Pattern this {@link Alignment} references
     * @param position the index into the {@see text} at which the {@see pattern} starts
     *
     * @return The {@link Alignment}
     */
    public static Alignment with(final Sequence text, final Sequence pattern, final int position) {
        return new Alignment(text, pattern, position, 0);
    }

    /**
     * Create an Alignment between the {@see pattern} and the {@see text} at {@see position} with {@see distance}
     *
     * @param text the Text this {@link Alignment} references
     * @param pattern the Pattern this {@link Alignment} references
     * @param position the index into the {@see text} at which the {@see pattern} starts
     * @param distance the distance between the {@see pattern} and {@see text} for this starting position
     *
     * @return The {@link Alignment}
     */
    public static Alignment with(final Sequence text, final Sequence pattern, final int position, final int distance) {
        return new Alignment(text, pattern, position, distance);
    }

    @Override
    public String toString() {
        return String.format("(p:%d, d:%d, p:%s, t:%s)", this.position, this.distance, this.pattern.toString(), this.text.toString());
    }
}
