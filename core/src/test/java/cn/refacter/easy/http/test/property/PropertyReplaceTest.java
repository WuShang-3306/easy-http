package cn.refacter.easy.http.test.property;

import cn.refacter.easy.http.annotations.HttpRequest;
import cn.refacter.easy.http.test.TestApplication;
import cn.refacter.easy.http.test.client.GetRequestTestClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author refacter
 * Date：Create in 2022/8/31 20:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@SpringBootApplication(scanBasePackages = "cn.refacter.easy.http")
public class PropertyReplaceTest {
    @Test
    public void classReplaceTest() {
        HttpRequest httpRequest = GetRequestTestClient.class.getDeclaredAnnotation(HttpRequest.class);
        Assert.assertEquals(httpRequest.baseUrl(), "https://autumnfish.cn");
    }
}
