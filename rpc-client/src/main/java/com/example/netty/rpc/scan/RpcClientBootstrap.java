package com.example.netty.rpc.scan;


import com.example.netty.rpc.registry.Registry;
import com.example.netty.rpc.transport.client.ConnectionManager;
import com.example.netty.rpc.transport.client.NettyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * rpc客户端引导
 */
@Component
public class RpcClientBootstrap  implements ApplicationListener<WebServerInitializedEvent>, DisposableBean{
    private final static Logger logger = LoggerFactory.getLogger(RpcClientBootstrap.class);
    private Registry registry;
    private NettyClient client;

    public RpcClientBootstrap(Registry registry, NettyClient client) {
        this.registry = registry;
        this.client = client;
    }

    @Override
    public void destroy() {
        ConnectionManager.instance().removeAllTransporter();
        logger.info("关闭Netty服务器");
        client.stop();
        logger.info("关闭注册中心");
        registry.stop();
    }



    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        // 初始化注册中心
        logger.info("初始化注册中心");
        registry.init();
        // 启动注册中心
        logger.info("启动注册中心");
        registry.start();
        // 启动Netty服务器
        logger.info("启动NettyRPC客户端");
        client.start();
        logger.info("初始化ConnectionManager");
        ConnectionManager.instance().setRegistry(registry);
        ConnectionManager.instance().setTransporterCreator(client);
    }
}
