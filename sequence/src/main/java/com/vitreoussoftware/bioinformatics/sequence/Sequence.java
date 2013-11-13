package com.vitreoussoftware.bioinformatics.sequence;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import javax.management.RuntimeErrorException;

import com.vitreoussoftware.bioinformatics.sequence.encoding.EncodingScheme;

/**
 * A DNA Sequence representation
 * @author John
 *
 */
public class Sequence {		
	private byte[] sequence;
	private final EncodingScheme encodingScheme;

	private Sequence(EncodingScheme encodingSheme)
	{
		this.encodingScheme = encodingSheme;
	}
	
	/**
	 * Create a new Sequence from a string and the given encoding scheme
	 * @param sequence the string sequence to encode
	 * @param encodingSheme the scheme to use for encoding
	 * @return the encoded sequence
	 * @throws InvalidDnaFormatException if the input doesn't match the scheme
	 */
	public static Sequence create(String sequence, EncodingScheme encodingSheme) throws InvalidDnaFormatException
	{
		Sequence seq = new Sequence(encodingSheme);
		seq.sequence = new byte[sequence.length()];
		
		for (int i = 0; i < sequence.length(); i++)
		{
			seq.sequence[i] = encodingSheme.getValue(sequence.charAt(i));
		}
		
		return seq;
	}
	
	/**
	 * Return an iterator that proceeds from the back to the front of the sequence
	 * @return the basepair iterator
	 */
	public Iterable<BasePair> reverse() {
		return new Iterable<BasePair>() {

			@Override
			public void forEach(Consumer<? super BasePair> consumer) {
				iterator().forEachRemaining(consumer);
			}

			@Override
			public Iterator<BasePair> iterator() {
				return new Iterator<BasePair>() {
					int index = sequence.length-1;
					@Override
					public void forEachRemaining(Consumer<? super BasePair> consumer) {
						int remaining = index;
						
						try {
							while (remaining >= 0)
							{
								consumer.accept(basePair(remaining));
								remaining--;
							}
						} catch (InvalidDnaFormatException e) {
							// TODO Auto-generated catch block
							throw new RuntimeException("We encountered a badly encoded set of base pairs inside sequence iterator");
						}
					}

					@Override
					public boolean hasNext() {
						return index >= 0;
					}

					@Override
					public BasePair next() {
						try {
							BasePair bp = basePair(index);
							index--;
							return bp;
						} catch (InvalidDnaFormatException e) {
							// TODO Auto-generated catch block
							throw new RuntimeException("We encountered a badly encoded set of base pairs inside sequence iterator");
						}
					}

					private BasePair basePair(int index)
							throws InvalidDnaFormatException {
						BasePair bp = BasePair.create(encodingScheme.toChar(sequence[index]), encodingScheme);
						return bp;
					}

					@Override
					public void remove() {
						index--;
					}
					
				};
			}

			@Override
			public Spliterator<BasePair> spliterator() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (byte bp : sequence) {
			sb.append(this.encodingScheme.toString(bp));
		}
		return sb.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + encodingScheme.getClass().getName().hashCode();
		result = prime * result + Arrays.hashCode(sequence);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sequence other = (Sequence) obj;
		if (this.encodingScheme == null || other.encodingScheme == null)
			return false;
		if (!encodingScheme.getClass().equals(other.encodingScheme.getClass()))
			return false;
		if (!Arrays.equals(sequence, other.sequence))
			return false;
		return true;
	}
}
