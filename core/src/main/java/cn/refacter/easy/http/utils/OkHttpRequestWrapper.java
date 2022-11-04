package cn.refacter.easy.http.utils;

import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author refacter
 * Date：Create in 2022/11/4 14:32
 */
@Component
public class OkHttpRequestWrapper implements EasyHttpRequestSupport {

    private static OkHttpClient CLIENT;

    public static void setCLIENT(OkHttpClient easyHttpOkHttpClient) {
        OkHttpRequestWrapper.CLIENT = easyHttpOkHttpClient;
    }

    @Override
    public String get(String url, Map<String, String> header) throws IOException {
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

    @Override
    public String postJson(String url, String json, Map<String, String> header) throws IOException {
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
