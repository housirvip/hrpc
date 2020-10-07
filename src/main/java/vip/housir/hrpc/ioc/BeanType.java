package vip.housir.hrpc.ioc;

/**
 * @author housirvip
 */

public enum BeanType {
    /**
     * 普通 bean
     * 或者用户自定义 bean
     */
    BEAN,
    /**
     * ioc 组件
     */
    COMPONENT,
    /**
     * ioc 服务
     */
    SERVICE;
}
