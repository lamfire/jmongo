package com.lamfire.jmongo.mapping.lazy;

import com.lamfire.jmongo.Datastore;

public class DefaultDatastoreProvider implements DatastoreProvider {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Datastore get() {
		final Datastore datastore = DatastoreHolder.getInstance().get();
		if (datastore == null) {
			throw new IllegalStateException(
					"DatastoreHolder does not carry a Datastore.");
		}
		return datastore;
	}

}