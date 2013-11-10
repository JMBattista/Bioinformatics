package firstapp;

import java.net.UnknownHostException;

import com.mongodb.*;

public class MyFirstApplication {
	/**
	 * @param args
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException
	{
		System.out.println("Hello, world!");
		
		MongoClient client = new MongoClient();
		DB db = client.getDB("test");
		
		DBCollection collection = db.getCollection("test");
		
		System.out.println("Number of elements " + collection.count());
		
		BasicDBObject doc = new BasicDBObject("name", "MongoDB").
                append("type", "database").
                append("count", 1).
                append("info", new BasicDBObject("x", 203).append("y", 102));
		
		collection.insert(doc);

		System.out.println("Number of elements " + collection.count());
	}
	
}