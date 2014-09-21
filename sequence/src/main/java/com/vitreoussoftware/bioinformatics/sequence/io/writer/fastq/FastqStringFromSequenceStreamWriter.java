package com.vitreoussoftware.bioinformatics.sequence.io.writer.fastq;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.SequenceStreamReader;
import com.vitreoussoftware.bioinformatics.sequence.io.reader.StringStreamReader;
import org.javatuples.Pair;

import java.util.Optional;

/**
 * Maps a StringStreamReader who's string return values are in FASTA format into encoded sequences
 * @author John
 *
 */
public class FastqStringFromSequenceStreamWriter implements SequenceStreamReader {
	private final StringStreamReader reader;
	private FastaSequenceFactory factory;
	
	/**
	 * Process Sequence Strings into encoded sequences
	 * @param reader The StringStreamReader to process sequence strings from
	 */
	public FastqStringFromSequenceStreamWriter(StringStreamReader reader)
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
        final Pair<String,String> next = this.reader.next();
        return this.factory.fromString(next.getValue0(), next.getValue1());
	}
}
