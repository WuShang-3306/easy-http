package cn.refacter.easy.http.utils;

import cn.refacter.easy.http.config.HttpAutoConfiguration;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author refacter
 * Date：Create in 2022/11/4 17:02
 */
public class HttpRequestWrapperFactory {

    public static final String OKHTTP = "okhttp3";
    public static final String HTTPCLIENT = "httpclient";

    private static Map<String, EasyHttpRequestSupport> wrapperContext = new ConcurrentHashMap(2);




    public static EasyHttpRequestSupport getRequestWrapper(String clientType) {
        EasyHttpRequestSupport requestSupport = wrapperContext.get(clientType);
        if (requestSupport == null) {
            if (StringUtils.equals(clientType, OKHTTP)) {
                OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                        .connectTimeout(HttpAutoConfiguration.getConnectTimeout(), TimeUnit.SECONDS)
                        .writeTimeout(HttpAutoConfiguration.getWriteTimeout(), TimeUnit.SECONDS)
                        .readTimeout(HttpAutoConfiguration.getReadTimeout(), TimeUnit.SECONDS)
                        .build();
                requestSupport = new OkHttpRequestWrapper(okHttpClient);
            }
            else if (StringUtils.equals(clientType, HTTPCLIENT)) {
                // do something
            }
            else {
                // other EasyHttpRequestSupport implements
            }
        }
        return requestSupport;
    }
}
