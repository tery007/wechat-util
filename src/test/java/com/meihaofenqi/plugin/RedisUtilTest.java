package com.meihaofenqi.plugin;

import com.meihaofenqi.wechat.sdk.plugin.redis.JedisConfig;
import com.meihaofenqi.wechat.sdk.plugin.redis.RedisUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.JedisPool;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/19
 **/
public class RedisUtilTest {

    private RedisUtil redisUtil;

    @Before
    public void before() {
        redisUtil = JedisConfig.localRedisTester();
    }

    @Test
    public void testSet() {
        String key = "key-test";
        String v = "v-test";
        redisUtil.set(key, v);
        String result = redisUtil.get(key);
        Assert.assertEquals(v, result);
    }
}
