package com.meihaofenqi.api;

import com.meihaofenqi.wechat.sdk.base.ApiConfig;
import com.meihaofenqi.wechat.sdk.base.ApiConfigKit;
import com.meihaofenqi.wechat.sdk.plugin.redis.JedisConfig;
import com.meihaofenqi.wechat.sdk.plugin.redis.RedisUtil;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/19
 **/
public class CommonApp {

    /** 测试微信号 */
    public static String    APPID_TEST     = "wxbe822210e70823fd";
    public static String    APPSECRET_TEST = "e10501e01aef7c796380c024228877d5";
    public static ApiConfig testConf;

    static {
        ApiConfig ac = ApiConfig.builder().appId(APPID_TEST).appSecret(APPSECRET_TEST).build();
        ApiConfigKit.putApiConfig(ac);
        testConf = ApiConfigKit.getApiConfig(APPID_TEST);
    }


    /**
     * 美好的研发宝宝本地测试初始化配置：
     * 设置app微信参数、开启redis缓存连接池
     */
    public static void localTestInit() {
        RedisUtil redisUtil = JedisConfig.localRedisTester();
        ApiConfigKit.initCache(redisUtil);
    }
}
