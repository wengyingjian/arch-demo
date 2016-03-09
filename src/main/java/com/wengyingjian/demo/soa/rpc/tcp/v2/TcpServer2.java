package com.wengyingjian.demo.soa.rpc.tcp.v2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by wengyingjian on 16/3/9.
 */
public class TcpServer2 {

    private static final int port = 10001;

    //------------v2.0

    /**
     * 进一步封装,更加灵活
     */
    public void listen2() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                doServer(ois, oos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void doServer(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            String interfaceName = ois.readUTF();
            String methodName = ois.readUTF();
            Class<?>[] methodParams = (Class<?>[]) ois.readObject();
            Object[] args = (Object[]) ois.readObject();

            oos.writeObject(invoke2(interfaceName, methodName, methodParams, args));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Object invoke2(String infterfaceName, String methodName, Class<?>[] methodParams, Object[] methodArgs) {
        System.out.println(String.format("invoke: [interface=%s,method=%s,methodParam=%s,methodArgs=%s]",
                infterfaceName, methodName,
                Arrays.toString(methodParams),
                Arrays.toString(methodArgs)));

        Object imlpBean = doGetImplBean(infterfaceName);
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

    private Object doGetImplBean(String infterfaceName) {
        // 此处简单起见
        //com.wengyingjian.demo.soa.rpc.tcp.service.HelloService -->  com.wengyingjian.demo.soa.rpc.tcp.service.impl.HelloServiceImpl
        String prefix = infterfaceName.substring(0, infterfaceName.lastIndexOf("."));
        String suffix = infterfaceName.substring(infterfaceName.lastIndexOf("."), infterfaceName.length()) + "Impl";
        String implName = prefix + ".impl" + suffix;

        try {
            return Class.forName(implName).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("实现类初始化失败");
    }
}
