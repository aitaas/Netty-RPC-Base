package com.example.netty.rpc.transport.client;

import com.example.netty.rpc.ref.client.Instance;
import com.example.netty.rpc.ref.client.ServiceInstance;
import com.example.netty.rpc.registry.Registry;
import com.example.netty.rpc.transport.functional.TransporterCreator;
import com.example.netty.rpc.utils.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理连接会话
 *
 */
public class ConnectionManager {
    private final static Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    private static ConnectionManager instance = new ConnectionManager();
    /**
     * 保存了rpc服务端地址和连接的映射
     */
    private final Map<String, Transporter> transporterMapping;
    /**
     * 用于创建连接，一般是NettyClient
     */
    private TransporterCreator transporterCreator;
    /**
     * 注册中心
     */
    private Registry registry;

    private ConnectionManager() {
        this.transporterMapping = new ConcurrentHashMap<>(32);
    }

    public static ConnectionManager instance() {
        return instance;
    }

    /**
     * 创建一个新的连接
     *
     * @param address
     */
    private boolean createTransporter(String address) {
        String host = null;
        int port = 0;
        try {
            String[] split = address.split(":");
            host = split[0];
            port = Integer.parseInt(split[1]);
        } catch (Exception e) {
            logger.warn("创建链接失败，可能是因为地址格式不正确，{}", address);
        }
        if (host == null || port == 0) {
            return false;
        }
        Transporter transporter = transporterCreator.createTransporter(host, port);
        if(transporter.isAvailable()){
            this.transporterMapping.put(address, transporter);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取或创建一个新的连接
     * @param address
     * @return
     */
    public Transporter getOrCreateTransporter(String address){
        Transporter transporter = transporterMapping.get(address);
        if(transporter != null){
            return transporter;
        }
        boolean create = false;
        synchronized (this){
            transporter = transporterMapping.get(address);
            if(transporter == null){
                create = createTransporter(address);
            } else {
                create = true;
            }
        }
        return create ? transporterMapping.get(address) : null;
    }


    /**
     * 移除连接
     * @param address
     */
    public void removeTransporter(String address) {
        Transporter transporter = this.transporterMapping.remove(address);
        transporter.destroy();
    }

    /**
     * 移除所有连接
     */
    public void removeAllTransporter(){
        Set<Map.Entry<String, Transporter>> entries =
                this.transporterMapping.entrySet();
        Iterator<Map.Entry<String, Transporter>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Transporter> next = iterator.next();
            Transporter value = next.getValue();
            value.destroy();
            iterator.remove();
        }
    }


    /**
     * 从注册中心获取实例
     * @param serviceName
     * @return
     */
    public List<Instance> getInstanceForServiceName(String serviceName){
        ServiceInstance discoverInstance = registry.discover(serviceName);
        List<Instance> hosts = discoverInstance.getHosts();
        if(CollectionUtil.isEmptyCollection(hosts)){
            return Collections.emptyList();
        } else {
            return hosts;
        }
    }

    /**
     *
     * @param transporterCreator
     */
    public void setTransporterCreator(TransporterCreator transporterCreator) {
        if (this.transporterCreator != null) {
            logger.warn("TransporterCreator应该只被设置一次");
            return;
        }
        this.transporterCreator = transporterCreator;
    }

    /**
     * 设置注册中心
     * @param registry 注册中心
     */
    public void setRegistry(Registry registry) {
        if(this.registry != null){
            logger.warn("Registry应该只被设置一次");
            return;
        }
        this.registry = registry;
    }

}
