package com.demo.jmongo;


import com.demo.jmongo.dao.UserDAO;
import com.demo.jmongo.entity.User;
import com.lamfire.jmongo.JMongo;
import com.lamfire.utils.RandomUtils;
import com.mongodb.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class DriverMain {

	public static void main(String[] args) {
        Mongo mongo = JMongo.getMongo();
        DB db = mongo.getDB("test");
        DBCollection dbCol = db.getCollection("User2");
        System.out.println(dbCol.count());

        System.out.println(dbCol.getIndexInfo());
	}
}
