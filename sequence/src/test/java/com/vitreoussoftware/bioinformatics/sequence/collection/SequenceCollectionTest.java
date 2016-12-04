package com.vitreoussoftware.bioinformatics.sequence.collection;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.SequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.io.FastaData;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.junit.Assert.*;

/**
 * Test the MongoDBSequenceCollection class
 * @author John
 *
 */
public abstract class SequenceCollectionTest {
	protected FastaData testData;
	protected SequenceFactory sequenceFactory;

    @Before
	public void setUp() throws Exception {
		this.sequenceFactory = new FastaSequenceFactory();
		this.testData = new FastaData();
	}


    abstract protected SequenceCollectionFactory getFactory();

    /**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 */
	@Test
	public void testClearCollection() throws UnknownHostException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());
	}

	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testAddSequence() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		final Sequence seq = this.sequenceFactory.fromString("ATCGN").get();
		collection.add(seq);

		assertEquals("The collection did not have only one element in it", 1, collection.size());
	}

	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testAddAllSequences() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		final Sequence seq = this.sequenceFactory.fromString("ATCGN").get();

		collection.addAll(Arrays.asList(seq, seq, seq));

		assertEquals("The collection did not have 3 elements in it", 3, collection.size());
	}

	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testAddAllSequencesMany() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		final Sequence seq = this.sequenceFactory.fromString("ATCGN").get();

		final List<Sequence> list = new LinkedList<>();
		final int many = 99999;
		for (int i = 0; i < many; i++) list.add(seq);
		assertEquals("The list didn't have many elements", many, list.size());

		collection.addAll(list);

		assertEquals("The collection did not have many elements in it", many, collection.size());
	}

	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testContainsSingle() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		final Sequence seq = this.sequenceFactory.fromString("ATCGN").get();
		collection.add(seq);

		assertEquals("The collection did not have only one element in it", 1, collection.size());

		assertTrue("The sequence was not found in the collection", collection.contains(seq));
	}

	/**
	 * Fail to find a Sequence because it is a substring of the Sequence stored in the collection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testContainsCanFailShorter() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		final Sequence seq  = this.sequenceFactory.fromString("ATCGN").get();
		final Sequence fail = this.sequenceFactory.fromString("ATCG").get();
		collection.add(seq);

		assertEquals("The collection did not have only one element in it", 1, collection.size());

		assertFalse("The sequence was found in the collection", collection.contains(fail));
	}

	/**
	 * Fail to find a Sequence because it is a superstring of the Sequence stored in the collection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testContainsCanFailLonger() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		final Sequence seq  = this.sequenceFactory.fromString("ATCGN").get();
		final Sequence fail = this.sequenceFactory.fromString("ATCGNA").get();
		collection.add(seq);

		assertEquals("The collection did not have only one element in it", 1, collection.size());

		assertFalse("The sequence was found in the collection", collection.contains(fail));
	}

	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testContainsAllSingle() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		final Sequence seq = this.sequenceFactory.fromString("ATCGN").get();
		collection.add(seq);

		assertEquals("The collection did not have only one element in it", 1, collection.size());

		final List<Sequence> seqs = new LinkedList<>();
		seqs.add(seq);
		assertTrue("The sequence was not found in the collection", collection.containsAll(seqs));
	}

	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testContainsAllSingleCanFail() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		final Sequence seq = this.sequenceFactory.fromString("ATCGN").get();
		final Sequence fail = this.sequenceFactory.fromString("ATCGNA").get();
		collection.add(seq);

		assertEquals("The collection did not have only one element in it", 1, collection.size());

		final List<Sequence> seqs = new LinkedList<>();
		seqs.add(fail);
		assertFalse("The sequence was found in the collection", collection.containsAll(seqs));
	}

	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testContainsAllMultiple() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		final Sequence seq1 = this.sequenceFactory.fromString("ATCGT").get();
		final Sequence seq2 = this.sequenceFactory.fromString("ATCGA").get();
		collection.add(seq1);
		collection.add(seq2);

		assertEquals("The collection did not have only one element in it", 2, collection.size());

		assertTrue("The first sequence was not contained", collection.contains(seq1));
		assertTrue("The second sequence was not contained", collection.contains(seq2));

		final List<Sequence> seqs = new LinkedList<>();
		seqs.add(seq1);
		seqs.add(seq2);
		assertTrue("The sequence was not found in the collection", collection.containsAll(seqs));
	}

	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testContainsAllMultipleCanFail() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		final Sequence seq1 = this.sequenceFactory.fromString("ATCGT").get();
		final Sequence seq2 = this.sequenceFactory.fromString("ATCGA").get();
		final Sequence fail = this.sequenceFactory.fromString("ATCGNA").get();
		collection.add(seq1);
		collection.add(seq2);

		assertEquals("The collection did not have only one element in it", 2, collection.size());

		assertTrue("The first sequence was not contained", collection.contains(seq1));
		assertTrue("The second sequence was not contained", collection.contains(seq2));

		final List<Sequence> seqs = new LinkedList<>();
		seqs.add(seq1);
		seqs.add(seq2);
		seqs.add(fail);

		assertFalse("The sequence was found in the collection", collection.containsAll(seqs));
	}

	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testIsEmtpy() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		assertTrue("The collection was not empty", collection.isEmpty());
		final Sequence seq1 = this.sequenceFactory.fromString("ATCGT").get();
		final Sequence seq2 = this.sequenceFactory.fromString("ATCGA").get();
		collection.add(seq1);
		collection.add(seq2);

		assertEquals("The collection did not have only one element in it", 2, collection.size());

		assertFalse("The collection was empty", collection.isEmpty());
	}

	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testIterator() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		final Sequence seq1 = this.sequenceFactory.fromString("ATCGT").get();
		final Sequence seq2 = this.sequenceFactory.fromString("ATCGA").get();
		collection.add(seq1);
		collection.add(seq2);

		assertEquals("The collection did not have only one element in it", 2, collection.size());

		assertTrue("The first sequence was not contained", collection.contains(seq1));
		assertTrue("The second sequence was not contained", collection.contains(seq2));

		final Iterator<Sequence> iterator = collection.iterator();

		assertTrue("Iterator has at least one element", iterator.hasNext());
		final Sequence first = iterator.next();
		assertTrue("The first iterator result was not found in the collection", collection.contains(first));

		assertTrue("Iterator has a second element", iterator.hasNext());
		final Sequence second = iterator.next();
		assertTrue("The second iterator result was not found in the collection", collection.contains(second));

		assertFalse("Iterator has a third element", iterator.hasNext());
		assertFalse("The first and second sequences were the same", first.equals(second));
	}

	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testIteratorRemove() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		final Sequence seq1 = this.sequenceFactory.fromString("ATCGT").get();
		final Sequence seq2 = this.sequenceFactory.fromString("ATCGA").get();
		collection.add(seq1);
		collection.add(seq2);

		assertEquals("The collection did not have only one element in it", 2, collection.size());

		assertTrue("The first sequence was not contained", collection.contains(seq1));
		assertTrue("The second sequence was not contained", collection.contains(seq2));

		final Iterator<Sequence> iterator = collection.iterator();

		assertTrue("Iterator has at least one element", iterator.hasNext());
		final Sequence first = iterator.next();
		assertTrue("The first iterator result was not found in the collection", collection.contains(first));
		iterator.remove();
		assertFalse("The first iterator result was found in the collection after removal", collection.contains(first));

		assertTrue("Iterator has a second element", iterator.hasNext());
		final Sequence second = iterator.next();
		assertTrue("The second iterator result was not found in the collection", collection.contains(second));
		iterator.remove();
		assertFalse("The second iterator result was found in the collection after removal", collection.contains(first));

		assertFalse("Iterator has a third element", iterator.hasNext());
		assertFalse("The first and second sequences were the same", first.equals(second));

		assertTrue("The collection was not empty after removing all elements", collection.isEmpty());
	}

	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testIteratorForEachRemaining() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		final Sequence seq1 = this.sequenceFactory.fromString("ATCGT").get();
		final Sequence seq2 = this.sequenceFactory.fromString("ATCGA").get();
		collection.add(seq1);
		collection.add(seq2);

		assertEquals("The collection did not have only one element in it", 2, collection.size());

		assertTrue("The first sequence was not contained", collection.contains(seq1));
		assertTrue("The second sequence was not contained", collection.contains(seq2));

		final Iterator<Sequence> iterator = collection.iterator();

		iterator.forEachRemaining(new Consumer<Sequence>() {

            public void accept(final Sequence seq) {
                assertTrue("The sequence was not in the collection", collection.contains(seq));
            }

            public Consumer<Sequence> andThen(final Consumer<? super Sequence> arg0) {
                fail("Called the andThen method in foreach remaining");
                return null;
            }
        });
	}

	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testRemove() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		final Sequence seq1 = this.sequenceFactory.fromString("ATCGT").get();
		final Sequence seq2 = this.sequenceFactory.fromString("ATCGA").get();
		final Sequence seq3 = this.sequenceFactory.fromString("ATCGNA").get();
		collection.add(seq1);
		collection.add(seq2);
		collection.add(seq3);

		assertEquals("The collection did not have three elements in it", 3, collection.size());

		assertTrue("The first sequence was not contained", collection.contains(seq1));
		assertTrue("The second sequence was not contained", collection.contains(seq2));
		assertTrue("The third sequence was not contained", collection.contains(seq3));

		assertTrue("The remove call returned false", collection.remove(seq3));

		assertEquals("The collection did not have two elements in it", 2, collection.size());

		assertTrue("The first sequence was not contained", collection.contains(seq1));
		assertTrue("The second sequence was not contained", collection.contains(seq2));

		assertFalse("The third sequence was contained", collection.contains(seq3));
	}

	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testRemoveAll() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		final Sequence seq1 = this.sequenceFactory.fromString("ATCGT").get();
		final Sequence seq2 = this.sequenceFactory.fromString("ATCGA").get();
		final Sequence seq3 = this.sequenceFactory.fromString("ATCGNA").get();
		collection.add(seq1);
		collection.add(seq2);
		collection.add(seq3);

		assertEquals("The collection did not have three elements in it", 3, collection.size());

		assertTrue("The first sequence was not contained", collection.contains(seq1));
		assertTrue("The second sequence was not contained", collection.contains(seq2));
		assertTrue("The third sequence was not contained", collection.contains(seq3));

		final List<Sequence> seqs = new LinkedList<>();
		seqs.add(seq1);
		seqs.add(seq3);

		assertTrue("The remove all returned false", collection.removeAll(seqs));
		assertEquals("The collection did not have two elements in it", 1, collection.size());

		assertTrue("The second sequence was not contained", collection.contains(seq2));

		assertFalse("The first sequence was contained", collection.contains(seq1));
		assertFalse("The third sequence was contained", collection.contains(seq3));
	}

	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testRemoveIf() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		final Sequence seq1 = this.sequenceFactory.fromString("ATCGT").get();
		final Sequence seq2 = this.sequenceFactory.fromString("ATCGA").get();
		final Sequence seq3 = this.sequenceFactory.fromString("ATCGNA").get();
		collection.add(seq1);
		collection.add(seq2);
		collection.add(seq3);

		assertEquals("The collection did not have three elements in it", 3, collection.size());

		assertTrue("The first sequence was not contained", collection.contains(seq1));
		assertTrue("The second sequence was not contained", collection.contains(seq2));
		assertTrue("The third sequence was not contained", collection.contains(seq3));

		collection.removeIf(new Predicate<Sequence>() {
			public boolean test(final Sequence seq) {
				return seq.equals(seq3);
			}
		});

		assertEquals("The collection did not have two elements in it", 2, collection.size());

		assertTrue("The first sequence was not contained", collection.contains(seq1));
		assertTrue("The second sequence was not contained", collection.contains(seq2));

		assertFalse("The third sequence was contained", collection.contains(seq3));
	}

	/**
	 * Create a SequenceCollection
	 * @throws java.net.UnknownHostException Failed to connect
	 * @throws com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException
	 */
	@Test
	public void testRetainAll() throws UnknownHostException, InvalidDnaFormatException {
		final SequenceCollection collection = getFactory().getSequenceCollection();
		assertNotNull("The collection returned was nulll", collection);
		collection.clear();
		assertEquals("The collection was not empty after a drop",  0, collection.size());

		final Sequence seq1 = this.sequenceFactory.fromString("ATCGT").get();
		final Sequence seq2 = this.sequenceFactory.fromString("ATCGA").get();
		final Sequence seq3 = this.sequenceFactory.fromString("ATCGNA").get();
		collection.add(seq1);
		collection.add(seq2);
		collection.add(seq3);

		assertEquals("The collection did not have three elements in it", 3, collection.size());

		assertTrue("The first sequence was not contained", collection.contains(seq1));
		assertTrue("The second sequence was not contained", collection.contains(seq2));
		assertTrue("The third sequence was not contained", collection.contains(seq3));


		final List<Sequence> list = new LinkedList<>();
		list.add(seq1);
		collection.retainAll(list);

		assertEquals("The collection did not have one elements in it", 1, collection.size());

		assertTrue("The first sequence was not contained", collection.contains(seq1));

		assertFalse("The second sequence was contained", collection.contains(seq2));
		assertFalse("The third sequence was contained", collection.contains(seq3));
	}
}
