package com.vitreoussoftware.bioinformatics.sequence.io.reader;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;

/**
 * A stream reader for pulling in Sequence data from some external source
 * @author John
 *
 */
public interface SequenceStreamReader extends AutoCloseable, Iterable<Optional<Sequence>>, Iterator<Optional<Sequence>> {

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
	 * @throws InvalidDnaFormatException 
	 */
	public abstract Optional<Sequence> next();

    @Override
    default public Iterator<Optional<Sequence>> iterator() {
        return this;
    }
}
