package vip.housir.hrpc.ioc;

/**
 * @author housirvip
 */
public class BeanDefinition {

    private String name;

    private Class<?> clazz;

    private BeanType beanType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public BeanType getBeanType() {
        return beanType;
    }

    public void setBeanType(BeanType beanType) {
        this.beanType = beanType;
    }
}
