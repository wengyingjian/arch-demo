package com.wengyingjian.demo.soa.rpc.tcp;

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
public class TcpClient {

    public Object call() {
        Class clazz = HelloService.class;
        try {
            Method method = clazz.getMethod("say", String.class);
            Object[] args = {"hello"};

            Socket socket = new Socket(InetAddress.getLocalHost(), 10001);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeUTF(clazz.getName());
            oos.writeUTF(method.getName());
            oos.writeObject(method.getParameterTypes());
            oos.writeObject(args);

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object result = ois.readObject();
            return method.getReturnType().cast(result);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}
