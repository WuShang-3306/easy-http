package cn.refacter.easy.http.annotations;

import cn.refacter.easy.http.proxy.EasyHttpClientProxyRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author refacter
 * Date：Create in 2022/8/30 21:24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(EasyHttpClientProxyRegister.class)
public @interface EnableHttpClient {
}
