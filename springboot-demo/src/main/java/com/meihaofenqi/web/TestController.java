package com.meihaofenqi.web;

import com.meihaofenqi.api.KefuMsgApi;
import com.meihaofenqi.base.ApiConfigKit;
import com.meihaofenqi.base.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanglei
 * @description：springboot-mvc测试
 * @date Created on 2020/9/1
 **/
@Slf4j
@RestController
@RequestMapping("/app")
public class TestController {


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
}
