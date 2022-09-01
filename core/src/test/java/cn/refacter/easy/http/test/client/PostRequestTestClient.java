package cn.refacter.easy.http.test.client;

import cn.refacter.easy.http.annotations.HttpBody;
import cn.refacter.easy.http.annotations.HttpRequest;

/**
 * @author refacter
 * Date：Create in 2022/9/1 21:46
 */
@HttpRequest
public interface PostRequestTestClient {


    @HttpRequest(requestUrl = "${request.post.base.url}")
    ResponseBody1 test(@HttpBody Body1 body);

    class Body1 {
        private String test;

        public Body1(String test) {
            this.test = test;
        }

        public String getTest() {
            return test;
        }

        public void setTest(String test) {
            this.test = test;
        }
    }

    class ResponseBody1 {
        private String json;

        public ResponseBody1(String json) {
            this.json = json;
        }

        public String getJson() {
            return json;
        }

        public void setJson(String json) {
            this.json = json;
        }
    }
}
