package com.wengyingjian.demo.soa.rpc.http;

import com.wengyingjian.demo.util.JsonUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by wengyingjian on 16/3/9.
 */
@SpringBootApplication
@RestController
public class HttpServer {

    /**
     *
     */
    @RequestMapping("/")
    public Object rpc(HttpServletRequest request) {
        try {
            String className = URLDecoder.decode(request.getParameter("className"), "utf-8");
            String methodName = URLDecoder.decode(request.getParameter("methodName"), "utf-8");
            String methodParamTypesStr = URLDecoder.decode(request.getParameter("methodParamTypes"), "utf-8");
            String methodParamsStr = URLDecoder.decode(request.getParameter("methodParams"), "utf-8");

            System.out.println(String.format("listen:className=[%s],methodName=[%s],methodParamTypes=[%s],methodParams=[%s]",
                    className,
                    methodName,
                    methodParamTypesStr,
                    methodParamsStr));

            Class<?>[] methodParamTypes = JsonUtil.getObjectFromJson(methodParamTypesStr, Class[].class);
            Object[] methodParams = JsonUtil.getObjectFromJson(methodParamsStr, Object[].class);

            return invokeMethod(className, methodName, methodParamTypes, methodParams);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object invokeMethod(String className, String methodName, Class<?>[] methodParamTypes, Object[] methodParams) {
        try {
            Class clazz = Class.forName(className);
            Object instance = clazz.newInstance();
            Method method = clazz.getMethod(methodName, methodParamTypes);
            return method.invoke(instance, methodParams);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("服务器端方法调用失败!");
    }

    public static void main(String[] args) {
        SpringApplication.run(HttpServer.class, args);
    }
}
