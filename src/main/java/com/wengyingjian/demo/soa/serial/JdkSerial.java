package com.wengyingjian.demo.soa.serial;


import java.io.*;

/**
 * Java自带序列化demo
 * Created by wengyingjian on 16/3/8.
 */
public class JdkSerial {


    /**
     * 序列化:
     * 1.判断接口实现
     * 2.序列化
     *
     * @param obj
     * @return
     */
    public byte[] serial(Object obj) {
        if (!(obj instanceof Serializable)) {
            throw new RuntimeException("未实现Serializable接口");
        }

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(obj);

            return byteOut.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 反序列化
     * @param bytes 序列化后的字节数字
     * @param clazz 反序列化后的目标对象类型
     * @param <T>
     * @return
     */
    public <T> T unSerial(byte[] bytes, Class<T> clazz) {
        if (!isImplOfSeria(clazz)) {
            throw new RuntimeException("未实现Serializable接口");
        }
        if (bytes == null) {
            throw new RuntimeException("序列化的数组为空");
        }

        ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream objIn = new ObjectInputStream(byteIn);

            return clazz.cast(objIn.readObject());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();

        }
    }

    /**
     * 判断clazz是否为Serializable实现类
     *
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> boolean isImplOfSeria(Class<T> clazz) {
        if (clazz == null) {
            return false;
        }
        Class[] classes = clazz.getInterfaces();
        for (Class cla : classes) {
            if (cla == Serializable.class) {
                return true;
            }
        }
        return false;
    }
}
