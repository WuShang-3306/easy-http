package cn.refacter.easy.http.base;

import cn.refacter.easy.http.constant.HttpMethod;
import cn.refacter.easy.http.interceptor.Interceptor;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author refacter
 * Date：Create in 2022/8/30 21:40
 */
@Data
public class ProxyMetaData {
    private String baseUrl;
    private String pathUrl;
    // real url
    private String requestUrl;
    // target method
    private HttpMethod httpMethod;
    // method return type
    private Class<?> responseType;
    // TODO: 2022/8/28 requestHeader
    // httpBody index of method params
    private Integer requestBodyIndex;
    // httpParam index&name
    private Map<Integer, String> paramIndexNameCacheMap;
    private List<Interceptor> interceptorChain = Collections.emptyList();
}
