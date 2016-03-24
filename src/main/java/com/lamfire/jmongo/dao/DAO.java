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
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

public interface DAO<T, K> {

	public Query<T> createQuery();

	public UpdateOperations<T> createUpdateOperations();

	public Class<T> getEntityClass();

	public Key<T> save(T entity);

    public Key<T> insert(T entity);

	public Key<T> save(T entity, WriteConcern wc);

	public UpdateResults<T> update(Query<T> q, UpdateOperations<T> ops);
	
	public UpdateResults<T> update(K k, String fieldName,Object value);
	
	public UpdateResults<T> update(K k, Map<String,Object> fieldAndValMap);

	public WriteResult delete(T entity);

	public WriteResult delete(T entity, WriteConcern wc);

	public WriteResult deleteById(K id);

	public WriteResult deleteByQuery(Query<T> q);

	public T get(K id);

	public T get(K id,String ... includeFields);

	public List<K> findIds(String key, Object value);

	public List<K> findIds();

	public List<K> findIds(Query<T> q);

	public boolean exists(String key, Object value);

	public boolean exists(Query<T> q);

	public boolean exists(K id);

	public long count();

	public long count(String key, Object value);

	public long count(Query<T> q);

	public T findOne(String key, Object value);

	public T findOne(Query<T> q);

	public QueryResults<T> find();

	public QueryResults<T> find(Query<T> q);

	public void ensureIndexes();

	public DBCollection getCollection();

	public Datastore getDatastore();

    public DBObject toDBObject(T entity);
	
	public List<T> gets(Iterable<K> ids);

	public T incrementAndGet(K id, String fieldName);

	public T decrementAndGet(K id, String fieldName);

	public void increment(K id, String fieldName);

	public void decrement(K id, String fieldName);
	
	public T incrementAndGet(K id, String fieldName,Number val);
	
	public T incrementAndGet(K id, String fieldName,String ... includeFields);

	public T decrementAndGet(K id, String fieldName,String ... includeFields) ;

	public void increment(K id, String fieldName,Number val);

	public List<Object> distinct(String fieldName);

	public List<Object> distinct(String fieldName, Query<T> q);
	
	public AggregateResults aggregate(Aggregator aggregator);

	public AggregateResults group(GroupBy group);

	public AggregateResults group(Query<T> query, GroupBy group);
	
	public AggregateResults group(Query<T> query,String groupField);
	
	public long distinctCount(Query<T> query,String distinctField);
	
	public long count(Aggregator aggregator);

    public void setFieldValue(K id,String fieldName,Object value);

    public void addFieldValue(K id,String fieldName,Object value);

    public Object getFieldValue(K id,String fieldName);

    public Map<String,Object> getAsMap(K id);

    public Map<String,Object> getAsMap(K id,String ... fields);

	public Key<T> save(Map<String,Object> map);

    public Key<T> save(K id,Map<String,Object> map);
}