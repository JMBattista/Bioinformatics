package com.vitreoussoftware.bioinformatics.sequence.generator;

import com.vitreoussoftware.bioinformatics.sequence.basic.BasicSequence;
import com.vitreoussoftware.bioinformatics.sequence.encoding.BasicDnaEncodingScheme;
import com.vitreoussoftware.bioinformatics.sequence.generator.distribution.discrete.UniformDistribution;
import lombok.val;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Test the {@link ReadGenerator}
 *
 * Created by John on 10/27/2016.
 */
public class ReadGeneratorTest {

    /**
     * Test that we can sample lenght 1 reads from a length 1 sequence
     */
    @Test
    public void testSample_fromSingle() {
        val generator = ReadGenerator.builder()
                .source(BasicSequence.create("A", BasicDnaEncodingScheme.instance).get())
                .lengthDistribution(() -> 1)
                .startPointDistribution(() -> 0)
                .build();

        val read = generator.sample();

        assertThat(read, is(not(nullValue())));
        assertThat(read.length(), is(1));
        assertThat(read.get(0).toChar(), is('A'));
    }
}
