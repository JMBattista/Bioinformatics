package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import java.io.IOException;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.reader.SequenceStreamReader;

/**
 * Create an instance of a Suffix Tree for use.
 * @author John
 *
 */
public interface SuffixTreeFactory {
	/**
	 * Create a SuffixTree based on a Sequence
	 * @param sequence the sequence
	 * @return the suffix tree for that sequence
	 */
	public SuffixTree create(Sequence sequence);
	
	/**
	 * Create a SuffixTree based on a SequenceCollection
	 * @param sequenceCollection the collection of sequences
	 * @return the SuffixTree for that collection of Sequences
	 */
	public SuffixTree create(SequenceCollection sequenceCollection);
	
	
	/**
	 * Create a SuffixTree based on a SequenceStreamREader.
	 * @param sequenceReader the SequenceStreamReader
	 * @return the SuffixTree
	 * @throws InvalidDnaFormatException 
	 * @throws IOException 
	 */
	public SuffixTree create(SequenceStreamReader sequenceReader) throws IOException, InvalidDnaFormatException;
	
}
