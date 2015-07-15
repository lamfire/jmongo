package com.lamfire.jmongo.mapping.cache;

import com.lamfire.jmongo.Key;

public interface EntityCache {
	Boolean exists(Key<?> k);
	
	void notifyExists(Key<?> k, boolean exists);
	
	<T> T getEntity(Key<T> k);
	
	<T> T getProxy(Key<T> k);
	
	<T> void putProxy(Key<T> k, T t);
	
	<T> void putEntity(Key<T> k, T t);
	
	void flush();
	
	EntityCacheStatistics stats();
}
