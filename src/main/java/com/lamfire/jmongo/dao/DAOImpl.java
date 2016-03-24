package com.lamfire.jmongo.dao;

import java.util.*;

import com.lamfire.jmongo.*;
import com.lamfire.jmongo.mapping.MappedClass;
import com.lamfire.jmongo.logger.Logger;
import com.lamfire.jmongo.Mapping;
import com.lamfire.jmongo.query.Query;
import com.lamfire.jmongo.query.QueryResults;
import com.lamfire.jmongo.query.UpdateOperations;
import com.lamfire.jmongo.query.UpdateResults;
import com.mongodb.*;

/**
 * @author lamfire
 */
@SuppressWarnings("unchecked")
public class DAOImpl<T, K> implements DAO<T, K> {
	private static final Logger LOGGER = Logger.getLogger(DAOImpl.class);
    protected final String kind;
	protected Class<T> entityClazz;
    protected MappedClass mappedClass;
	protected Datastore ds;
    protected Mongo mongo;
    protected Mapping mapping;
    protected String dbName;

    public DAOImpl(Mongo mongo, Mapping mapping, String dbName,Class<T> entityClass,String kind) {
        this.mongo = mongo;
        this.mapping = mapping;
        this.dbName = dbName;
        this.entityClazz = entityClass;
        this.mappedClass =mapping.getMapper().getMappedClass(entityClass);
        this.kind = kind;
        this.ds  = new DatastoreImpl(mapping.getMapper(), mongo, dbName);
        ensureIndexes();
    }
	
	public DAOImpl(Mongo mongo, Mapping mapping, String dbName,Class<T> entityClass) {
        this.mongo = mongo;
        this.mapping = mapping;
        this.dbName = dbName;
        this.entityClazz = entityClass;
        this.mappedClass =mapping.getMapper().getMappedClass(entityClass);
        this.kind = mappedClass.getCollectionName();
        this.ds  = new DatastoreImpl(mapping.getMapper(), mongo, dbName);
        ensureIndexes();
	}

	protected List<?> keysToIds(List<Key<T>> keys) {
		ArrayList ids = new ArrayList(keys.size() * 2);
		for (Key<T> key : keys)
			ids.add(key.getId());
		return ids;
	}
	
	/** The underlying collection for this DAO */
	public DBCollection getCollection() {
		return ds.getCollection(kind);
	}

	public Query<T> createQuery() {
		return ds.createQuery(kind,entityClazz);
	}
	

	public UpdateOperations<T> createUpdateOperations() {
		return ds.createUpdateOperations(entityClazz);
	}

	public Class<T> getEntityClass() {
		return entityClazz;
	}

	public Key<T> save(T entity) {
		return ds.save(kind,entity);
	}


    public Key<T> insert(T entity) {
        return ds.insert(kind,entity);
    }

    public Key<T> save(T entity, WriteConcern wc) {
		return ds.save(kind,entity, wc);
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
		return ds.delete(kind,entity);
	}


	public WriteResult delete(T entity, WriteConcern wc) {
		return ds.delete(kind,entity, wc);
	}

	public WriteResult deleteById(K id) {
		return ds.delete(kind,entityClazz,id);
	}

	public WriteResult deleteByQuery(Query<T> q) {
		return ds.delete(q);
	}

	public T get(K id) {
		return ds.get(kind,entityClazz,id);
	}

	public T get(K id, String... fields) {
		return ds.get(kind,entityClazz, id,fields);
	}
	
	public List<T> gets(Iterable<K> ids) {
		return this.createQuery().field("_id").in(ids).asList();
	}
	

	public List<K> findIds(String key, Object value) {
		return (List<K>) keysToIds(ds.find(kind,entityClazz, key, value).asKeyList());
	}
	

	public List<K> findIds() {
		return (List<K>) keysToIds(ds.find(kind,entityClazz).asKeyList());
	}
	
	/* (non-Javadoc)
	 * @see com.google.code.jmongo.DAO#findIds(com.google.code.jmongo.query.Query)
	 */
	public List<K> findIds(Query<T> q) {
		return (List<K>) keysToIds(q.asKeyList());
	}

	public boolean exists(K id) {
		return exists(createQuery().field("_id").equal(id));
	}
	

	public boolean exists(String key, Object value) {
		return exists(ds.find(kind,entityClazz, key, value));
	}
	

	public boolean exists(Query<T> q) {
		return ds.getCount(q) > 0;
	}

	public long count() {
		return ds.getCount(kind);
	}

	public long count(String key, Object value) {
		return count(ds.find(kind,entityClazz, key, value));
	}

