package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import static junit.framework.Assert.*;

import org.junit.Test;

import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTreeFactory;
import com.vitreoussoftware.bioinformatics.alignment.suffixtree.SuffixTreeTest;

/**
 * Run SuffixTreeTest for BasicSuffixTree
 * @author John
 *
 */
public class BasicSuffixTreeTest extends SuffixTreeTest {
	@Override
	protected SuffixTreeFactory getSuffixTreeFactory() {
		return new BasicSuffixTreeFactory();
	}

}
