package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import java.io.IOException;

import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTree;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTreeFactory;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.reader.SequenceStreamReader;

public class BasicSuffixTreeFactory implements SuffixTreeFactory {

	@Override
	public SuffixTree create(Sequence sequence) {
		return new BasicSuffixTree(sequence);
	}

	@Override
	public SuffixTree create(SequenceCollection sequenceCollection) {
		SuffixTree t = new BasicSuffixTree();
		for (Sequence s : sequenceCollection)
			t.addSequence(s);
		return t;
	}

	@Override
	public SuffixTree create(SequenceStreamReader sequenceReader) throws IOException, InvalidDnaFormatException {
		SuffixTree t = new BasicSuffixTree();
		
		while (sequenceReader.hasRecord())
			t.addSequence(sequenceReader.readRecord());
		
		return t;
	}

}
