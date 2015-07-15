/**
 * 
 */
package com.lamfire.jmongo.mapping.lazy.proxy;

import java.util.Map;

import com.lamfire.jmongo.Key;

/**
 * @author Uwe Schaefer, (us@thomas-daily.de)
 * 
 */
public interface ProxiedEntityReferenceMap extends ProxiedReference {

	void __put(String key, Key<?> referenceKey);
	
	Map<String, Key<?>> __getReferenceMap();
}
