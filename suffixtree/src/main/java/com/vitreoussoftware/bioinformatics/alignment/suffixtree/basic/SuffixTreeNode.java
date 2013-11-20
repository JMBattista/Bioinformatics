package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.basic.SequenceSetFactory;

class SuffixTreeNode {
	/**
	 * The set of child BasePairs
	 */
	private final HashMap<BasePair, SuffixTreeNode> children;
	private final SequenceCollection parents;
	
	SuffixTreeNode()
	{
		this.parents = new SequenceSetFactory().getSequenceCollection();
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

	/**
	 * Retrieve the child that matches the given BasePair
	 * @param bp the BasePair to match against
	 * @return the matching child
	 */
	SuffixTreeNode get(BasePair bp) {
		return this.children.get(bp);
	}
	
	/**
	 * Retrieve all children of this node
	 * @return return all the children
	 */
	Collection<? extends SuffixTreeNode> getAll() {
		return this.children.values();
	}

	int depth() {
		int max = 0;
		for (SuffixTreeNode node: this.children.values()) {
			max = Math.max(max, node.depth());
		}
		
		return max + 1;
	}

	SequenceCollection getParents() {
		return this.parents;
	}

	void addParent(Sequence sequence) {
		this.parents.add(sequence);
	}
}
