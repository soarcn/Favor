package com.cocosw.favor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p/>
 * Created by kai on 29/09/15.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface Commit {
}
