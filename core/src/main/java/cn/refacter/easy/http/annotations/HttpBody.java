package cn.refacter.easy.http.annotations;

import cn.refacter.easy.http.proxy.EasyHttpClientProxyRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author refacter
 * use this annotation to mark request body object
 * Date：Create in 2022/9/1 21:45
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
@Import(EasyHttpClientProxyRegister.class)
public @interface HttpBody {
}
