package com.lamfire.jmongo;

public class VersionHelper {
	
	public static long nextValue(Long oldVersion) {
		long newVersion = oldVersion == null ? 1 : oldVersion + 1;
		return newVersion;
	}
	
}
