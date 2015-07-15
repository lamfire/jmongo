package com.lamfire.jmongo;

import java.util.List;
import java.util.Map;

import com.lamfire.jmongo.mapping.Mapper;
import com.lamfire.jmongo.query.Query;
import com.lamfire.jmongo.query.UpdateOperations;
import com.lamfire.jmongo.query.UpdateResults;
import com.lamfire.jmongo.utils.IndexDirection;
import com.lamfire.jmongo.utils.IndexFieldDef;
import com.mongodb.*;

/**
 * Datastore interface to get/delete/save objects
 * @author Scott Hernandez
 */
@SuppressWarnings("unchecked")
public interface Datastore {	
	/** Creates a (type-safe) reference to the entity; if stored this will become a {@link DBRef} */
	<T> Key<T> getKey(T entity);

	/** Does a query to check if the keyOrEntity exists in jmongo */
	Key<?> exists(Object keyOrEntity);
	
	/** Deletes the given entity (by id) */
	<T,V> WriteResult delete(Class<T> clazz, V id);

    <T,V> WriteResult delete(Class<T> clazz, V id,String colName);

	/** Deletes the given entities (by id) */
	<T,V> WriteResult delete(Class<T> clazz, Iterable<V> ids);
	/** Deletes the given entities based on the query */
	<T> WriteResult delete(Query<T> q);
	/** Deletes the given entities based on the query, with the WriteConcern 
	 * @return */
	<T> WriteResult delete(Query<T> q, WriteConcern wc);
	/** Deletes the given entity (by @Id) */
	<T> WriteResult delete(T entity);

    <T> WriteResult delete(T entity,String colName);

	/** Deletes the given entity (by @Id), with the WriteConcern */
	<T> WriteResult delete(T entity, WriteConcern wc);

    <T> WriteResult delete(T entity, WriteConcern wc,String colName);

	/** Find all instances by type */
	<T> Query<T> find(Class<T> clazz);

    <T> Query<T> find(Class<T> clazz,String colName);

	/** 
	 * <p>
	 * Find all instances by collectionName, and filter property.
	 * </p><p>
	 * This is the same as: {@code find(clazzOrEntity).filter(property, value); }
	 * </p>
	 */
	<T, V> Query<T> find(Class<T> clazz, String property, V value);

    <T, V> Query<T> find(Class<T> clazz, String property, V value,String colName);
	
	/** 
	 * <p>
	 * Find all instances by collectionName, and filter property.
	 * </p><p>
	 * This is the same as: {@code find(clazzOrEntity).filter(property, value).offset(offset).limit(size); }
	 * </p>
	 */
	<T,V> Query<T> find(Class<T> clazz, String property, V value, int offset, int size);

	/** Find the given entities (by id); shorthand for {@code find("_id in", ids)} */
	<T,V> Query<T> get(Class<T> clazz, Iterable<V> ids);
	/** Find the given entity (by id); shorthand for {@code find("_id ", id)} */
	<T,V> T get(Class<T> clazz, V id);

    <T,V> T get(Class<T> clazz, V id,String colName);

	/** Find the given entity (by collectionName/id); think of this as refresh */
	<T> T get(T entity);
	
	/** Find the given entities (by id), verifying they are of the correct type; shorthand for {@code find("_id in", ids)} */
	<T> List<T> getByKeys(Class<T> clazz, Iterable<Key<T>> keys);
	/** Find the given entities (by id); shorthand for {@code find("_id in", ids)} */
	<T> List<T> getByKeys(Iterable<Key<T>> keys);
	/** Find the given entity (by collectionName/id);*/
	<T> T getByKey(Class<T> clazz, Key<T> key);

	/** Gets the count this kind ({@link DBCollection})*/
	<T> long getCount(T entity);
	/** Gets the count this kind ({@link DBCollection})*/
	<T> long getCount(Class<T> clazz);

    <T> long getCount(Class<T> clazz,String colName);

	/** Gets the count of items returned by this query; same as {@code query.countAll()}*/
	<T> long getCount(Query<T> query); 
	
