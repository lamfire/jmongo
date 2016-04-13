package com.demo.jmongo;


import com.demo.jmongo.entity.User;
import com.lamfire.jmongo.JMongo;
import com.lamfire.jmongo.MongoOpts;
import com.lamfire.jmongo.dao.DAO;
import com.lamfire.jmongo.dao.DAOFactory;


public class DriverMain {

    public static void main(String[] args) throws Exception {
        MongoOpts opts = new MongoOpts("testmongo");
        opts.addHost("192.168.180.49:27000");
        JMongo.register(opts);


        DAO<User, String> dao = DAOFactory.get("testmongo", "testmongo","User2", User.class);
        System.out.println(dao.count());
        String uid = "00001";
        User user = new User();
        user.setId(uid);
        dao.save(user);

        dao.setFieldValue(uid,"f1","f1");
        dao.setFieldValue(uid,"f2","f2");

        System.out.println(dao.getAsMap(uid));
    }
}
