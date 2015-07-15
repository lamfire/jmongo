/**
 * 
 */
package com.lamfire.jmongo.mapping.lazy.proxy;

import com.lamfire.jmongo.Key;

/**
 * @author Uwe Schaefer, (schaefer@thomas-daily.de)
 */
public interface ProxiedEntityReference extends ProxiedReference {
	Key<?> __getKey();
}
