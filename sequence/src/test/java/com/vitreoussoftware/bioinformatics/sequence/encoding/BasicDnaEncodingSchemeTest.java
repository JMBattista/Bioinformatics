package com.vitreoussoftware.bioinformatics.sequence.encoding;

import com.vitreoussoftware.bioinformatics.sequence.BasePair;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Tests the AcceptUnkownDnaEncodingScheme class
 *
 * @author John
 */
@SuppressWarnings("AccessStaticViaInstance")
public class BasicDnaEncodingSchemeTest {
    private BasicDnaEncodingScheme scheme = new BasicDnaEncodingScheme();

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
    @Test(expected = InvalidDnaFormatException.class)
    public void testEquality_againstN() throws InvalidDnaFormatException {
        scheme.create('A').equals(scheme.create('N'));
    }

    /**
     * Test Equality for AcceptUnkownDnaEncodingScheme, against N
     *
          */
    @Test(expected = InvalidDnaFormatException.class)
    public void testEquality_TU() throws InvalidDnaFormatException {
        scheme.create('T').equals(scheme.create('U'));
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
     * U is not supported in this encoding scheme, break
     */
    @Test(expected = InvalidDnaFormatException.class)
    public void testCreation_U() throws InvalidDnaFormatException {
        scheme.create('U');
    }

    /**
     * u is not supported in this encoding scheme, break
      */
    @Test(expected = InvalidDnaFormatException.class)
    public void testCreation_u() throws InvalidDnaFormatException {
        scheme.create('u');
    }
}
