package com.lamfire.jmongo;

import com.lamfire.jmongo.logger.Logger;

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

    public boolean isEnsureIndexes(Datastore ds,String kind){
        String key = ds.getName() + "/" + kind;
        return indexes.containsKey(key);
    }

    public synchronized void ensureIndexes(Datastore ds,String kind,Class<?> entityClazz){
        String key = ds.getName() + "/" + kind;
        if(indexes.containsKey(key)){
            return;
        }
        LOGGER.info("[EnsureIndexes] : " + key +" -> " + entityClazz.getName());
        ds.ensureIndexes(kind,entityClazz);
        indexes.put(key,entityClazz);
    }


}
