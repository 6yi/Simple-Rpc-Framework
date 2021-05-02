package cn.lzheng.rpc.utils;

import cn.lzheng.rpc.entity.RedisInstance;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName RedisUtil
 * @Author 6yi
 * @Date 2021/4/26 15:08
 * @Version 1.0
 * @Description:  Redis注册工具
 */

public class RedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    
    private static String SERVER_ADDR="127.0.0.1";

    private static Jedis jedis;

    private static Set<String> serviceNames;

    private static InetSocketAddress address;

    private RedisUtil() {
    }

    private static void init(){
        if(jedis==null){
            synchronized (RedisUtil.class){
                if(jedis==null){
                    jedis=getJedis();
                    serviceNames=new HashSet<>();
                }
            }
        }
    }

    public static void registerService(String serviceName, InetSocketAddress address){

        try{
            init();
            ObjectMapper objectMapper = new ObjectMapper();
            RedisInstance redisInstance = RedisInstance
                    .builder()
                    .host(address.getHostString())
                    .port(address.getPort())
                    .build();

            jedis.hset(serviceName,
                    address.getHostString()+":"+address.getPort(),
                    objectMapper.writeValueAsString(redisInstance));

            serviceNames.add(serviceName);
            RedisUtil.address = address;
        }catch (Exception e){
            logger.error("注册redis失败"+e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @author 6yi
     * @date 2021/4/26
     * @return
     * @Description 获取实例
     **/
    public static List<RedisInstance> getAllInstance(String serviceName){
        init();
        return jedis.hgetAll(serviceName).values().stream().map(json -> {
            try {
                return new ObjectMapper().readValue(json, RedisInstance.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
    }


    public static Jedis getJedis()  {
        try{
            Jedis jedis = new Jedis(SERVER_ADDR);
            jedis.auth("lzheng");
            jedis.ping();
            return jedis;
        }catch (Exception e){
            logger.error("连接到Redis时有错误发生: ", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @author 6yi
     * @date 2021/4/26
     * @return
     * @Description 注销服务
     **/
    public static void clearRegistry(){
        if(!serviceNames.isEmpty() && address != null ){
            String host = address.getHostString();
            int port = address.getPort();
            Iterator<String> iterator = serviceNames.iterator();
            while(iterator.hasNext()){
                String serviceName = iterator.next();
                try{
                    logger.error("注销服务 {}", serviceName);
                    jedis.hdel(serviceName,host+":"+port);
                }catch (Exception e){
                    logger.error("注销服务 {} 失败", serviceName, e);
                }
            }
        }
    }

}
