package cn.refacter.easy.http.config;

import lombok.Data;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author refacter
 * Date：Create in 2022/8/30 21:49
 */
@Component
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@ConfigurationProperties(prefix = "easy-http", ignoreUnknownFields = true)
@Data
public class HttpAutoConfiguration {
    private Integer connectTimeout = null;
    private Integer readTimeout = null;
    private Integer writeTimeout = null;
    // TODO: 2022/8/30 默认日志是否开启、代理缓存是否开启



    @Bean
    public OkHttpClient easyHttpOkHttpClient() {
        return new OkHttpClient().newBuilder().connectTimeout(connectTimeout, TimeUnit.SECONDS).writeTimeout(writeTimeout, TimeUnit.SECONDS).readTimeout(readTimeout, TimeUnit.SECONDS).build();
    }
}
