package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import com.vitreoussoftware.bioinformatics.alignment.Position;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;


class SuffixTreeNode {
	/**
	 * The set of child BasePairs
	 */
	private final HashMap<BasePair, SuffixTreeNode> children;
	private final Collection<Position> texts;
    private final BasePair basePair;

    SuffixTreeNode(BasePair basePair)
	{
		this.texts = new LinkedList<>();
		this.children = new HashMap<>();
        this.basePair = basePair;
	}
	
	/**
	 * Return the existing SuffixTreeNode for the given BasePair if it exists, otherwise fromCharacter a new node for that BasePair and return it.
	 * @param bp the key for the node
	 * @return the node for the key
	 */
	SuffixTreeNode getOrCreate(BasePair bp) {
		if (!children.containsKey(bp))
		{
			children.put(bp, new SuffixTreeNode(bp));
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
	Collection<? extends SuffixTreeNode> values() {
		return this.children.values();
	}

    /**
     * Retrieve all Keys for the children
     * @return return all the keys
     */
    Collection<BasePair> keySet() {
        return this.children.keySet();
    }

	int depth() {
		int max = 0;
		for (SuffixTreeNode node: this.children.values()) {
			max = Math.max(max, node.depth());
		}
		
		return max + 1;
	}

	Collection<Position> getTexts() {
		return this.texts;
	}

	void addPosition(Sequence sequence, int index) {
		this.texts.add(Position.with(sequence, index));
	}

    public BasePair getBasePair() {
        return this.basePair;
    }
}
