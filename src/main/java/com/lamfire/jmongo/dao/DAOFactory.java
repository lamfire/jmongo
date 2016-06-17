package com.lamfire.jmongo.dao;

import com.lamfire.jmongo.JMongo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 16-3-22
 * Time: 上午11:54
 * To change this template use File | Settings | File Templates.
 */
public class DAOFactory {
    private static final Map<String,DAO<?,?>> daos = new HashMap<String, DAO<?, ?>>();

    public synchronized static <T,K> DAO<T,K>  get(String server,String db,String kind,Class<T> entityClass){
        String key = String.format("%s-%s-%s",server,db,kind);
        DAO<T,K> dao = (DAO<T,K>)daos.get(key);
        if(dao != null){
            return dao;
        }

        dao = new DAOImpl<T, K>(JMongo.getMongo(server),JMongo.getMapping(db),db,entityClass,kind);
        daos.put(key,dao);
        return dao;
    }

    public static <T,K> DAO<T,K>  get(String server,String db,Class<T> entityClass){
        String kind = JMongo.getMapping(db).getMapper().getMappedClass(entityClass).getCollectionName();
        return get(server,db,kind,entityClass);
    }

    public synchronized static <T,K> DAO<T,K>  get(String server,String db,String username,String password,String kind,Class<T> entityClass){
        String key = String.format("%s-%s-%s",server,db,kind);
        DAO<T,K> dao = (DAO<T,K>)daos.get(key);
        if(dao != null){
            return dao;
        }

        dao = new DAOImpl<T, K>(JMongo.getMongo(server),JMongo.getMapping(db),db,username,password,entityClass,kind);
        daos.put(key,dao);
        return dao;
    }

    public static <T,K> DAO<T,K>  get(String server,String db,String username,String password,Class<T> entityClass){
        String kind = JMongo.getMapping(db).getMapper().getMappedClass(entityClass).getCollectionName();
        return get(server,db,username,password,kind,entityClass);
    }
}
