package com.wengyingjian.demo.soa.rpc.tcp;

import com.wengyingjian.demo.soa.rpc.tcp.service.HelloService;
import com.wengyingjian.demo.soa.rpc.tcp.service.impl.HelloServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于tcp的远程调用:服务端
 * Created by wengyingjian on 16/3/9.
 */
public class TcpServer {

    public void listen() {
        Class clazz = HelloService.class;


        try {
            ServerSocket serverSocket = new ServerSocket(10001);
            Socket socket = serverSocket.accept();

            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String inferfaceName = ois.readUTF();
            String methodName = ois.readUTF();
            Class<?>[] methodParams = (Class<?>[]) ois.readObject();
            Object[] args = (Object[]) ois.readObject();

            Object imlpBean = getImpl(inferfaceName);
            Method method = Class.forName(inferfaceName).getMethod(methodName, methodParams);

            Object result = method.invoke(imlpBean, args);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(result);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Object getImpl(String inferfaceName) {
        if (HelloService.class.getName().equals(inferfaceName)) {
            return new HelloServiceImpl();
        }
        throw new RuntimeException(inferfaceName + "::错误!!");
    }
}
