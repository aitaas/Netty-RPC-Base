package com.example.netty.rpc.codec;

import com.example.netty.rpc.enums.MessageType;

/**
 * rpc心跳包
 */
public final class RpcBeat implements RpcMessage {
    private RpcBeat(){}
    private static final RpcBeat instance = new RpcBeat();
    public static RpcBeat instance(){
        return instance;
    }
    @Override
    public MessageType getMessageType() {
        return MessageType.RPC_BEAT;
    }
}
