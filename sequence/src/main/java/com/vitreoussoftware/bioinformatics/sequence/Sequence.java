package com.vitreoussoftware.bioinformatics.sequence;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import javax.management.RuntimeErrorException;

import com.vitreoussoftware.bioinformatics.sequence.encoding.EncodingScheme;

/**
 * A DNA Sequence representation
 * @author John
 *
 */
public interface Sequence extends Iterable<BasePair> {
	/**
	 * Return an iterator that proceeds from the back to the front of the sequence
	 * @return the basepair iterator
	 */
	public default Iterable<BasePair> reverse() {
        return new Iterable<BasePair>() {

            @Override
            public void forEach(Consumer<? super BasePair> consumer) {
                iterator().forEachRemaining(consumer);
            }

            @Override
            public Iterator<BasePair> iterator() {
                return new Iterator<BasePair>() {
                    int index = length() -1;
                    @Override
                    public void forEachRemaining(Consumer<? super BasePair> consumer) {
                        int remaining = index;

                        try {
                            while (remaining >= 0)
                            {
                                consumer.accept(getBasePair(remaining));
                                remaining--;
                            }
                        } catch (InvalidDnaFormatException e) {
                            throw new RuntimeException("We encountered a badly encoded set of base pairs inside sequence iterator");
                        }
                    }

                    @Override
                    public boolean hasNext() {
                        return index >= 0;
                    }

                    @Override
                    public BasePair next() {
                        try {
                            BasePair bp = getBasePair(index);
                            index--;
                            return bp;
                        } catch (InvalidDnaFormatException e) {
                            throw new RuntimeException("We encountered a badly encoded set of base pairs inside sequence iterator");
                        }
                    }

                    private BasePair getBasePair(int index) throws InvalidDnaFormatException {
                        return get(index);
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException("Sequence is immutable");
                    }

                };
            }

            @Override
            public Spliterator<BasePair> spliterator() {
                throw new UnsupportedOperationException("Splitterator is not defined for Sequence");
            }
        };
    }

    /**
	 * Get the BasePair at the specified index
	 * @param index the index to get the BasePair from
	 * @return the BasePair
	 */
	public BasePair get(int index);

    @Override
	public String toString();

    @Override
	public int hashCode();

    @Override
	public boolean equals(Object obj);

    /**
	 * The length of the sequence
	 * @return length
	 */
	public int length();

    @Override
    public default void forEach(Consumer<? super BasePair> consumer) {
        iterator().forEachRemaining(consumer);
    }

    @Override
    public default Iterator<BasePair> iterator() {
        return new Iterator<BasePair>() {
            int index = 0;

            @Override
            public void forEachRemaining(Consumer<? super BasePair> consumer) {
                int remaining = index;

                try {
                    while (remaining < length())
                    {
                        consumer.accept(getBasePair(remaining));
                        remaining++;
                    }
                } catch (InvalidDnaFormatException e) {
                    throw new RuntimeException("We encountered a badly encoded set of base pairs inside sequence iterator");
                }
            }

            @Override
            public boolean hasNext() {
                return index < length();
            }

            @Override
            public BasePair next() {
                try {
                    BasePair bp = getBasePair(index);
                    index++;
                    return bp;
                } catch (InvalidDnaFormatException e) {
                    throw new RuntimeException("We encountered a badly encoded set of base pairs inside sequence iterator");
                }
            }

            private BasePair getBasePair(int index) throws InvalidDnaFormatException {
                return get(index);
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Sequence is immutable");
            }

        };
    }

    @Override
    public default Spliterator<BasePair> spliterator() {
        throw new UnsupportedOperationException("Splitterator is not defined for Sequence");
    }
}
