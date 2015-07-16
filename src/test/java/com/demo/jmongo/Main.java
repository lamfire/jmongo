package com.demo.jmongo;


import com.lamfire.jmongo.Mongos;
import com.lamfire.jmongo.annotations.PrePersist;
import com.lamfire.jmongo.annotations.PreSave;
import com.lamfire.jmongo.mapping.MappedClass;
import com.lamfire.jmongo.mapping.MappedField;
import com.lamfire.jmongo.mapping.MappingException;
import com.lamfire.utils.RandomUtils;
import com.mongodb.*;

import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    private static AtomicInteger count = new AtomicInteger();

	public static void main(String[] args) {
        DB db = Mongos.getMongo().getDB("test");
        DBCollection col = db.getCollection("User");


        for(int i=0;i<100;i++){
            DBObject entity = new BasicDBObject();
            entity.put("_id",String.format("%05d", count.incrementAndGet()));
            entity.put("username","username-" + count.get());
            entity.put("age",RandomUtils.nextInt(99));
            col.insert(entity, WriteConcern.MAJORITY);
        }


	}
}
