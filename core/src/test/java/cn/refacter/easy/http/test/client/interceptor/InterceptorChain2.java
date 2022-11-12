package cn.refacter.easy.http.test.client.interceptor;

import cn.refacter.easy.http.base.ProxyMetaData;
import cn.refacter.easy.http.interceptor.Interceptor;
import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * @author refacter
 * Date：Create in 2022/11/12 11:07
 */
public class InterceptorChain2 implements Interceptor {

    @Override
    public void beforeRequest(ProxyMetaData metaData, Map<String, String> requestParam, Map<String, String> requestBody) {
        requestParam.put("text", "interceptorChain2");
    }

    @Override
    public String postRequest(String responseStr) {
        InterceptorRequestTestClient.ResponseBody responseBody = JSON.parseObject(responseStr, InterceptorRequestTestClient.ResponseBody.class);
        responseBody.setUrl("url");
        return JSON.toJSONString(responseBody);
    }
}
