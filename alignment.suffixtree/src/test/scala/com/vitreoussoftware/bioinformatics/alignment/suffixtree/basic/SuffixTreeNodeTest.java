package com.vitreoussoftware.bioinformatics.alignment.suffixtree.basic;

import com.vitreoussoftware.bioinformatics.alignment.Position;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence;
import com.vitreoussoftware.bioinformatics.sequence.encoding.BasicDnaEncodingScheme;
import lombok.val;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Test the {@link SuffixTreeNode} for proper behavior
 * Created by John on 12/10/2016.
 */
public class SuffixTreeNodeTest {
    private static final BasePair ABP = BasicDnaEncodingScheme.A;
    private static final BasePair TBP = BasicDnaEncodingScheme.T;
    private static final Sequence ASEQ = BasicSequence.create("A", BasicDnaEncodingScheme.instance).get();
    private static final Sequence TSEQ = BasicSequence.create("T", BasicDnaEncodingScheme.instance).get();
    private static final Position APOS = Position.with(ASEQ, 0);
    private static final Position TPOS = Position.with(TSEQ, 0);

    private SuffixTreeNode node;

    @Before
    public void setup() {
        node = new SuffixTreeNode(ABP);
    }

    @Test
    public void testCreate() {
        assertThat(node.getBasePair(), is(ABP));
        assertThat(node.getTexts(), is(empty()));
    }

    @Test
    public void testGetOrCreate() throws Exception {
        val created = node.getOrCreate(TBP);

        assertThat(created.getBasePair(), is(TBP));
        assertThat(created.getTexts(), is(empty()));
        assertThat(node.getOrCreate(TBP), is(created));
    }

    @Test
    public void testContainsSingle() throws Exception {
        node.getOrCreate(TBP);

        assertThat(node.contains(TBP), is(true));
    }

    @Test
    public void testContainsMultiple() throws Exception {
        node.getOrCreate(ABP);
        node.getOrCreate(TBP);

        assertThat(node.contains(TBP), is(true));
    }

    @Test
    public void testcontainsCanFail() {
        node.getOrCreate(TBP);

        assertThat(node.contains(ABP), is(false));
    }

    @Test
    public void testcontainsCanFailEmpt() {
        assertThat(node.contains(ABP), is(false));
    }

    @Test
    public void testGet() throws Exception {
        val created = node.getOrCreate(TBP);

        assertThat(node.get(TBP), is(created));
    }

    @Test
    public void testGetMissing() throws Exception {
        assertThat(node.get(TBP), is(nullValue()));
    }

    @Test
    public void testValuesSingle() throws Exception {
        val createdA = node.getOrCreate(ABP);
        assertThat(node.values(), contains(createdA));
        assertThat(node.values().size(), is(1));
    }

    @Test
    public void testValuesMultiple() throws Exception {
        val createdA = node.getOrCreate(ABP);
        val createdT = node.getOrCreate(TBP);
        assertThat(node.values(), containsInAnyOrder(createdA, createdT));
        assertThat(node.values().size(), is(2));
    }

    @Test
    public void testValuesEmpty() throws Exception {
        assertThat(node.values(), is(empty()));
    }

    @Test
    public void testKeySetSingle() throws Exception {
        node.getOrCreate(ABP);
        assertThat(node.keySet(), contains(ABP));
        assertThat(node.keySet().size(), is(1));
    }

    @Test
    public void testKeySetMultiple() throws Exception {
        node.getOrCreate(ABP);
        node.getOrCreate(TBP);
        assertThat(node.keySet(), containsInAnyOrder(ABP, TBP));
        assertThat(node.keySet().size(), is(2));
    }

    @Test
    public void testKeySetEmpty() throws Exception {
        assertThat(node.keySet(), is(empty()));
    }
    
    @Test
    public void testGetTextsSingle() throws Exception {
        node.addPosition(APOS);
        assertThat(node.getTexts(), contains(APOS));
        assertThat(node.getTexts().size(), is(1));
    }

    @Test
    public void testGetTextsMultiple() throws Exception {
        node.addPosition(APOS);
        node.addPosition(TPOS);
        assertThat(node.getTexts(), containsInAnyOrder(APOS, TPOS));
        assertThat(node.getTexts().size(), is(2));
    }

    @Test
    public void testGetTextsEmpty() throws Exception {
        assertThat(node.getTexts(), is(empty()));
    }

    /**
     * Depth starts with 1 (for the current node).
     */
    @Test
    public void testDepthEmpty() throws Exception {
        assertThat(node.depth(), is(1));
    }

    /**
     * Depth starts with 1 (for the current node).
     */
    @Test
    public void testDepthSingle() throws Exception {
        node.getOrCreate(ABP);
        assertThat(node.depth(), is(2));
    }

    /**
     * Depth starts with 1 (for the current node).
     */
    @Test
    public void testDepthMany() throws Exception {
        val expectedDepth = (int) (Math.random() * 100);

        SuffixTreeNode prev = node;
        for (int i = 0; i < expectedDepth; i++) {
            prev = prev.getOrCreate(ABP);
        }

        assertThat(node.depth(), is(expectedDepth+1));
    }

}