package cn.refacter.easy.http.test.client.interceptor;

import cn.refacter.easy.http.test.TestApplication;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author refacter
 * Date：Create in 2022/11/12 10:49
 */
@SpringBootTest(classes = TestApplication.class)
@SpringBootApplication(scanBasePackages = "cn.refacter.easy.http")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InterceptorRequestTest {

    @Autowired
    private InterceptorRequestTestClient client;

    InterceptorRequestTestClient.ParamBean paramBean;
    InterceptorRequestTestClient.RequestBody requestBody = new InterceptorRequestTestClient.RequestBody();
    InterceptorRequestTestClient.ResponseBody responseBody = new InterceptorRequestTestClient.ResponseBody();

    @BeforeEach
    public void beforeAll() {
        paramBean = new InterceptorRequestTestClient.ParamBean();
        paramBean.setRar("rar");
        paramBean.setText("text");
        requestBody.setTest("test");
    }

    @Test
    public void beforeRequestTest1() {
        InterceptorRequestTestClient.ResponseBody post = client.post(paramBean, requestBody);
        Assert.assertEquals(post.getJson(), "json");
        Assert.assertEquals(JSON.toJSONString(post.getArgs()), JSON.toJSONString(new InterceptorRequestTestClient.ParamBean()));
    }

    @Test
    public void beforeRequestChainTest() {
        InterceptorRequestTestClient.ResponseBody post = client.postChain(paramBean, requestBody);
        Map<String, String> requestParam = new HashMap<>();
        requestParam.put("rar", "interceptorChain1");
        requestParam.put("text", "interceptorChain2");
        Assert.assertEquals(JSON.toJSONString(post.getArgs()), JSON.toJSONString(requestParam));
    }

    @Test
    public void postRequestChainTest() {
        InterceptorRequestTestClient.ResponseBody post = client.postChain(paramBean, requestBody);
        Assert.assertEquals("json", post.getJson());
        Assert.assertEquals("url", post.getUrl());
    }

    // TODO: 2022/11/12 spring bean interceptor test
}
