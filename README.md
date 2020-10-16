# wechat-util

## 实现功能：
- 自带redis缓存，存储access_token和js_token，使用者只需配置缓存池参数
- 实现微信消息、菜单、客服文本消息、access_token、js_ticket、url授权包装、获取用户信息等功能

## 使用方式
### 1、 引入jar包：
```
上传到自己到mvn仓库
<dependency>
    <groupId>xxx/groupId>
    <artifactId>wechat-util</artifactId>
    <version>1.0.1-SNAPSHOT</version>
</dependency>
```
### 2、 配置相对微信账号的配置
```
ApiConfig ac = ApiConfig.builder().appId("xxx").appSecret("xxx").build();
ApiConfigKit.putApiConfig(ac);
```
### 3、 根据appId获取对应的配置
```
ApiConfig conf = ApiConfigKit.getApiConfig(APPID_TEST);
```
### 4、 初始化jedis缓存池
```
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
```
#### -初始化缓存和微信配置
```
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
```

 ```
#### -获取缓存对象：
```
RedisUtil redis = ApiConfigKit.getCache();
```
### 5、使用示例：
##### 详见应用demo：
https://github.com/tery007/wechat-util-demo
