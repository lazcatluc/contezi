package ro.contezi.novote.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 *
 * @author Catalin
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface Config {
    /**
     * Key that will be searched when injecting the value.
     */
    @Nonbinding
    String name() default "";

    /**
     * Defines if value for the given key must be defined.
     */
    @Nonbinding
    boolean required() default true;
}