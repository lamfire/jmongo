package com.lamfire.jmongo;

import com.lamfire.jmongo.query.Query;
import com.lamfire.jmongo.query.UpdateOperations;
import com.mongodb.DBDecoderFactory;
import com.mongodb.DBObject;
import com.mongodb.DBRef;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

public interface AdvancedDatastore extends Datastore {
	/** Creates a reference to the entity (using the current DB -can be null-, the collectionName, and id) */
	<T,V> DBRef createRef(Class<T> clazz, V id);
	/** Creates a reference to the entity (using the current DB -can be null-, the collectionName, and id) */
	<T> DBRef createRef(T entity);

	/** Find the given entity (by collectionName/id);*/
	<T> T get(Class<T> clazz, DBRef ref);

	/** Gets the count this kind*/
	long getCount(String kind);
	<T,V> T get(String kind, Class<T> clazz, V id);
	<T> Query<T> find(String kind, Class<T> clazz);
	<T,V> Query<T> find(String kind, Class<T> clazz, String property, V value, int offset, int size);
	<T> Key<T> save(String kind, T entity);
	/** No validation or conversion is done to the id*/
	@Deprecated
	<T> WriteResult delete(String kind, T id);
	<T, V> WriteResult delete(String kind, Class<T> clazz, V id);
	
	<T> Key<T> insert(String kind, T entity);
	
	<T> Key<T> insert(T entity);
	<T> Key<T> insert(T entity, WriteConcern wc);
	<T> Iterable<Key<T>> insert(T... entities);
	<T> Iterable<Key<T>> insert(Iterable<T>entities, WriteConcern wc);

	<T> Iterable<Key<T>> insert(String kind, Iterable<T> entities);
	<T> Iterable<Key<T>> insert(String kind, Iterable<T> entities, WriteConcern wc);


	<T> Query<T> createQuery(String kind, Class<T> clazz);
	//DBObject implementations; in case we don't have features impl'd yet
	<T> Query<T> createQuery(Class<T> kind, DBObject q);
	<T> Query<T> createQuery(String kind, Class<T> clazz, DBObject q);
	
	/** Returns a new query based on the example object*/
	<T> Query<T> queryByExample(String kind, T example);

	
	<T> UpdateOperations<T> createUpdateOperations(Class<T> kind, DBObject ops);
	
	DBDecoderFactory setDecoderFact(DBDecoderFactory fact);
	DBDecoderFactory getDecoderFactory();
	
}