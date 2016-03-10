## 各种demo

### com.wengyingjian.demo.soa.serial
**com.wengyingjian.demo.soa.serial.JdkSerial**   
 Jdk自带的序列化(Serializable)  
**com.wengyingjian.demo.soa.serial.HessianSerial**  
  hessian序列化

### com.wemngyingjian.demo.soa.rpc
**com.wemngyingjian.demo.soa.rpc.tcp**  
基于tcp实现rpc调用  
**com.wemngyingjian.demo.soa.rpc.http**  
基于http实现rpc调用  

### com.wemngyingjian.demo.soa.zookeeper  
**com.wemngyingjian.demo.soa.zookeeper.ZkTest**  
zookeeper节点增删改查基本功能  
  
### com.wengyingjian.demo.soa.zookeeper.loadbalance  
基于zookeeper实现负载均衡  
**com.wengyingjian.demo.soa.zookeeper.loadbalance.ZkServerRegistry**  
zookeeper service/host(ip)注册  
**com.wengyingjian.demo.soa.zookeeper.loadbalance.ZkLoadBalance**  
zookeeper 负载均衡 service host(ip)获取 接口  
**com.wengyingjian.demo.soa.zookeeper.loadbalance.DefaultZkLoadBalance**  
ZkLoadBalance接口默认实现,子类可通过重写doLoadBalance方法来实现不同的负载均衡算法  