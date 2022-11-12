package cn.refacter.easy.http.proxy;

import cn.refacter.easy.http.annotations.HttpBody;
import cn.refacter.easy.http.annotations.HttpParam;
import cn.refacter.easy.http.annotations.HttpRequest;
import cn.refacter.easy.http.base.ProxyMetaData;
import cn.refacter.easy.http.config.EasyHttpGlobalConfiguration;
import cn.refacter.easy.http.config.HttpAutoConfiguration;
import cn.refacter.easy.http.constant.HttpMethod;
import cn.refacter.easy.http.exception.EasyHttpRuntimeException;
import cn.refacter.easy.http.interceptor.Interceptor;
import cn.refacter.easy.http.utils.ClassUtils;
import cn.refacter.easy.http.utils.EasyHttpRequestSupport;
import cn.refacter.easy.http.utils.HttpRequestWrapperFactory;
import com.alibaba.fastjson.JSON;
import javafx.application.Application;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author refacter
 * Date：Create in 2022/8/30 21:31
 */
public class EasyHttpClientProxyHandler implements InvocationHandler {

    private static LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    private volatile static EasyHttpClientProxyHandler uniqueHandler;

    private EasyHttpClientProxyHandler() {}

    public static EasyHttpClientProxyHandler getInstance(ApplicationContext applicationContext) {
        if(uniqueHandler == null) {
            synchronized(EasyHttpClientProxyHandler.class) {
                if(uniqueHandler == null) {
                    uniqueHandler = new EasyHttpClientProxyHandler();
                    uniqueHandler.applicationContext = applicationContext;
                }
            }
        }
        return uniqueHandler;
    }

    private ApplicationContext applicationContext;

    // proxy cache
    private Map<Method, ProxyMetaData> metaCache = new ConcurrentHashMap<>();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO: 2022/9/2 proxy cache
        ProxyMetaData metaData = null;

