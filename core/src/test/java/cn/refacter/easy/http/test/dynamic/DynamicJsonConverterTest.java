package cn.refacter.easy.http.test.dynamic;

import org.junit.Test;

/**
 * @author refacter
 * Date：Create in 2022/9/2 10:58
 */
public class DynamicJsonConverterTest {


    @Test
    public void dynamicJsonConverterSimpleTest() throws ClassNotFoundException {
        System.out.println(Class.forName("com.alibaba.fastjson.JSON"));
        System.out.println(Class.forName("com.fasterxml.jackson.databind.ObjectMapper"));
        System.out.println(Class.forName("com.google.gson.JsonParser"));
    }
}
