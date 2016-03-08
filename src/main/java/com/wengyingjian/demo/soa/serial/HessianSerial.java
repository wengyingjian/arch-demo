package com.wengyingjian.demo.soa.serial;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.*;

/**
 * Hessian 序列化
 * Created by wengyingjian on 16/3/8.
 */
public class HessianSerial {


    /**
     * 序列化:
     *
     * @param obj
     * @return
     */
    public byte[] serial(Object obj) {

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            HessianOutput objOut = new HessianOutput(byteOut);
            objOut.writeObject(obj);

            return byteOut.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 反序列化
     *
     * @param bytes 序列化后的字节数字
     * @param clazz 反序列化后的目标对象类型
     * @param <T>
     * @return
     */
    public <T> T unSerial(byte[] bytes, Class<T> clazz) {
        if (bytes == null) {
            throw new RuntimeException("序列化的数组为空");
        }

        ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
        try {
            HessianInput objIn = new HessianInput(byteIn);

            return clazz.cast(objIn.readObject());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
