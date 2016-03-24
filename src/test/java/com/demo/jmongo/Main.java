package com.demo.jmongo;


import com.demo.jmongo.dao.UserDAO;
import com.demo.jmongo.entity.User;
import com.lamfire.jmongo.JMongo;
import com.lamfire.jmongo.Key;
import com.lamfire.jmongo.dao.DAO;
import com.lamfire.jmongo.dao.DAOFactory;
import com.lamfire.jmongo.query.Query;
import com.lamfire.jmongo.query.UpdateOperations;
import com.lamfire.json.JSON;
import com.lamfire.utils.RandomUtils;
import com.lamfire.utils.Threads;
import com.mongodb.*;

import java.util.List;
import java.util.Map;
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

	public static void test(String[] args) {
        long startAt = System.currentTimeMillis();
        UserDAO dao = new UserDAO("db1","USER_BIG");
        User user = Users.getUser();
        System.out.println(JSON.toJSONString(user));
        dao.save(user);

        user.setPassword("12345");
        dao.save(user);
        System.out.println(JSON.toJSONString(user));

        List<User> users = dao.createQuery().useReadPreference(ReadPreference.primary()).field("_id").equal(user.getUsername()).asList();
        user = users.get(0);
        System.out.println("[GET] : " + JSON.toJSONString(user));
        System.out.println((System.currentTimeMillis()-startAt));
	}

    public static void main1(String[] args){
        DAO<User,String> dao = DAOFactory.get("default","test",User.class);

        //single
        dao.setFieldValue("00038", "testField", "aaaaa");
        Object  val = dao.getFieldValue("00038", "testField");
        System.out.println(val);

        //array
        dao.addFieldValue("00038", "uids", "123456789");
        dao.addFieldValue("00038", "uids", "123456789000");
        Object  obj = dao.getFieldValue("00038", "uids");
        System.out.println(obj);

        //entity as map
        Map<String,Object> map = dao.getAsMap("00038");
        System.out.println(map);

    }

    public static void main(String[] args) {
        DAO<User,String> dao = DAOFactory.get("default","test",User.class);
        Map<String,Object> o = new BasicDBObject();
        o.put("_id","00037");
        o.put("username","hayash");
        o.put("password","123456");

        Object id = dao.save(o).getId();
        System.out.println("ID:"+id);

        System.out.println(dao.count());

        System.out.println(dao.exists("00037"));
        System.out.println(dao.exists("00031"));

        DBObject d = new BasicDBObject();
        d.put("_id",1);
        System.out.println(dao.getCollection().findOne("000371",d));

        long t = System.nanoTime();
        for(int i=0;i<100;i++){
            dao.exists("00037");
        }
        System.out.println(System.nanoTime() - t);

        t = System.nanoTime();
        for(int i=0;i<100;i++){
            dao.getCollection().findOne("00037",d);
        }
        System.out.println(System.nanoTime() - t);
    }
}
