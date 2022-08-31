package cn.refacter.easy.http.test.proxy;


import cn.refacter.easy.http.annotations.HttpRequest;
import cn.refacter.easy.http.proxy.HttpClientProxyFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;

/**
 * @author refacter
 * Date：Create in 2022/8/30 22:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HttpClientProxyFactoryBeanTest.class)
public class HttpClientProxyFactoryBeanTest {

    @Autowired
    private HttpClientProxyFactoryBean factoryBean;

    @Test
    public void placeHolderTest() throws Exception {
        HttpRequest httpRequest = Object.class.getDeclaredAnnotation(HttpRequest.class);
        Method testMethod = HttpClientProxyFactoryBean.class.getDeclaredMethod("processValueInject", HttpRequest.class);
        testMethod.setAccessible(true);
        Object invoke = testMethod.invoke(factoryBean, httpRequest);
    }
}