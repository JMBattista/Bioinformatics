package com.vitreoussoftware.bioinformatics.sequence.io.writer;

import org.javatuples.Pair;

import java.util.Iterator;

/**
 * A stream reader for pulling in Sequence data from some external source
 * @author John
 *
 */
public interface StringStreamWriter extends AutoCloseable, Iterable<Pair<String,String>>, Iterator<Pair<String,String>> {

	/**
     * Does the stream reader still have a record?
	 * @return boolean indicator
	 * @throws java.io.IOException If the file cannot be accessed it may fail
	 */
	public abstract boolean hasNext();

	/**
     * Reads a record from the file
	 * @return the record
	 * @throws java.io.IOException something went wrong reading from the file
	 */
	public abstract Pair<String,String> next();

    @Override
    default public Iterator<Pair<String,String>> iterator() {
        return this;
    }
}
