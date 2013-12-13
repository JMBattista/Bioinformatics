package com.vitreoussoftware.bioinformatics.sequence.reader;

import java.io.IOException;
import java.util.Iterator;

/**
 * A stream reader for pulling in Sequence data from some external source
 * @author John
 *
 */
public interface SequenceStringStreamReader extends AutoCloseable, Iterable<String>, Iterator<String> {

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
	public abstract String next();

    @Override
    default public Iterator<String> iterator() {
        return this;
    }
}
