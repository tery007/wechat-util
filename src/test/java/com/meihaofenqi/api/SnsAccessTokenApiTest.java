package com.meihaofenqi.api;

import com.alibaba.fastjson.JSON;
import com.meihaofenqi.base.SnsAccessToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.meihaofenqi.api.TestBase.localTestInit;
import static com.meihaofenqi.api.TestBase.testConf;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/9/3
 **/
@Slf4j
public class SnsAccessTokenApiTest {

    @Before
    public void init() {
        localTestInit();
    }

    @Test
    public void testCode2Token() {
        SnsAccessToken token = SnsAccessTokenApi.getSnsAccessToken(testConf, "xxlss333");
        String result = "{\"available\":false,\"errcode\":40029,\"errmsg\":\"invalid code rid: 5f50b356-6c9cbcf6-4a4b3049\",\"errorCode\":40029,\"errorMsg\":\"不合法的oauth_code\",\"json\":\"{\\\"errcode\\\":40029,\\\"errmsg\\\":\\\"invalid code rid: 5f50b356-6c9cbcf6-4a4b3049\\\"}\"}";
        Assert.assertEquals(JSON.toJSONString(token), result);
    }
}
