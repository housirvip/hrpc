package vip.housir.hrpc.annotation;

import java.lang.annotation.*;

/**
 * @author housirvip
 */
@Inherited
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface HrpcAutowire {
}
