package com.meihaofenqi.api;


import com.meihaofenqi.base.ApiConfig;
import com.meihaofenqi.base.ApiConfigKit;
import com.meihaofenqi.utils.redis.RedisUtil;
import com.meihaofenqi.utils.redis.JedisConfig;

/**
 * @author wanglei
 * @description:测试基本配置
 * @date Created on 2020/8/19
 **/
public class TestBase {

    /** 测试微信号 */
    public static final String APPID_TEST     = "wxbe822210e70823fd";
    public static final String APPSECRET_TEST = "e10501e01aef7c796380c024228877d5";
    public static final String TOKEN          = "QFrmM3uMyoKXTCGBnythHO";

    public static ApiConfig testConf;

    static {
        ApiConfig ac = ApiConfig.builder().appId(APPID_TEST).appSecret(APPSECRET_TEST).token(TOKEN).build();
        ApiConfigKit.putApiConfig(ac);
        testConf = ApiConfigKit.getApiConfig(APPID_TEST);
    }


    /**
     * 本地测试初始化配置：
     * 设置app微信参数、开启redis缓存连接池
     */
    public static void localTestInit() {
        RedisUtil redisUtil = JedisConfig.localRedisTester();
        ApiConfigKit.initCache(redisUtil);
    }
}
