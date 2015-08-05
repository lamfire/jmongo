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

	public static void main(String[] args) {

        UserDAO dao = new UserDAO("User2");

        double x = Double.valueOf(RandomUtils.nextInt(100) +"." + (10000) +RandomUtils.nextInt(99999) );
        double y = Double.valueOf((RandomUtils.nextInt(180)) +"." + (10000) +RandomUtils.nextInt(99999) );
        User user = new User();
        user.setAge(RandomUtils.nextInt(99));
        user.setUsername(String.format("%05d", RandomUtils.nextInt(100)));
        user.setPostion(x, y);
        user.setPassword("password" + String.valueOf(10000 + RandomUtils.nextInt(9999)));
        dao.save(user);

        System.out.println(dao.count());


        List<User> users = dao.createQuery().asList();
        System.out.println(users);

	}
}
