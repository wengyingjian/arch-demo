package com.wengyingjian.demo.soa.rpc.tcp.v2;

import com.wengyingjian.demo.soa.rpc.tcp.service.HelloService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 基于tcp的远程调用:客户端
 * Created by wengyingjian on 16/3/9.
 */
public class TcpClient2 {

    private static final int port = 10001;
    private static final String host = "127.0.0.1";

    public Object call2(Class interfacs, Method method, Object[] args) {
        try {
            Socket socket = new Socket(InetAddress.getByName(host), 10001);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeUTF(interfacs.getName());
            oos.writeUTF(method.getName());
            oos.writeObject(method.getParameterTypes());
            oos.writeObject(args);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            return ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("远程调用失败");
    }
}
