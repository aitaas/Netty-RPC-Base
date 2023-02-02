package com.example.netty.rpc;

import com.example.netty.rpc.entity.User;
import com.example.netty.rpc.registry.Registry;
import com.example.netty.rpc.registry.RegistryConfig;
import com.example.netty.rpc.registry.zk.ZookeeperRegistry;
import com.example.netty.rpc.serializer.kryo.KryoSerializer;
import com.example.netty.rpc.service.TestService;
import com.example.netty.rpc.transport.NettyConfig;
import com.example.netty.rpc.transport.client.NettyClient;
import com.example.netty.rpc.transport.client.ResponseFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ClientTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientTestApplication.class);
    }

    @RestController
    public static class TestController{
        @Autowired
        TestService testService;
        @GetMapping("user")
        public String user(){

            User user = testService.getUser(1, "demo", "demo");
            return user.toString();
        }

        @GetMapping("update")
        public String update(){
            int update = testService.updateUser(new User(1,"demo","demo"));
            return Integer.toString(update);
        }

        @GetMapping("async")
        public String asyncTest(){
            String msg = "before_";
            ResponseFuture<String> async = testService.async();
            try {
                String value = async.get();
                msg += value;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return msg;
        }
    }

    @Bean
    public Registry registry(){
        RegistryConfig config = RegistryConfig.builder()
                .connectionTimeout(500)
                .timeout(500)
                .ephemeral(true)
                .address("192.168.20.130:2181").build();
        Registry registry = new ZookeeperRegistry(config);
        return registry;
    }


    @Bean
    public NettyClient nettyClient(){
        NettyConfig config = NettyConfig.clientBuilder()
                .port(8027)
                .serializer(KryoSerializer.class)
                .requestTimeout(5000)
                .build();
        NettyClient nettyClient = new NettyClient(config);
        return nettyClient;
    }
}
