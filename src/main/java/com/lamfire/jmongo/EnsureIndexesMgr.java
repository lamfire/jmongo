package com.lamfire.jmongo;

import com.lamfire.jmongo.logger.Logger;
import com.mongodb.Mongo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-8-31
 * Time: 上午10:03
 * To change this template use File | Settings | File Templates.
 */
public class EnsureIndexesMgr {
    private static final Logger LOGGER = Logger.getLogger(EnsureIndexesMgr.class);
    private final Map<String,Class<?>> indexes = new HashMap<String, Class<?>>();

    private static final EnsureIndexesMgr instance = new EnsureIndexesMgr();

    public static EnsureIndexesMgr getInstance(){
        return instance;
    }

    private String getKey(Mongo mongo,Datastore ds,String kind){
        String key = mongo.getAddress() +"/" + ds.getName() + "/" + kind;
        return key;
    }

    public boolean isEnsureIndexes(Mongo mongo,Datastore ds,String kind){
        String key = getKey(mongo,ds,kind);
        return indexes.containsKey(key);
    }

    public synchronized void ensureIndexes(Mongo mongo,Datastore ds,String kind,Class<?> entityClazz){
        String key = getKey(mongo,ds,kind);
        if(indexes.containsKey(key)){
            return;
        }
        LOGGER.info("[EnsureIndexes] : " + key +" -> " + entityClazz.getName());
        ds.ensureIndexes(kind,entityClazz);
        indexes.put(key,entityClazz);
    }


}
