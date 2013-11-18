package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTree;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTreeFactory;
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
		
		return null;
	}

	@Override
	public SuffixTree create(SequenceStreamReader sequenceReader) {
		// TODO Auto-generated method stub
		return null;
	}

}
