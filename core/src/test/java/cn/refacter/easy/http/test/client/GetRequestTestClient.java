package cn.refacter.easy.http.test.client;

import cn.refacter.easy.http.annotations.HttpRequest;
import cn.refacter.easy.http.constant.HttpMethod;

/**
 * @author refacter
 * Date：Create in 2022/8/30 22:47
 */
@HttpRequest(baseUrl = "${request.get.base.url}")
public interface GetRequestTestClient {

    @HttpRequest(pathUrl = "/search?keywords=你不懂我", httpMethod = HttpMethod.GET)
    String getRequest();
}
