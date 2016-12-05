package com.vitreoussoftware.bioinformatics.sequence.basic;

import com.google.common.collect.ImmutableList;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.encoding.AcceptUnknownDnaEncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.encoding.BasicDnaEncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.encoding.EncodingScheme;
import lombok.val;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Test parts of the {@link BasicSequence} also {@see SequenceTest}
 * <p>
 * Created by John on 10/26/2016.
 */
public class BasicSequenceTest {
    private EncodingScheme schemeA;
    private EncodingScheme schemeB;

    @Before
    public void setup() {
        this.schemeA = AcceptUnknownDnaEncodingScheme.instance;
        this.schemeB = BasicDnaEncodingScheme.instance;
    }

    @Test
    public void testCreateListBasePair() {
        val sequence = BasicSequence.create(ImmutableList.of(AcceptUnknownDnaEncodingScheme.A));

        assertThat(sequence.isPresent(), is(true));
        assertThat(sequence.get().length(), is(1));
        assertThat(sequence.get().get(0), is(AcceptUnknownDnaEncodingScheme.A));
    }

    @Test
    public void testCreateListBasePairFailNull() {
        val sequence = BasicSequence.create(null);

        assertThat(sequence.isPresent(), is(false));
    }

    @Test
    public void testCreateListBasePairFail0len() {
        val sequence = BasicSequence.create(ImmutableList.<BasePair>of());

        assertThat(sequence.isPresent(), is(false));
    }

    @Test
    public void testEqualsWithDifferentEncodingSchemes() {
        val sequenceA = BasicSequence.create("A", schemeA);
        val sequenceB = BasicSequence.create("A", schemeB);

        assertThat(sequenceA, is(sequenceB));
    }

    @Test
    public void testEqualsWithDifferentEncodingSchemesCanFail() {
        val sequenceA = BasicSequence.create("A", schemeA);
        val sequenceB = BasicSequence.create("T", schemeB);

        assertThat(sequenceA, is(not(sequenceB)));
    }
}
