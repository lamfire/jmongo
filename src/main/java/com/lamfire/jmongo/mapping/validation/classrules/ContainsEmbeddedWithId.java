package com.lamfire.jmongo.mapping.validation.classrules;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import com.lamfire.jmongo.annotations.Id;
import com.lamfire.jmongo.annotations.Reference;
import com.lamfire.jmongo.annotations.Transient;
import com.lamfire.jmongo.mapping.MappedClass;
import com.lamfire.jmongo.mapping.validation.ClassConstraint;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation;
import com.lamfire.jmongo.mapping.validation.ConstraintViolation.Level;
import com.lamfire.jmongo.utils.ReflectionUtils;

/**
 * @author josephpachod
 */
public class ContainsEmbeddedWithId implements ClassConstraint
{

    private boolean hasTypeFieldAnnotation(final Class<?> type, final Class<Id> class1)
    {
        for (Field field : ReflectionUtils.getDeclaredAndInheritedFields(type, true))
        {
            if (field.getAnnotation(class1) != null)
            {
                return true;
            }
        }
        return false;
    }

	public void check(final MappedClass mc, final Set<ConstraintViolation> ve)
    {
        Set<Class<?>> classesToInspect = new HashSet<Class<?>>();
        for (Field field : ReflectionUtils.getDeclaredAndInheritedFields(mc.getClazz(), true))
        {
            if (isFieldToInspect(field) && !field.isAnnotationPresent(Id.class))
            {
                classesToInspect.add(field.getType());
            }
        }
        checkRecursivelyHasNoIdAnnotationPresent(classesToInspect, new HashSet<Class<?>>(), mc, ve);
    }

    private boolean isFieldToInspect(final Field field)
    {
        return (!field.isAnnotationPresent(Transient.class) && !field.isAnnotationPresent(Reference.class));
    }

    private void checkRecursivelyHasNoIdAnnotationPresent(final Set<Class<?>> classesToInspect,
            final HashSet<Class<?>> alreadyInspectedClasses, final MappedClass mc, final Set<ConstraintViolation> ve)
    {
        for (Class<?> clazz : classesToInspect)
        {
            if (alreadyInspectedClasses.contains(clazz))
            {
                continue;
            }
            if (hasTypeFieldAnnotation(clazz, Id.class))
            {
                ve.add(new ConstraintViolation(Level.FATAL, mc, this.getClass(),
                        "You cannot use @Id on any field of an Embedded/Property object"));
            }
            alreadyInspectedClasses.add(clazz);
            Set<Class<?>> extraClassesToInspect = new HashSet<Class<?>>();
            for (Field field : ReflectionUtils.getDeclaredAndInheritedFields(clazz, true))
            {
                if (isFieldToInspect(field))
                {
                    extraClassesToInspect.add(field.getType());
                }
            }
            checkRecursivelyHasNoIdAnnotationPresent(extraClassesToInspect, alreadyInspectedClasses, mc, ve);
        }
    }
}
