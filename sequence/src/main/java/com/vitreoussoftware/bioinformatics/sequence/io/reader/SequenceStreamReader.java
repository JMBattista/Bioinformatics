package com.vitreoussoftware.bioinformatics.sequence.io.reader;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.SequenceFactory;
import lombok.Builder;
import lombok.NonNull;
import org.javatuples.Pair;

/**
 * A stream reader for pulling in Sequence data from some external source
 * @author John
 *
 */
public class SequenceStreamReader implements AutoCloseable, Iterable<Optional<Sequence>>, Iterator<Optional<Sequence>> {
	private final StringStreamReader reader;
	private final SequenceFactory factory;

	/**
	 * Create a {@link SequenceStreamReader} that will produce sequences from the {@link StringStreamReader}
	 * @param reader The {@link StringStreamReader} to read sequences from
	 * @param factory The {@link SequenceFactory} to construct the proper {@link Sequence} instances
	 */
	@Builder
	protected SequenceStreamReader(@NonNull final StringStreamReader reader, @NonNull final SequenceFactory factory) {
		this.reader = reader;
		this.factory = factory;
	}

	/**
     * Does the stream reader still have a record?
	 * @return boolean indicator
	 * @throws IOException If the file cannot be accessed it may fail
	 */
	public boolean hasNext() {
		return reader.hasNext();
	}

	/**
     * Reads a record from the file
	 * @return the record
	 * @throws IOException something went wrong reading from the file 
	 * @throws InvalidDnaFormatException 
	 */
	public Optional<Sequence> next() {
		final Pair<String,String> next = this.reader.next();
		return this.factory.fromString(next.getValue0(), next.getValue1());
	}

    @Override
    public Iterator<Optional<Sequence>> iterator() {
        return this;
    }

	@Override
	public void close() throws Exception {
		reader.close();
	}
}
