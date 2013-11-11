package com.vitreoussoftare.bioinformatics.sequence.reader.fasta;

import java.io.IOException;

import com.vitreoussoftare.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftare.bioinformatics.sequence.Sequence;
import com.vitreoussoftare.bioinformatics.sequence.reader.SequenceStreamReader;
import com.vitreoussoftare.bioinformatics.sequence.reader.SequenceStringStreamReader;

/**
 * Maps a SequenceStringStreamReader who's string return values are in FASTA format into encoded sequences
 * @author John
 *
 */
public class SequenceFromFastaStringStreamReader implements SequenceStreamReader {
	private final SequenceStringStreamReader reader;
	
	/**
	 * Process Sequence Strings into encoded sequences
	 * @param reader The SequenceStringStreamReader to process sequence strings from
	 */
	public SequenceFromFastaStringStreamReader(SequenceStringStreamReader reader)
	{
		this.reader = reader;
	}
	
	@Override
	public void close() throws Exception {
		reader.close();
	}

	@Override
	public boolean hasRecord() throws IOException {
		return reader.hasRecord();
	}

	@Override
	public Sequence readRecord() throws IOException, InvalidDnaFormatException {
		return Sequence.fromFasta(this.reader.readRecord());
	}

}
