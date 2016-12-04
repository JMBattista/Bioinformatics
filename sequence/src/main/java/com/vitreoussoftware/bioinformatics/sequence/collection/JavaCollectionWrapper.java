package com.vitreoussoftware.bioinformatics.sequence.collection;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;

/**
 * Wrapper to convert a Java Collection<Sequence> to a SequenceCollecttion
 * Created by John on 8/31/14.
 */
public class JavaCollectionWrapper implements SequenceCollection {

    private final Collection<Sequence> collection;

    public JavaCollectionWrapper(final Collection<Sequence> collection) {
        this.collection = collection;
    }

    public Iterator<Sequence> iterator() {
        return collection.iterator();
    }

    public boolean add(final Sequence basePairs) {
        return collection.add(basePairs);
    }

    public boolean containsAll(final Collection<?> c) {
        return collection.containsAll(c);
    }

    public boolean addAll(final Collection<? extends Sequence> c) {
        return collection.addAll(c);
    }

    public String toString() {
        return collection.toString();
    }

    public boolean equals(final Object o) {
        return collection.equals(o);
    }

    public int hashCode() {
        return collection.hashCode();
    }

    public int size() {
        return collection.size();
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public boolean contains(final Object o) {
        return collection.contains(o);
    }

    public Object[] toArray() {
        return collection.toArray();
    }

    public <T> T[] toArray(final T[] a) {
        return collection.toArray(a);
    }

    public boolean remove(final Object o) {
        return collection.remove(o);
    }

    public void clear() {
        collection.clear();
    }

    public boolean removeAll(final Collection<?> c) {
        return collection.removeAll(c);
    }

    public boolean retainAll(final Collection<?> c) {
        return collection.retainAll(c);
    }

    public Spliterator<Sequence> spliterator() {
        return collection.spliterator();
    }
}
