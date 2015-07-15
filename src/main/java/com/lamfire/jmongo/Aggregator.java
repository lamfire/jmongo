package com.lamfire.jmongo;

import java.util.ArrayList;
import java.util.List;

import com.lamfire.jmongo.query.Query;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Aggregator {

	private List<DBObject> list = new ArrayList<DBObject>();
	
	public Aggregator match(Query<?> query){
		DBObject match = new BasicDBObject("$match", query.getQueryObject());
		list.add(match);
		return this;
	}
	
	public Aggregator project(String ... fields){
		DBObject project = new BasicDBObject();
		for(String field : fields){
			project.put(field, 1);
		}
		list.add(new BasicDBObject("$project",project));
		return this;
	}
	
	public Aggregator unwind(String field){
		list.add(new BasicDBObject("$unwind","$"+field));
		return this;
	}
	
	public Aggregator limit(int limit){
		list.add(new BasicDBObject("$limit",limit));
		return this;
	}
	
	public Aggregator skip(int skip){
		list.add(new BasicDBObject("$skip",skip));
		return this;
	}
	
	public Aggregator sort(String field ,int sort){
		list.add(new BasicDBObject("$sort",new BasicDBObject(field,sort)));
		return this;
	}
	
	public Aggregator group(GroupBy group){
		list.add(group.getGroupObject());
		return this;
	}
	
	public Aggregator group(String field){
		list.add(new GroupBy(field).getGroupObject());
		return this;
	}
	
	public Aggregator group(String field,String ... addToSetFields){
		GroupBy groupBy = new GroupBy(field);
		for(String addToSet : addToSetFields){
			groupBy.addToSet(addToSet, addToSet);
		}
		list.add(groupBy.getGroupObject());
		return this;
	}
	
	public DBObject[] additionalOps(){
		DBObject[] result = new BasicDBObject[list.size() -1];
		for(int i=1;i<list.size();i++){
			result[i-1] = list.get(i);
		}
		return result;
	}

	public DBObject firstOp(){
		return list.get(0);
	}
}
