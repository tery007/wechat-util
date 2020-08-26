package com.meihaofenqi.api;

import com.alibaba.fastjson.JSON;
import com.meihaofenqi.wechat.sdk.api.JsTicketApi;
import com.meihaofenqi.wechat.sdk.base.JsTicket;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.meihaofenqi.api.TestBase.localTestInit;
import static com.meihaofenqi.api.TestBase.testConf;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/19
 **/
@Slf4j
public class JsTicketApiTest {

    @Before
    public void init() {
        localTestInit();
    }

    @Test
    public void test() {
        JsTicket ticket = JsTicketApi.getJsTicket(testConf);
        log.info("jsTicket json:{}", JSON.toJSONString(ticket));
        String jsTicket = "HoagFKDcsGMVCIY2vOjf9gB455SS5SeUFlWxfUcFgmN7nsGanG1wrK809KLqeGw91aRgtFC6wHEzz6jToDDkww";
        Assert.assertEquals(jsTicket, ticket.getTicket());
    }
}
