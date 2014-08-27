package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import java.io.IOException;

import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTree;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTreeFactory;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.basic.SequenceListFactory;
import com.vitreoussoftware.bioinformatics.sequence.reader.SequenceStreamReader;

/**
 * Create a BasicSuffixTree
 * @author John
 *
 */
public class BasicSuffixTreeFactory implements SuffixTreeFactory {

	private SequenceCollectionFactory factory;

	/**
	 * Create a BasicSuffixTreeFactory with a custom SequenceCollectionFactory
	 * @param factory the SequenceCollectionFactory to use with the SuffixTree
	 */
	public BasicSuffixTreeFactory(SequenceCollectionFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * Create a BasicSuffixTreeFactory with default configuration
	 */
	public BasicSuffixTreeFactory() {
		this.factory = new SequenceListFactory();
	}
	
	@Override
	public SuffixTree create(Sequence sequence) {
		SuffixTree tree = new BasicSuffixTree(this.factory);
		tree.addSequence(sequence);
		return tree;
	}

	@Override
	public SuffixTree create(SequenceCollection sequenceCollection) {
		SuffixTree t = new BasicSuffixTree(this.factory);
		for (Sequence s : sequenceCollection)
			t.addSequence(s);
		return t;
	}

	@Override
	public SuffixTree create(SequenceStreamReader sequenceReader) throws IOException, InvalidDnaFormatException {
		SuffixTree t = new BasicSuffixTree(this.factory);
		
		while (sequenceReader.hasNext())
			t.addSequence(sequenceReader.next().orElseThrow(() -> new RuntimeException("TODO update this exception")));
		
		return t;
	}

}
