package com.demo.jmongo;


import com.demo.jmongo.entity.User;
import com.lamfire.jmongo.JMongo;
import com.lamfire.jmongo.JMongoRegistry;
import com.lamfire.jmongo.MongoOpts;
import com.lamfire.jmongo.dao.DAO;
import com.lamfire.jmongo.dao.DAOFactory;


public class DriverMain {

    public static void main(String[] args) throws Exception {
        MongoOpts opts = new MongoOpts("testmongo");
        opts.addHost("192.168.180.49:27000");
        JMongoRegistry.getInstance().register(opts);


        DAO<User, String> dao = DAOFactory.get("testmongo", "testmongo","User4", User.class);
        System.out.println(dao.count());
        String uid = "00004";

        dao.setFieldValue(uid,"f1","f1");
        dao.setFieldValue(uid,"f2","f2");

        System.out.println(dao.getAsMap(uid));

        dao.removeField(uid,"f1");
        System.out.println(dao.getAsMap(uid));
    }
}
