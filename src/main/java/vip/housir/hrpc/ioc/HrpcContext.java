package vip.housir.hrpc.ioc;

/**
 * @author housirvip
 */
public interface HrpcContext extends BeanFactory {

    /**
     * @return 上下文名称
     */
    String getContextName();
}
