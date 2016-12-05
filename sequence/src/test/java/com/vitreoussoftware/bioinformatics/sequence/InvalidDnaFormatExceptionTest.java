package com.vitreoussoftware.bioinformatics.sequence;

import lombok.val;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Created by John on 12/4/2016.
 */
public class InvalidDnaFormatExceptionTest {

    @Test
    public void testErrorMessage() {
        val exception = new InvalidDnaFormatException("foo");

        assertThat(exception.getMessage(), is("foo"));
        assertThat(exception.getCause(), is(nullValue()));
    }

    @Test
    public void testErrorMessageWithCause() {
        val exception = new InvalidDnaFormatException("foo", null);
        assertThat(exception.getMessage(), is("foo"));
        assertThat(exception.getCause(), is(nullValue()));
    }

    @Test
    public void testGetCause() {
        val cause = new InvalidDnaFormatException("bar");
        val exception = new InvalidDnaFormatException("foo", cause);

        assertThat(exception.getMessage(), is("foo"));
        assertThat(exception.getCause(), is(cause));
    }
}