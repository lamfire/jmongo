package com.lamfire.jmongo.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lamfire.jmongo.*;
import com.lamfire.jmongo.mapping.MappedClass;
import com.lamfire.logger.Logger;
import com.lamfire.jmongo.Mapping;
import com.lamfire.jmongo.query.Query;
import com.lamfire.jmongo.query.QueryResults;
import com.lamfire.jmongo.query.UpdateOperations;
import com.lamfire.jmongo.query.UpdateResults;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

/**
 * @author lamfire
 */
@SuppressWarnings("unchecked")
public class DAOImpl<T, K> implements DAO<T, K> {
	private static final Logger LOGGER = Logger.getLogger(DAOImpl.class);
	protected Class<T> entityClazz;
    protected String colName;
	protected Datastore ds;

    public DAOImpl(Mongo mongo, Mapping mapping, String dbName,Class<T> entityClass,String colName) {
        initDS(mongo, mapping, dbName);
        initType(entityClass);
        this.colName = colName;
        ensureIndexes();
    }
	
	public DAOImpl(Mongo mongo, Mapping mapping, String dbName,Class<T> entityClass) {
		initDS(mongo, mapping, dbName);
		initType(entityClass);
        ensureIndexes();
	}

	protected DAOImpl(Mongo mongo, Mapping mapping, String dbName) {
		initDS(mongo, mapping, dbName);
		initType(((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
        ensureIndexes();
	}
	
	protected DAOImpl(Datastore ds) {
		this.ds = (DatastoreImpl) ds;
		initType(((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
        ensureIndexes();
	}
	
	protected synchronized void initType(Class<T> type) {
		this.entityClazz = type;
		if(ds.getMapper().isMapped(type)){
			return;
		}
		LOGGER.info("[MAPPING]:" + type.getName());
        MappedClass mappedClass = ds.getMapper().addMappedClass(type);
        this.colName = mappedClass.getCollectionName();
	}
	protected void initDS(Mongo mon, Mapping mor, String db) {
		ds = new DatastoreImpl(mor, mon, db);
	}
	

	protected List<?> keysToIds(List<Key<T>> keys) {
		ArrayList ids = new ArrayList(keys.size() * 2);
		for (Key<T> key : keys)
			ids.add(key.getId());
		return ids;
	}
	
	/** The underlying collection for this DAO */
	public DBCollection getCollection() {
		return ds.getCollection(colName);
	}

	public Query<T> createQuery() {
		return ds.createQuery(entityClazz,colName);
	}
	

	public UpdateOperations<T> createUpdateOperations() {
		return ds.createUpdateOperations(entityClazz);
	}

	public Class<T> getEntityClass() {
		return entityClazz;
	}

	public Key<T> save(T entity) {
		return ds.save(entity,colName);
	}

	public Key<T> save(T entity, WriteConcern wc) {
		return ds.save(entity, wc,colName);
	}

	public UpdateResults<T> update(Query<T> q, UpdateOperations<T> ops) {
		return ds.update(q, ops);
	}
	
	public UpdateResults<T> update(K k, UpdateOperations<T> ops) {
		Key<T> key = new Key<T>(entityClazz, k);
		return ds.update(key, ops);
	}
	
	public UpdateResults<T> update(K k, String fieldName,Object value) {
		Key<T> key = new Key<T>(entityClazz, k);
		UpdateOperations<T> ops = this.ds.createUpdateOperations(entityClazz).set(fieldName, value);
		return ds.update(key, ops);
	}
	
	public UpdateResults<T> update(K k, Map<String,Object> fieldAndValMap) {
		Key<T> key = new Key<T>(entityClazz, k);
		UpdateOperations<T> ops = this.ds.createUpdateOperations(entityClazz);
		for(Map.Entry<String,Object> e : fieldAndValMap.entrySet()){
			ops.set(e.getKey(), e.getValue());
		}
		return ds.update(key, ops);
	}

	public WriteResult delete(T entity) {
		return ds.delete(entity,colName);
	}


	public WriteResult delete(T entity, WriteConcern wc) {
		return ds.delete(entity, wc,colName);
	}

	public WriteResult deleteById(K id) {
		return ds.delete(entityClazz, id);
	}

	public WriteResult deleteByQuery(Query<T> q) {
		return ds.delete(q);
	}
	
	/* (non-Javadoc)
	 * @see com.google.code.jmongo.DAO#get(K)
	 */
	public T get(K id) {
		return ds.get(entityClazz,id,colName);
	}

	public T get(K id, String... fields) {
		return ds.get(entityClazz, id,colName,fields);
	}
	
	public List<T> gets(Iterable<K> ids) {
		return this.createQuery().field("_id").in(ids).asList();
	}
	

	public List<K> findIds(String key, Object value) {
		return (List<K>) keysToIds(ds.find(entityClazz, key, value,colName).asKeyList());
	}
	

	public List<K> findIds() {
		return (List<K>) keysToIds(ds.find(entityClazz,colName).asKeyList());
	}
	
	/* (non-Javadoc)
	 * @see com.google.code.jmongo.DAO#findIds(com.google.code.jmongo.query.Query)
	 */
	public List<K> findIds(Query<T> q) {
		return (List<K>) keysToIds(q.asKeyList());
	}
	

	public boolean exists(String key, Object value) {
		return exists(ds.find(entityClazz, key, value,colName));
	}
	

	public boolean exists(Query<T> q) {
		return ds.getCount(q) > 0;
	}

	public long count() {
		return ds.getCount(entityClazz,colName);
	}

	public long count(String key, Object value) {
		return count(ds.find(entityClazz, key, value,colName));
	}

	public long count(Query<T> q) {
		return ds.getCount(q);
	}

	public T findOne(String key, Object value) {
		return ds.find(entityClazz, key, value,colName).get();
	}

	public T findOne(Query<T> q) {
		return q.get();
	}

	public QueryResults<T> find() {
		return createQuery();
	}

	public QueryResults<T> find(Query<T> q) {
		return q;
	}

	public Datastore getDatastore() {
		return ds;
	}
	
	public void ensureIndexes() {
		ds.ensureIndexes(entityClazz,colName);
	}

	public T incAndGet(K id, String fieldName) {
		Query<T> q = this.ds.find(entityClazz, "_id", id,colName);
		UpdateOperations<T> uOps = this.ds.createUpdateOperations(entityClazz).inc(fieldName);
		return this.ds.findAndModify(q, uOps);
	}

	public T decAndGet(K id, String fieldName) {
		Query<T> q = this.ds.find(entityClazz, "_id", id,colName);
		UpdateOperations<T> uOps = this.ds.createUpdateOperations(entityClazz).dec(fieldName);
		return this.ds.findAndModify(q, uOps);
	}
	
	public T incAndGet(K id, String fieldName,String ... includeFields) {
		Query<T> q = this.ds.find(entityClazz, "_id", id,colName);
		for(String f : includeFields){
			q.includeFields(f);
		}
		UpdateOperations<T> uOps = this.ds.createUpdateOperations(entityClazz).inc(fieldName);
		return this.ds.findAndModify(q, uOps);
	}

	public T decAndGet(K id, String fieldName,String ... includeFields) {
		Query<T> q = this.ds.find(entityClazz, "_id", id,colName);
		for(String f : includeFields){
			q.includeFields(f);
		}
		UpdateOperations<T> uOps = this.ds.createUpdateOperations(entityClazz).dec(fieldName);
		return this.ds.findAndModify(q, uOps);
	}

	public void inc(K id, String fieldName) {
		UpdateOperations<T> uOps = this.ds.createUpdateOperations(entityClazz).inc(fieldName);
		Key<T> key = new Key<T>(entityClazz, id);
		this.ds.update(key, uOps,colName);
	}

	public void dec(K id, String fieldName) {
		UpdateOperations<T> uOps = this.ds.createUpdateOperations(entityClazz).dec(fieldName);
		Key<T> key = new Key<T>(entityClazz, id);
		this.ds.update(key, uOps,colName);
	}

	@Override
	public void inc(K id, String fieldName, Number val) {
		UpdateOperations<T> uOps = this.ds.createUpdateOperations(entityClazz).inc(fieldName,val);
		Key<T> key = new Key<T>(entityClazz, id);
		this.ds.update(key, uOps,colName);
	}

	@Override
	public T incAndGet(K id, String fieldName, Number val) {
		Query<T> q = this.ds.find(entityClazz, "_id", id,colName);
		UpdateOperations<T> uOps = this.ds.createUpdateOperations(entityClazz).inc(fieldName,val);
		return this.ds.findAndModify(q, uOps);
	}
	
	protected  AggregateResults aggregate(DBObject firstOp, DBObject... additionalOps){
		return new AggregateResults( this.getCollection().aggregate(firstOp,additionalOps).results());
	}
	
	public AggregateResults aggregate(Aggregator aggregator){
		return aggregate(aggregator.firstOp(),aggregator.additionalOps());
	}

	public List<Object> distinct(String fieldName){
		return this.getCollection().distinct(fieldName);
	}
	
	public List<Object> distinct(String fieldName,Query<T> q){
		return q.distinct(fieldName);
	}
	
	public AggregateResults group(String field){
		return aggregate(new Aggregator().group(field));
	}
	
	public AggregateResults group(GroupBy group){
		return aggregate(group.getGroupObject());
	}
	
	public AggregateResults group(Query<T> query,GroupBy group){
		Aggregator aggregator = new Aggregator();
		aggregator.match(query).group(group);
		return (aggregate(aggregator));
	}
	
	public AggregateResults group(Query<T> query,String groupField){
		Aggregator aggregator = new Aggregator();
		aggregator.match(query).group(groupField);
		return (aggregate(aggregator));
	}
	
	public long distinctCount(Query<T> query,String distinctField){
		Aggregator aggregator = new Aggregator();
		aggregator.match(query).group(distinctField);
		return count(aggregator);
	}

	@Override
	public long count(Aggregator aggregator) {
		aggregator.group("__");
		AggregateResults  result = this.aggregate(aggregator);
		Iterator<DBObject> it = result.iterator();
		if(it.hasNext()){
			Number count = (Number)it.next().get("count");
			return count.longValue();
		}
		return 0l;
	}

}
