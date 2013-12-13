package com.vitreoussoftware.bioinformatics.sequence.reader.fasta;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.reader.SequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.reader.SequenceStringStreamReader;

import java.util.Optional;

/**
 * Maps a SequenceStringStreamReader who's string return values are in FASTA format into encoded sequences
 * @author John
 *
 */
public class SequenceFromFastaStringStreamReader implements SequenceStreamReader {
	private final SequenceStringStreamReader reader;
	private FastaSequenceFactory factory;
	
	/**
	 * Process Sequence Strings into encoded sequences
	 * @param reader The SequenceStringStreamReader to process sequence strings from
	 */
	public SequenceFromFastaStringStreamReader(SequenceStringStreamReader reader)
	{
		this.reader = reader;
		this.factory = new FastaSequenceFactory();
	}
	
	@Override
	public void close() throws Exception {
		reader.close();
	}

	@Override
    public boolean hasNext() {
		return reader.hasNext();
	}

	@Override
    public Optional<Sequence> next() {
		return this.factory.fromString(this.reader.next());
	}
}
