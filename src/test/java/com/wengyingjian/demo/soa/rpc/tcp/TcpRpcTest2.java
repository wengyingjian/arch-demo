package com.wengyingjian.demo.soa.rpc.tcp;

import com.wengyingjian.demo.soa.rpc.service.HelloService;
import com.wengyingjian.demo.soa.rpc.service.HelloService2;
import com.wengyingjian.demo.soa.rpc.tcp.v2.TcpClient2;
import com.wengyingjian.demo.soa.rpc.tcp.v2.TcpServer2;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created by wengyingjian on 16/3/9.
 */
public class TcpRpcTest2 {

    private TcpServer2 tcpServer;
    private TcpClient2 tcpClient;


    @Before
    public void init() {
        tcpServer = new TcpServer2();
        tcpClient = new TcpClient2();
    }

    @Test
    public void testServer() {
        tcpServer.listen2();
    }

    @Test
    public void testClient() {
        Object result = null;
        try {
            Object[] args = {"hello"};
            Method method = HelloService.class.getMethod("say", String.class);
            result = tcpClient.call2(HelloService.class, method, args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println("result:" + result);
    }

    @Test
    public void testClient2() {
        Object result = null;
        try {
            Object[] args = {"jack"};
            Method method = HelloService2.class.getMethod("hi", String.class);
            result = tcpClient.call2(HelloService2.class, method, args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println("result:" + result);
    }
}
