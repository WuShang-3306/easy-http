package cn.refacter.easy.http.proxy;

import cn.refacter.easy.http.annotations.HttpRequest;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.PropertyPlaceholderHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author refacter
 * Date：Create in 2022/8/30 21:30
 */
@Component
public class HttpClientProxyFactoryBean<T> implements FactoryBean<T>, InitializingBean, ApplicationContextAware, EnvironmentAware {

    private static final PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("${", "}", null, true);
    private ApplicationContext applicationContext;
    private Class<?> type;
    private String name;
    private String contextId;

    @Override
    public T getObject() throws Exception {
        this.processHttpRequestValue();
        return (T) Proxy.newProxyInstance(getObjectType().getClassLoader(), new Class[]{getObjectType()}, EasyHttpClientProxyHandler.getInstance());
    }

    private void processHttpRequestValue() throws Exception {
        this.processClassAnnotationValue();
        // TODO: 2022/1/10 暂时不支持方法注解中使用SPEL表达式
//        this.processMethodAnnotationValue();
    }

    private void processClassAnnotationValue() throws Exception {
        HttpRequest httpRequest = getObjectType().getDeclaredAnnotation(HttpRequest.class);
        if (httpRequest != null) {
            this.processValueInject(httpRequest);
        }
    }

    private void processMethodAnnotationValue() throws Exception {
        // TODO: 2022/1/9 修改方法注解属性，代理对象没有生效
        Method[] declaredMethods = getObjectType().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            HttpRequest httpRequest = declaredMethod.getDeclaredAnnotation(HttpRequest.class);
            if (httpRequest != null) {
                this.processValueInject(httpRequest);
            }
        }
    }

    private void processValueInject(HttpRequest httpRequest) throws Exception {
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(httpRequest);
        Field memberValues = invocationHandler.getClass().getDeclaredField("memberValues");
        memberValues.setAccessible(true);
        Map<String, Object> attributes = (Map<String, Object>) memberValues.get(invocationHandler);

        String value;
        String envKey;
        for (String key : attributes.keySet()) {
            if (attributes.get(key) instanceof String) {
                value = (String) attributes.get(key);
                attributes.put(key, placeholderHelper.replacePlaceholders(value, environment::getProperty));
            }
        }
    }

    @Override
    public Class<?> getObjectType() {
        return getType();
    }

    @Override
    public void afterPropertiesSet() {
        // do nothing
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    //设置Bean单例
    @Override
    public boolean isSingleton() {
        return true;
    }
    public Class<?> getType() {
        return type;
    }
    public void setType(Class<?> type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getContextId() {
        return contextId;
    }
    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
