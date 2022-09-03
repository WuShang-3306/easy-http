package cn.refacter.easy.http.converter.json;

/**
 * @author refacter
 * Date：Create in 2022/9/2 11:03
 */
public interface EasyHttpJsonConverter {

    String toJSONString(Object object);

    <T> T parseObject(String text, Class<T> clazz);
}
