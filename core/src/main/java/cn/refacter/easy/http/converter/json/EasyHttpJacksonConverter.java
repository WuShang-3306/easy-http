package cn.refacter.easy.http.converter.json;

import cn.refacter.easy.http.exception.EasyHttpRuntimeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author refacter
 * Date：Create in 2022/9/2 11:13
 */
public class EasyHttpJacksonConverter implements EasyHttpJsonConverter {

    private final static ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public String toJSONString(Object object) {
        // TODO: 2022/9/2 EasyHttpJsonConverterException
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new EasyHttpRuntimeException(e);
        }
    }

    @Override
    public <T> T parseObject(String text, Class<T> clazz) {
        try {
            return objectMapper.readValue(text, clazz);
        } catch (JsonProcessingException e) {
            throw new EasyHttpRuntimeException(e);
        }
    }
}
