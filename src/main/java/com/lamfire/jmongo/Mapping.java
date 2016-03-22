package com.lamfire.jmongo;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import com.lamfire.jmongo.annotations.Embedded;
import com.lamfire.jmongo.annotations.Entity;
import com.lamfire.jmongo.mapping.Mapper;
import com.lamfire.jmongo.mapping.MappingException;
import com.lamfire.jmongo.mapping.cache.EntityCache;
import com.lamfire.jmongo.utils.ReflectionUtils;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

@SuppressWarnings({"unchecked"})
public class Mapping {
	private final Mapper mapper;

	public Mapping() {
        this(Collections.EMPTY_SET);
    }

	public Mapping(Set<Class> classesToMap) {
        this.mapper = new Mapper();
        for (Class c : classesToMap) {
            map(c);
        }
    }
	
    public synchronized Mapping map(Class... entityClasses) {
    	if ( entityClasses != null && entityClasses.length > 0)
    		for(Class entityClass : entityClasses) {
		        if ( !mapper.isMapped(entityClass) ) {
		            mapper.addMappedClass(entityClass);
		        }
        }
        return this;
    }

    public synchronized Mapping mapPackageFromClass(Class clazz) {
        return mapPackage(clazz.getPackage().getName(), false);
    }

    /**
     * Tries to map all classes in the package specified. Fails if one of the classes is not valid for mapping.
     *
     * @param packageName
     *            the name of the package to process
     * @return the Mapping instance
     */
    public synchronized Mapping mapPackage(String packageName) {
        return mapPackage(packageName, false);
    }

    /**
     * Tries to map all classes in the package specified.
     *
     * @param packageName
     *            the name of the package to process
     * @param ignoreInvalidClasses
     *            specifies whether to ignore classes in the package that cannot be mapped
     * @return the Mapping instance
     */
    public synchronized Mapping mapPackage(String packageName, boolean ignoreInvalidClasses) {
        try {
            for (Class c : ReflectionUtils.getClasses(packageName)) {
                try {
                    Embedded embeddedAnn = ReflectionUtils.getClassEmbeddedAnnotation(c);
                    Entity enityAnn = ReflectionUtils.getClassEntityAnnotation(c);
                    if ( enityAnn != null || embeddedAnn != null ) {
                        map(c);
                    }
                } catch (MappingException ex) {
                    if (!ignoreInvalidClasses) {
                        throw ex;
                    }
                }
            }
            return this;
        } catch (IOException ioex) {
            throw new MappingException("Could not get map classes from package " + packageName, ioex);
        } catch (ClassNotFoundException cnfex) {
            throw new MappingException("Could not get map classes from package " + packageName, cnfex);
        }
    }


    public boolean isMapped(Class entityClass) {
        return mapper.isMapped(entityClass);
    }

	public <T> T fromDBObject(Class<T> entityClass, DBObject dbObject) {
		return fromDBObject(entityClass, dbObject, mapper.createEntityCache());
	}
	
	public <T> T fromDBObject(Class<T> entityClass, DBObject dbObject, EntityCache cache) {
        if ( !entityClass.isInterface() && !mapper.isMapped(entityClass)) {
            throw new MappingException("Trying to map to an unmapped class: " + entityClass.getName());
        }
        try {
			return (T) mapper.fromDBObject(entityClass, dbObject, cache);
        } catch ( Exception e ) {
            throw new MappingException("Could not map entity from DBObject", e); }
    }

    public DBObject toDBObject( Object entity ) {
        try {
            return mapper.toDBObject(entity);
        } catch ( Exception e ) {
            throw new MappingException("Could not map entity to DBObject", e); }
    }

    public Mapper getMapper() { return this.mapper; }

}
