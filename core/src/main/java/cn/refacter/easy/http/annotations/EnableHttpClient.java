package cn.refacter.easy.http.annotations;

import cn.refacter.easy.http.proxy.HttpClientProxyRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author refacter
 * Date：Create in 2022/8/30 21:24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(HttpClientProxyRegister.class)
public @interface EnableHttpClient {
}
