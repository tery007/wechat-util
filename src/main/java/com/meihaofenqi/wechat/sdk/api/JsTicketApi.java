package com.meihaofenqi.wechat.sdk.api;

import com.meihaofenqi.wechat.sdk.base.ApiConfig;
import com.meihaofenqi.wechat.sdk.base.ApiConfigKit;
import com.meihaofenqi.wechat.sdk.base.CacheKeyBuilder;
import com.meihaofenqi.wechat.sdk.base.JsTicket;
import com.meihaofenqi.wechat.sdk.base.ParaMap;
import com.meihaofenqi.wechat.sdk.plugin.redis.RedisUtil;
import com.meihaofenqi.wechat.sdk.utils.HttpUtils;
import com.meihaofenqi.wechat.sdk.utils.RetryUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/19
 **/
public class JsTicketApi {

    private static final String apiUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";


    /**
     * JSApi的类型
     * <p>
     * jsapi: 用于分享等js-api
     */
    public enum JsApiType {
        jsapi
    }

    /**
     * 从缓存中获取 access token，如果未取到或者 access token 不可用则先更新再获取
     *
     * @return AccessToken accessToken
     */
    public static JsTicket getJsTicket(ApiConfig ac) {
        JsTicket result = getAvailableJsTicket(ac);
        if (Objects.isNull(result)) {
            synchronized (ApiConfig.class) {
                result = getAvailableJsTicket(ac);
                if (Objects.isNull(result)) {
                    result = refreshJsTicket(ac);
                }
            }
        }
        return result;
    }

    /**
     * 从缓存中获取token
     */
    public static JsTicket getAvailableJsTicket(ApiConfig ac) {
        RedisUtil redis = ApiConfigKit.getCache();
        String json = redis.get(CacheKeyBuilder.jsApiKey(ac.getAppId()));
        if (StringUtils.isNotBlank(json)) {
            JsTicket token = new JsTicket(json);
            if (token.isAvailable()) {
                return token;
            }
        }
        return null;
    }

    /**
     * http GET请求获得jsapi_ticket（有效期7200秒）
     *
     * @return JsTicket
     */
    public static JsTicket refreshJsTicket(ApiConfig ac) {
        String access_token = AccessTokenApi.getAccessTokenStr(ac);
        final ParaMap pm = ParaMap.create("access_token", access_token).put("type", JsApiType.jsapi.name());
        final RedisUtil redis = ApiConfigKit.getCache();
        String jsTicketJson = redis.get(CacheKeyBuilder.jsApiKey(ac.getAppId()));
        JsTicket jsTicket = null;
        if (StringUtils.isNotBlank(jsTicketJson)) {
            jsTicket = new JsTicket(jsTicketJson);
        }
        if (Objects.isNull(jsTicket) || !jsTicket.isAvailable()) {
            jsTicket = RetryUtils.retryOnException(3, () -> new JsTicket(HttpUtils.get(apiUrl, pm.getData())), ac);
        }
        redis.setex(CacheKeyBuilder.jsApiKey(ac.getAppId()), jsTicket.cacheJson(), jsTicket.cacheExpireTime());
        return jsTicket;
    }
}
