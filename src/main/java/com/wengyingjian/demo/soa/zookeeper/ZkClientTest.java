package com.wengyingjian.demo.soa.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

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

}
