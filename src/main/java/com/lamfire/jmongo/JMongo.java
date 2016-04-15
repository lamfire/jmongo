package com.lamfire.jmongo;

import java.util.HashMap;
import java.util.Map;

import com.lamfire.jmongo.config.Configuration;
import com.lamfire.jmongo.logger.Logger;
import com.mongodb.Mongo;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;


public class JMongo {
	private static final Logger LOGGER = Logger.getLogger(JMongo.class);
	private static final Map<String, Mapping> mappings = new HashMap<String, Mapping>();


	public static synchronized Mongo getMongo(String id){
		Mongo mongo =  JMongoRegistry.getInstance().lookup(id);
		if(mongo == null){
			MongoOpts opts = Configuration.getInstance().getMongoOpts(id);
			if(opts == null) {
				throw new RuntimeException("The id[" + id + "] settings not found at 'jmongo.properties'");
			}
			mongo =  JMongoRegistry.getInstance().register(opts);
		}
		return mongo;
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
