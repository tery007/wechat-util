package com.meihaofenqi.utils.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/19
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JedisConfig {

    private String host;
    private String database;
    private int    port;
    private String password;
    private int    timeout;
    private int    maxIdle;
    private int    minIdle;
    private long   maxWaitMillis;

    private JedisPool jedisPool;

    public static JedisPool init(JedisConfig config) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(config.getMaxIdle());
        jedisPoolConfig.setMinIdle(config.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(config.getMaxWaitMillis());
        jedisPoolConfig.setJmxEnabled(true);
        return new JedisPool(jedisPoolConfig, config.getHost(), config.getPort(), config.getTimeout(), config.getPassword());
    }

    /**
     * 开发者本地redis连接池
     */
    public static RedisUtil localRedisTester() {
        JedisConfig c = JedisConfig.builder()
                .host("127.0.0.1")
                .port(6379)
                .password("12345678")
                .build();
        JedisPool pool = JedisConfig.init(c);
        return new RedisUtil(pool);
    }
}
