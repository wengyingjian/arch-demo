package com.wengyingjian.demo.soa.zookeeper;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;

import java.util.List;

/**
 * Created by wengyingjian on 16/3/10.
 */
public class ZkClientTest {


    private static String serverList = "120.27.97.242:2181";

    public static void main(String[] args) {
        String path = "/root";
        Object data = "hello zkClient";
        CreateMode acl = CreateMode.EPHEMERAL;

        ZkClient zkClient = new ZkClient(serverList);

        //监听path路径下子节点变化
        zkClient.subscribeChildChanges(path, new MyChildChangeListener());
        //监听path路径下数据变化
        zkClient.subscribeDataChanges(path, new MyDataChangeListener());
        //监听连接的状态
        zkClient.subscribeStateChanges(new MyStateChangeListener());

        //删除节点
        boolean isDeleted = zkClient.delete(path);
        System.out.println("删除节点[" + path + "]返回值:" + isDeleted);

        //创建节点
        zkClient.createPersistent(path);
        System.out.println("创建了父节点:" + path);

        //创建子节点
        String createdPath = zkClient.create(path + "/child", data, acl);
        String createdPath2 = zkClient.create(path + "/child2", data + "2", acl);
        System.out.println("创建了子节点:" + createdPath);
        System.out.println("创建了子节点:" + createdPath2);


        //获得子节点
        List<String> children = zkClient.getChildren(path);
        System.out.println("获得子节点[" + path + "]:" + children);

        //获得子节点个数
        int childCount = zkClient.countChildren(path);
        System.out.println("查询:子节点个数=" + childCount);

        //判断节点是否存在
        boolean existsChild = zkClient.exists(path);
        System.out.println("节点[" + path + "]存在状态:" + existsChild);

        //写入数据
        zkClient.writeData(path + "/child", "new hello world");
        System.out.println("节点[" + path + "/child]写入数据:\"new hello world\"");

        //读取节点数据
        Object obj = zkClient.readData(path + "/child");
        System.out.println("读取到节点[" + path + "/child]数据:" + obj);

        //删除节点
        isDeleted = zkClient.delete(path + "/child");
        System.out.println("删除节点[" + path + "/child]返回值:" + isDeleted);


    }

    /**
     * 子节点状态发生变化监听器
     */
    static class MyChildChangeListener implements IZkChildListener {

        /**
         * 当指定路径的子节点发生变化时,会调用以下方法
         *
         * @param parentPath
         * @param currentChilds
         * @throws Exception
         */
        @Override
        public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {

        }
    }

    /**
     * 节点数据发生变化监听器
     */
    static class MyDataChangeListener implements IZkDataListener {

        @Override
        public void handleDataChange(String dataPath, Object data) throws Exception {

        }

        @Override
        public void handleDataDeleted(String dataPath) throws Exception {

        }
    }

    /**
     * 连接状态发生变化监听器
     */
    static class MyStateChangeListener implements IZkStateListener {

        /**
         * zookeeper连接状态发生变化的时候会调用以下方法
         *
         * @param state
         * @throws Exception
         */
        @Override
        public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {

        }

        /**
         * 当zookeeper旧的session过期了,新的session创建的时候会调用以下方法.
         * <p>
         * 需要做的:
         * 重建 EPHEMERAL 模式的连接
         *
         * @throws Exception
         * @see CreateMode#EPHEMERAL
         */
        @Override
        public void handleNewSession() throws Exception {

        }

        /**
         * 当session无法被重新创建(重新创建失败)的时候调用以下的方法
         * <p>
         * 需要做的:
         * 做好连接失败异常处理,比如:重新建立连接,报告异常...
         *
         * @param error
         * @throws Exception
         */
        @Override
        public void handleSessionEstablishmentError(Throwable error) throws Exception {

        }
    }
}
