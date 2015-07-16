package com.lamfire.jmongo.dao;

import com.lamfire.jmongo.JMongo;
import com.lamfire.jmongo.Mapping;
import com.mongodb.Mongo;

public abstract class DAOSupport<E,K> extends DAOImpl<E,K>{

    public DAOSupport(String mongo,String dbName,Class<E> clazz,String collection) {
        super(JMongo.getMongo(mongo), JMongo.getMapping(dbName),dbName,clazz,collection);
    }
	
	public DAOSupport(String mongo,String dbName,Class<E> clazz) {
		super(JMongo.getMongo(mongo), JMongo.getMapping(dbName),dbName,clazz);
	}
	
	protected DAOSupport(String dbName) {
		super(JMongo.getMongo(), JMongo.getMapping(dbName), dbName);
	}
	
	protected DAOSupport(String mongo,String dbName) {
		super(JMongo.getMongo(mongo), JMongo.getMapping(dbName), dbName);
	}

	protected DAOSupport(Mongo mongo, Mapping morphia, String dbName) {
		super(mongo, morphia, dbName);
	}
	
}
