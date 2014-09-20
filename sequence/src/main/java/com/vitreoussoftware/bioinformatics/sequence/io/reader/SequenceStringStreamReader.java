package com.vitreoussoftware.bioinformatics.sequence.io.reader;

import org.javatuples.Pair;

import java.io.IOException;
import java.util.Iterator;

/**
 * A stream reader for pulling in Sequence data from some external source
 * @author John
 *
 */
public interface SequenceStringStreamReader extends AutoCloseable, Iterable<Pair<String,String>>, Iterator<Pair<String,String>> {

	/**
     * Does the stream reader still have a record?
	 * @return boolean indicator
	 * @throws IOException If the file cannot be accessed it may fail
	 */
	public abstract boolean hasNext();

	/**
     * Reads a record from the file
	 * @return the record
	 * @throws IOException something went wrong reading from the file 
	 */
	public abstract Pair<String,String> next();

    @Override
    default public Iterator<Pair<String,String>> iterator() {
        return this;
    }
}
