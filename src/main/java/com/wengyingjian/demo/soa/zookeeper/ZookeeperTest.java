package com.wengyingjian.demo.soa.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;

/**
 * Created by wengyingjian on 16/3/9.
 *
 * @see org.apache.zookeeper.KeeperException.ConnectionLossException 客户端与其中的一台服务器socket连接出现异常,连接消失
 * <p>
 * 该异常可以通过重试进行处理,
 * 客户端会根据初始化Zookeeper时传递的服务列表,自动尝试下一个服务端节点,
 * 而在这段时间内,服务端节点变更的事件就会丢失
 * @see org.apache.zookeeper.KeeperException.SessionExpiredException 客户端的session已经超过sessionTimeout,未进行任何操作
 * <p>
 * 该异常不能通过重试进行解决,需要应用重新创建一个新的客户端(new Zookeeper),
 * 这是所有的wather和EPHEMERAL节点都将失效
 */
public class ZookeeperTest {

    private static final String HOST = "120.27.97.242";
    private static final int sessionTimeout = 6000;

    public static void main(String[] args) throws Exception {

        ZooKeeper zooKeeper = new ZooKeeper(HOST, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("process!!!-============");
                return;
            }
        });

        String path = "/root";
        byte[] data = "root data".getBytes();
        // 创建节点
//        String nodePath = createNode(zooKeeper, path, data);

        //删除节点
//        deleteNode(zooKeeper, path, -1);
        Stat stat = setData(zooKeeper, path, "hello".getBytes(), -1);
        byte[] newData = getData(zooKeeper, path, stat);
        System.out.println("newData:" + new String(newData, "utf-8"));
    }

    /**
     * 创建节点
     *
     * @param zooKeeper
     * @param path
     * @param data
     * @return path
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static String createNode(ZooKeeper zooKeeper, String path, byte[] data) throws KeeperException, InterruptedException {
        CreateMode createMode = CreateMode.PERSISTENT;
        ArrayList<ACL> acl = ZooDefs.Ids.OPEN_ACL_UNSAFE;
        return zooKeeper.create(path, data, acl, createMode);
    }

    /**
     * 添加子节点
     *
     * @param zooKeeper
     * @param superPath
     * @param subPath
     * @param data
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static String addSubNode(ZooKeeper zooKeeper, String superPath, String subPath, byte[] data) throws KeeperException, InterruptedException {
        CreateMode createMode = CreateMode.PERSISTENT;
        ArrayList<ACL> acl = ZooDefs.Ids.OPEN_ACL_UNSAFE;
        return zooKeeper.create(superPath + "/" + subPath, data, acl, createMode);
    }

    /**
     * 删除节点
     *
     * @param zooKeeper
     * @param path
     * @param version   -1 匹配所有版本
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void deleteNode(ZooKeeper zooKeeper, String path, int version) throws KeeperException, InterruptedException {
        zooKeeper.delete(path, version);
    }

    /**
     * 设置节点内容
     *
     * @param zooKeeper
     * @param path
     * @param data
     * @param version
     * @return
     * @throws KeeperException      如果找不到对于的version,抛出异常
     * @throws InterruptedException
     */
    public static Stat setData(ZooKeeper zooKeeper, String path, byte[] data, int version) throws KeeperException, InterruptedException {
        return zooKeeper.setData(path, data, version);
    }

    /**
     * 获取节点内容
     *
     * @param zooKeeper
     * @param path
     * @param stat
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static byte[] getData(ZooKeeper zooKeeper, String path, Stat stat) throws KeeperException, InterruptedException {
        return zooKeeper.getData(path, false, stat);
    }

    /**
     * 查看节点是否存在
     *
     * @param zooKeeper
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static boolean existsNode(ZooKeeper zooKeeper, String path) throws KeeperException, InterruptedException {
        return zooKeeper.exists(path, false) == null ? false : true;
    }


    /**
     * zookeeper的watcher是一次性的,每次处理完这个变化之后,需要重新注册watcher.
     * <p>
     * 所以在处理事件和重新加上watcher这段时间发生的节点状态变化无法被感知
     */
    public static class MyWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            //生成节点
            if (Event.EventType.NodeCreated == event.getType()) {
                System.out.println("生成节点");
            } else if (Event.EventType.NodeChildrenChanged == event.getType()) {
                System.out.println("修改节点的子节点");
            } else if (Event.EventType.NodeDeleted == event.getType()) {
                System.out.println("删除节点");
            } else if (Event.EventType.NodeDataChanged == event.getType()) {
                System.out.println("修改节点数据");
            }
        }
    }
}
