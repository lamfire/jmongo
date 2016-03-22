package com.lamfire.jmongo.mapping.lazy.proxy;

@SuppressWarnings("unchecked")
public interface ProxiedReference {
	boolean __isFetched();

	Class __getReferenceObjClass();

	Object __unwrap();
}
