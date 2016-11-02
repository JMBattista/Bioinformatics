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
    public void testEqualityByte_defaultValues() throws InvalidDnaFormatException {
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
    public void testEquality_toSelf() throws InvalidDnaFormatException {
        BasePair bp = scheme.create('A');
        assertThat(bp, is(bp));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme default values
     *
          */
    @Test
    public void testEquality_defaultValues() throws InvalidDnaFormatException {
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
    public void testEquality_sameStart() throws InvalidDnaFormatException {
        assertThat(scheme.create('A'), is(scheme.create('A')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, with different cases
     *
          */
    @Test
    public void testEquality_differentCase() throws InvalidDnaFormatException {
        assertThat(scheme.create('A'), is(scheme.create('a')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, different base pair
     *
          */
    @Test
    public void testEquality_notSame() throws InvalidDnaFormatException {
        assertFalse(scheme.create('A').equals(scheme.create('T')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, against N
     *
          */
    @Test
    public void testEquality_againstN() throws InvalidDnaFormatException {
        assertFalse(scheme.create('A').equals(scheme.create('N')));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, against N
     *
          */
    @Test
    public void testEquality_TU() throws InvalidDnaFormatException {
        assertFalse(scheme.create('T').equals(scheme.create('U')));
    }

    /**
          */
    @Test
    public void testCreation_A() throws InvalidDnaFormatException {
        assertThat("A", is(scheme.create('A').toString()));
    }

    /**
          */
    @Test
    public void testCreation_a() throws InvalidDnaFormatException {
        assertThat("A", is(scheme.create('a').toString()));
    }

    /**
          */
    @Test
    public void testCreation_T() throws InvalidDnaFormatException {
        assertThat("T", is(scheme.create('T').toString()));
    }

    /**
          */
    @Test
    public void testCreation_t() throws InvalidDnaFormatException {
        assertThat("T", is(scheme.create('t').toString()));
    }

    /**
          */
    @Test
    public void testCreation_C() throws InvalidDnaFormatException {
        assertThat("C", is(scheme.create('C').toString()));
    }

    /**
          */
    @Test
    public void testCreation_c() throws InvalidDnaFormatException {
        assertThat("C", is(scheme.create('c').toString()));
    }

    /**
          */
    @Test
    public void testCreation_G() throws InvalidDnaFormatException {
        assertThat("G", is(scheme.create('G').toString()));
    }

    /**
          */
    @Test
    public void testCreation_g() throws InvalidDnaFormatException {
        assertThat("G", is(scheme.create('g').toString()));
    }

    /**
          */
    @Test
    public void testCreation_U() throws InvalidDnaFormatException {
        assertThat("U", is(scheme.create('U').toString()));
    }

    /**
          */
    @Test
    public void testCreation_u() throws InvalidDnaFormatException {
        assertThat("U", is(scheme.create('u').toString()));
    }
}
