package com.wengyingjian.demo.soa.rpc.tcp.service.impl;

import com.wengyingjian.demo.soa.rpc.tcp.service.HelloService;

/**
 * Created by wengyingjian on 16/3/9.
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String say(String args) {
        if ("hello".equals(args)) {
            return "hello!";
        }
        return "nihao!";
    }

}
