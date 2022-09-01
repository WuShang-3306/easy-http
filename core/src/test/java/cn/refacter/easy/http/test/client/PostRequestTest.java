package cn.refacter.easy.http.test.client;

import cn.refacter.easy.http.test.TestApplication;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Test
    public void simplePostRequestTest() {
        String str = "123";
        PostRequestTestClient.Body1 body = new PostRequestTestClient.Body1(str);
        PostRequestTestClient.ResponseBody1 response = postRequestTestClient.test(body);
        Assert.assertEquals(response.getJson(), JSON.toJSONString(body));
    }
}
