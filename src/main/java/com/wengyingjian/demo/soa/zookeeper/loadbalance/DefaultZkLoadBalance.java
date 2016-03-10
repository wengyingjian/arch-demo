package com.wengyingjian.demo.soa.zookeeper.loadbalance;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * zookeeper负载均衡选择器
 * <p>
 * Created by wengyingjian on 16/3/10.
 */
public class DefaultZkLoadBalance implements ZkLoadBalance {

    private static Logger logger = LoggerFactory.getLogger(DefaultZkLoadBalance.class);

    private String zkServerList = "120.27.97.242:2181";
    private String rootPath = "/configcenter";
    /**
     * key=serviceName,value=serverList
     */
    Map<String, List<String>> serviceServersMapping = new ConcurrentHashMap<>();


    public DefaultZkLoadBalance() {
        this(null, null);
    }

    /**
     * @param zkServerList zookeeper服务器
     * @param rootPath
     */
    public DefaultZkLoadBalance(String zkServerList, String rootPath) {
        super();
        if (zkServerList != null) {
            this.zkServerList = zkServerList;
        }
        if (rootPath != null) {
            this.rootPath = rootPath;
        }
        logger.info("DefaultZkLoadBalance初始化,使用配置:zkServerList=[{}],rootPath=[{}]", zkServerList, rootPath);
        init();
    }

    /**
     * 初始化操作
     */
    private void init() {

        //1.新建zkClient对象
        ZkClient zkClient = new ZkClient(zkServerList);
        //2.根据root路径获取路径下所有的 服务名称
        List<String> serviceNameList = zkClient.getChildren(rootPath);
        logger.info("获取到[{}]目录下的子节点(服务)如下:{}", rootPath, serviceNameList);
        //3.遍历所有的服务:
        for (String serviceName : serviceNameList) {
            String currentPath = rootPath + "/" + serviceName;
            //3.1.获取每个服务的host列表信息,缓存起来
            List<String> serverList = zkClient.getChildren(currentPath);
            logger.info("服务[{}]可用的ip(host)如下:{}", serviceName, serverList);
            serviceServersMapping.put(serviceName, serverList);
            //3.2.注册事件监听,当该服务的子节点(也就是ip)发生变化时,更新至最新的ip(host)列表
            zkClient.subscribeChildChanges(currentPath, new DefaultIzkChildListener(serviceServersMapping));
        }

    }

    /**
     * @param serviceName
     * @return
     */
    @Override
    public String routing(String serviceName) {
        List<String> serverList = serviceServersMapping.get(serviceName);
        if (serverList == null) {
            throw new RuntimeException("服务名称不存在:" + serviceName);
        }
        return this.doLoadBalance(serverList);
    }


    /**
     * 负载均衡算法实现(默认实现)
     *
     * @param serverList 服务器列表
     * @return 应该访问的服务器ip(host)
     */
    protected String doLoadBalance(List<String> serverList) {
        if (CollectionUtils.isEmpty(serverList)) {
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(serverList.size());
        return serverList.get(index);
    }

    private static class DefaultIzkChildListener implements IZkChildListener {

        private Map<String, List<String>> serviceServersMapping;

        /**
         * @param serviceServersMapping
         */
        DefaultIzkChildListener(Map<String, List<String>> serviceServersMapping) {
            this.serviceServersMapping = serviceServersMapping;
        }

        /**
         * 更新该服务最新的所有服务器ip(host)
         *
         * @param parentPath    父节点路径,在此处即 rootPath/serviceName/
         * @param currentChilds 当前所有的子节点,即所有的ip(host)
         * @throws Exception
         * @see IZkChildListener#handleChildChange(String, List)
         */
        @Override
        public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
            logger.info("监听到parentPath=[{}]下的子节点发生变化,新的子节点(ip/host)如下:{}", parentPath, currentChilds);
            serviceServersMapping.put(parentPath, currentChilds);
        }
    }

}
