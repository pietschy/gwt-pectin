package com.pietschy.gwt.pectin.client.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be added to {@link BeanModelProvider} declarations
 * to let tell the rebind process know how deep to traverse a recursive
 * property.  A recursive property is one that returns another property
 * the same type as itself either as a directed descendant or as a descendant
 * of another property (at any depth).
 * <p>
 * For example, the following usage will limit the depth to which the employ.getManager()
 * path can be traversed.  In this case the manager path can be traversed
 * 3 times, i.e. <code>employee.manager.manager.manager</code>
 * <pre>
 * Another example is where the department
 * employee.department.manager.department.manager
 *
 * &#064;NestedTypes({Employee.class, Department.class})
 * &#064;RecursionLimit(3)
 * private static class PersonProvider extends BeanModelProvider<Person>{}
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RecursionLimit
{
   int value() default 10;
}