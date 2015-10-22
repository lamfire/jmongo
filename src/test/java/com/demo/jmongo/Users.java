package com.demo.jmongo;

import com.demo.jmongo.entity.User;
import com.lamfire.code.PUID;
import com.lamfire.utils.RandomUtils;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-9-6
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 */
public class Users {

    public static User getUser(){
        double x = Double.valueOf(RandomUtils.nextInt(100) +"." + (10000) +RandomUtils.nextInt(99999) );
        double y = Double.valueOf((RandomUtils.nextInt(180)) +"." + (10000) +RandomUtils.nextInt(99999) );
        User user = new User();
        user.setAge(RandomUtils.nextInt(99));
        user.setUsername(PUID.puidAsString());
        user.setPostion(x, y);
        user.setPassword("password" + String.valueOf(10000 + RandomUtils.nextInt(9999)));
        return user;
    }
}
