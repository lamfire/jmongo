package com.demo.jmongo;


import com.demo.jmongo.entity.User;
import com.lamfire.jmongo.JMongo;
import com.lamfire.jmongo.JMongoRegistry;
import com.lamfire.jmongo.MongoOpts;
import com.lamfire.jmongo.dao.DAO;
import com.lamfire.jmongo.dao.DAOFactory;
import com.lamfire.json.JSON;
import com.lamfire.utils.Lists;

import java.util.List;


public class DriverMain {

    public static void main(String[] args) throws Exception {
        MongoOpts opts = new MongoOpts("testmongo");
        opts.addHost("192.168.180.49:27000");
        JMongoRegistry.getInstance().register(opts);


        DAO<User, String> dao = DAOFactory.get("testmongo", "testmongo","User01", User.class);
        System.out.println(dao.count());
        String uid = "00004";


//        for(int i=0;i<100;i++){
//            User user = Users.randomUser();
//            user.setId(String.valueOf(i));
//            dao.save(user);
//        }
//
//        List<String> ids = Lists.newArrayList();
//        ids.add("1");
//        ids.add("2");
//        ids.add("3");
//        ids.add("4");
//        ids.add("5");
//        ids.add("6");
//        ids.add("7");
//        ids.add("8");
//        ids.add("9");
//
//        List<User> users = dao.gets(ids);
//        for(User u : users){
//            System.out.println(JSON.toJSONString(u));
//        }


        System.out.println(dao.update("0","username","111010101").getUpdatedCount());

    }
}