	public long count(Query<T> q) {
		return ds.getCount(q);
	}

	public T findOne(String key, Object value) {
		return ds.find(kind,entityClazz, key, value).get();
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
        EnsureIndexesMgr mgr = EnsureIndexesMgr.getInstance();
        mgr.ensureIndexes(mongo,ds,kind,entityClazz);
	}

	public T incrementAndGet(K id, String fieldName) {
		Query<T> q = this.ds.find(kind,entityClazz, "_id", id);
		UpdateOperations<T> uOps = this.ds.createUpdateOperations(entityClazz).inc(fieldName);
		return this.ds.findAndModify(q, uOps);
	}

	public T decrementAndGet(K id, String fieldName) {
		Query<T> q = this.ds.find(kind,entityClazz, "_id", id);
		UpdateOperations<T> uOps = this.ds.createUpdateOperations(entityClazz).dec(fieldName);
		return this.ds.findAndModify(q, uOps);
	}
	
	public T incrementAndGet(K id, String fieldName,String ... includeFields) {
		Query<T> q = this.ds.find(kind,entityClazz, "_id", id);
		for(String f : includeFields){
			q.includeFields(f);
		}
		UpdateOperations<T> uOps = this.ds.createUpdateOperations(entityClazz).inc(fieldName);
		return this.ds.findAndModify(q, uOps);
	}

	public T decrementAndGet(K id, String fieldName,String ... includeFields) {
		Query<T> q = this.ds.find(kind,entityClazz, "_id", id);
		for(String f : includeFields){
			q.includeFields(f);
		}
		UpdateOperations<T> uOps = this.ds.createUpdateOperations(entityClazz).dec(fieldName);
		return this.ds.findAndModify(q, uOps);
	}

	public void increment(K id, String fieldName) {
		UpdateOperations<T> uOps = this.ds.createUpdateOperations(entityClazz).inc(fieldName);
		Key<T> key = new Key<T>(entityClazz, id);
		this.ds.update(kind,key, uOps);
	}

	public void decrement(K id, String fieldName) {
		UpdateOperations<T> uOps = this.ds.createUpdateOperations(entityClazz).dec(fieldName);
		Key<T> key = new Key<T>(entityClazz, id);
		this.ds.update(kind,key, uOps);
	}

	public void increment(K id, String fieldName, Number val) {
		UpdateOperations<T> uOps = this.ds.createUpdateOperations(entityClazz).inc(fieldName,val);
		Key<T> key = new Key<T>(entityClazz, id);
		this.ds.update(kind,key, uOps);
	}

	public T incrementAndGet(K id, String fieldName, Number val) {
		Query<T> q = this.ds.find(kind,entityClazz, "_id", id);
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

    public DBObject toDBObject(T entity){
        return ds.toDBObject(entity);
    }

    public void addFieldValue(K id,String fieldName,Object value){
        UpdateOperations<T> up = getDatastore().createUpdateOperations(entityClazz);
        up.disableValidation();
        up.add(fieldName, value);
        Key<T> key = new Key<T>(entityClazz, id);
        getDatastore().update(key,up);
    }

    public void setFieldValue(K id,String fieldName,Object value){
        UpdateOperations<T> up = getDatastore().createUpdateOperations(entityClazz);
        up.disableValidation();
        up.set(fieldName,value);
        Key<T> key = new Key<T>(entityClazz, id);
        getDatastore().update(key,up);
    }

    public Object getFieldValue(K id,String fieldName){
        DBObject fields = new BasicDBObject();
        fields.put(fieldName,1);
        DBObject one = getCollection().findOne(id,fields);
        if(one == null){
            return null;
        }
        Object o =  one.get(fieldName);
        return o;
    }

    public Map<String,Object> getAsMap(K id){
        DBObject one = getCollection().findOne(id);
        if(one == null){
            return null;
        }
        return (Map<String,Object>)one;
    }

    public Map<String,Object> getAsMap(K id,String ... fields){
        DBObject one = null;
        if(fields != null){
            DBObject fieldsObj = new BasicDBObject();
            for(String f : fields){
                fieldsObj.put(f,1);
            }
            one = getCollection().findOne(id,fieldsObj);
        }else{
            one = getCollection().findOne(id);
        }

        if(one == null){
            return null;
        }
        return (Map<String,Object>)one;
    }

	public Key<T> save(Map<String,Object> map){
		DBObject obj = new BasicDBObject();
		obj.putAll(map);
		return getDatastore().save(getCollection(),obj);
	}
}
