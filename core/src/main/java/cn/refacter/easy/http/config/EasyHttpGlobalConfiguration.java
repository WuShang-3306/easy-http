package cn.refacter.easy.http.config;

import cn.refacter.easy.http.converter.json.EasyHttpFastJsonConverter;
import cn.refacter.easy.http.converter.json.EasyHttpGsonConverter;
import cn.refacter.easy.http.converter.json.EasyHttpJacksonConverter;
import cn.refacter.easy.http.converter.json.EasyHttpJsonConverter;
import cn.refacter.easy.http.exception.EasyHttpRuntimeException;

/**
 * @author refacter
 * Date：Create in 2022/9/2 11:09
 */
public class EasyHttpGlobalConfiguration {

    private final static EasyHttpJsonConverter jsonConverter;

    static {
        jsonConverter = initJsonConverter();
    }

    private static EasyHttpJsonConverter initJsonConverter() {
        try {
            Class.forName("com.alibaba.fastjson.JSON");
            return new EasyHttpFastJsonConverter();
        } catch (ClassNotFoundException e) {
        }
        try {
            Class.forName("com.fasterxml.jackson.databind.ObjectMapper");
            return new EasyHttpJacksonConverter();
        } catch (ClassNotFoundException e) {
        }
        try {
            Class.forName("com.google.gson.JsonParser");
            return new EasyHttpGsonConverter();
        } catch (ClassNotFoundException e) {
        }
        throw new Error("json converter has not config");
    }

    public static EasyHttpJsonConverter getJsonConverter() {
        if (jsonConverter == null) {
            throw new EasyHttpRuntimeException("json dependency not exist");
        }
        return jsonConverter;
    }
}
