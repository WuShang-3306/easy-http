package cn.refacter.easy.http;

import cn.refacter.easy.http.annotations.EnableHttpClient;
import cn.refacter.easy.http.integration.TestClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author refacter
 * Date：Create in 2022/8/30 21:55
 */
@SpringBootApplication
@EnableHttpClient
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        TestClient testClient = context.getBean(TestClient.class);
        testClient.test();
    }
}
