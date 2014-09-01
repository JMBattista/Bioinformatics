package com.vitreoussoftware.collections;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Interface to add Stream functionality
 * Created by John on 8/31/14.
 */
public interface Streamable<T> {
    /**
     * Create a stream from this object
     * @return a Stream
     */
    public Stream<T> stream();

    /**
     * Create a parallel stream from this object
     * @return a parallel Stream
     */
    public Stream<T> parallelStream();

    /**
     * Create a Streamable from a java collection implicitly, as it already implements the required methods.
     * @param collection the java collection
     * @param <T> The type of the collection
     * @return the streamable object
     */
    public static <T> Streamable<T> from(Collection<T> collection) {
        return (Streamable<T>) collection;
    }
}
