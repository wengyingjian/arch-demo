package com.wengyingjian.demo.soa.zookeeper.loadbalance;

/**
 * Created by wengyingjian on 16/3/10.
 */
public interface ZkLoadBalance {
    /**
     * 根据serviceName获取可以访问的主机ip(host)
     *
     * @param serviceName
     * @return ip(host)
     */
    String routing(String serviceName);

}
