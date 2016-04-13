package com.lamfire.jmongo.query;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class EntityNotMappedIterator implements Iterable<Map<String,Object>>, Iterator<Map<String,Object>>{
	protected final Iterator<DBObject> wrapped;
	protected long  driverTime = 0;
	protected long  mapperTime= 0;

	public EntityNotMappedIterator(Iterator<DBObject> it) {
		this.wrapped = it;
	}
	
	public Iterator<Map<String,Object>> iterator() {
		return this;
	}
	
	public boolean hasNext() {
		if(wrapped == null) return false;
    	long start = System.currentTimeMillis();
		boolean ret = wrapped.hasNext();
    	driverTime += System.currentTimeMillis() - start;
		return ret;
	}
	
	public Map<String,Object> next() {
		if(!hasNext()) throw new NoSuchElementException();
    	DBObject dbObj = getNext();
    	return (Map<String,Object>)dbObj;
	}

	
	protected DBObject getNext() {
		long start = System.currentTimeMillis();
		DBObject dbObj = wrapped.next();
    	driverTime += System.currentTimeMillis() - start;
    	return dbObj;
	}

	
	public void remove() {
		long start = System.currentTimeMillis();
		wrapped.remove();
    	driverTime += System.currentTimeMillis() - start;
	}
	
	/** Returns the time spent calling the driver in ms */
	public long getDriverTime() {
		return driverTime;
	}
	
	/** Returns the time spent calling the mapper in ms */
	public long getMapperTime() {
		return mapperTime;
	}
	
	public DBCursor getCursor() {
		return (DBCursor)wrapped;
	}
	
	public void close() {
		if (wrapped != null && wrapped instanceof DBCursor)
			((DBCursor)wrapped).close();
	}
}