package com.wengyingjian.demo.soa.zookeeper.loadbalance;

import org.junit.Test;

/**
 * Created by wengyingjian on 16/3/11.
 */
public class LoadBalanceTest {

    private ZkLoadBalance zkLoadBalance;
    private ZkServerRegistry zkServerRegistry;


    //首先注册service
    @Test
    public void register() {
        registryInit();
        //这里只是模拟,一般都是在本地测试,所以指定serviceName,IP(host)即本地的地址
        zkServerRegistry.register("testService1");
        zkServerRegistry.register("testService2");
    }


    @Test
    public void loadBanance() {
        loadBalanceInit();
        String host = zkLoadBalance.routing("testService1");
        System.out.println("getHost:" + host);
    }

    private void registryInit() {
        zkServerRegistry = new ZkServerRegistry();
    }

    private void loadBalanceInit() {
        zkLoadBalance = new DefaultZkLoadBalance();
    }
}
