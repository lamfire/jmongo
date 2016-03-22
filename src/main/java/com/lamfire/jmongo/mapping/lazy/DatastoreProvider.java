package com.lamfire.jmongo.mapping.lazy;

import java.io.Serializable;

import com.lamfire.jmongo.Datastore;

public interface DatastoreProvider extends Serializable {
	Datastore get();
}
