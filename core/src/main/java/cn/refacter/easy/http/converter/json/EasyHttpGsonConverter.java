package cn.refacter.easy.http.converter.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author refacter
 * Date：Create in 2022/9/2 11:13
 */
public class EasyHttpGsonConverter implements EasyHttpJsonConverter {

    // TODO: 2022/9/2 dateformat的设置
    private static Gson gson = new GsonBuilder().create();

    @Override
    public String toJSONString(Object object) {
        return gson.toJson(object);
    }

    @Override
    public <T> T parseObject(String text, Class<T> clazz) {
        return gson.fromJson(text, clazz);
    }
}
