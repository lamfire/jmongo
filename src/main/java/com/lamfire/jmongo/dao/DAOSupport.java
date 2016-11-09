package com.lamfire.jmongo.dao;

import com.lamfire.jmongo.JMongo;
import com.lamfire.jmongo.Mapping;
import com.mongodb.Mongo;

public abstract class DAOSupport<E,K> extends DAOImpl<E,K>{

    public DAOSupport(String mongoName,String dbName,Class<E> clazz,String kind) {
        super(JMongo.getMongo(mongoName), JMongo.getMapping(dbName),dbName,clazz,kind);
    }
	
	public DAOSupport(String mongoName,String dbName,Class<E> clazz) {
		super(JMongo.getMongo(mongoName), JMongo.getMapping(dbName),dbName,clazz);
	}

    public DAOSupport(String mongoName,String dbName,String user,String password,Class<E> clazz,String kind) {
        super(JMongo.getMongo(mongoName), JMongo.getMapping(dbName),dbName,user,password,clazz,kind);
    }

    public DAOSupport(String mongoName,String dbName,String user,String password,Class<E> clazz) {
        super(JMongo.getMongo(mongoName), JMongo.getMapping(dbName),dbName,user,password,clazz);
    }

}
