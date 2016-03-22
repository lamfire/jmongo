package com.lamfire.jmongo.mapping.lazy.proxy;

import com.lamfire.jmongo.Key;

public interface ProxiedEntityReference extends ProxiedReference {
	Key<?> __getKey();
}
