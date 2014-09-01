package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import com.vitreoussoftware.bioinformatics.alignment.Alignment;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic.Position;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

/**
 * Wraps a Walk of type <T, R>, and behaves as a walk of type <T, R2>.
 * By default it will cast the result of the wrapped walk to (R2), if this is not the appropriate behavior you will
 * need to provide a Function<R, R2> to perform the conversion.
 * For example converting from Optional<Integer> to <Integer>, when it is certain to contain a value.
 * new WalkWrapper<T, Optional<Integer>, Integer>(walk, (x) -> x.get())
 *
 * Created by John on 12/22/13.
 */
public final class WalkWrapper<T, R, R2> implements Walk<T, R2> {
    private final Walk<T,R> wrappedWalk;
    private final Function<R, R2> convert;

    public WalkWrapper(Walk<T, R> wrappedWalk) {
        this.wrappedWalk = wrappedWalk;
        this.convert = (x) -> (R2) x;
    }

    public WalkWrapper(Walk<T, R> wrappedWalk, Function<R, R2> convert) {
        this.wrappedWalk = wrappedWalk;
        this.convert = convert;
    }

    @Override
    public T initialValue() {
        return wrappedWalk.initialValue();
    }

    @Override
    public final R2 getResult() {
        return this.convert.apply(this.wrappedWalk.getResult());
    }

    @Override
    public Optional<T> visit(BasePair basePair, Collection<Position> positions, T metadata) {
        return wrappedWalk.visit(basePair, positions, metadata);
    }

    @Override
    public boolean isFinished(T metadata) {
        return wrappedWalk.isFinished(metadata);
    }

    @Override
    public int compare(T a, T b) {
        return wrappedWalk.compare(a, b);
    }
}
