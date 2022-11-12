package cn.refacter.easy.http.test.client.interceptor;

import cn.refacter.easy.http.base.ProxyMetaData;
import cn.refacter.easy.http.interceptor.Interceptor;
import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * @author refacter
 * Date：Create in 2022/11/12 11:07
 */
public class InterceptorChain1 implements Interceptor {

    @Override
    public void beforeRequest(ProxyMetaData metaData, Map<String, String> requestParam, Map<String, String> requestBody) {
        requestParam.put("rar", "interceptorChain1");
    }

    @Override
    public String postRequest(String responseStr) {
        InterceptorRequestTestClient.ResponseBody responseBody = JSON.parseObject(responseStr, InterceptorRequestTestClient.ResponseBody.class);
        responseBody.setJson("json");
        return JSON.toJSONString(responseBody);
    }
}
