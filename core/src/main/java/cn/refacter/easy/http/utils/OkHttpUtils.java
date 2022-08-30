package cn.refacter.easy.http.utils;

import okhttp3.*;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @author refacter
 * Date：Create in 2022/8/30 21:44
 */
public class OkHttpUtils {
    private static OkHttpClient CLIENT;

    public static void setCLIENT(OkHttpClient okHttpClient) {
        OkHttpUtils.CLIENT = okHttpClient;
    }

    public static String get(String url, Map<String, String> requestParam, Map<String, String> header) throws IOException {
        StringBuilder requestUrl = new StringBuilder(url);
        if (!CollectionUtils.isEmpty(requestParam)) {
            requestUrl.append("?");
            for (Map.Entry<String, String> entry : requestParam.entrySet()) {
                requestUrl.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        return get(requestUrl.toString(), header);
    }

    public static String get(String url, Map<String, String> header) throws IOException {
        Request.Builder request = new Request.Builder().url(url).get();
        if (header != null && !header.isEmpty()) {
            for (Map.Entry<String, String> headerEntry : header.entrySet()) {
                request.addHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }
        request.addHeader("Connection", "close");
        Response response = CLIENT.newCall(request.build()).execute();
        String result;
        try {
            ResponseBody res = response.body();
            result = res.string();
        } finally {
            response.close();
        }
        return result;
    }

    public static String postJson(String url, String json, Map<String, String> header) throws IOException  {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request.Builder request = (new Request.Builder()).url(url).post(body);
        if (header != null && !header.isEmpty()) {
            for (Map.Entry<String, String> headerEntry : header.entrySet()) {
                request.addHeader(headerEntry.getKey(), headerEntry.getValue());
            }
        }
        request.addHeader("Connection", "close");

        Response response = CLIENT.newCall(request.build()).execute();

        String result;
        try {
            ResponseBody res = response.body();
            result = res.string();
        } finally {
            response.close();
        }
        return result;
    }
}
