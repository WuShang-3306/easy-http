package cn.refacter.easy.http.test.client;

import cn.refacter.easy.http.test.TestApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author refacter
 * Date：Create in 2022/9/3 14:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@SpringBootApplication(scanBasePackages = "cn.refacter.easy.http")
public class GetRequestTest {

    @Autowired
    private GetRequestTestClient getRequestTestClient;

    @Test
    public void getSimpleRequestTest() {
        String result = getRequestTestClient.getSimpleRequest();
        Assert.assertNotNull(result);
    }


    @Test
    public void getParamRequestTest() {
        String result = getRequestTestClient.getParamRequest("你不懂我");
        Assert.assertNotNull(result);
    }


    @Test
    // error
    public void getParamBeanRequestTest() {
        ParamBean bean = new ParamBean().setKeywords("你不懂我").setType(0);
        String result = getRequestTestClient.getParamBeanRequest(bean);
        Assert.assertNotNull(result);
    }


    public class ParamBean {
        private String keywords;
        private Integer type;

        public ParamBean() {
        }

        public String getKeywords() {
            return keywords;
        }

        public ParamBean setKeywords(String keywords) {
            this.keywords = keywords;
            return this;
        }

        public Integer getType() {
            return type;
        }

        public ParamBean setType(Integer type) {
            this.type = type;
            return this;
        }
    }
}
