package com.demo.jmongo.dao;

import java.util.List;
import java.util.Map;

import com.demo.jmongo.entity.User;
import com.lamfire.jmongo.dao.DAOSupport;
import com.lamfire.jmongo.AggregateResults;
import com.lamfire.jmongo.Aggregator;
import com.mongodb.DBObject;

public class UserDAO extends DAOSupport<User, String> {
	public UserDAO() {
		super("default", "test", User.class);
	}

    public UserDAO(String db,String collection) {
        super("default", db, User.class,collection);
    }

    public UserDAO(String collection) {
        super("default", "test", User.class,collection);
    }
	
	public List<User>  near(double x,double y,int count){
		return this.createQuery().field("postion").near(x, y).limit(count).asList();
	}

	public static void distinct() {
		UserDAO dao = new UserDAO();
		int age = 5;
		long count = dao.distinctCount(dao.createQuery().field("age").equal(age), "password");
		System.out.println(count);
	}
	
	public static void group() {
		UserDAO dao = new UserDAO();
		AggregateResults rs = dao.group("age");
		System.out.println(rs.get(5));
	}
	
	public static void limit() {
		UserDAO dao = new UserDAO();
		Aggregator aggregator = new Aggregator();
		aggregator.match(dao.createQuery().field("age").equal(5));
		aggregator.limit(8);
		AggregateResults result = dao.aggregate(aggregator);
		for(Map.Entry<Object,DBObject> e : result.getResultMap().entrySet()){
            System.out.println(e.getKey() +" = " + e.getValue());
        }
	}
}
