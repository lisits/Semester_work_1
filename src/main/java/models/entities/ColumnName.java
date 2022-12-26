package models.entities;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface ColumnName {
    String name();
    boolean primary() default false;
    boolean identity() default false;
    boolean uniq() default false;
    boolean defaultBooleanFalse() default false;
    boolean notNull() default false;
}