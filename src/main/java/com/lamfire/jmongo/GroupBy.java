package com.lamfire.jmongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class GroupBy {
	static final String FUNC_ADDTOSET = "$addToSet";
	static final String FUNC_FIRST = "$first";
	static final String FUNC_LAST = "$last";
	static final String FUNC_MAX = "$max";
	static final String FUNC_MIN = "$min";
	static final String FUNC_AVG = "$avg";
	static final String FUNC_PUSH = "$push";
	static final String FUNC_SUM = "$sum";
	static final String FUNC_COUNT = "$count";
	
	private final DBObject group = new BasicDBObject();
	private String field;
	public GroupBy(String field) {
		this.field = field;
		group.put("_id", "$" + field);
	}
	
	public GroupBy(String... fields) {
		this.field = fields[0];
		if(fields.length == 1){
			group.put("_id", "$" + field);
			return;
		}
		DBObject ids = new BasicDBObject();
		for(String field : fields){
			ids.put(field, "$"+field);
		}
		group.put("_id", ids);
	}
	
	public GroupBy count() {
		group.put("count", new BasicDBObject("$sum",1));
		return this;
	}
	
	public GroupBy count(String keyname) {
		group.put(keyname, new BasicDBObject("$sum",1));
		return this;
	}

	public GroupBy sum() {
		group.put("sum", new BasicDBObject("$sum", "$" + field));
		return this;
	}
	
	public GroupBy sum(String field) {
		group.put("sum", new BasicDBObject("$sum", "$" + field));
		return this;
	}

	public GroupBy push(String field) {
		group.put("push", new BasicDBObject("$push", "$" + field));
		return this;
	}
	
	public GroupBy push(String keyname,String field) {
		group.put(keyname, new BasicDBObject("$push", "$" + field));
		return this;
	}
	
	public GroupBy max(String field) {
		group.put("max", new BasicDBObject("$max", "$" + field));
		return this;
	}
	
	public GroupBy max(String keyname,String field) {
		group.put(keyname, new BasicDBObject("$max", "$" + field));
		return this;
	}
	
	public GroupBy last(String field) {
		group.put("last", new BasicDBObject("$last", "$" + field));
		return this;
	}
	
	public GroupBy last(String keyname,String field) {
		group.put(keyname, new BasicDBObject("$last", "$" + field));
		return this;
	}
	
	public GroupBy first(String field) {
		group.put("first", new BasicDBObject("$first", "$" + field));
		return this;
	}
	
	public GroupBy first(String keyname,String field) {
		group.put(keyname, new BasicDBObject("$first", "$" + field));
		return this;
	}
	
	public GroupBy min(String field) {
		group.put("min", new BasicDBObject("$min", "$" + field));
		return this;
	}
	
	
	public GroupBy min(String keyname,String field) {
		group.put(keyname, new BasicDBObject("$min", "$" + field));
		return this;
	}
	
	public GroupBy avg(String field) {
		group.put("avg", new BasicDBObject("$avg", "$" + field));
		return this;
	}
	
	public GroupBy avg(String keyname,String field) {
		group.put(keyname, new BasicDBObject("$avg", "$" + field));
		return this;
	}

	public GroupBy sum(String keyname,String field) {
		group.put(keyname, new BasicDBObject("$sum", "$" + field));
		return this;
	}

	public GroupBy addToSet(String keyname, String fieldName) {
		group.put(keyname, new BasicDBObject("$addToSet", "$" + fieldName));
		return this;
	}

	public GroupBy keys() {
		group.put("keys", new BasicDBObject("$addToSet", "$_id"));
		return this;
	}

	public DBObject getGroupObject() {
		return new BasicDBObject("$group", this.group);
	}
}
