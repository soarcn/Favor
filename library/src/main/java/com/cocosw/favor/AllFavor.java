package com.cocosw.favor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p/>
 * Created by kai on 30/09/15.
 */

@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface AllFavor {
}
