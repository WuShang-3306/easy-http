package cn.refacter.easy.http.test;

import cn.refacter.easy.http.annotations.EnableHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author refacter
 * Date：Create in 2022/8/31 20:39
 */
@SpringBootApplication(scanBasePackages = "cn.refacter.easy.http")
@EnableHttpClient
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
