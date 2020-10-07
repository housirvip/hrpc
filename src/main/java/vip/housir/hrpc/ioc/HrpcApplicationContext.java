package vip.housir.hrpc.ioc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.housir.hrpc.annotation.HrpcAutowire;
import vip.housir.hrpc.annotation.HrpcComponent;
import vip.housir.hrpc.annotation.HrpcComponentScan;
import vip.housir.hrpc.utils.ClassUtils;
import vip.housir.hrpc.utils.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author housirvip
 */
public class HrpcApplicationContext implements HrpcContext {

    private final Map<String, BeanDefinition> definitionMap;
    private final Map<String, Object> beanMap;

    private static final Logger logger = LoggerFactory.getLogger(HrpcApplicationContext.class);

    public HrpcApplicationContext(Class<?> clazz) {
        definitionMap = new HashMap<>(16);
        beanMap = new HashMap<>(16);

        HrpcComponentScan scan = clazz.getAnnotation(HrpcComponentScan.class);
        if (scan == null) {
            throw new IllegalArgumentException("缺少 HrpcComponentScan 注解");
        }
        String path = scan.path();
        logger.debug("IoC 容器扫描路径：" + path);

        // 扫描路径下所有的类，得到一个 classSet
        Set<Class<?>> classSet = ClassUtils.getClassSet(path);
        // 遍历 classSet，并存入 definitionMap 中
        classSet.forEach(className -> {
            BeanDefinition bd = new BeanDefinition();

            Annotation[] annotations = className.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof HrpcComponent) {
                    String name=((HrpcComponent) annotation).name();
                    if (!StringUtils.isNotEmpty(name)) {
                        name = StringUtils.toLowerCaseFirstOne(className.getSimpleName());
                    }
                    bd.setName(name);
                    bd.setClazz(className);
                    definitionMap.put(bd.getName(), bd);
                    return;
                }

                Class<? extends Annotation> aClass = annotation.annotationType();
                Annotation[] annos = aClass.getAnnotations();
                for (Annotation anno : annos) {
                    if (anno instanceof HrpcComponent) {
                        try {
                            // 反射获取注解的 name
                            Method method = aClass.getMethod("name");
                            String value = (String) method.invoke(annotation);
                            if (!StringUtils.isNotEmpty(value)) {
                                value = StringUtils.toLowerCaseFirstOne(className.getSimpleName());
                            }
                            bd.setName(value);
                            bd.setClazz(className);
                            definitionMap.put(bd.getName(), bd);
                            return;
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
            }
        });

        logger.debug(definitionMap.toString());

        // 遍历 definitionMap 生成 bean 并存入 beanMap 中
        definitionMap.forEach((key, value) -> {
            Object bean = createBean(key, value.getClazz());
            beanMap.put(key, bean);
        });

        logger.debug(beanMap.toString());
    }

    private Object createBean(String beanName, Class<?> clazz) {
        if (beanMap.containsKey(beanName)) {
            return beanMap.get(beanName);
        }
        try {
            Object bean = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                if (!field.isAnnotationPresent(HrpcAutowire.class)) {
                    continue;
                }

                Object value;
                if (beanMap.containsKey(name)) {
                    value = beanMap.get(name);
                } else {
                    value = createBean(name, field.getDeclaringClass());
                    beanMap.put(name, value);
                }
                field.setAccessible(true);
                field.set(bean, value);
            }
            return bean;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> clazz) {
        Object bean = getBean(beanName);
        if (clazz.isInstance(bean)) {
            return (T) bean;
        }
        return null;
    }

    @Override
    public String getContextName() {
        return "HrpcApplicationContext";
    }
}
