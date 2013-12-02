package com.vitreoussoftware.utilities;

/**
 * A tuple for storing immutable data with two types.
 * @author John
 *
 * @param <T1> The first type
 * @param <T2> The second type
 */
public class Tuple<T1, T2> {
	private final T1 item1;
	private final T2 item2;
	
	/**
	 * Build the immutable two item tuple
	 * @param item1 the first item
	 * @param item2 the second item
	 */
	public Tuple(T1 item1, T2 item2)
	{
		this.item1 = item1;
		this.item2 = item2;
	}

	/**
	 * Get the first item in the tuple
	 * @return The first item
	 */
	public T1 getItem1() {
		return this.item1;
	}
	
	/**
	 * Get the first item in the tuple
	 * @return The first item
	 */
	public T2 getItem2() {
		return this.item2;
	}

}
