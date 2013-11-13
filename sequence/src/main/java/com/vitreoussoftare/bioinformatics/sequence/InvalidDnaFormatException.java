package com.vitreoussoftare.bioinformatics.sequence;

/**
 * The input contained invalid characters
 * @author John
 *
 */
public class InvalidDnaFormatException extends Exception {

	/**
	 * Create an InvalidDnaFormatException with an error message
	 * @param error The error message
	 */
	public InvalidDnaFormatException(String error) {
		super(error);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
