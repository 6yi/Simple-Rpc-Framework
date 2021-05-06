package cn.lzheng.rpc.transport.JDKSocket.server;

import cn.lzheng.rpc.entity.RpcRequest;
import cn.lzheng.rpc.entity.RpcResponse;
import cn.lzheng.rpc.handler.InvokeMethodHandler;
import cn.lzheng.rpc.serializer.CommonSerializer;
import cn.lzheng.rpc.transport.JDKSocket.utils.RpcObjectReader;
import cn.lzheng.rpc.transport.JDKSocket.utils.RpcObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


/**
 * @ClassName SocketRequestHandlerThread
 * @Author 6yi
 * @Date 2021/4/26 14:54
 * @Version 1.0
 * @Description:
 */


public class SocketRequestHandlerThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(SocketRequestHandlerThread.class);
    private Socket socket;
    private InvokeMethodHandler requestHandler;
    private CommonSerializer serializer;

    public SocketRequestHandlerThread(Socket socket, InvokeMethodHandler requestHandler, CommonSerializer serializer) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serializer = serializer;
    }

    @Override
    public void run() {
        try(InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream()){

             RpcRequest rpcRequest = (RpcRequest) RpcObjectReader.readObject(inputStream);
             Object result = requestHandler.handle(rpcRequest);
             RpcResponse<Object> successResponse = RpcResponse.success(result,rpcRequest.getRequestId());
             RpcObjectWriter.writeObject(outputStream,successResponse,serializer);

        } catch (IOException e) {
            logger.error("调用或发送时有错误发生：", e);
        }
    }

}
