package com.meihaofenqi.web;

import com.alibaba.fastjson.JSON;
import com.meihaofenqi.api.AccessTokenApi;
import com.meihaofenqi.api.JsTicketApi;
import com.meihaofenqi.api.KefuMsgApi;
import com.meihaofenqi.api.MenuApi;
import com.meihaofenqi.api.SnsAccessTokenApi;
import com.meihaofenqi.base.AccessToken;
import com.meihaofenqi.base.ApiConfig;
import com.meihaofenqi.base.ApiConfigKit;
import com.meihaofenqi.base.ApiResult;
import com.meihaofenqi.base.menu.Menu;
import com.meihaofenqi.service.menu.MeihaoMenuBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanglei
 * @description：springboot-mvc测试
 * @date Created on 2020/9/1
 **/
@Slf4j
@RestController
@RequestMapping("/app")
public class BasicApiTestController {


    /**
     * 给用户发客服送消息
     */
    @RequestMapping("/send/{appId}")
    public Object sendMsg(@PathVariable String appId) {
        String openId = "oiQcv1MDML-CcghyxFu4Etc1JwLI";
        String text = "请您对本次服务进行评价，祝您生活愉快\n" +
                "\n" +
                "<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd2ff381c818e334b&redirect_uri=http%3A%2F%2Fwechat.meihaofenqi.com%2Fh5%2Fsatis%3Fsi%3D2008150020%26sf%3D1&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect'>【非常满意】</a>\n" +
                "\n" +
                "<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd2ff381c818e334b&redirect_uri=http%3A%2F%2Fwechat.meihaofenqi.com%2Fh5%2Fsatis%3Fsi%3D2008150020%26sf%3D2&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect'>【满意】</a>\n" +
                "\n" +
                "<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd2ff381c818e334b&redirect_uri=http%3A%2F%2Fwechat.meihaofenqi.com%2Fh5%2Fsatis%3Fsi%3D2008150020%26sf%3D3&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect'>【不满意】</a>\n";
        // 测试发送纯文本消息
        ApiResult result = KefuMsgApi.newInstance(ApiConfigKit.getApiConfig(appId)).sendText(openId, text);
        return result.getJson();
    }

    /**
     * 获取accessToken
     */
    @RequestMapping("/access/token/{appId}")
    public Object accessToken(@PathVariable String appId) {
        AccessToken at = AccessTokenApi.getAccessToken(appId);
        if (at.isAvailable()) {
            log.info("access_token : {}", at.getAccessToken());
            return at.getAccess_token();
        } else {
            log.error(at.getErrcode() + " : " + at.getErrorMsg());
            return null;
        }
    }

    /**
     * code 换 token
     */
    @RequestMapping(value = "/code/token/{appId}", method = RequestMethod.POST)
    public Object code2Token(@PathVariable String appId, @RequestParam String code) {
        return SnsAccessTokenApi.getSnsAccessToken(ApiConfigKit.getApiConfig(appId), code);
    }

    /**
     * 获取js-ticket
     */
    @RequestMapping(value = "/js/ticket/{appId}")
    public Object jsTicket(@PathVariable String appId) {
        return JsTicketApi.getJsTicket(ApiConfigKit.getApiConfig(appId)).getTicket();
    }

    /**
     * 创建菜单
     */
    @RequestMapping(value = "/createMenu/{appId}")
    public Object createMenu(@PathVariable String appId) {
        ApiConfig apiConfig = ApiConfigKit.getApiConfig(appId);
        Menu menu = MeihaoMenuBuilder.createMenu(apiConfig);
        ApiResult result = MenuApi.createMenu(menu, apiConfig);
        return result.getJson();
    }

    /**
     * 获取菜单
     */
    @RequestMapping(value = "/menu/get/{appId}")
    public Object getMenu(@PathVariable String appId) {
        ApiConfig apiConfig = ApiConfigKit.getApiConfig(appId);
        ApiResult result = MenuApi.getMenu(apiConfig);
        Menu menu = Menu.parseMenu(result.getJson());
        return JSON.toJSONString(menu);
    }
}
