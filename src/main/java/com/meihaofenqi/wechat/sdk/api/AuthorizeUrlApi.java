package com.meihaofenqi.wechat.sdk.api;

import com.meihaofenqi.wechat.sdk.base.PackSignKit;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/20
 **/
public class AuthorizeUrlApi {

    private static String authorize_uri = "https://open.weixin.qq.com/connect/oauth2/authorize";

    /**
     * 生成Authorize链接
     *
     * @param appId        应用id
     * @param redirect_uri 回跳地址
     * @param snsapiBase   snsapi_base（不弹出授权页面，只能拿到用户openid）snsapi_userinfo（弹出授权页面，这个可以通过 openid 拿到昵称、性别、所在地）
     * @return url
     */
    public static String getAuthorizeURL(String appId, String redirect_uri, boolean snsapiBase) {
        return getAuthorizeURL(appId, redirect_uri, null, snsapiBase);
    }

    /**
     * 生成Authorize链接
     *
     * @param appId       应用id
     * @param redirectUri 回跳地址
     * @param state       重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     * @param snsapiBase  snsapi_base（不弹出授权页面，只能拿到用户openid）snsapi_userinfo（弹出授权页面，这个可以通过 openid 拿到昵称、性别、所在地）
     * @return url
     */
    public static String getAuthorizeURL(String appId, String redirectUri, String state, boolean snsapiBase) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", appId);
        params.put("response_type", "code");
        params.put("redirect_uri", redirectUri);
        // snsapi_base（不弹出授权页面，只能拿到用户openid）
        // snsapi_userinfo（弹出授权页面，这个可以通过 openid 拿到昵称、性别、所在地）
        if (snsapiBase) {
            params.put("scope", "snsapi_base");
        } else {
            params.put("scope", "snsapi_userinfo");
        }
        if (StringUtils.isBlank(state)) {
            params.put("state", "wx#wechat_redirect");
        } else {
            params.put("state", state.concat("#wechat_redirect"));
        }
        String para = PackSignKit.packageSign(params, false);
        return authorize_uri + "?" + para;
    }

}
