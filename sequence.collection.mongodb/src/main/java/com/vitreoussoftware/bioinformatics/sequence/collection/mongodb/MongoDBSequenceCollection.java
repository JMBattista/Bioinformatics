package com.vitreoussoftware.bioinformatics.sequence.collection.mongodb;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;

import java.util.Arrays;

import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;

/**
 * Sequence Collection backed on MongoDB NoSQL store 
 * @author John
 *
 */
public class MongoDBSequenceCollection implements SequenceCollection {
	/**
	 * The collection being used to store content from this SequenceCollection
	 */
	private DBCollection collection;

	MongoDBSequenceCollection(DB db, String collectionName) {
		this.collection = db.getCollection(collectionName);		
	}

	public boolean add(Sequence arg0) {
		if (arg0 != null) {
			this.collection.insert(buildDocument(arg0));
			return true;
		}
		
		return false;
	}

	private BasicDBObject buildDocument(Sequence arg0) {
		return new BasicDBObject("sequence", arg0.toString());
	}

	public boolean addAll(Collection<? extends Sequence> arg0) {
		final int maxCount = 1000;
		if (arg0 != null) {
			// Buffer the collection before submitting to increase the speed
			int count = 0;
			BasicDBObject[] buffer = arg0.size() > maxCount ? new BasicDBObject[maxCount] : new BasicDBObject[arg0.size()];
			
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
		// Drop would remove the collection instead of just wiping it out
		this.collection.remove(new BasicDBObject(), WriteConcern.ACKNOWLEDGED);
	}

	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEmpty() {
		return this.size() == 0;
	}

	public Iterator<Sequence> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public Stream<Sequence> parallelStream() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeIf(Predicate<? super Sequence> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public int size() {
		return (int) this.collection.count();
	}

	public Spliterator<Sequence> spliterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public Stream<Sequence> stream() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void forEach(Consumer<? super Sequence> arg0) {
		// TODO Auto-generated method stub

	}

}
