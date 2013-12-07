package com.vitreoussoftware.utility.mongodb;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.Date;

/**
 * Created by John on 12/7/13.
 */
public class EasyDBObject {
    public static final String EXISTS = "$exists";
    public static final String SET = "$set";
    public static final String NOT_EQUALS = "$ne";
    public static final String EQUAls = "$eq";
    public static final String OR = "$or";
    public static final String AND = "$and";
    public static final String GT = "$gt";
    public static final String GTE= "$gte";
    public static final String LT = "$lt";
    public static final String LTE = "$lte";

    /**
     * Designed to be used via static imports
     */
    private EasyDBObject () {};

    /**
     * Get current time
     * @return the current time
     */
    public static Date now() {
        return new Date();
    }

    /**
     * The field must exist in the document
     * @return a DBObject with $exists: true
     */
    public static DBObject exists() {
        return field(EXISTS, true);
    }

    /**
     * Determines if the field exists or not
     * @param exists if the field must or must not exist
     * @return a DBObject with $exists: exists
     */
    public static DBObject exists(boolean exists) {
        return field(EXISTS, exists);
    }

    /**
     * The field must exist in the document
     * @return a DBObject with $exists: true
     */
    public static DBObject exists(String key) {
        return new BasicDBObject(key, exists());
    }

    /**
     * Determines if the field exists or not
     * @param key the field key to check for
     * @param exists if the field must or must not exist
     * @return a DBObject with $exists: exists
     */
    public static DBObject exists(String key, boolean exists) {
        return field(key, exists(exists));
    }


    /**
     * Update this value, but don't override the document completely
     * @param obj the value to set
     * @return a DBObject with $set: obj
     */
    public static DBObject set(DBObject obj) {
        return field(SET, obj);
    }

    /**
     * Update this value, but don't override the document completely
     * @param key the key to update
     * @param value the value for that key
     * @return a DBObject with $set: {key: value}
     */
    public static DBObject set(String key, Object value) {
        return field(SET, field(key, value));
    }

    /**
     * Create a DBObject for the given key value pair
     * @param key The { key : _ }
     * @param value The { _ : value }
     * @return The DBObject for the { key, value }
     */
    public static DBObject field(String key, Object value) {
        return new BasicDBObject(key, value);
    }

    /**
     * All of these must be true
     * @param args the list of arguments to check
     * @return The DBObject representing {$and : [ ... ]}
     */
    public static DBObject and(DBObject ... args) {
        BasicDBList andArray = new BasicDBList();
        for (DBObject arg : args) {
            andArray.add(arg);
        }
        return field(AND, andArray);
    }


    /**
     * One of these must be true
     * @param args the list of arguments to check
     * @return The DBObject representing {$or : [ ... ]}
     */
    public static DBObject or(DBObject ... args) {
        BasicDBList orArray = new BasicDBList();
        for (DBObject arg : args) {
            orArray.add(arg);
        }
        return field(OR, orArray);
    }

    /**
     * Determine if the record is gt value for key
     * @param key The field to use for comparison
     * @param value The value to compare against
     * @return DBObject for {key : {$gt : value}}
     */
    public static DBObject gt(String key, Object value) {
        return field(key, field(GT, value));
    }

    /**
     * Determine if the record is gte value for key
     * @param key The field to use for comparison
     * @param value The value to compare against
     * @return DBObject for {key : {$gte : value}}
     */
    public static DBObject gte(String key, Object value) {
        return field(key, field(GTE, value));
    }

    /**
     * Determine if the record is lt value for key
     * @param key The field to use for comparison
     * @param value The value to compare against
     * @return DBObject for {key : {$lt : value}}
     */
    public static DBObject lt(String key, Object value) {
        return field(key, field(LT, value));
    }

    /**
     * Determine if the record is lte value for key
     * @param key The field to use for comparison
     * @param value The value to compare against
     * @return DBObject for {key : {$lte : value}}
     */
    public static DBObject lte(String key, Object value) {
        return field(key, field(LTE, value));
    }
}
