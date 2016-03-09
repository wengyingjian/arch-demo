package com.wengyingjian.demo.soa.rpc;

import com.wengyingjian.demo.soa.rpc.tcp.v1.TcpClient;
import com.wengyingjian.demo.soa.rpc.tcp.v1.TcpServer;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by wengyingjian on 16/3/9.
 */
public class TcpRpcTest {

    private TcpServer tcpServer;
    private TcpClient tcpClient;


    @Before
    public void init() {
        tcpServer = new TcpServer();
        tcpClient = new TcpClient();
    }

    @Test
    public void testServer() {
        tcpServer.listen();
    }

    @Test
    public void testClient() {
        Object result = tcpClient.call();
        System.out.println("result:" + result);
    }

}
