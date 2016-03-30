package com.lamfire.jmongo.query;

import com.lamfire.jmongo.Key;
import com.lamfire.jmongo.mapping.Mapper;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class EntityKeyIterator<T> extends EntityMappedIterator<T, Key<T>> {
	public EntityKeyIterator(DBCursor cursor, Mapper m, Class<T> clazz, String kind) {
		super(cursor, m, clazz, kind, null);
	}

	@Override
	protected Key<T> convertItem(DBObject dbObj) {
		Key<T> key = new Key<T>(kind, dbObj.get(Mapper.ID_KEY));
		key.setKindClass(this.clazz);
		return key;
	}
	
}