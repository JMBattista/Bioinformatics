package com.vitreoussoftware.bioinformatics.alignment;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import lombok.Value;

/**
 * Created by John on 8/31/14.
 */
@Value
public final class Position {
    final private Sequence text;
    final private int position;

    private Position(final Sequence text, final int position) {
        this.text = text;
        this.position = position;
    }


    public static Position with(final Sequence sequence, final int position) {
        return new Position(sequence, position);
    }

    @Override
    public String toString() {
        return String.format("(%d, %s)", this.position, this.text.toString());
    }
}
