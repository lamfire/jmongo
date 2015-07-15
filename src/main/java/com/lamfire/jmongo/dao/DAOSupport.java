package com.lamfire.jmongo.dao;

import com.lamfire.jmongo.Mapping;
import com.lamfire.jmongo.Mongos;
import com.mongodb.Mongo;

public abstract class DAOSupport<E,K> extends DAOImpl<E,K>{

    public DAOSupport(String mongo,String dbName,Class<E> clazz,String collection) {
        super(Mongos.getMongo(mongo), Mongos.getMapping(dbName),dbName,clazz,collection);
    }
	
	public DAOSupport(String mongo,String dbName,Class<E> clazz) {
		super(Mongos.getMongo(mongo), Mongos.getMapping(dbName),dbName,clazz);
	}
	
	protected DAOSupport(String dbName) {
		super(Mongos.getMongo(), Mongos.getMapping(dbName), dbName);
	}
	
	protected DAOSupport(String mongo,String dbName) {
		super(Mongos.getMongo(mongo), Mongos.getMapping(dbName), dbName);
	}

	protected DAOSupport(Mongo mongo, Mapping morphia, String dbName) {
		super(mongo, morphia, dbName);
	}
	
}
