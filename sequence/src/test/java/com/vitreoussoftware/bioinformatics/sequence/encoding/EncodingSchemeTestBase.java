package com.vitreoussoftware.bioinformatics.sequence.encoding;

import lombok.val;
import org.javatuples.Pair;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * These theories require that there is an @DataPoints array of the supported {@link Character}s and a
 *
 * @DataPoint with the {@link EncodingScheme
 * Created by John on 10/30/2016.
 */
@RunWith(Theories.class)
public abstract class EncodingSchemeTestBase {
    /**
     * Ensure that the base pair created from a character can be converted back to that character
     */
    @Theory
    public void theoryBasePairConversion(final EncodingScheme scheme, final Character character) {
        val basePair = scheme.fromCharacter(character);

        assertThat("Failed for character " + character, basePair.toChar(), is(character));
    }

    /**
     * Ensure that the encoded byte for a character can be converted back to that character
     */
    @Theory
    public void theoryEncodedByteConversion(final EncodingScheme scheme, final Character character) {
        val encodedByte = scheme.getValue(character);

        assertThat("Failed for character " + character, scheme.toChar(encodedByte), is(character));
    }


    /**
     * Ensure that the base pair created from a character is the same as the basepair created from the chars encoded byte
     */
    @Theory
    public void theoryBasePairEquality(final EncodingScheme scheme, final Character character) {
        val basePairFromCharacter = scheme.fromCharacter(character);
        val encodedByte = scheme.getValue(character);
        val basePairFromByte = scheme.toBasePair(encodedByte);

        assertThat("Failed for character " + character, basePairFromCharacter, is(basePairFromByte));
    }

    /**
     * Ensure that the BasePair toString is correct
     */
    @Theory
    public void theoryBasePairToString(final EncodingScheme scheme, final Character character) {
        val basePair = scheme.fromCharacter(character);

        assertThat(character.toString(), is(basePair.toString()));
    }

    /**
     * Ensure that the BasePair toString is correct
     */
    @Theory
    public void theoryBasePairToStringFromLower(final EncodingScheme scheme, final Character character) {
        val basePair = scheme.fromCharacter(Character.toLowerCase(character));

        assertThat(character.toString(), is(basePair.toString()));
    }

    /**
     * Given the set of valid pairs ensure we can flip between them in the 'forward' direction
     */
    @Theory
    public void theoryBasePairCanBeFlipped0to1(EncodingScheme scheme, Pair<Character, Character> pair) {
        val basePair0 = scheme.fromCharacter(pair.getValue0());
        val basePair1 = scheme.fromCharacter(pair.getValue1());

        assertThat(basePair0.flip(), is(basePair1));
    }

    /**
     * Given the set of valid pairs ensure we can flip between them in the 'backward' direction
     */
    @Theory
    public void theoryBasePairCanBeFlipped1to0(EncodingScheme scheme, Pair<Character, Character> pair) {
        val basePair0 = scheme.fromCharacter(pair.getValue0());
        val basePair1 = scheme.fromCharacter(pair.getValue1());

        assertThat(basePair1.flip(), is(basePair0));
    }
}
