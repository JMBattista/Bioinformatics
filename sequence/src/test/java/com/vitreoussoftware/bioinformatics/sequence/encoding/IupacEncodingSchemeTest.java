package com.vitreoussoftware.bioinformatics.sequence.encoding;

import com.google.common.collect.ImmutableList;
import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import org.junit.Test;

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
public class IupacEncodingSchemeTest extends EncodingSchemeTestBase {
    private IupacEncodingScheme scheme = new IupacEncodingScheme();

    @Override
    public List<Character> getAcceptedCharacters() {
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
                'V'
        );
    }

    @Override
    public EncodingScheme getEncodingScheme() {
        return scheme;
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme default values against byte codes
     *
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
     *
     */
    @Test
    public void testEqualityToSelf() throws InvalidDnaFormatException {
        BasePair bp = scheme.create('A');
        assertThat(bp, is(bp));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme default values
     *
          */
    @Test
    public void testEqualityDefaultValues() throws InvalidDnaFormatException {
        assertThat(scheme.A, is(scheme.create('A')));
        assertThat(scheme.T, is(scheme.create('T')));
        assertThat(scheme.C, is(scheme.create('C')));
        assertThat(scheme.G, is(scheme.create('G')));
        assertThat(scheme.U, is(scheme.create('U')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, with same start
     *
          */
    @Test
    public void testEqualitySameStart() throws InvalidDnaFormatException {
        assertThat(scheme.create('A'), is(scheme.create('A')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, with different cases
     *
          */
    @Test
    public void testEqualityDifferentCase() throws InvalidDnaFormatException {
        assertThat(scheme.create('A'), is(scheme.create('a')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, different base pair
     *
          */
    @Test
    public void testEqualityNotSame() throws InvalidDnaFormatException {
        assertFalse(scheme.create('A').equals(scheme.create('T')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, against N
     *
          */
    @Test
    public void testEqualityAgainstN() throws InvalidDnaFormatException {
        assertFalse(scheme.create('A').equals(scheme.create('N')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, against N
     *
          */
    @Test
    public void testEqualityTU() throws InvalidDnaFormatException {
        assertFalse(scheme.create('T').equals(scheme.create('U')));
    }

    /**
          */
    @Test
    public void testCreationA() throws InvalidDnaFormatException {
        assertThat("A", is(scheme.create('A').toString()));
    }

    /**
          */
    @Test
    public void testCreationLowerA() throws InvalidDnaFormatException {
        assertThat("A", is(scheme.create('a').toString()));
    }

    /**
          */
    @Test
    public void testCreationT() throws InvalidDnaFormatException {
        assertThat("T", is(scheme.create('T').toString()));
    }

    /**
          */
    @Test
    public void testCreationLowerT() throws InvalidDnaFormatException {
        assertThat("T", is(scheme.create('t').toString()));
    }

    /**
          */
    @Test
    public void testCreationC() throws InvalidDnaFormatException {
        assertThat("C", is(scheme.create('C').toString()));
    }

    /**
          */
    @Test
    public void testCreationLowerC() throws InvalidDnaFormatException {
        assertThat("C", is(scheme.create('c').toString()));
    }

    /**
          */
    @Test
    public void testCreationG() throws InvalidDnaFormatException {
        assertThat("G", is(scheme.create('G').toString()));
    }

    /**
          */
    @Test
    public void testCreationLowerG() throws InvalidDnaFormatException {
        assertThat("G", is(scheme.create('g').toString()));
    }

    /**
          */
    @Test
    public void testCreationU() throws InvalidDnaFormatException {
        assertThat("U", is(scheme.create('U').toString()));
    }

    /**
          */
    @Test
    public void testCreatioLowerU() throws InvalidDnaFormatException {
        assertThat("U", is(scheme.create('u').toString()));
    }
}
