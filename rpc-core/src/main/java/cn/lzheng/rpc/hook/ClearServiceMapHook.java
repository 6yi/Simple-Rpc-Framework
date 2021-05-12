package cn.lzheng.rpc.hook;

import cn.lzheng.rpc.entity.RpcInstance;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @ClassName ClearServiceMapHook
 * @Author 6yi
 * @Date 2021/5/12 21:45
 * @Version 1.0
 * @Description:
 */


public class ClearServiceMapHook {

    public static void start(Map<String, List<RpcInstance>> map){
        Timer timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(map.size()!=0){
                    map.clear();
                }
            }
        },20000,20000);
    }

}
