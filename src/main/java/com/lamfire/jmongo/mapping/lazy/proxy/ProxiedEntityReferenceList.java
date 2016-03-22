package com.lamfire.jmongo.mapping.lazy.proxy;

import java.util.Collection;
import java.util.List;

import com.lamfire.jmongo.Key;

public interface ProxiedEntityReferenceList extends ProxiedReference {

	void __add(Key<?> key);
	void __addAll(Collection<? extends Key<?>> keys);

	List<Key<?>> __getKeysAsList();

}
