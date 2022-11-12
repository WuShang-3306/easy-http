package cn.refacter.easy.http.interceptor;

import cn.refacter.easy.http.base.ProxyMetaData;
import cn.refacter.easy.http.callback.OnError;
import cn.refacter.easy.http.callback.OnRetry;
import cn.refacter.easy.http.callback.OnSuccess;

import java.util.Map;

/**
 * @author refacter
 * Date：Create in 2022/11/4 20:45
 */
public interface Interceptor extends OnSuccess, OnError, OnRetry {

    default void beforeRequest(ProxyMetaData metaData, Map<String, String> requestParam, Map<String, String> requestBody) {
        // do nothing
    };

    default String postRequest(String responseStr) {
        // do nothing
        return responseStr;
    };

    default void onSuccess() {

    }

    default void onError() {

    }

    default void onRetry() {

    }
}
