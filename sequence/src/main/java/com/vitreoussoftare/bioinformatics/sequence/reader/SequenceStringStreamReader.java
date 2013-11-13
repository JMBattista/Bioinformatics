package com.vitreoussoftare.bioinformatics.sequence.reader;

import java.io.IOException;

/**
 * A stream reader for pulling in Sequence data from some external source
 * @author John
 *
 */
public interface SequenceStringStreamReader extends AutoCloseable {

	/** 
	 * Does the stream reader still have a record?
	 * @return boolean indicator
	 * @throws IOException If the file cannot be accessed it may fail
	 */
	public abstract boolean hasRecord() throws IOException;

	/**
	 * Reads a record from the file
	 * @return the record
	 * @throws IOException something went wrong reading from the file 
	 */
	public abstract String readRecord() throws IOException;

}
