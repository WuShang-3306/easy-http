package cn.refacter.easy.http.utils;

import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author refacter
 * Date：Create in 2022/9/3 14:21
 */
public interface EasyHttpRequestSupport {

    default String get(String url, Map<String, String> requestParam, Map<String, String> header) throws IOException {
        StringBuilder requestUrl = new StringBuilder(url);
        if (!CollectionUtils.isEmpty(requestParam)) {
            requestUrl.append("?");
            for (Map.Entry<String, String> entry : requestParam.entrySet()) {
                requestUrl.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            requestUrl.delete(requestUrl.length()-1, requestUrl.length());
        }
        return get(requestUrl.toString(), header);
    }

    default String postJson(String url, Map<String, String> requestParam, String jsonBody, Map<String, String> header) throws IOException {
        StringBuilder requestUrl = new StringBuilder(url);
        if (!CollectionUtils.isEmpty(requestParam)) {
            requestUrl.append("?");
            for (Map.Entry<String, String> entry : requestParam.entrySet()) {
                requestUrl.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            requestUrl.delete(requestUrl.length()-1, requestUrl.length());
        }
        return postJson(requestUrl.toString(), jsonBody, header);
    }

    String get(String url, Map<String, String> header) throws IOException;

    String postJson(String url, String json, Map<String, String> header) throws IOException;
}
