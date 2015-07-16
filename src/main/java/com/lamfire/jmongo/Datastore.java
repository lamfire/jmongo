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

@SuppressWarnings("unchecked")
public interface Datastore {	

	<T> Key<T> getKey(T entity);

	Key<?> exists(Object keyOrEntity);

	<T,V> WriteResult delete(Class<T> clazz, V id);

    <T,V> WriteResult delete(String kind,Class<T> clazz, V id);

	<T,V> WriteResult delete(Class<T> clazz, Iterable<V> ids);

	<T> WriteResult delete(Query<T> q);

	<T> WriteResult delete(Query<T> q, WriteConcern wc);

	<T> WriteResult delete(T entity);

    <T> WriteResult delete(String kind,T entity);

	<T> WriteResult delete(T entity, WriteConcern wc);

    <T> WriteResult delete(String kind,T entity, WriteConcern wc);

	<T> Query<T> find(Class<T> clazz);

    <T> Query<T> find(String kind,Class<T> clazz);

	<T, V> Query<T> find(Class<T> clazz, String property, V value);

    <T, V> Query<T> find(String kind, Class<T> clazz,String property, V value);

	<T,V> Query<T> find(Class<T> clazz, String property, V value, int offset, int size);

	<T,V> Query<T> get(Class<T> clazz, Iterable<V> ids);

	<T,V> T get(Class<T> clazz, V id);

    <T,V> T get(String kind,Class<T> clazz, V id);

	<T> T get(T entity);

	<T> List<T> getByKeys(Class<T> clazz, Iterable<Key<T>> keys);

	<T> List<T> getByKeys(Iterable<Key<T>> keys);

	<T> T getByKey(String kind,Class<T> clazz, Key<T> key);

	<T> long getCount(T entity);

	<T> long getCount(Class<T> clazz);

    <T> long getCount(String kind);

	<T> long getCount(Query<T> query); 
	

    <T> Key<T> save(String kind, T entity);

	<T> Iterable<Key<T>> save(Iterable<T> entities);

	<T> Iterable<Key<T>> save(Iterable<T> entities, WriteConcern wc);

	<T> Iterable<Key<T>> save(T... entities);

    <T> Iterable<Key<T>> save(String kind,T... entities);

	<T> Key<T> save(T entity);

	<T> Key<T> save(T entity, WriteConcern wc);

    <T> Key<T> save(String kind,T entity, WriteConcern wc);

    <T> Key<T> insert(String kind, T entity);

    <T> Key<T> insert(DBCollection dbColl, T entity, WriteConcern wc);

	<T> Key<T> merge(String kind,T entity);

	<T> Key<T> merge(String kind,T entity, WriteConcern wc);

	<T> UpdateResults<T> update(T ent, UpdateOperations<T> ops);

	<T> UpdateResults<T> update(Key<T> key, UpdateOperations<T> ops);

    <T> UpdateResults<T> update(String kind,Key<T> key, UpdateOperations<T> ops);
	
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

	<T> T findAndDelete(Query<T> q);

	<T> T findAndModify(Query<T> q, UpdateOperations<T> ops);

	<T> T findAndModify(Query<T> q, UpdateOperations<T> ops, boolean oldVersion);

	<T> T findAndModify(Query<T> q, UpdateOperations<T> ops, boolean oldVersion, boolean createIfMissing);

	<T> MapreduceResults<T> mapReduce(MapreduceType type, Query q, String map, String reduce, String finalize, Map<String, Object> scopeFields, Class<T> outputType);

	<T> MapreduceResults<T> mapReduce(MapreduceType type, Query q, Class<T> outputType, MapReduceCommand baseCommand);
	
	/** The builder for all update operations */
	<T> UpdateOperations<T> createUpdateOperations(Class<T> kind);

	/** Returns a new query bound to the kind (a specific {@link DBCollection})  */
	<T> Query<T> createQuery(Class<T> kind);

    <T> Query<T> createQuery(String kind,Class<T> claxx);

	/** Returns a new query based on the example object*/
	<T> Query<T> queryByExample(T example);

	<T> void ensureIndex(Class<T> clazz, String field, IndexDirection dir);

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

    <T> void ensureIndexes(String kind,Class<T>  clazz);

	/** Ensures (creating if necessary) the indexes found during class mapping (using {@code @Indexed, @Indexes)}, possibly in the background*/
	<T> void ensureIndexes(Class<T>  clazz, boolean background);

    <T, V> T get(Class<T> clazz, V id,String ... fields);

    <T, V> T get(String kind,Class<T> clazz, V id,String ... fields);

	/** ensure capped DBCollections for {@code Entity}(s) */
	void ensureCaps();
	
	DB getDB();

	Mongo getMongo();

    Mapper getMapper();
	
	DBCollection getCollection(Class<?> c);

    DBCollection getCollection(String kind);
	
	WriteConcern getDefaultWriteConcern();

	void setDefaultWriteConcern(WriteConcern wc);

    DBDecoderFactory getDecoderFactory();
}