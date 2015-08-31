package com.demo.jmongo;


import com.demo.jmongo.dao.UserDAO;
import com.demo.jmongo.entity.User;
import com.lamfire.jmongo.JMongo;
import com.lamfire.utils.RandomUtils;
import com.mongodb.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    private static AtomicInteger count = new AtomicInteger();

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

	public static void main(String[] args) {

        test1();
        test2();

	}
}
