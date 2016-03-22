package com.lamfire.jmongo.mapping.lazy;

import com.lamfire.jmongo.logger.Logger;

public class LazyFeatureDependencies {
	
	private static final Logger logger = Logger.getLogger(LazyFeatureDependencies.class);
	private static Boolean fullFilled;
	
	private LazyFeatureDependencies() {
	}
	
	public static boolean assertDependencyFullFilled() {
		boolean fullfilled = testDependencyFullFilled();
		if (!fullfilled)
			logger.warn("Lazy loading impossible due to missing dependencies.");
		return fullfilled;
	}
	
	public static boolean testDependencyFullFilled() {
		if (fullFilled != null)
			return fullFilled;
		try {
			fullFilled = Class.forName("net.sf.cglib.proxy.Enhancer") != null
					&& Class.forName("com.thoughtworks.proxy.toys.hotswap.HotSwapping") != null;
		} catch (ClassNotFoundException e) {
			fullFilled = false;
		}
		return fullFilled;
	}
	
	/**
	 * @return
	 */
	public static LazyProxyFactory createDefaultProxyFactory() {
		if (testDependencyFullFilled()) {
			String factoryClassName = "com.google.code.jmongo.mapping.lazy.CGLibLazyProxyFactory";
			try {
				return (LazyProxyFactory) Class.forName(factoryClassName).newInstance();
			} catch (Exception e) {
				logger.error("While instanciating " + factoryClassName, e);
			}
		}
		return null;
	}
}
