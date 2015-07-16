package com.demo.jmongo.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.demo.jmongo.dao.UserDAO;
import com.demo.jmongo.entity.User;
import com.lamfire.code.PUID;
import com.lamfire.jmongo.GroupBy;
import com.lamfire.json.JSON;
import com.lamfire.jmongo.Key;
import com.lamfire.jmongo.query.Query;
import com.lamfire.utils.Asserts;
import com.lamfire.utils.Printers;
import com.lamfire.utils.RandomUtils;
import com.lamfire.utils.Threads;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;

public class UserTest {

	static UserDAO dao = new UserDAO("User1");
    static AtomicInteger ids = new AtomicInteger();

    public static User newRandomUser(){
        double x = Double.valueOf(RandomUtils.nextInt(100) +"." + (10000) +RandomUtils.nextInt(99999) );
        double y = Double.valueOf((RandomUtils.nextInt(180)) +"." + (10000) +RandomUtils.nextInt(99999) );
        User user = new User();
        user.setAge(RandomUtils.nextInt(99));
        user.setUsername(String.format("%05d", ids.incrementAndGet()));
        user.setPostion(x, y);
        user.setPassword("password" + String.valueOf(10000 + RandomUtils.nextInt(9999)));
        return user;
    }

	public static String insertRandom() {
		try {
			User user = newRandomUser();
			Key<User> key = dao.insert(user);
			return (String) key.getId();
		} catch (Exception e) {
            e.printStackTrace();
		}
		return null;
	}

	public static void batInsert(int count) {
		long begin = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			insertRandom();
		}
		System.out.println("[INSERT (+count+)] time:" + (System.currentTimeMillis() - begin));
	}

	public static void listKeys() {
		long begin = System.currentTimeMillis();
		List<String> keys = dao.findIds();
		System.out.println(keys);
		System.out.println("time:" + (System.currentTimeMillis() - begin));
	}

	public static List<User> findAgeGreater(int greaterThan, int offset, int limit) {
		Query<User> query = dao.createQuery();
		query.filter("age >=", greaterThan);
		query.limit(limit);
		query.offset(offset);
		//query.useReadPreference(ReadPreference.primaryPreferred());
		query.asList();
		List<User> users = query.asList();
		return users;
	}

	public static void query() {
		long begin = System.currentTimeMillis();
		List<User> users = findAgeGreater(RandomUtils.nextInt(100), RandomUtils.nextInt(9999), 100);
		System.out.println("[QUERY]: found " + users.size() + " items , time:" + (System.currentTimeMillis() - begin));
	}

	public static void queryAll() {
		long begin = System.currentTimeMillis();
		List<User> users = dao.find().asList();
		for (User user : users) {
			System.out.println(JSON.toJSONString(user));
		}
		System.out.println("[COUNT]" + users.size());
		System.out.println("time:" + (System.currentTimeMillis() - begin));
	}

	public static long countUsers() {
		return dao.count();
	}
	
	public static void near() {
		List<User> list = dao.near(7.123456, 121.123456,3);
		for(User u : list){
            System.out.println(JSON.toJSONString(u));
        }
	}
	
	public static void get() {
		User user = dao.get("1");
		Printers.print(user);
	}

    public static void clear(){
        dao.deleteByQuery(dao.createQuery());
    }
	public static void test() {
        clear();
		batInsert(100);
		// System.out.println(countUsers());
		queryAll();

		// System.out.println(insertRandom());
		//Printers.printAsJson(dao.get(10001l));

//		List<Long> list = new ArrayList<Long>();
//		list.add(10001l);
//		list.add(1537l);
//		list.add(5730l);
//		list.add(2l);
//		Printers.printAsJson(dao.createQuery().field("_id").in(list).asList());

        List<DBObject> groupList = dao.group(new GroupBy("age", "username").count()).asList();
		Printers.print(groupList);

        int ageCountMax = 0;
        int maxAge = 0;
        for(DBObject o : groupList){
            int count = (Integer)o.get("count");
            if(count > ageCountMax){
                ageCountMax = count;
                maxAge = (Integer)((DBObject)o.get("_id")).get("age");
            }
        }

        System.out.println("====================================> age count max : age=" + maxAge + ",count=" + ageCountMax);

        System.out.println("====================================> count :");
		System.out.println(countUsers());

        System.out.println("====================================> near :");
		near();


        System.out.println("====================================> in query :");
		List<String> ids = new ArrayList<String>();
		ids.add("00012");
        ids.add("00013");
        ids.add("00014");
		List<User> users = dao.createQuery().field("_id").in(ids).includeFields("password").asList();
        for(User u : users){
            System.out.println(JSON.toJSONString(u));
        }

        System.out.println("====================================> age="+maxAge+" : " + ageCountMax);
        users  = dao.createQuery().field("age").equal(maxAge).asList();
        for(User u : users){
            System.out.println(JSON.toJSONString(u));
            Asserts.equalsAssert(u.getAge(),maxAge);
        }
        Asserts.equalsAssert(ageCountMax,users.size());

        System.out.println("====================================> get 00088 :");
        User user =dao.get("00088");
        System.out.println(JSON.toJSONString(user));
        Asserts.equalsAssert(user.getUsername(),"00088");

        System.out.println("====================================> delete 00088 :");
        dao.deleteById("00088");

        user =dao.get("00088");
        Asserts.nullAssert(user);

        System.out.println("count :" + countUsers());
	}

    public static void main(String[] args) {
        //test();
        User user = newRandomUser();
        System.out.println(dao.toDBObject(user));
    }
}
