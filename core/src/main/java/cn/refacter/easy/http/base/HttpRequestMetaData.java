package cn.refacter.easy.http.base;

import cn.refacter.easy.http.constant.HttpMethod;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author refacter
 * Date：Create in 2022/8/30 21:40
 */
@Data
public class HttpRequestMetaData {
    private String baseUrl;
    private String pathUrl;
    private String requestUrl;
    private HttpMethod httpMethod;
    private Map<String, String> requestParam;
    private Map<String, String> requestBody;
    private Class<?> responseType;
    // TODO: 2022/8/28 requestHeader
    private Integer requestBodyIndex;
    private List<Integer> requestParamIndexList;
}
