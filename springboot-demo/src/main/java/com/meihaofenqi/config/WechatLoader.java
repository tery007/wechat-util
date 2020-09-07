package com.meihaofenqi.config;

import com.meihaofenqi.base.ApiConfig;
import com.meihaofenqi.base.ApiConfigKit;
import com.meihaofenqi.utils.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;

/**
 * @author wanglei
 * @description：初始化缓存、微信配置
 * @date Created on 2020/9/2
 **/
@Slf4j
@Configuration
public class WechatLoader {

    @Autowired
    private JedisPool       jedisPool;
    @Autowired
    private WechatAppConfig wechatAppConfig;

    @PostConstruct
    public void load() {
        loadWeixinSdk(wechatAppConfig);
        cacheInit(jedisPool);
    }

    public void loadWeixinSdk(WechatAppConfig w) {
        ApiConfig ac = ApiConfig.builder().appId(w.getAppId()).appSecret(w.getAppSecret()).token(w.getToken()).build();
        ApiConfigKit.putApiConfig(ac);
        log.info("==> load weixin-api-config finished");
    }

    public void cacheInit(JedisPool pool) {
        RedisUtil redisUtil = new RedisUtil(pool);
        ApiConfigKit.initCache(redisUtil);
        log.info("==> load jedis cache finished");
    }
}
