package com.meihaofenqi.wechat.sdk.base;

import com.meihaofenqi.wechat.sdk.exception.WechatException;
import com.meihaofenqi.wechat.sdk.plugin.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/19
 **/
@Slf4j
public class ApiConfigKit {

    private static final Map<String, ApiConfig> CFG_MAP = new ConcurrentHashMap<String, ApiConfig>();

    private static final String DEFAULT_CFG_KEY = "_default_cfg_key_";

    public static RedisUtil redisUtil;

    public static void initCache(RedisUtil r) {
        redisUtil = r;
    }

    public static RedisUtil getCache() {
        if (Objects.isNull(redisUtil)) {
            throw new WechatException("未配置缓存");
        }
        return redisUtil;
    }

    /**
     * 添加公众号配置，每个appId只需添加一次，相同appId将被覆盖。
     * 第一个添加的将作为默认公众号配置
     *
     * @param apiConfig 公众号配置
     * @return ApiConfig 公众号配置
     */
    public static ApiConfig putApiConfig(ApiConfig apiConfig) {
        if (CFG_MAP.size() == 0) {
            CFG_MAP.put(DEFAULT_CFG_KEY, apiConfig);
        }
        return CFG_MAP.put(apiConfig.getAppId(), apiConfig);
    }

    public static ApiConfig removeApiConfig(ApiConfig apiConfig) {
        return removeApiConfig(apiConfig.getAppId());
    }

    /**
     * 删除对应appId==>没有则删除默认公众号配置==>重新配置默认公众号
     *
     * @param appId
     * @return 被删除的apiConfig
     */
    public static ApiConfig removeApiConfig(String appId) {
        ApiConfig removedConfig = CFG_MAP.remove(appId);
        if (removedConfig != null) {
            ApiConfig defaultApiConfig = CFG_MAP.get(DEFAULT_CFG_KEY);

            if (defaultApiConfig.getAppId().equals(removedConfig.getAppId())) {

                CFG_MAP.remove(DEFAULT_CFG_KEY);

                ApiConfig firstApiConfig = null;
                if (CFG_MAP.size() > 0) {
                    for (Map.Entry<String, ApiConfig> entry : CFG_MAP.entrySet()) {
                        firstApiConfig = entry.getValue();
                        break;
                    }
                }

                if (firstApiConfig != null) {
                    CFG_MAP.put(DEFAULT_CFG_KEY, firstApiConfig);
                }
            }
        }

        return removedConfig;
    }


    public static ApiConfig getApiConfig(String appId) {
        log.info("appId:{}", appId);
        ApiConfig cfg = CFG_MAP.get(appId);
        if (cfg == null) {
            throw new WechatException("需事先调用 ApiConfigKit.putApiConfig(apiConfig) 将 appId对应的 ApiConfig 对象存入");
        }
        return cfg;
    }
}
