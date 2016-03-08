package com.wengyingjian.demo.soa.serial;

import com.wengyingjian.demo.util.JsonUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * @see JdkSerial
 * Created by wengyingjian on 16/3/8.
 */
public class HessianSerialTest {

    private HessianSerial serial;

    @Before
    public void init() {
        serial = new HessianSerial();
    }

    @Test
    public void test() {
        testHessian();
    }

    public void testHessian() {
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

    @Test
    public void testSpeed() {


        HessianSerialTest hTest = new HessianSerialTest();
        hTest.init();
        JdkSerialTest jTest = new JdkSerialTest();
        jTest.init();

        long start1 = System.currentTimeMillis();
        double count = 2e10;
        System.out.println(count);
        while (count-- > 0) {
            hTest.test();
        }
        long end1 = System.currentTimeMillis();
        long start2 = end1;
        count = 2e10;
        while (count-- > 0) {
            jTest.test();
        }
        long end2 = System.currentTimeMillis();

        System.out.println("hessian耗时:"+(end1-start1)+",jdk耗时:"+(end2-start2));

    }
}
