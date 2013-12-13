package com.vitreoussoftware.bioinformatics.sequence.collection.mongodb;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.mongodb.WriteConcern;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.SequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;

import static com.vitreoussoftware.utility.mongodb.EasyDBObject.*;

/**
 * Sequence Collection backed on MongoDB NoSQL store 
 * @author John
 *
 */
public class MongoDBSequenceCollection implements SequenceCollection {
    /**
     * The document field that stores the string representation of the sequence
     */
    static final String SEQUENCE = "sequence";

    /**
     * The document field used to indicate that document has been marked for deletion
     */
    static final String DELETED = "deleted";

    /**
	 * The collection being used to store content from this SequenceCollection
	 */
	private DBCollection collection;

    /**
     * The factory used to produce new sequences from the returned values.
     */
	private SequenceFactory factory;

	MongoDBSequenceCollection(DBCollection dbCollection, SequenceFactory factory) {
		this.factory = factory;
		this.collection = dbCollection;
	}

	public boolean add(Sequence arg0) {
		if (arg0 != null) {
			this.collection.insert(buildDocument(arg0));
			return true;
		}
		
		return false;
	}

	public boolean addAll(Collection<? extends Sequence> arg0) {
		final int maxCount = 1000;
		if (arg0 != null) {
			// Buffer the collection before submitting to increase the speed
			int count = 0;
			DBObject[] buffer = arg0.size() > maxCount ? new DBObject[maxCount] : new DBObject[arg0.size()];
			
			for (Sequence seq : arg0) {
				buffer[count % maxCount] = buildDocument(seq);
				count++;
				
				// if the buffer is full send it to the db
				if (count % maxCount == 0) {
					this.collection.insert(buffer);
					
					// if the remaining values won't fit the current buffer re-allocate it
					if (count + maxCount > arg0.size()) {
						buffer = new BasicDBObject[arg0.size()-count];
					}
				}
			}
			
			// if we had to resize the buffer send it to the db
			if (buffer.length != maxCount)
				this.collection.insert(buffer);
			
			return true;
		}
		
		return false;
	}

	public void clear() {
		// Set the deleted field across the whole collection. This will cause them to disapear.
        this.collection.update(exists(DELETED, false), set(DELETED, now()), false, true, WriteConcern.ACKNOWLEDGED);
	}

	public boolean contains(Object arg0) {
		if (arg0 instanceof Sequence)
		{
            DBObject doc = buildDocument((Sequence) arg0);
            doc.put(DELETED, exists(false));

            return this.collection.count(doc) > 0;
		}
		
		return false;
	}

	public boolean containsAll(Collection<?> arg0) {
		boolean contained = true;
		
		for(Object obj: arg0) {
			contained = contained && contains(obj);	
		}
		
		return contained;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	public Iterator<Sequence> iterator() {
		final DBCursor cursor = this.collection.find(or(exists(DELETED, false), gte(DELETED, now())));
        final MongoDBSequenceCollection parent = this;

        return new Iterator<Sequence>() {
			Sequence last = null;
			public void forEachRemaining(Consumer<? super Sequence> arg0) {
				DBCursor iter = cursor.copy();
				while (iter.hasNext()) {
					arg0.accept(buildSequence(iter.next()));
				}
			}

			public boolean hasNext() {
				return cursor.hasNext();
			}

			public Sequence next() {
				last = buildSequence(cursor.next());
				return last;
			}

			public void remove() {
				if (last != null)
                    parent.remove(last);
			}
		};
	}

	public boolean remove(Object arg0) {
		if (this.contains(arg0)) {
			if (arg0 instanceof Sequence) {
				this.collection.update(buildDocument((Sequence) arg0), set(DELETED, now()), false, true);
			}
			return true;
		}
		
		return false;
	}

	public boolean removeAll(Collection<?> collection) {
		boolean changed = false;
		
		for(Object obj: collection) {
			changed = this.remove(obj) || changed;
		}
		
		return changed;
	}

	public boolean removeIf(final Predicate<? super Sequence> predicate) {
		iterator().forEachRemaining(new Consumer<Sequence>() {

			public void accept(Sequence seq) {
				if(predicate.test(seq))
					remove(seq);
			}

			public Consumer<Sequence> andThen(Consumer<? super Sequence> arg0) {
				return null;
			}
		});
		
		return false;
	}

	public boolean retainAll(final Collection<?> collection) {
		class Container {
			public boolean result = false;
		}
		final Container changed = new Container();
		
		iterator().forEachRemaining(new Consumer<Sequence>() {
			public void accept(Sequence seq) {
				if (!collection.contains(seq)) {
					remove(seq);
					changed.result = true;
				}
			}

			public Consumer<Sequence> andThen(Consumer<? super Sequence> arg0) {
				throw new RuntimeException("Called andThen on custom Consume that was not implemented");
			}
		});
		
		return changed.result; 
	}

	public int size() {
		return (int) this.collection.count(exists(DELETED, false));
	}

	public Spliterator<Sequence> spliterator() {
		throw new UnsupportedOperationException("Spliterator functionality is not currently supported for the MongoDBSequenceCollection");
	}

	public Stream<Sequence> stream() {
		throw new UnsupportedOperationException("Stream functionality is not currently supported for the MongoDBSequenceCollection");
	}
	
	public Stream<Sequence> parallelStream() {
		throw new UnsupportedOperationException("Stream functionality is not currently supported for the MongoDBSequenceCollection");
	}

	public Sequence[] toArray() {
		Sequence[] array = new Sequence[this.size()];
        int index = 0;
        Iterator<Sequence> iterator = iterator();

        while (iterator.hasNext()) {
            array[index] = iterator.next();
            index++;
        }

        return array;
	}

	public <T> T[] toArray(T[] arg0) {
		throw new UnsupportedOperationException("toArray(T[] arg0 is not supported");
	}

	public void forEach(Consumer<? super Sequence> arg0) {
		// TODO Auto-generated method stub

	}

	private DBObject buildDocument(Sequence arg0) {

        return field(SEQUENCE, arg0.toString());
	}
	
	private Sequence buildSequence(DBObject arg0) {
        return this.factory.fromString((String)arg0.get(SEQUENCE))
                    .orElseThrow(() -> new RuntimeException("We were unable to decode a value we stored stored in the database!" + arg0.get(SEQUENCE)));
	}
}
