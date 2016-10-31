package com.vitreoussoftware.bioinformatics.sequence.encoding;

import lombok.val;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by John on 10/30/2016.
 */
public abstract class EncodingSchemeTestBase {
    public abstract List<Character> getAcceptedCharacters();

    public abstract EncodingScheme getEncodingScheme();

    /**
     * Loop through the set of {@see acceptedCharacters} for this {@link EncodingScheme} and perform some sanity
     *   checks on those values.
     */
    @Test
    public void testConsistency() {
        val acceptedCharacters = getAcceptedCharacters();
        val scheme = getEncodingScheme();

        for (int i = 0; i < acceptedCharacters.size(); i++) {
            val character = acceptedCharacters.get(i);
            val basePairFromCharacter = scheme.fromCharacter(character);
            val encodedByte = scheme.getValue(character);
            val basePairFromByte = scheme.toBasePair(encodedByte);

            assertThat("Failed for character " + character, basePairFromCharacter.toChar(), is(character));
            assertThat("Failed for character " + character, scheme.toChar(encodedByte), is(character));
            assertThat("Failed for character " + character, basePairFromCharacter, is(basePairFromByte));
        }
    }
}
