package cn.refacter.easy.http.test.client.interceptor;

import cn.refacter.easy.http.annotations.HttpBody;
import cn.refacter.easy.http.annotations.HttpParam;
import cn.refacter.easy.http.annotations.HttpRequest;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * @author refacter
 * Date：Create in 2022/11/12 10:42
 */
@HttpRequest
public interface InterceptorRequestTestClient {
    @HttpRequest(requestUrl = "${request.post.base.url}", interceptors = {Interceptor1.class})
    ResponseBody post(@HttpParam ParamBean param, @HttpBody RequestBody body);

    @HttpRequest(requestUrl = "${request.post.base.url}", interceptors = {InterceptorChain1.class, InterceptorChain2.class})
    ResponseBody postChain(@HttpParam ParamBean param, @HttpBody RequestBody body);

    @Data
    @Accessors(chain = true)
    class ParamBean {
        String rar;
        String text;
    }


    @Data
    class RequestBody {
        private String test;
    }

    @Data
    class ResponseBody {
        private String json;

        private String url;

        private InterceptorRequestTestClient.ParamBean args;
    }
}

