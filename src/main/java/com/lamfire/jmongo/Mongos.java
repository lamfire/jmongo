package com.lamfire.jmongo;

import java.util.HashMap;
import java.util.Map;

import com.lamfire.jmongo.config.Configuration;
import com.lamfire.logger.Logger;
import com.mongodb.Mongo;


public class Mongos {
	private static final Logger LOGGER = Logger.getLogger(Mongos.class);
	private static final Map<String, Mapping> morphias = new HashMap<String, Mapping>();
	private static final String DEFAULT_ID = "default";
	private static Map<String, Mongo> pool = new HashMap<String, Mongo>();
	
	private static synchronized Mongo newMongo(MongoOpts opts){
		if(opts == null){
			throw new RuntimeException("the parameter 'MongoOpts' cannot be null.");
		}
		String id = opts.getId();
		Mongo mongo = pool.get(id);
		if(mongo != null){
			LOGGER.error("the mongo id '"+id+"' exists.");
			return mongo;
		}
		mongo =  new Mongo(opts.seeds,opts.options);
		pool.put(id, mongo);
		return mongo;
	}

	public static Mongo getMongo(String id){
		Mongo mongo =  pool.get(id);
		if(mongo == null){
			MongoOpts opts = Configuration.getInstance().getMongoOpts(id);
			if(opts != null){
				mongo = newMongo(opts);
			}
		}
		return mongo;
	}

	public static Mongo getMongo(){
		Mongo mongo = getMongo(DEFAULT_ID);
		if(mongo == null){
			MongoOpts opts = Configuration.getInstance().getMongoOpts(DEFAULT_ID);
			mongo = newMongo(opts);
		}
		return mongo;
	}
	
	public static synchronized Mapping getMapping(){
		return getMapping(DEFAULT_ID);
	}
	
	public static synchronized Mapping getMapping(String dbName){
		Mapping morphia = morphias.get(dbName);
		if(morphia != null){
			return morphia;
		}
		morphia = new Mapping();
		morphias.put(dbName, morphia);
		return morphia;
	}
	
	public static Datastore getDatastore(String mongoId,String dbName){
		Datastore ds =  getMapping(dbName).createDatastore(getMongo(mongoId), dbName);
		ds.ensureIndexes();
		return ds;
	}

	public static Datastore getDatastore(String dbName){
		Datastore ds =  getMapping(dbName).createDatastore(getMongo(), dbName);
		ds.ensureIndexes();
		return ds;
	}

}
