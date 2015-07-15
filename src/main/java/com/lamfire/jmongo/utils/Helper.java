package com.lamfire.jmongo.utils;

import com.lamfire.jmongo.Datastore;
import com.lamfire.jmongo.query.MorphiaIterator;
import com.lamfire.jmongo.query.Query;
import com.lamfire.jmongo.query.QueryImpl;
import com.lamfire.jmongo.query.UpdateOperations;
import com.lamfire.jmongo.query.UpdateOpsImpl;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * Exposes driver related DBOBject stuff from Mapping objects
 * @author scotthernandez
 */

public class Helper {
	public static DBObject getCriteria(Query<?> q) {
		QueryImpl<?> qi = (QueryImpl<?>) q;
		return qi.getQueryObject();
	}
	
	public static DBObject getSort(Query<?> q) {
		QueryImpl<?> qi = (QueryImpl<?>) q;
		return qi.getSortObject();
	}
	
	public static DBObject getFields(Query<?> q) {
		QueryImpl<?> qi = (QueryImpl<?>) q;
		return qi.getFieldsObject();
	}
	
	public static DBCollection getCollection(Query<?> q) {
		QueryImpl<?> qi = (QueryImpl<?>) q;
		return qi.getCollection();
	}
	
	public static DBCursor getCursor(Iterable<?> it) {
		return ((MorphiaIterator<?,?>)it).getCursor();
	}

	public static DBObject getUpdateOperations(UpdateOperations<?> ops) {
		UpdateOpsImpl<?> uo = (UpdateOpsImpl<?>) ops;
		return uo.getOps();
	}
	
	public static DB getDB(Datastore ds) {
		return ds.getDB();
	}
}