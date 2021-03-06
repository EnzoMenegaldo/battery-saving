package com.emenegal.battery_saving.annotation;

import com.emenegal.battery_saving.method.IMethod;
import com.emenegal.battery_saving.method.DefaultMethod;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@IndexAnnotated
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IPrecision {
    Class<? extends IMethod> method() default DefaultMethod.class;
    double min() default 1;
    double max() default 1;
    double[] params() default 0;
}
