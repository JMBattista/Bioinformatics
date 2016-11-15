package com.vitreoussoftware.bioinformatics.sequence.encoding;

import com.google.common.collect.ImmutableList;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import lombok.val;
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
public class AcceptUnknownDnaEncodingSchemeTest extends EncodingSchemeTestBase {
    private AcceptUnknownDnaEncodingScheme scheme = new AcceptUnknownDnaEncodingScheme();

    @DataPoints
    public static List<Character> getAcceptedCharacters() {
        return ImmutableList.of(
                'A',
                'T',
                'C',
                'U',
                'G',
                'N',
                'R',
                'Y',
                'K',
                'M',
                'S',
                'W',
                'B',
                'D',
                'H',
                'V',
                'X',
                '-'
        );
    }

    @DataPoints
    public static List<Pair<Character, Character>> getFlipPairs() {
        // See https://en.wikipedia.org/wiki/Complementarity_(molecular_biology)
        return ImmutableList.of(
                Pair.with('A', 'T'),
                Pair.with('C', 'G'),
                Pair.with('R', 'Y'),
                Pair.with('S', 'S'),
                Pair.with('W', 'W'),
                Pair.with('K', 'M'),
                Pair.with('B', 'V'), // Not A -> Not T
                Pair.with('D', 'H'), // Not C -> Not G
                Pair.with('N', 'N'),
                Pair.with('X', 'X'),
                Pair.with('-', '-')
        );
    }

    @DataPoint
    public static EncodingScheme getEncodingScheme() {
        return new AcceptUnknownDnaEncodingScheme();
    }

    /**
     * Demonstrate that A is the complement of U
     */
    @Test
    public void theoryBasePairComplementUtoA() {
        val initial = scheme.fromCharacter('U');
        val complement = scheme.fromCharacter('A');

        assertThat(initial.complement(), is(complement));
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
        assertThat(scheme.U, is(scheme.getValue('U')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, with same start
     */
    @Test
    public void testEqualityToSelf() throws InvalidDnaFormatException {
        final BasePair bp = scheme.fromCharacter('A');
        assertThat(bp, is(bp));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme default values
     */
    @Test
    public void testEqualityDefaultValues() throws InvalidDnaFormatException {
        assertThat(scheme.A, is(scheme.fromCharacter('A')));
        assertThat(scheme.T, is(scheme.fromCharacter('T')));
        assertThat(scheme.C, is(scheme.fromCharacter('C')));
        assertThat(scheme.G, is(scheme.fromCharacter('G')));
        assertThat(scheme.U, is(scheme.fromCharacter('U')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, with same start
     */
    @Test
    public void testEqualitySameStart() throws InvalidDnaFormatException {
        assertThat(scheme.fromCharacter('A'), is(scheme.fromCharacter('A')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, with different cases
     */
    @Test
    public void testEqualityDifferentCase() throws InvalidDnaFormatException {
        assertThat(scheme.fromCharacter('A'), is(scheme.fromCharacter('a')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, different base pair
     */
    @Test
    public void testEqualityNotSame() throws InvalidDnaFormatException {
        assertFalse(scheme.fromCharacter('A').equals(scheme.fromCharacter('T')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, against N
     */
    @Test
    public void testEqualityAgainstN() throws InvalidDnaFormatException {
        assertFalse(scheme.fromCharacter('A').equals(scheme.fromCharacter('N')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, against N
     */
    @Test
    public void testEqualityTU() throws InvalidDnaFormatException {
        assertFalse(scheme.fromCharacter('T').equals(scheme.fromCharacter('U')));
    }

}
