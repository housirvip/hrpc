package vip.housir.hrpc.annotation;

import java.lang.annotation.*;

/**
 * @author housirvip
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HrpcComponent {

    String name() default "";
}
