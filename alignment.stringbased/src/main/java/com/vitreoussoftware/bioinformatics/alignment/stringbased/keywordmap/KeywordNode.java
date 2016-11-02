package com.vitreoussoftware.bioinformatics.alignment.stringbased.keywordmap;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Optional;

/**
 * Created by John on 9/9/14.
 */
public class KeywordNode {
    /**
     * The set of child BasePairs
     */
    private final HashMap<BasePair, KeywordNode> children;
    private final BasePair basePair;
    private final LinkedList<Sequence> terminals;

    KeywordNode(BasePair basePair)
    {
        this.children = new HashMap<>();
        this.basePair = basePair;
        this.terminals = new LinkedList<>();
    }

    /**
     * Return the existing KeywordNode for the given BasePair if it exists, otherwise fromCharacter a new node for that BasePair and return it.
     * @param bp the key for the node
     * @return the node for the key
     */
    KeywordNode getOrCreate(BasePair bp) {
        if (!children.containsKey(bp))
        {
            children.put(bp, new KeywordNode(bp));
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
    Optional<KeywordNode> get(BasePair bp) {
        KeywordNode node = this.children.get(bp);
        if (node != null)
            return Optional.of(node);

        return Optional.empty();
    }

    /**
     * Retrieve all children of this node
     * @return return all the children
     */
    Collection<? extends KeywordNode> values() {
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
        for (KeywordNode node: this.children.values()) {
            max = Math.max(max, node.depth());
        }

        return max + 1;
    }

    public BasePair getBasePair() {
        return this.basePair;
    }

    /**
     *
     * @return The collections of patterns that are teminated at this node
     */
    public Collection<Sequence> getTerminals() {
        return this.terminals;
    }

    /**
     * Set this node as terminal for the given pattern.
     * @param pattern the pattern fro which this node is terminal
     */
    public void setTerminal(Sequence pattern) {
        this.terminals.add(pattern);
    }

    @Override
    public String toString() {
        return this.basePair.toString() + this.terminals;
    }
}
