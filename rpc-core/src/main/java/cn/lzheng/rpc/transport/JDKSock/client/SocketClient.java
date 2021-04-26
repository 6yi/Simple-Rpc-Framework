package cn.lzheng.rpc.transport.JDKSock.client;

import cn.lzheng.rpc.entity.RpcRequest;
import cn.lzheng.rpc.transport.RpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @ClassName SocketClient
 * @Author 6yi
 * @Date 2021/4/26 1:14
 * @Version 1.0
 * @Description:  使用JDKsocket方式进行远程调用
 */


public class SocketClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private String host;
    private int port;

    public SocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try(Socket socket=new Socket(host, port)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        } catch (Exception e) {
           logger.error("调用错误:"+e);
            return null;
        }
    }

}
