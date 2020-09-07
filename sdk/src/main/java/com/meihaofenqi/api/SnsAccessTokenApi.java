package com.meihaofenqi.api;

import com.meihaofenqi.base.ApiConfig;
import com.meihaofenqi.base.SnsAccessToken;
import com.meihaofenqi.utils.HttpUtils;
import com.meihaofenqi.utils.RetryUtils;

/**
 * @author wanglei
 * @description：网页授权获取 access_token
 * @date Created on 2020/9/3
 **/
public class SnsAccessTokenApi {

    private static String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={appid}&secret={secret}&code={code}&grant_type=authorization_code";

    /**
     * 通过code获取access_token
     *
     * @param code      第一步获取的code参数
     * @param apiConfig 微信配置
     * @return SnsAccessToken
     */
    public static SnsAccessToken getSnsAccessToken(ApiConfig apiConfig, String code) {
        final String accessTokenUrl = url.replace("{appid}", apiConfig.getAppId()).replace("{secret}", apiConfig.getAppSecret()).replace("{code}", code);
        return RetryUtils.retryOnException(3, () -> {
            String json = HttpUtils.get(accessTokenUrl);
            return new SnsAccessToken(json);
        }, apiConfig);
    }
}
