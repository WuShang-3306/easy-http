package cn.refacter.easy.http.annotations;

import cn.refacter.easy.http.constant.HttpMethod;
import cn.refacter.easy.http.interceptor.Interceptor;

import java.lang.annotation.*;
import java.util.List;

/**
 * @author refacter
 * Date：Create in 2022/8/30 21:27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface HttpRequest {
    /**
     * every interface common request-url
     */
    String baseUrl() default "";

    /**
     * every interface owner path-url
     */
    String pathUrl() default "";

    /**
     * the full path of http request
     * <notice>use this field will override {@code pathUrl} & {@code baseUrl}</notice>
     */
    String requestUrl() default "";

    /**
     * the mode of http request
     * support: POST GET
     */
    HttpMethod httpMethod() default HttpMethod.POST;

    Class<? extends Interceptor>[] interceptors() default {};
}
