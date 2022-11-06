package cn.refacter.easy.http.test.client;

import cn.refacter.easy.http.config.EasyHttpGlobalConfiguration;
import cn.refacter.easy.http.test.TestApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author refacter
 * Date：Create in 2022/9/1 21:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@SpringBootApplication(scanBasePackages = "cn.refacter.easy.http")
public class PostRequestTest {

    @Autowired
    private PostRequestTestClient postRequestTestClient;

    @Value("${request.post.base.url}")
    private String postBaseUrl;

    @Test
    public void simplePostRequestTest() {
        String str = "123";
        PostRequestTestClient.Body1 body = new PostRequestTestClient.Body1(str);
        PostRequestTestClient.ResponseBody1 response = postRequestTestClient.simpleBodyTest(body);
        Assert.assertEquals(response.getJson(), EasyHttpGlobalConfiguration.getJsonConverter().toJSONString(body));
    }

    @Test
    public void paramAndBodyTest() {
        String str = "123";
        PostRequestTestClient.Body1 body = new PostRequestTestClient.Body1(str);
        PostRequestTestClient.ResponseBody1 response = postRequestTestClient.paramAndBodyTest(str, body);
        Assert.assertEquals(response.getJson(), EasyHttpGlobalConfiguration.getJsonConverter().toJSONString(body));
        String url = String.format("%s%s%s", postBaseUrl, "?rar=", str);
        Assert.assertEquals(response.getJson(), url);
    }


    @Test
    public void paramBeanAndBodyTest() {
        String rar = "rar"; String text = "text"; String str = "123";
        PostRequestTestClient.ParamBean paramBean = new PostRequestTestClient.ParamBean(rar, text);
        PostRequestTestClient.Body1 body = new PostRequestTestClient.Body1(str);
        PostRequestTestClient.ResponseBody1 response = postRequestTestClient.paramBeanAndBodyTest(paramBean, body);
        Assert.assertEquals(response.getJson(), EasyHttpGlobalConfiguration.getJsonConverter().toJSONString(body));
        Assert.assertEquals(paramBean.getRar(), response.getArgs().getRar());
        Assert.assertEquals(paramBean.getText(), response.getArgs().getText());
    }
}