        if (HttpAutoConfiguration.getEnableProxyCache()) {
            metaData = metaCache.get(method);
        }
        if (metaData == null) {
            metaData = new ProxyMetaData();
            HttpRequest classAnnotation = AnnotationUtils.getAnnotation(method.getDeclaringClass(), HttpRequest.class);
            HttpRequest methodAnnotation = method.getDeclaringClass().getDeclaredMethod(method.getName(), method.getParameterTypes()).getDeclaredAnnotation(HttpRequest.class);
            this.classAnnotationProcess(classAnnotation, metaData);
            this.methodAnnotationProcess(methodAnnotation, metaData);
            this.paramAnnotationProcess(method, metaData);
            // TODO: 2022/9/3 requestHeader
            this.metaDataProcess(metaData, method, args);
            this.metaValidate(metaData, method);
            metaCache.put(method, metaData);
        }
        Map<String, String> requestParam = this.requestParamProcess(metaData, args);
        Map<String, String> requestBody = this.requestBodyProcess(metaData, args);
        boolean hasInterceptor = !metaData.getInterceptorChain().isEmpty();
        // before request
        if (hasInterceptor) {
            for (Interceptor interceptor : metaData.getInterceptorChain()) {
                interceptor.beforeRequest(metaData, requestParam, requestBody);
            }
        }
        String responseStr = this.request(metaData, requestParam, requestBody);
        // post request
        if (hasInterceptor) {
            for (Interceptor interceptor : metaData.getInterceptorChain()) {
                responseStr = interceptor.postRequest(responseStr);
            }
        }
        return EasyHttpGlobalConfiguration.getJsonConverter().parseObject(responseStr, metaData.getResponseType());
    }

    private String request(ProxyMetaData metaData, Map<String, String> requestParam, Map<String, String> requestBody) {
        EasyHttpRequestSupport requestDelegate = HttpRequestWrapperFactory.getRequestWrapper(HttpAutoConfiguration.getBackClient());
        try {
            if (HttpMethod.POST.equals(metaData.getHttpMethod())) {
                return requestDelegate.postJson(metaData.getRequestUrl(), requestParam, EasyHttpGlobalConfiguration.getJsonConverter().toJSONString(requestBody), null);
            } else if (HttpMethod.GET.equals(metaData.getHttpMethod())) {
                return requestDelegate.get(metaData.getRequestUrl(), requestParam, null);
            } else {
                throw new UnsupportedOperationException("unsupported http request type");
            }
        } catch (Exception e) {
            throw new EasyHttpRuntimeException(e);
        }
    }

    private void classAnnotationProcess(HttpRequest httpRequest, ProxyMetaData metaData) throws InstantiationException, IllegalAccessException {
        Assert.state(httpRequest != null, "HttpClient proxy class not find specific annotation");
        this.annotationProcess(httpRequest, metaData);
    }

    private void methodAnnotationProcess(HttpRequest httpRequest, ProxyMetaData metaData) throws InstantiationException, IllegalAccessException {
        Assert.state(httpRequest != null, "HttpClient proxy class-method not find specific annotation");
        this.annotationProcess(httpRequest, metaData);
    }

    private void annotationProcess(HttpRequest httpRequest, ProxyMetaData metaData) throws InstantiationException, IllegalAccessException {
        metaData.setRequestUrl(StringUtils.isNotBlank(httpRequest.requestUrl()) ? httpRequest.requestUrl() : metaData.getRequestUrl());
        metaData.setBaseUrl(StringUtils.isNotBlank(httpRequest.baseUrl()) ? httpRequest.baseUrl() : metaData.getBaseUrl());
        metaData.setPathUrl(StringUtils.isNotBlank(httpRequest.pathUrl()) ? httpRequest.pathUrl() : metaData.getPathUrl());
        metaData.setHttpMethod(Objects.isNull(httpRequest.httpMethod()) ? metaData.getHttpMethod() : httpRequest.httpMethod());
        if (httpRequest.interceptors().length > 0) {
            List<Interceptor> interceptorChain = new ArrayList<>(httpRequest.interceptors().length);
            for (Class<? extends Interceptor> interceptor : httpRequest.interceptors()) {
                Interceptor bean;
                try {
                    bean = this.applicationContext.getBean(interceptor);
                } catch (BeansException e) {
                    // no spring bean
                    bean = interceptor.newInstance();
                }
                interceptorChain.add(bean);
            }
            metaData.setInterceptorChain(interceptorChain);
        }
    }

    private void paramAnnotationProcess(Method method, ProxyMetaData metaData) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Parameter[] parameters = method.getParameters();
        Map<Integer, String> paramIndexNameCacheMap = new HashMap<>();
        metaData.setParamIndexNameCacheMap(paramIndexNameCacheMap);
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                if (parameterAnnotations[i][j] instanceof HttpParam) {
                    if (ClassUtils.isCustomClass(parameters[0].getType())) {
                        // param-bean support
                        paramIndexNameCacheMap.put(i, ((HttpParam)parameterAnnotations[i][j]).value());
                    }
                    else {
                        paramIndexNameCacheMap.put(i, ((HttpParam)parameterAnnotations[i][j]).value());
                    }
                    break;
                }
                else if (parameterAnnotations[i][j] instanceof HttpBody) {
                    metaData.setRequestBodyIndex(i);
                    break;
                }

            }
        }
    }

    private Map<String, String> requestParamProcess(ProxyMetaData metaData, Object[] args) {
        Map<String, String> requestParam = new HashMap<>();
        if (metaData.getParamIndexNameCacheMap() != null && !metaData.getParamIndexNameCacheMap().isEmpty()) {
            for (Map.Entry<Integer, String> entry : metaData.getParamIndexNameCacheMap().entrySet()) {
                if (ClassUtils.isCustomClass(args[entry.getKey()].getClass())) {
                    requestParam.putAll(this.getRequestParamFromBean(args[entry.getKey()]));
                }
                else {
                    requestParam.put(entry.getValue(), (String) args[entry.getKey()]);
                }
            }
        }
        return requestParam;
    }

    public Map<String, String> getRequestParamFromBean(Object paramBean) {
        Field[] fields = paramBean.getClass().getDeclaredFields();
        Map<String, String> paramMap = new HashMap<>(fields.length);
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object o = field.get(paramBean);
                if (!Objects.isNull(o)) {
                    paramMap.put(field.getName(), o.toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return paramMap;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> requestBodyProcess(ProxyMetaData metaData, Object[] args) {
        if (metaData.getRequestBodyIndex() != null) {
            return EasyHttpGlobalConfiguration.getJsonConverter().parseObject(JSON.toJSONString(args[metaData.getRequestBodyIndex()]), Map.class);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void metaDataProcess(ProxyMetaData metaData, Method method, Object[] args) throws InstantiationException, IllegalAccessException {
        if (StringUtils.isBlank(metaData.getRequestUrl())) {
            if (StringUtils.isNotBlank(metaData.getBaseUrl()) && StringUtils.isNotBlank(metaData.getPathUrl())) {
                metaData.setRequestUrl(String.format("%s%s", metaData.getBaseUrl(), metaData.getPathUrl()));
            }
        }
        metaData.setResponseType(method.getReturnType());
    }

    private void metaValidate(ProxyMetaData metaData, Method method) {
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        String errorTemplate = String.format("%s#%s", className, methodName);
        Assert.state(StringUtils.isNotBlank(metaData.getRequestUrl()), String.format("%s requestUrl is blank", errorTemplate));
        Assert.state(metaData.getHttpMethod() != null, String.format("%s httpMethod is null", errorTemplate));
    }
}
