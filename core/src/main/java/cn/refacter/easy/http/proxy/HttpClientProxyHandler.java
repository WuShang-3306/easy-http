package cn.refacter.easy.http.proxy;

import cn.refacter.easy.http.annotations.HttpRequest;
import cn.refacter.easy.http.base.HttpRequestMetaData;
import cn.refacter.easy.http.constant.HttpMethod;
import cn.refacter.easy.http.exception.EasyHttpRuntimeException;
import cn.refacter.easy.http.utils.OkHttpUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author refacter
 * Date：Create in 2022/8/30 21:31
 */
@Component
@Slf4j
public class HttpClientProxyHandler implements InvocationHandler, ApplicationContextAware {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        HttpRequestMetaData metaData = new HttpRequestMetaData();
        HttpRequest classAnnotation = AnnotationUtils.getAnnotation(method.getDeclaringClass(), HttpRequest.class);
        HttpRequest methodAnnotation = AnnotationUtils.getAnnotation(method, HttpRequest.class);
        this.classAnnotationProcess(classAnnotation, metaData);
        this.methodAnnotationProcess(methodAnnotation, metaData);
        this.metaDataProcess(metaData, method);
        this.metaValidate(metaData, method);

        String responseStr = this.request(metaData);
        return JSON.parseObject(responseStr, metaData.getResponseType());
    }

    private String request(HttpRequestMetaData metaData) {
        try {
            if (HttpMethod.POST.equals(metaData.getHttpMethod())) {
                return OkHttpUtils.postJson(metaData.getRequestUrl(), JSON.toJSONString(metaData.getRequestBody()), null);
            } else if (HttpMethod.GET.equals(metaData.getHttpMethod())) {
                return OkHttpUtils.get(metaData.getRequestUrl(), metaData.getRequestParam(), null);
            } else {
                throw new UnsupportedOperationException("unsupported http request type");
            }
        } catch (Exception e) {
            throw new EasyHttpRuntimeException(e);
        }
    }

    private void classAnnotationProcess(HttpRequest httpRequest, HttpRequestMetaData metaData) {
        Assert.state(httpRequest != null, "HttpClient proxy class not find specific annotation");
        this.annotationProcess(httpRequest, metaData);
    }

    private void methodAnnotationProcess(HttpRequest httpRequest, HttpRequestMetaData metaData) {
        Assert.state(httpRequest != null, "HttpClient proxy class-method not find specific annotation");
        this.annotationProcess(httpRequest, metaData);
    }

    private void annotationProcess(HttpRequest httpRequest, HttpRequestMetaData metaData) {
        metaData.setRequestUrl(StringUtils.isNotBlank(httpRequest.requestUrl()) ? httpRequest.requestUrl() : metaData.getRequestUrl());
        metaData.setBaseUrl(StringUtils.isNotBlank(httpRequest.baseUrl()) ? httpRequest.baseUrl() : metaData.getBaseUrl());
        metaData.setPathUrl(StringUtils.isNotBlank(httpRequest.pathUrl()) ? httpRequest.pathUrl() : metaData.getPathUrl());
        metaData.setHttpMethod(Objects.isNull(httpRequest.httpMethod()) ? metaData.getHttpMethod() : httpRequest.httpMethod());
    }

    private void metaDataProcess(HttpRequestMetaData metaData, Method method) throws InstantiationException, IllegalAccessException {
        if (StringUtils.isBlank(metaData.getRequestUrl())) {
            if (StringUtils.isNotBlank(metaData.getBaseUrl()) && StringUtils.isNotBlank(metaData.getPathUrl())) {
                metaData.setRequestUrl(String.format("%s%s", metaData.getBaseUrl(), metaData.getPathUrl()));
            }
        }
    }

    private void metaValidate(HttpRequestMetaData metaData, Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        String errorTemplate = String.format("%s#%s", className, methodName);
        Assert.state(StringUtils.isNotBlank(metaData.getRequestUrl()), String.format("%s requestUrl is blank", errorTemplate));
        Assert.state(metaData.getHttpMethod() != null, String.format("%s httpMethod is null", errorTemplate));
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
