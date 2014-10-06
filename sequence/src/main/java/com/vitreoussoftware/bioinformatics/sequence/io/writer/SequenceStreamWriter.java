package com.vitreoussoftware.bioinformatics.sequence.io.writer;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

/**
 * A stream reader for writing Sequence data to another location
 * @author John
 *
 */
public interface SequenceStreamWriter extends AutoCloseable {

	/**
     * Write the sequence to the stream
	 * @return the number of characters written
	 */
	public int write(Sequence sequence) throws IOException;
}
