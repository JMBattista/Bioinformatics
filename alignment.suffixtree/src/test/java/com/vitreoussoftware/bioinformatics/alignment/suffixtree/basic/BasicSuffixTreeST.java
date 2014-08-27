package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTreeFactory;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTreeTest;

/**
 * Run SuffixTreeTest for BasicSuffixTree
 * @author John
 *
 */
public class BasicSuffixTreeST extends SuffixTreeTest {
	@Override
	protected SuffixTreeFactory getSuffixTreeFactory() {
		return new BasicSuffixTreeFactory();
	}

}
