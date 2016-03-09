package com.wengyingjian.demo.soa.rpc.tcp.v1;

import com.wengyingjian.demo.soa.rpc.tcp.service.HelloService;
import com.wengyingjian.demo.soa.rpc.tcp.service.impl.HelloServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * 基于tcp的远程调用:服务端
 * Created by wengyingjian on 16/3/9.
 */
public class TcpServer {


    //--------------v1.0

    /**
     * 远程调用服务端功能实现
     */
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

    /**
     * 方法具体调用
     *
     * @param infterfaceName
     * @param methodName
     * @param methodParams
     * @param methodArgs
     * @return
     */
    public Object invoke(String infterfaceName, String methodName, Class<?>[] methodParams, Object[] methodArgs) {
        Object imlpBean = getImpl(infterfaceName);
        try {
            Method method = Class.forName(infterfaceName).getMethod(methodName, methodParams);
            Object result = method.invoke(imlpBean, methodArgs);
            return result;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
