package cn.lzheng.rpc.serializer;

import cn.lzheng.rpc.entity.RpcRequest;
import cn.lzheng.rpc.enumeration.SerializerCode;
import cn.lzheng.rpc.exception.SerializeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @ClassName JsonSerializer
 * @Author 6yi
 * @Date 2021/5/7 13:29
 * @Version 1.0
 * @Description:
 */


public class JsonSerializer implements CommonSerializer{

    private static final Logger logger = LoggerFactory.getLogger(JsonSerializer.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            logger.error("序列化时有错误发生:", e);
            throw new SerializeException("序列化时有错误发生");
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try {
            Object obj = objectMapper.readValue(bytes, clazz);
            if (obj instanceof RpcRequest) {
                obj = handleRequest(obj);
            }
            return obj;
        } catch (IOException e) {
            logger.error("序列化时有错误发生:", e);
            throw new SerializeException("序列化时有错误发生");
        }
    }

    /*
    这里由于使用JSON序列化和反序列化Object数组，无法保证反序列化后仍然为原实例类型
    需要重新判断处理
 */
    private Object handleRequest(Object obj) throws IOException {
        RpcRequest rpcRequest = (RpcRequest) obj;
        for (int i = 0; i < rpcRequest.getArgsType().length; i++) {
            Class<?> clazz = rpcRequest.getArgsType()[i];
            if (!clazz.isAssignableFrom(rpcRequest.getArgs()[i].getClass())) {
                byte[] bytes = objectMapper.writeValueAsBytes(rpcRequest.getArgs()[i]);
                rpcRequest.getArgs()[i] = objectMapper.readValue(bytes, clazz);
            }
        }
        return rpcRequest;
    }


    @Override
    public int getCode() {
        return SerializerCode.valueOf("JSON").getCode();
    }
}
