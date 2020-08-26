# wechat-util

## 实现功能：
- 自带redis缓存，存储access_token和js_token，使用者只需配置缓存池参数
- 实现菜单、客服文本消息、access_token、js_ticket、url授权包装、获取用户信息等功能
##. 使用方式
###1、 引入jar包：
`<dependency>
  <groupId>com.meihaofenqi</groupId>
  <artifactId>wechat-util</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>`
###2、 配置相对微信账号的配置
`
ApiConfig ac = ApiConfig.builder().appId("xxx").appSecret("xxx").build();
ApiConfigKit.putApiConfig(ac);
`
###3、 根据appId获取对应的配置
`
ApiConfig conf = ApiConfigKit.getApiConfig(APPID_TEST);
`
###4、 初始化jedis缓存池
####-调用JedisConfig的init方法
` public static JedisPool init(JedisConfig config) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(config.getMaxIdle());
        jedisPoolConfig.setMinIdle(config.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(config.getMaxWaitMillis());
        jedisPoolConfig.setJmxEnabled(true);
        return new JedisPool(jedisPoolConfig, config.getHost(), config.getPort(), config.getTimeout(), config.getPassword());
    }
`
####-构造一个缓存对象RedisUtil：
`RedisUtil redisUtil = new RedisUtil(pool);
 `
 ####-将redisUtil设为全局使用：
 `
 ApiConfigKit.initCache(redisUtil);
 `
 ####-获取缓存对象：
`RedisUtil redis = ApiConfigKit.getCache();
`
# 使用示例：
### 详见测试用例
