package com.vitreoussoftware.bioinformatics.sequence.reader;

import java.io.IOException;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;

/**
 * A stream reader for pulling in Sequence data from some external source
 * @author John
 *
 */
public interface SequenceStreamReader extends AutoCloseable {

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
	 * @throws InvalidDnaFormatException 
	 */
	public abstract Sequence readRecord() throws IOException, InvalidDnaFormatException;

}
