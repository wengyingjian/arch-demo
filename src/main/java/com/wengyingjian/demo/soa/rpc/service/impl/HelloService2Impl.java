package com.wengyingjian.demo.soa.rpc.service.impl;

import com.wengyingjian.demo.soa.rpc.service.HelloService2;

/**
 * Created by wengyingjian on 16/3/9.
 */
public class HelloService2Impl implements HelloService2 {
    @Override
    public String hi(String name) {
        return "hi," + name;
    }
}
