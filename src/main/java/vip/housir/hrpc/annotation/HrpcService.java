package vip.housir.hrpc.annotation;

import java.lang.annotation.*;

/**
 * @author housirvip
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@HrpcComponent
public @interface HrpcService {

    String name() default "";
}
