package com.peerless2012.qingniantuzhai.colorui.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/10 21:09
 * @Version V1.0
 * @Description :
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AttrTag {
    String tag() default "";
    int value() default -1;
}
