package com.vitreoussoftware.bioinformatics.sequence;

import com.vitreoussoftware.bioinformatics.sequence.encoding.BasicDnaEncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.encoding.EncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.encoding.EncodingSchemeTestBase;
import com.vitreoussoftware.bioinformatics.sequence.encoding.ExpandedIupacEncodingScheme;
import lombok.val;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Tests the BasePair class
 *
 * @author John
 */
public class BasePairTest {
    private EncodingScheme scheme;
    private EncodingScheme otherScheme;

    /**
     * Setup the test object
     */
    @Before
    public void setup() {
        this.scheme = ExpandedIupacEncodingScheme.instance;
        this.otherScheme = BasicDnaEncodingScheme.instance;
    }

    /**
     * Test Equality for BasePair, with same start
     */
    @Test
    public void testEqualityToSelf() throws InvalidDnaFormatException {
        val bp = BasePair.create('A', scheme);
        assertThat(bp, is(bp));
    }


    /**
     * Test equality with different {@link EncodingScheme}
     */
    @Test
    public void testEqualityDifferentScheme() {
        val bp = BasePair.create('A', scheme);
        val other = BasePair.create('A', otherScheme);

        assertThat(bp, is(other));
    }

    /**
     * Test Equality for BasePair, with same start
     */
    @Test
    public void testEqualitySameStart() throws InvalidDnaFormatException {
        assertThat(BasePair.create('A', scheme), is(BasePair.create('A', scheme)));
    }

    /**
     * Test Equality for BasePair, with different cases
     */
    @Test
    public void testEqualityDifferentCase() throws InvalidDnaFormatException {
        assertThat(BasePair.create('A', scheme), is(BasePair.create('a', scheme)));
    }

    /**
     * Test Equality for BasePair, different base pair
     */
    @Test
    public void testEqualityNotSame() throws InvalidDnaFormatException {
        assertThat(BasePair.create('A', scheme), is(not(BasePair.create('T', scheme))));
    }

    /**
     * Test that we can perform some creation. This is primarily covered via {@link EncodingSchemeTestBase}
     */
    @Test
    public void testCreation() throws InvalidDnaFormatException {
        assertThat(BasePair.create('A', scheme).toString(), is("A"));
    }

    /**
     * Test that we can fail at creating some {@link BasePair}
     */

    @Test(expected = InvalidDnaFormatException.class)
    public void testCreationCanFAil() throws InvalidDnaFormatException {
        BasePair.create('Q', scheme);
    }

    /**
     * Test that distance is 0 when the {@link BasePair} values are the same
     */
    @Test
    public void testDistanceSame() {
        val bp = BasePair.create('A', scheme);
        assertThat(bp.distance(bp), is(0));
    }

    /**
     * Test that distance is 1 when the {@link BasePair} values are the same
     */
    @Test
    public void testDistanceDifferent() {
        val a = BasePair.create('A', scheme);
        val b = BasePair.create('T', scheme);
        assertThat(a.distance(b), is(1));
    }
}
