package com.wengyingjian.demo.soa.rpc.http;

import com.wengyingjian.demo.util.JsonUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wengyingjian on 16/3/9.
 */
public class HttpClientTest {


    private HttpClient client;

    @Before
    public void init() {
        client = new HttpClient();
    }

    @Test
    public void call() {
        String className = "com.wengyingjian.demo.soa.rpc.service.impl.HelloServiceImpl";
        String methodName = "say";
        Class<?>[] methodParamTypes = {String.class};
        Object[] methodParams = {"hello a"};

        Map<String, String> params = new HashMap<>();
        params.put("className", className);
        params.put("methodName", methodName);
        params.put("methodParamTypes", JsonUtil.getJsonFromObject(methodParamTypes));
        params.put("methodParams", JsonUtil.getJsonFromObject(methodParams));

        Object result = client.call("http://localhost:8080", params);

        System.out.println("result=[" + result + "]");
    }
}
