package com.wengyingjian.demo.soa.serial;

import com.wengyingjian.demo.util.JsonUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * @see JdkSerial
 * Created by wengyingjian on 16/3/8.
 */
public class JdkSerialTest {

    private JdkSerial serial;

    @Before
    public void init() {
        serial = new JdkSerial();
    }

    @Test
    public void test() {

        testJdk();

    }

    private void testJdk() {
        //原始
        TestBean testBean = new TestBean();
        testBean.setName("andy");
        testBean.setType(123);
//        System.out.println("before:" + JsonUtil.getJsonFromObject(testBean));

        //序列化
        byte[] tBytes = serial.serial(testBean);
//        System.out.println("tBytes:" + Arrays.toString(tBytes));

        //反序列化
        TestBean result = serial.unSerial(tBytes, TestBean.class);
//        System.out.println("after:" + JsonUtil.getJsonFromObject(result));
    }
}
