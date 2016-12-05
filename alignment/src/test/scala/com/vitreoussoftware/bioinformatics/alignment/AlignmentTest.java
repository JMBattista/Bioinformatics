package com.vitreoussoftware.bioinformatics.alignment;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Created by John on 12/4/2016.
 */
public class AlignmentTest {
    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(Alignment.class)
                .verify();
    }
}