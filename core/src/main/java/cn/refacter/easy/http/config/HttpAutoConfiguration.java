package cn.refacter.easy.http.config;

import cn.refacter.easy.http.utils.HttpRequestWrapperFactory;
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
public class HttpAutoConfiguration {
    private static Integer connectTimeout = 5;
    private static Integer readTimeout = 5;
    private static Integer writeTimeout = 5;
    // proxy cache
    private static Boolean enableProxyCache = true;
    private static String backClient = HttpRequestWrapperFactory.OKHTTP;
    // TODO: 2022/8/30 默认日志是否开启、代理缓存是否开启


    private HttpAutoConfiguration() {
    }

    public static Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        HttpAutoConfiguration.connectTimeout = connectTimeout;
    }

    public static Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        HttpAutoConfiguration.readTimeout = readTimeout;
    }

    public static Integer getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Integer writeTimeout) {
        HttpAutoConfiguration.writeTimeout = writeTimeout;
    }

    public static Boolean getEnableProxyCache() {
        return enableProxyCache;
    }

    public void setEnableProxyCache(Boolean enableProxyCache) {
        HttpAutoConfiguration.enableProxyCache = enableProxyCache;
    }

    public static String getBackClient() {
        return backClient;
    }

    public void setBackClient(String backClient) {
        HttpAutoConfiguration.backClient = backClient;
    }
}
