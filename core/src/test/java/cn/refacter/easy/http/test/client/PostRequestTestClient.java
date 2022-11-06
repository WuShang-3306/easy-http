package cn.refacter.easy.http.test.client;

import cn.refacter.easy.http.annotations.HttpBody;
import cn.refacter.easy.http.annotations.HttpParam;
import cn.refacter.easy.http.annotations.HttpRequest;

/**
 * @author refacter
 * Date：Create in 2022/9/1 21:46
 */
@HttpRequest
public interface PostRequestTestClient {


    @HttpRequest(requestUrl = "${request.post.base.url}")
    ResponseBody1 simpleBodyTest(@HttpBody Body1 body);

    @HttpRequest(requestUrl = "${request.post.base.url}")
    ResponseBody1 paramAndBodyTest(@HttpParam("rar") String rar, @HttpBody Body1 body);

    @HttpRequest(requestUrl = "${request.post.base.url}")
    ResponseBody1 paramBeanAndBodyTest(@HttpParam ParamBean param, @HttpBody Body1 body);

    class ParamBean {
        String rar;
        String text;

        public ParamBean() {
        }

        public ParamBean(String rar, String text) {
            this.rar = rar;
            this.text = text;
        }

        public String getRar() {
            return rar;
        }

        public void setRar(String rar) {
            this.rar = rar;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    class Body1 {
        private String test;

        public Body1() {
        }

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

        private String url;

        private ParamBean args;

        public ResponseBody1() {
        }

        public ResponseBody1(String json) {
            this.json = json;
        }

        public String getJson() {
            return json;
        }

        public void setJson(String json) {
            this.json = json;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public ParamBean getArgs() {
            return args;
        }

        public void setArgs(ParamBean args) {
            this.args = args;
        }
    }
}
