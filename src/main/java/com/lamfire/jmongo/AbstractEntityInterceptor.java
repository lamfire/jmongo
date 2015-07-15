/**
 * 
 */
package com.lamfire.jmongo;

import com.lamfire.jmongo.mapping.Mapper;
import com.mongodb.DBObject;

public class AbstractEntityInterceptor implements EntityInterceptor {
	
	public void postLoad(Object ent, DBObject dbObj, Mapper mapr) {
	}
	
	public void postPersist(Object ent, DBObject dbObj, Mapper mapr) {
	}
	
	public void preLoad(Object ent, DBObject dbObj, Mapper mapr) {
	}
	
	public void prePersist(Object ent, DBObject dbObj, Mapper mapr) {
	}
	
	public void preSave(Object ent, DBObject dbObj, Mapper mapr) {
	}
}
