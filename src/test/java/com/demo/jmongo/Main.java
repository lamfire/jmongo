package com.demo.jmongo;


import com.demo.jmongo.dao.UserDAO;
import com.demo.jmongo.entity.User;
import com.lamfire.jmongo.JMongo;
import com.lamfire.jmongo.query.Query;
import com.lamfire.json.JSON;
import com.lamfire.utils.RandomUtils;
import com.lamfire.utils.Threads;
import com.mongodb.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    private static AtomicInteger counter = new AtomicInteger();

    static void test1(){
        UserDAO dao = new UserDAO();

        double x = Double.valueOf(RandomUtils.nextInt(100) +"." + (10000) +RandomUtils.nextInt(99999) );
        double y = Double.valueOf((RandomUtils.nextInt(180)) +"." + (10000) +RandomUtils.nextInt(99999) );
        User user = new User();
        user.setAge(RandomUtils.nextInt(99));
        user.setUsername(String.format("%05d", RandomUtils.nextInt(100)));
        user.setPostion(x, y);
        user.setPassword("password" + String.valueOf(10000 + RandomUtils.nextInt(9999)));
        dao.save(user);

        dao = new UserDAO();
        System.out.println(dao.count());

        dao = new UserDAO();
        List<User> users = dao.createQuery().asList();
        System.out.println(users);
    }

    static void test2(){
        UserDAO dao = new UserDAO("USER2");

        double x = Double.valueOf(RandomUtils.nextInt(100) +"." + (10000) +RandomUtils.nextInt(99999) );
        double y = Double.valueOf((RandomUtils.nextInt(180)) +"." + (10000) +RandomUtils.nextInt(99999) );
        User user = new User();
        user.setAge(RandomUtils.nextInt(99));
        user.setUsername(String.format("%05d", RandomUtils.nextInt(100)));
        user.setPostion(x, y);
        user.setPassword("password" + String.valueOf(10000 + RandomUtils.nextInt(9999)));
        dao.save(user);

        dao = new UserDAO("USER2");
        System.out.println(dao.count());

        dao = new UserDAO("USER2");
        List<User> users = dao.createQuery().asList();
        System.out.println(users);
    }

    public static void big() {
        OPSMonitor monitor = new OPSMonitor();
        monitor.startup();
        for(int i=0;i<10000000;i++){
            UserDAO dao = new UserDAO("db1","USER_BIG");
            dao.save(Users.getUser());
            monitor.increment();
        }

    }

	public static void main(String[] args) {
        long startAt = System.currentTimeMillis();
        UserDAO dao = new UserDAO("db1","USER_BIG");
        Query q = dao.createQuery().field("age").equal(45);
        System.out.println(dao.count(q));
        System.out.println((System.currentTimeMillis()-startAt));
	}
}
