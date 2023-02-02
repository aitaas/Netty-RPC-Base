package com.example.netty.rpc.codec;

import com.example.netty.rpc.enums.MessageType;

import java.io.Serializable;

/**
 * 消息总接口，和MessageType双向绑定
 */
public interface RpcMessage extends Serializable {
    /**
     * 获取消息类型枚举
     * @return
     */
    MessageType getMessageType();
}
