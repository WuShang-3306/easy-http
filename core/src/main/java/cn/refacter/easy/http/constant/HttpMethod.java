package cn.refacter.easy.http.constant;

import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author refacter
 * Date：Create in 2022/8/30 21:28
 */
public enum HttpMethod {

    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

    private static final Map<String, HttpMethod> mappings = new HashMap<>(16);

    static {
        for (HttpMethod httpMethod : values()) {
            mappings.put(httpMethod.name(), httpMethod);
        }
    }

    @Nullable
    public static HttpMethod resolve(@Nullable String method) {
        return (method != null ? mappings.get(method) : null);
    }

    public boolean matches(String method) {
        return (this == resolve(method));
    }
}
