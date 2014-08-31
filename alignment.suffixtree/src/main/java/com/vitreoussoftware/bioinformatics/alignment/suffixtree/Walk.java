package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import com.vitreoussoftware.bioinformatics.alignment.Alignment;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;

import java.util.Collection;
import java.util.Optional;

/**
 * Allows walking the suffix tree
 * @param <T> The metadata that will be used by this walk. Each node will be presented alongside its parents metadata.
 * @param <R> The result type the walk method should return after obtaining from getResult.
 * Created by John on 12/18/13.
 */
public interface Walk<T, R> {

    /**
     * The initial value for the metadata of the root nodes
     * @return the metadata for the root nodes
     */
    public T initialValue();

    /**
     * The result computed by the walk
     * @return the result
     */
    public R getResult();

    /**
     * Visit a position in the suffix tree, represented by its BasePair and the metadata that was assigned to its parent.
     * Root elements use the result of initialValue.
     *
     * @param basePair The BasePair for the current node
     * @param alignments
     *@param metadata The metadata associated with this nodes parent  @return the metadata to associate with this node
     */
    public Optional<T> visit(BasePair basePair, Collection<Alignment> alignments, T metadata);

    /**
     * Called after each visit that yields metadata to determine if the walk should continue.
     * By default this will return false resulting in a walk of all nodes.
     * @param metadata the metadata returned after visiting the most recent node.
     * @return true if the walk is finished and should return getResult, false otherwise.
     */
    public default boolean isFinished(T metadata) {
        return false;
    }

    /**
     * Compare two values of type T.
     * By default we try to use the objects implementation of comparable, if it does not implement comparable we say they are equal.
     * @param a The first value
     * @param b The second value
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    public default int compare(T a, T b) {
        if (a instanceof Comparable) {
            return ((Comparable) a).compareTo(b);
        }

        return 0;
    }
}
