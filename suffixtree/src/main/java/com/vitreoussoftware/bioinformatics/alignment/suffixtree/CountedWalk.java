package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import org.javatuples.Pair;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by John on 12/19/13.
 */
public class CountedWalk<T,R> implements Walk<T,R> {
    private final Walk<T, R> walker;
    private final AtomicInteger isFinished;
    private final AtomicInteger getResult;
    private final AtomicInteger visit;
    private final AtomicInteger initialValue;

    public CountedWalk(Walk<T, R> walker) {
        this.walker = walker;

        initialValue = new AtomicInteger(0);
        visit = new AtomicInteger(0);
        getResult = new AtomicInteger(0);
        isFinished = new AtomicInteger(0);
    }

    /**
     * Gets the number of time the isFinished method was called
     * @return the number of calls
     */
    public int getIsFinishedCount() {
        return isFinished.get();
    }

    /**
     * Gets the number of times the initialValue method was called
     * @return the number of calls
     */
    public int getInitialValueCount() {
        return initialValue.get();
    }

    /**
     * Gets the number of times the visit method was called
     * @return the number of nodes visited
     */
    public int getVisitCount() {
        return visit.get();
    }

    /**
     * Gets the number of times the getResult method was called as part of the walk
     * @return the number of calls
     */
    public int getResultcount() {
        return getResult.get();
    }

    @Override
    public boolean isFinished(T metadata) {
        isFinished.incrementAndGet();
        return walker.isFinished(metadata);
    }

    @Override
    public T initialValue() {
        initialValue.incrementAndGet();
        return walker.initialValue();
    }

    @Override
    public R getResult() {
        getResult.incrementAndGet();
        return walker.getResult();
    }

    @Override
    public Optional<T> visit(BasePair basePair, Collection<Position> positions, T metadata) {
        visit.incrementAndGet();
        return walker.visit(basePair, positions, metadata);
    }
}
