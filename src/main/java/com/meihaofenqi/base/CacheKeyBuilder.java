package com.meihaofenqi.base;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/19
 **/
public class CacheKeyBuilder {

    /**
     * access_token缓存key
     */
    public static String accessTokenKey(String appId) {
        return "wx:at:" + appId.substring(0, 5);
    }

    /**
     * jsapi_ticket缓存key
     */
    public static String jsApiKey(String appId) {
        return "wx:js:" + appId.substring(0, 5);
    }


}
