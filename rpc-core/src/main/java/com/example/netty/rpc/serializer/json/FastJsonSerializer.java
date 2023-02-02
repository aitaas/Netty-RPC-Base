package com.example.netty.rpc.serializer.json;

import com.example.netty.rpc.exception.SerializationException;
import com.example.netty.rpc.serializer.RpcSerializer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;

public class FastJsonSerializer implements RpcSerializer {
    public FastJsonSerializer() {

    }

    @Override
    public <T extends Serializable> byte[] encode(T obj) throws SerializationException {
        return JSON.toJSONBytes(obj, SerializerFeature.SkipTransientField, SerializerFeature.WriteClassName);
    }

    @Override
    public <T extends Serializable> T decode(byte[] bytes, Class<T> classz) throws SerializationException {
        return JSON.parseObject(new String(bytes), classz, Feature.SupportAutoType);
    }

    @Override
    public byte serializerTypeCode() {
        return 1;
    }
}
