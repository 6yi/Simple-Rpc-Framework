package cn.lzheng.rpc.transport.JDKSocket.utils;

import cn.lzheng.rpc.entity.RpcRequest;
import cn.lzheng.rpc.entity.RpcResponse;
import cn.lzheng.rpc.enumeration.PackageType;
import cn.lzheng.rpc.enumeration.RpcError;
import cn.lzheng.rpc.exception.RpcException;
import cn.lzheng.rpc.serializer.CommonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName RpcObjectReader
 * @Author 6yi
 * @Date 2021/4/27 22:39
 * @Version 1.0
 * @Description:
 */


public class RpcObjectReader {

    private static final Logger logger = LoggerFactory.getLogger(RpcObjectReader.class);
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    public static Object readObject(InputStream in) throws IOException {
        byte[] numberBytes = new byte[4];
        in.read(numberBytes);
        int magicNumber = bytesToInt(numberBytes);
        if(magicNumber != MAGIC_NUMBER){
            logger.error("不能识别的协议包:{}",magicNumber);
            throw new RpcException(RpcError.UNKNOWN_PROTOCOL);
        }
        in.read(numberBytes);
        Class<?> packageClass=null;
        int packageCode = bytesToInt(numberBytes);
        if (packageCode == PackageType.REQUEST_PACK.getCode())
            packageClass = RpcRequest.class;
        else if(packageCode == PackageType.RESPONSE_PACK.getCode())
            packageClass = RpcResponse.class;
        else{
            logger.error("不识别的数据包: {}", packageCode);
            throw new RpcException(RpcError.UNKNOWN_PACKAGE_TYPE);
        }
        in.read(numberBytes);
        int serializerCode = bytesToInt(numberBytes);
        CommonSerializer serializer = CommonSerializer.getByCode(serializerCode);
        if(serializer == null){
            logger.error("不识别的反序列化器: {}", serializerCode);
            throw new RpcException(RpcError.UNKNOWN_SERIALIZER);
        }

        in.read(numberBytes);
        int packageLen = bytesToInt(numberBytes);
        byte[] data = new byte[packageLen];
        in.read(data);
        return serializer.deserialize(data,packageClass);
    }

    public static int bytesToInt(byte[] src) {
        int value;
        value = ((src[0] & 0xFF)<<24)
                |((src[1] & 0xFF)<<16)
                |((src[2] & 0xFF)<<8)
                |(src[3] & 0xFF);
        return value;
    }

}
