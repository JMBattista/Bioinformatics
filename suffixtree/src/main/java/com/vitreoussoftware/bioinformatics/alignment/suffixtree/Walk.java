package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import org.javatuples.Pair;

import java.util.Optional;

/**
 * Allows walking the suffix tree
 *
 * Created by John on 12/18/13.
 */
public interface Walk<T, R> {

    /**
     * Called to determine if the walk has completed yet, based on the most recent value
     * @param value the most recently seen value
     * @return if the walk is isFinished
     */
    public boolean isFinished(T value);

    /**
     * The initial value to use for the result type
     * @return
     */
    public T initialValue();

    /**
     * The result of the walk
     * @return the result
     */
    public R getResult();

    /**
     * Visit a position in the suffix tree, represented by its BasePair and the value that was assigned to its parent.
     * Root elements use the result of initialValue.
     * @param basePair The BasePair for the current node
     * @param value The value associated with this nodes parent
     * @return the value to associate with this node
     */
    public Optional<T> visit(BasePair basePair, T value);


    /**
     * Compare two values of type T.
     * By default we say they are equal unless comparable is implemented then we use that
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
