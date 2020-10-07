package vip.housir.hrpc.ioc;

/**
 * @author housirvip
 */
public interface BeanFactory {

    /**
     * 根据名称获取 bean，类型未知
     *
     * @param beanName bean 名称
     * @return bean 对象
     */
    Object getBean(String beanName);

    /**
     * 根据名称获取 bean，类型已知
     *
     * @param beanName bean 名称
     * @param clazz    bean 类型
     * @return bean 对象
     */
    <T> T getBean(String beanName, Class<T> clazz);
}
