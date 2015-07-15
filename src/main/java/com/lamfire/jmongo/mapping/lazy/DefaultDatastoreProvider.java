package com.lamfire.jmongo.mapping.lazy;

import com.lamfire.jmongo.Datastore;

/**
 * Default implementation to be used in the assumtion that one Datastore per
 * classloader is the default wa to use Mapping. Might be discussable.
 * 
 * @author uwe schaefer
 */
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