	/** Saves the entities (Objects) and updates the @Id field */
	<T> Iterable<Key<T>> save(Iterable<T> entities);
	/** Saves the entities (Objects) and updates the @Id field, with the WriteConcern  */
	<T> Iterable<Key<T>> save(Iterable<T> entities, WriteConcern wc);
	/** Saves the entities (Objects) and updates the @Id field */
	<T> Iterable<Key<T>> save(T... entities);

    <T> Key<T> save(T entity,String colName);
	/** Saves the entity (Object) and updates the @Id field */
	<T> Key<T> save(T entity);
	/** Saves the entity (Object) and updates the @Id field, with the WriteConcern  */
	<T> Key<T> save(T entity, WriteConcern wc);

    <T> Key<T> save(T entity, WriteConcern wc,String colName);

	/** Work as if you did an update with each field in the entity doing a $set; Only at the top level of the entity. */
	<T> Key<T> merge(T entity);
	/** Work as if you did an update with each field in the entity doing a $set; Only at the top level of the entity. */
	<T> Key<T> merge(T entity, WriteConcern wc);

	/** updates the entity with the operations; this is an atomic operation*/
	<T> UpdateResults<T> update(T ent, UpdateOperations<T> ops);
	/** updates the entity with the operations; this is an atomic operation*/
	<T> UpdateResults<T> update(Key<T> key, UpdateOperations<T> ops);

    <T> UpdateResults<T> update(Key<T> key, UpdateOperations<T> ops,String colName);
	
	/** updates all entities found with the operations; this is an atomic operation per entity*/
	<T> UpdateResults<T> update(Query<T> query, UpdateOperations<T> ops);
	/** updates all entities found with the operations, if nothing is found insert the update as an entity if "createIfMissing" is true; this is an atomic operation per entity*/
	<T> UpdateResults<T> update(Query<T> query, UpdateOperations<T> ops, boolean createIfMissing);
	<T> UpdateResults<T> update(Query<T> query, UpdateOperations<T> ops, boolean createIfMissing, WriteConcern wc);
	/** updates the first entity found with the operations; this is an atomic operation*/
	<T> UpdateResults<T> updateFirst(Query<T> query, UpdateOperations<T> ops);
	/** updates the first entity found with the operations, if nothing is found insert the update as an entity if "createIfMissing" is true; this is an atomic operation per entity*/
	<T> UpdateResults<T> updateFirst(Query<T> query, UpdateOperations<T> ops, boolean createIfMissing);
	<T> UpdateResults<T> updateFirst(Query<T> query, UpdateOperations<T> ops, boolean createIfMissing, WriteConcern wc);
	/** updates the first entity found with the operations, if nothing is found insert the update as an entity if "createIfMissing" is true; this is an atomic operation per entity*/
	<T> UpdateResults<T> updateFirst(Query<T> query, T entity, boolean createIfMissing);

	
	/** 
	 * Deletes the given entities based on the query (first item only). 
	 * @return the deleted Entity
	 */
	<T> T findAndDelete(Query<T> q);

	/** 
	 * Find the first Entity from the Query, and modify it.  
	 * @return The modified Entity (the result of the update)
	 */
	<T> T findAndModify(Query<T> q, UpdateOperations<T> ops);

	/** 
	 * Find the first Entity from the Query, and modify it.
	 * @param q the query to find the Entity with; You are not allowed to offset/skip in the query.
	 * @param oldVersion indicated the old version of the Entity should be returned
	 * @return The Entity (the result of the update if oldVersion is false)
	 */
	<T> T findAndModify(Query<T> q, UpdateOperations<T> ops, boolean oldVersion);
	/** 
	 * Find the first Entity from the Query, and modify it.
	 * @param q the query to find the Entity with; You are not allowed to offset/skip in the query.
	 * @param oldVersion indicated the old version of the Entity should be returned
	 * @param createIfMissing if the query returns no results, then a new object will be created (sets upsert=true)
	 * @return The Entity (the result of the update if oldVersion is false)
	 */
	<T> T findAndModify(Query<T> q, UpdateOperations<T> ops, boolean oldVersion, boolean createIfMissing);

 
	/**
	 * Runs a map/reduce job at the server; this should be used with a server version 1.7.4 or higher
	 * @param <T> The type of resulting data
	 * @param outputType The type of resulting data; inline is not working yet
	 * @param type MapreduceType
	 * @param q The query (only the criteria, limit and sort will be used)
	 * @param map The map function, in javascript, as a string
	 * @param reduce The reduce function, in javascript, as a string
	 * @param finalize The finalize function, in javascript, as a string; can be null
	 * @param scopeFields Each map entry will be a global variable in all the functions; can be null
	 * @return counts and stuff
	 */
	
