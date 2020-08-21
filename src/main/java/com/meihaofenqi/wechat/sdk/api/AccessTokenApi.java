package com.meihaofenqi.wechat.sdk.api;

import com.meihaofenqi.wechat.sdk.base.AccessToken;
import com.meihaofenqi.wechat.sdk.base.ApiConfig;
import com.meihaofenqi.wechat.sdk.base.ApiConfigKit;
import com.meihaofenqi.wechat.sdk.base.CacheKeyBuilder;
import com.meihaofenqi.wechat.sdk.base.ParaMap;
import com.meihaofenqi.wechat.sdk.plugin.redis.RedisUtil;
import com.meihaofenqi.wechat.sdk.utils.HttpUtils;
import com.meihaofenqi.wechat.sdk.utils.RetryUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author wanglei
 * @description: 认证并获取 access_token
 * @date Created on 2020/8/18
 **/
@Slf4j
public class AccessTokenApi {

    private static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

    private static AccessToken accessToken = null;

    /**
     * 从缓存中获取 access token，如果未取到或者 access token 不可用则先更新再获取
     *
     * @return AccessToken accessToken
     */
    public static AccessToken getAccessToken(String appId) {
        if (accessToken != null) {
            return accessToken;
        }
        ApiConfig ac = ApiConfigKit.getApiConfig(appId);
        AccessToken result = getAvailableAccessToken(ac);
        if (result == null) {
            synchronized (ApiConfig.class) {
                result = getAvailableAccessToken(ac);
                if (result == null) {
                    result = refreshAccessToken(ac);
                }
            }
        }
        return result;
    }

    /**
     * 从缓存中获取token
     */
    public static AccessToken getAvailableAccessToken(ApiConfig ac) {
        RedisUtil redis = ApiConfigKit.getCache();
        String json = redis.get(CacheKeyBuilder.accessTokenKey(ac.getAppId()));
        if (StringUtils.isNotBlank(json)) {
            AccessToken token = new AccessToken(json);
            if (token.isAvailable()) {
                return token;
            }
        }
        return null;
    }

    /**
     * 直接获取 accessToken 字符串，方便使用
     *
     * @return String accessToken
     */
    public static String getAccessTokenStr(ApiConfig cf) {
        return getAccessToken(cf.getAppId()).getAccessToken();
    }

    /**
     * 无条件强制更新 access token 值，不再对 cache 中的 token 进行判断
     *
     * @param ac ApiConfig
     * @return AccessToken
     */
    public static AccessToken refreshAccessToken(ApiConfig ac) {
        String appId = ac.getAppId();
        String appSecret = ac.getAppSecret();
        final Map<String, String> queryParas = ParaMap.create("appid", appId).put("secret", appSecret).getData();

        // 最多三次请求
        AccessToken result = RetryUtils.retryOnException(3, () -> {
            String json = HttpUtils.get(ACCESS_TOKEN_URL, queryParas);
            return new AccessToken(json);
        }, ac);
        if (null != result) {
            RedisUtil redis = ApiConfigKit.getCache();
            int expire = result.cacheExpireTime();
            //设置缓存并加过期时间
            redis.set(CacheKeyBuilder.accessTokenKey(ac.getAppId()), result.cacheJson(), expire);
        }
        return result;
    }


}
