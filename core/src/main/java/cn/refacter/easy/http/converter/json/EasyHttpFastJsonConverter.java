package cn.refacter.easy.http.converter.json;

import com.alibaba.fastjson.JSON;

/**
 * @author refacter
 * Date：Create in 2022/9/2 11:06
 */
public class EasyHttpFastJsonConverter implements EasyHttpJsonConverter {
    @Override
    public String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }

    @Override
    public <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }
}
