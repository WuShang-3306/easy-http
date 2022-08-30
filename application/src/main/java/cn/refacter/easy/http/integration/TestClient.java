package cn.refacter.easy.http.integration;

import cn.refacter.easy.http.annotations.HttpRequest;

/**
 * @author refacter
 * Date：Create in 2022/8/30 21:56
 */
@HttpRequest(baseUrl = "${http.base.url}")
public interface TestClient {
    void test();
}
