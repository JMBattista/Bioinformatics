package com.vitreoussoftware.bioinformatics.sequence.collection.mongodb;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collection;

import com.mongodb.*;
import com.vitreoussoftware.bioinformatics.sequence.InvalidDnaFormatException;
import com.vitreoussoftware.bioinformatics.sequence.Sequence;
import com.vitreoussoftware.bioinformatics.sequence.SequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollection;
import com.vitreoussoftware.bioinformatics.sequence.collection.SequenceCollectionFactory;
import com.vitreoussoftware.bioinformatics.sequence.fasta.FastaSequenceFactory;
import com.vitreoussoftware.bioinformatics.sequence.reader.SequenceStreamReader;

import static com.vitreoussoftware.utility.mongodb.EasyDBObject.field;

/**
 * Create a SequenceCollection backed onto a MongoDB collection
 * Repeated usage of this factory will return the same collection.
 * @author John
 *
 */
public class MongoDBSequenceCollectionFactory implements SequenceCollectionFactory {
	/**
	 * The collection instance for this factory
	 */
	private MongoDBSequenceCollection collection = null;

    /**
	 * The DB connection for this factory
	 */
	private DB db;

    /**
     * The DB collection for this factory
     */
    private DBCollection dbCollection;

	/**
	 * The SequenceFactory for converting returned items into Sequences
	 */
	private SequenceFactory factory;

	/**
	 * @param serverName URL that resolves to the MongoDB host server
	 * @param port The port running the MongoDB instance
	 * @param dbName The name of the DB
	 * @param collectionName The name of the collection
	 * @throws UnknownHostException Fired in the event that a connection to the MongoDB server cannot be established
	 */
	public MongoDBSequenceCollectionFactory(String serverName, int port, String dbName, String collectionName) throws UnknownHostException {
		MongoClient mongoClient = new MongoClient( serverName , port );
		this.db = mongoClient.getDB( dbName );
        this.dbCollection = this.db.getCollection(collectionName);
        // Ensure that items marked as 'deleted' are indexed and cleaned up after 10 seconds
        this.dbCollection.ensureIndex(field(MongoDBSequenceCollection.DELETED, 1), field("expireAfterSeconds", 30));
        this.factory = new FastaSequenceFactory();
	}

	public SequenceCollection getSequenceCollection() {
		if (this.collection == null)
			this.collection = new MongoDBSequenceCollection(this.dbCollection, this.factory);
		
		return this.collection;
	}
}
