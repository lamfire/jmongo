package com.lamfire.jmongo.dao;

import java.util.List;
import java.util.Map;

import com.lamfire.jmongo.Aggregator;
import com.lamfire.jmongo.Datastore;
import com.lamfire.jmongo.GroupBy;
import com.lamfire.jmongo.AggregateResults;
import com.lamfire.jmongo.Key;
import com.lamfire.jmongo.query.Query;
import com.lamfire.jmongo.query.QueryResults;
import com.lamfire.jmongo.query.UpdateOperations;
import com.lamfire.jmongo.query.UpdateResults;
import com.mongodb.DBCollection;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

public interface DAO<T, K> {
	/** Starts a query for this DAO entities type */
	public Query<T> createQuery();

	/** Starts a update-operations def for this DAO entities type */
	public UpdateOperations<T> createUpdateOperations();

	/** The type of entities for this DAO */
	public Class<T> getEntityClass();

	/** Saves the entity; either inserting or overriding the existing document */
	public Key<T> save(T entity);

	/** Saves the entity; either inserting or overriding the existing document */
	public Key<T> save(T entity, WriteConcern wc);

	/**
	 * Updates all entities matched by the constraints with the modifiers
	 * supplied.
	 */
	public UpdateResults<T> update(Query<T> q, UpdateOperations<T> ops);
	
	public UpdateResults<T> update(K k, String fieldName,Object value);
	
	public UpdateResults<T> update(K k, Map<String,Object> fieldAndValMap);

	/** Deletes the entity */
	public WriteResult delete(T entity);

	/**
	 * Deletes the entity
	 * 
	 * @return
	 */
	public WriteResult delete(T entity, WriteConcern wc);

	/** Delete the entity by id value */
	public WriteResult deleteById(K id);

	/** Saves the entities given the query */
	public WriteResult deleteByQuery(Query<T> q);

	/** Loads the entity by id value */
	public T get(K id);
	
	/** Loads the entity by id value */
	public T get(K id,String ... includeFields);

	/** Finds the entities Key<T> by the criteria {key:value} */
	public List<K> findIds(String key, Object value);

	/** Finds the entities Ts */
	public List<K> findIds();

	/** Finds the entities Ts by the criteria {key:value} */
	public List<K> findIds(Query<T> q);

	/** checks for entities which match criteria {key:value} */
	public boolean exists(String key, Object value);

	/** checks for entities which match the criteria */
	public boolean exists(Query<T> q);

	/** returns the total count */
	public long count();

	/** returns the count which match criteria {key:value} */
	public long count(String key, Object value);

	/** returns the count which match the criteria */
	public long count(Query<T> q);

	/** returns the entity which match criteria {key:value} */
	public T findOne(String key, Object value);

	/** returns the entity which match the criteria */
	public T findOne(Query<T> q);

	/** returns the entities */
	public QueryResults<T> find();

	/** returns the entities which match the criteria */
	public QueryResults<T> find(Query<T> q);

	/** ensures indexed for this DAO */
	public void ensureIndexes();

	/** gets the collection */
	public DBCollection getCollection();

	/** returns the underlying datastore */
	public Datastore getDatastore();
	
	public List<T> gets(Iterable<K> ids);

	public T incAndGet(K id, String fieldName);

	public T decAndGet(K id, String fieldName);

	public void inc(K id, String fieldName);

	public void dec(K id, String fieldName);
	
	public T incAndGet(K id, String fieldName,Number val);
	
	public T incAndGet(K id, String fieldName,String ... includeFields);

	public T decAndGet(K id, String fieldName,String ... includeFields) ;

	public void inc(K id, String fieldName,Number val);

	public List<Object> distinct(String fieldName);

	public List<Object> distinct(String fieldName, Query<T> q);
	
	public AggregateResults aggregate(Aggregator aggregator);

	public AggregateResults group(GroupBy group);

	public AggregateResults group(Query<T> query, GroupBy group);
	
	public AggregateResults group(Query<T> query,String groupField);
	
	public long distinctCount(Query<T> query,String distinctField);
	
	public long count(Aggregator aggregator);
}