package cn.refacter.easy.http.annotations;

import cn.refacter.easy.http.proxy.EasyHttpClientProxyRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author refacter
 * Date：Create in 2022/9/3 14:29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface HttpParam {
}
