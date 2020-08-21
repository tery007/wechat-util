package com.meihaofenqi.api;

import com.alibaba.fastjson.JSON;
import com.meihaofenqi.wechat.sdk.api.CustomerServiceApi;
import com.meihaofenqi.wechat.sdk.base.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.meihaofenqi.api.CommonApp.localTestInit;
import static com.meihaofenqi.api.CommonApp.testConf;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/20
 **/
@Slf4j
public class CustomerServiceApiTest {


    @Before
    public void init() {
        localTestInit();
    }

    /**
     * 发送文本消息
     * 本测试用例为：用户取消关注，发送消息返回报错
     */
    @Test
    public void testMessage() {
        String openId = "oiQcv1MDML-CcghyxFu4Etc1JwLI";
        String text = "请您对本次服务进行评价，祝您生活愉快\n" +
                "\n" +
                "<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd2ff381c818e334b&redirect_uri=http%3A%2F%2Fwechat.meihaofenqi.com%2Fh5%2Fsatis%3Fsi%3D2008150020%26sf%3D1&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect'>【非常满意】</a>\n" +
                "\n" +
                "<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd2ff381c818e334b&redirect_uri=http%3A%2F%2Fwechat.meihaofenqi.com%2Fh5%2Fsatis%3Fsi%3D2008150020%26sf%3D2&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect'>【满意】</a>\n" +
                "\n" +
                "<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd2ff381c818e334b&redirect_uri=http%3A%2F%2Fwechat.meihaofenqi.com%2Fh5%2Fsatis%3Fsi%3D2008150020%26sf%3D3&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect'>【不满意】</a>\n";
        // 测试发送纯文本消息
        ApiResult result = CustomerServiceApi.newInstance(testConf).sendText(openId, text);
        Assert.assertEquals("{\"attrs\":{\"errcode\":45015,\"errmsg\":\"response out of time limit or subscription is canceled rid: 5f3e3696-11e78743-10655b48\"},\"json\":\"{\\\"errcode\\\":45015,\\\"errmsg\\\":\\\"response out of time limit or subscription is canceled rid: 5f3e3696-11e78743-10655b48\\\"}\"}"
                , JSON.toJSONString(result));
    }
}
