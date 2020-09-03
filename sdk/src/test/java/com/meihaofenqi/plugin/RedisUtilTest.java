package com.meihaofenqi.plugin;

import com.meihaofenqi.utils.redis.JedisConfig;
import com.meihaofenqi.utils.redis.RedisUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
