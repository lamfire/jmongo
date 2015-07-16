package com.lamfire.jmongo;

import java.util.HashMap;
import java.util.Map;

import com.lamfire.jmongo.config.Configuration;
import com.lamfire.jmongo.logger.Logger;
import com.mongodb.Mongo;


public class JMongo {
	private static final Logger LOGGER = Logger.getLogger(JMongo.class);
	private static final Map<String, Mapping> mappings = new HashMap<String, Mapping>();
	private static final String DEFAULT_ID = "default";
	private static Map<String, Mongo> pool = new HashMap<String, Mongo>();
	
	public static synchronized Mongo register(MongoOpts opts){
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
				mongo = register(opts);
			}
		}
		return mongo;
	}

	public static Mongo getMongo(){
		Mongo mongo = getMongo(DEFAULT_ID);
		if(mongo == null){
			MongoOpts opts = Configuration.getInstance().getMongoOpts(DEFAULT_ID);
			mongo = register(opts);
		}
		return mongo;
	}
	
	public static synchronized Mapping getMapping(){
		return getMapping(DEFAULT_ID);
	}
	
	public static synchronized Mapping getMapping(String dbName){
		Mapping mapping = mappings.get(dbName);
		if(mapping != null){
			return mapping;
		}
        mapping = new Mapping();
		mappings.put(dbName, mapping);
		return mapping;
	}

}
