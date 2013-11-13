package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import java.util.HashMap;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;

class SuffixTreeNode {
	/**
	 * The set of child BasePairs
	 */
	private final HashMap<BasePair, SuffixTreeNode> children;
	
	public SuffixTreeNode()
	{
		this.children = new HashMap<BasePair, SuffixTreeNode>();
	}
	
	/**
	 * Return the existing SuffixTreeNode for the given BasePair if it exists, otherwise create a new node for that BasePair and return it.
	 * @param bp the key for the node
	 * @return the node for the key
	 */
	public SuffixTreeNode getOrCreate(BasePair bp) {
		if (!children.containsKey(bp))
		{
			children.put(bp, new SuffixTreeNode());
		}
		
		return children.get(bp);
	}

	public boolean contains(BasePair bp) {
		return this.children.containsKey(bp);
	}

	public SuffixTreeNode get(BasePair bp) {
		return this.children.get(bp);
	}

}
