package com.lamfire.jmongo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mongodb.DBObject;

/**
 * 
 * @author lamfire
 *
 */
public class AggregateResults {
	private final Iterable<DBObject> results ;
	
	private Map<Object, DBObject> resultMap;
	private List<DBObject> resultAsList;
	
	public AggregateResults(Iterable<DBObject> results){
		this.results = results;
	}
	
	public synchronized Map<Object, DBObject> getResultMap(){
		if(resultMap == null){
			resultMap = new HashMap<Object, DBObject>();
		}
		for(DBObject obj : asList()){
			resultMap.put(obj.get("_id"), obj);
		}
		return resultMap;
	}
	
	public DBObject get(Object key){
		return getResultMap().get(key);
	}
		
	public synchronized List<DBObject> asList(){
		if(resultAsList != null){
			return resultAsList;
		}
		resultAsList = new ArrayList<DBObject>();
		for(DBObject obj : this.results){
			resultAsList.add(obj);
		}
		return resultAsList;
	}
	
	public Set<Object> keys(){
		return getResultMap().keySet();
	}
	
	public DBObject getFist(){
		Iterator<DBObject> it = this.results.iterator();
		if(it.hasNext()){
			return it.next();
		}
		return null;
	}
	
	public int size(){
		return getResultMap().size();
	}
	
	public  Set<String> getFieldNames(){
		Iterator<DBObject> it = this.results.iterator();
		if(it.hasNext()){
			DBObject obj = it.next();
			return obj.keySet();
		}
		
		return new HashSet<String>();
	}
	
	public Iterator<DBObject> iterator(){
		return this.results.iterator();
	}
}
