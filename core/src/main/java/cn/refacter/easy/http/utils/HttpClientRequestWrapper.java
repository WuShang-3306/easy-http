package cn.refacter.easy.http.utils;

import java.io.IOException;
import java.util.Map;

/**
 * @author refacter
 * Date：Create in 2022/11/4 17:45
 */
public class HttpClientRequestWrapper implements EasyHttpRequestSupport {
    @Override
    public String get(String url, Map<String, String> header) throws IOException {
        return null;
    }

    @Override
    public String postJson(String url, String json, Map<String, String> header) throws IOException {
        return null;
    }
}
