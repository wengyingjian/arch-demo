package com.wengyingjian.demo.soa.zookeeper.loadbalance;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

/**
 * zookeeper集群服务注册
 * Created by wengyingjian on 16/3/10.
 */
public class ZkServerRegistry {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * zookeeper服务地址
     */
    private String serverList = "120.27.97.242:2181";
    private String rootPath = "/configcenter";//根节点路径

    private String localIp = "120.27.97.242";

    private ZkClient zkClient;


    public ZkServerRegistry() {
        this(null, null);
    }

    public ZkServerRegistry(String serverList, String rootPath) {
        super();
        if (serverList != null) {
            this.serverList = serverList;
        }
        if (rootPath != null) {
            this.rootPath = rootPath;
        }
        logger.info("ZkServerRegistry,使用配置:serverList=[{}],rootPath=[{}]", serverList, rootPath);
        init();
    }

    private void init() {
        zkClient = new ZkClient(serverList);
        if (!zkClient.exists(rootPath)) {
            zkClient.createPersistent(rootPath);
            logger.info("创建根路径[{}]", rootPath);
        }
    }

    /**
     * 注册服务
     *
     * @param serviceName
     */
    public void register(String serviceName) {

        boolean serviceExists = zkClient.exists(rootPath + "/" + serviceName);
        if (!serviceExists) {
            zkClient.createPersistent(rootPath + "/" + serviceName);//创建服务节点
            logger.info("\"名称=[{}]\"服务不存在,新创建!", serviceName);
        }

        //注册当前服务器,可以在节点的数据里面存放节点的权重
        //获取本机IP
        String ip = localIp;

        String host = rootPath + "/" + serviceName + "/" + ip;
        logger.info("新创建路径(ip/host):[{}]", host);
        //创建当前服务器节点
        zkClient.createEphemeral(host);
    }
}
