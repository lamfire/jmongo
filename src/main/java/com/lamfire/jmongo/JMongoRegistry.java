package com.lamfire.jmongo;

import com.mongodb.Mongo;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lamfire on 16/4/15.
 */
public class JMongoRegistry {

    private static final JMongoRegistry instance = new JMongoRegistry();

    public static JMongoRegistry getInstance(){
        return instance;
    }

    private Map<String, Mongo> pool = new HashMap<String, Mongo>();

    private JMongoRegistry(){

    }

    public synchronized Mongo register(MongoOpts opts){
        if(opts == null){
            throw new RuntimeException("the parameter 'MongoOpts' cannot be null.");
        }
        String id = opts.getId();
        Mongo mongo = pool.get(id);
        if(mongo != null){
            throw new RuntimeException("the id was exists : " + id);
        }
        mongo =  new Mongo(opts.seeds,opts.options);
        mongo.setReadPreference(ReadPreference.secondaryPreferred());
        mongo.setWriteConcern(WriteConcern.NORMAL);
        pool.put(id, mongo);
        return mongo;
    }

    public Mongo lookup(String id){
        Mongo mongo =  pool.get(id);
        return mongo;
    }
}
