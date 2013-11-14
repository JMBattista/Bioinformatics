package com.vitreoussoftware.bioinformatics.alignment.suffixtree;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;

class SuffixTreeNode {
	/**
	 * The set of child BasePairs
	 */
	private final HashMap<BasePair, SuffixTreeNode> children;
	private final Collection<Sequence> parents;
	
	SuffixTreeNode()
	{
		this.parents = new HashSet<Sequence>();
		this.children = new HashMap<BasePair, SuffixTreeNode>();
	}
	
	/**
	 * Return the existing SuffixTreeNode for the given BasePair if it exists, otherwise create a new node for that BasePair and return it.
	 * @param bp the key for the node
	 * @return the node for the key
	 */
	SuffixTreeNode getOrCreate(BasePair bp) {
		if (!children.containsKey(bp))
		{
			children.put(bp, new SuffixTreeNode());
		}
		
		return children.get(bp);
	}

	boolean contains(BasePair bp) {
		return this.children.containsKey(bp);
	}

	SuffixTreeNode get(BasePair bp) {
		return this.children.get(bp);
	}

	int depth() {
		int max = 0;
		for (SuffixTreeNode node: this.children.values()) {
			max = Math.max(max, node.depth());
		}
		
		return max + 1;
	}

	Collection<Sequence> getParents() {
		return this.parents;
	}

	void addParent(Sequence sequence) {
		this.parents.add(sequence);
	}

}
