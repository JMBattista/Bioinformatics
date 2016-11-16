package com.vitreoussoftware.bioinformatics.sequence.encoding;

import com.google.common.collect.ImmutableList;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import org.javatuples.Pair;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Tests the AcceptUnkownDnaEncodingScheme class
 *
 * @author John
 */
@SuppressWarnings("AccessStaticViaInstance")
public class BasicDnaEncodingSchemeTest extends EncodingSchemeTestBase {
    private BasicDnaEncodingScheme scheme = new BasicDnaEncodingScheme();

    @DataPoints
    public static List<Character> getAcceptedCharacters() {
        return ImmutableList.of(
                'A',
                'T',
                'C',
                'G'
        );
    }

    @DataPoints
    public static List<Pair<Character, Character>> getFlipPairs() {
        return ImmutableList.of(
                Pair.with('A', 'T'),
                Pair.with('C', 'G')
        );
    }

    @DataPoint
    public static EncodingScheme getEncodingSchemeDataPoint() {
        return new BasicDnaEncodingScheme();
    }

    @Override
    public EncodingScheme getEncodingScheme() {
        return getEncodingSchemeDataPoint();
    }

    @Override
    public EncodingScheme getOtherEncodingScheme() {
        return IupacEncodingScheme.instance;
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme default values against byte codes
     */
    @Test
    public void testEqualityByteDefaultValues() throws InvalidDnaFormatException {
        assertThat(scheme.A, is(scheme.getValue('A')));
        assertThat(scheme.T, is(scheme.getValue('T')));
        assertThat(scheme.C, is(scheme.getValue('C')));
        assertThat(scheme.G, is(scheme.getValue('G')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, with same start
     */
    @Test
    public void testEqualityToSelf() throws InvalidDnaFormatException {
        final BasePair bp = scheme.create('A');
        assertThat(bp, is(bp));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme default values
     */
    @Test
    public void testEqualityDefaultValues() throws InvalidDnaFormatException {
        assertThat(scheme.A, is(scheme.create('A')));
        assertThat(scheme.T, is(scheme.create('T')));
        assertThat(scheme.C, is(scheme.create('C')));
        assertThat(scheme.G, is(scheme.create('G')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, with same start
     */
    @Test
    public void testEqualitySameStart() throws InvalidDnaFormatException {
        assertThat(scheme.create('A'), is(scheme.create('A')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, with different cases
     */
    @Test
    public void testEqualityDifferentCase() throws InvalidDnaFormatException {
        assertThat(scheme.create('A'), is(scheme.create('a')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, different base pair
     */
    @Test
    public void testEqualityNotSame() throws InvalidDnaFormatException {
        assertFalse(scheme.create('A').equals(scheme.create('T')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, against N
     */
    @Test(expected = InvalidDnaFormatException.class)
    public void testEqualityAgainstN() throws InvalidDnaFormatException {
        scheme.create('A').equals(scheme.create('N'));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, against N
     */
    @Test(expected = InvalidDnaFormatException.class)
    public void testEqualityTU() throws InvalidDnaFormatException {
        scheme.create('T').equals(scheme.create('U'));
    }
}
