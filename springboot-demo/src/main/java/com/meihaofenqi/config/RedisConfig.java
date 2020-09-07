package com.meihaofenqi.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author wanglei
 * @description：redis缓存配置
 * @date Created on 2020/9/1
 **/
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {

    private String host;
    private String database;
    private int    port;
    private String password;
    private int    timeout;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int    maxIdle;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int    minIdle;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private long   maxWaitMillis;


    @Bean
    public JedisPool redisPoolFactory() {
        log.info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        log.info("==> JedisPool配置成功");
        return new JedisPool(jedisPoolConfig, host, port, timeout, password);
    }

}
