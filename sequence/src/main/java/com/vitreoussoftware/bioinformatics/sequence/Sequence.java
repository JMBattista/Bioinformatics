package com.vitreoussoftware.bioinformatics.sequence;

import com.vitreoussoftware.collections.Streamable;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A DNA Sequence representation
 * @author John
 *
 */
public interface Sequence extends Iterable<BasePair>, Streamable<BasePair> {

    String INVALID_BASEPAIR_ERROR = "We encountered a badly encoded set of base pairs inside sequence iterator";

    /**
	 * Get the BasePair at the specified index
	 * @param index the index to get the BasePair from
	 * @return the BasePair
	 */
	public BasePair get(int index);

    /**
     * Get the metadata string for the sequence
     * @return The metadata for the sequence
     */
    public String getMetadata();

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

    /**
     * Return an iterator that proceeds from the back to the front of the sequence
     * @return the basepair iterator
     */
    public default Iterable<BasePair> reverse() {
        return new Iterable<BasePair>() {

            @Override
            public void forEach(final Consumer<? super BasePair> consumer) {
                iterator().forEachRemaining(consumer);
            }

            @Override
            public Iterator<BasePair> iterator() {
                return new Iterator<BasePair>() {
                    int index = length() -1;
                    @Override
                    public void forEachRemaining(final Consumer<? super BasePair> consumer) {
                        int remaining = index;

                        try {
                            while (remaining >= 0)
                            {
                                consumer.accept(getBasePair(remaining));
                                remaining--;
                            }
                        } catch (final InvalidDnaFormatException e) {
                            throw new InvalidDnaFormatException(INVALID_BASEPAIR_ERROR);
                        }
                    }

                    @Override
                    public boolean hasNext() {
                        return index >= 0;
                    }

                    @Override
                    public BasePair next() {
                        try {
                            final BasePair bp = getBasePair(index);
                            index--;
                            return bp;
                        } catch (final InvalidDnaFormatException e) {
                            throw new InvalidDnaFormatException(INVALID_BASEPAIR_ERROR);
                        }
                    }

                    private BasePair getBasePair(final int index) throws InvalidDnaFormatException {
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

    @Override
    public default Stream<BasePair> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    @Override
    public default Stream<BasePair> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }

    @Override
    public default void forEach(final Consumer<? super BasePair> consumer) {
        iterator().forEachRemaining(consumer);
    }

    @Override
    public default Iterator<BasePair> iterator() {
        return new Iterator<BasePair>() {
            int index = 0;

            @Override
            public void forEachRemaining(final Consumer<? super BasePair> consumer) {
                try {
                    while (index < length())
                    {
                        consumer.accept(get(index));
                        index++;
                    }
                } catch (final InvalidDnaFormatException e) {
                    throw new InvalidDnaFormatException(INVALID_BASEPAIR_ERROR);
                }
            }

            @Override
            public boolean hasNext() {
                return index < length();
            }

            @Override
            public BasePair next() {
                try {
                    final BasePair bp = get(index);
                    index++;
                    return bp;
                } catch (final InvalidDnaFormatException e) {
                    throw new InvalidDnaFormatException(INVALID_BASEPAIR_ERROR);
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Sequence is immutable");
            }

        };
    }

    @Override
    public default Spliterator<BasePair> spliterator() {
        return new SequenceSpliterator(this, 0, length());
    }

    class SequenceSpliterator implements Spliterator<BasePair> {
        private final Sequence sequence;
        int origin;
        int fence;

        public SequenceSpliterator(final Sequence sequence, final int origin, final int fence) {
            this.sequence = sequence;
            this.origin = origin;
            this.fence = fence;
        }

        @Override
        public boolean tryAdvance(final Consumer<? super BasePair> action) {
            if (origin < fence) {
                action.accept(sequence.get(origin));
                origin++;
                return true;
            }

            return false;
        }

            @Override
            public Spliterator<BasePair> trySplit() {
            final int low = origin;
            final int mid = ((low + fence) >>> 1);
            if (low < mid) {
                origin = mid;
                return new SequenceSpliterator(sequence, low, mid);
            }

            return null;
        }

            @Override
            public long estimateSize() {
            return (fence - origin) / 2;
        }

            @Override
            public int characteristics() {
            return ORDERED | SIZED | IMMUTABLE | SUBSIZED | NONNULL;
        }
    }
}
