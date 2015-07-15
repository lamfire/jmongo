package com.lamfire.jmongo;

import com.lamfire.jmongo.annotations.PostLoad;
import com.lamfire.jmongo.annotations.PostPersist;
import com.lamfire.jmongo.annotations.PreLoad;
import com.lamfire.jmongo.annotations.PrePersist;
import com.lamfire.jmongo.annotations.PreSave;
import com.lamfire.jmongo.mapping.Mapper;
import com.mongodb.DBObject;

/** Interface for intercepting @Entity lifecycle events */
public interface EntityInterceptor {
	/** see {@link PrePersist} */
	void prePersist(Object ent, DBObject dbObj, Mapper mapr);
	/** see {@link PreSave} */
	void preSave(Object ent, DBObject dbObj, Mapper mapr);
	/** see {@link PostPersist} */
	void postPersist(Object ent, DBObject dbObj, Mapper mapr);
	/** see {@link PreLoad} */
	void preLoad(Object ent, DBObject dbObj, Mapper mapr);
	/** see {@link PostLoad} */
	void postLoad(Object ent, DBObject dbObj, Mapper mapr);
}