	<T> MapreduceResults<T> mapReduce(MapreduceType type, Query q, String map, String reduce, String finalize, Map<String, Object> scopeFields, Class<T> outputType);
	
	/**
	 * Runs a map/reduce job at the server; this should be used with a server version 1.7.4 or higher
	 * @param <T> The type of resulting data
	 * @param type MapreduceType
	 * @param q The query (only the criteria, limit and sort will be used)
	 * @param outputType The type of resulting data; inline is not working yet
	 * @param baseCommand The base command to fill in and send to the server
	 * @return counts and stuff
	 */
	<T> MapreduceResults<T> mapReduce(MapreduceType type, Query q, Class<T> outputType, MapReduceCommand baseCommand);
	
	/** The builder for all update operations */
	<T> UpdateOperations<T> createUpdateOperations(Class<T> kind);

	/** Returns a new query bound to the kind (a specific {@link DBCollection})  */
	<T> Query<T> createQuery(Class<T> kind);

    <T> Query<T> createQuery(Class<T> kind,String colName);

	/** Returns a new query based on the example object*/
	<T> Query<T> queryByExample(T example);
	
	/** Ensures (creating if necessary) the index and direction */
	<T> void ensureIndex(Class<T> clazz, String field, IndexDirection dir);
	/** Ensures (creating if necessary) the index including the field(s) + directions*/
	@Deprecated
	<T> void ensureIndex(Class<T> clazz, IndexFieldDef...fields);
	/** Ensures (creating if necessary) the index including the field(s) + directions*/
	@Deprecated
	<T> void ensureIndex(Class<T> clazz, String name, IndexFieldDef[] fields, boolean unique, boolean dropDupsOnCreate);
	/** Ensures (creating if necessary) the index including the field(s) + directions; eg fields = "field1, -field2" ({field1:1, field2:-1}) */
	<T> void ensureIndex(Class<T> clazz, String fields);
	/** Ensures (creating if necessary) the index including the field(s) + directions; eg fields = "field1, -field2" ({field1:1, field2:-1}) */
	<T> void ensureIndex(Class<T> clazz, String name, String fields, boolean unique, boolean dropDupsOnCreate);

	/** Ensures (creating if necessary) the indexes found during class mapping (using {@code @Indexed, @Indexes)}*/
	void ensureIndexes();
	/** Ensures (creating if necessary) the indexes found during class mapping (using {@code @Indexed, @Indexes)}, possibly in the background*/
	void ensureIndexes(boolean background);
	/** Ensures (creating if necessary) the indexes found during class mapping (using {@code @Indexed, @Indexes)}*/
	<T> void ensureIndexes(Class<T>  clazz);
    <T> void ensureIndexes(Class<T>  clazz,String colName);

	/** Ensures (creating if necessary) the indexes found during class mapping (using {@code @Indexed, @Indexes)}, possibly in the background*/
	<T> void ensureIndexes(Class<T>  clazz, boolean background);

    <T, V> T get(Class<T> clazz, V id,String ... fields);
    <T, V> T get(Class<T> clazz, V id,String colName,String ... fields);

	/** ensure capped DBCollections for {@code Entity}(s) */
	void ensureCaps();
	
	DB getDB();

	Mongo getMongo();

    Mapper getMapper();
	
	DBCollection getCollection(Class<?> c);

    DBCollection getCollection(String colName);
	
	WriteConcern getDefaultWriteConcern();

	void setDefaultWriteConcern(WriteConcern wc);

    DBDecoderFactory getDecoderFactory();
}