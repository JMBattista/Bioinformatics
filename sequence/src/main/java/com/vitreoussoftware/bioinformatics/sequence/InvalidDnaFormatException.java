package com.vitreoussoftware.bioinformatics.sequence;

/**
 * The input contained invalid characters
 * @author John
 *
 */
public class InvalidDnaFormatException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Create an InvalidDnaFormatException with an error message
	 * @param error The error message
	 */
	public InvalidDnaFormatException(final String error) {
		super(error);
	}


}
