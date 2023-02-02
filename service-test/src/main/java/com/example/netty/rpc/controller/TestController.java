package com.example.netty.rpc.controller;


import com.example.netty.rpc.registry.Registry;
import com.example.netty.rpc.service.TestService;
import com.example.netty.rpc.transport.server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private TestService testService;
    @Autowired
    private Registry registry;
    @Autowired
    private NettyServer nettyServer;

    @GetMapping("/startNetty")
    public String startNetty(){
        nettyServer.start();
        return "ok";
    }

    @GetMapping("/stopNetty")
    public String stopNetty(){
        nettyServer.stop();
        return "ok";
    }

}
