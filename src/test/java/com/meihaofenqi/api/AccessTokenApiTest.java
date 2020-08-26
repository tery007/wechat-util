package com.meihaofenqi.api;

import com.meihaofenqi.wechat.sdk.api.AccessTokenApi;
import com.meihaofenqi.wechat.sdk.base.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.meihaofenqi.api.TestBase.APPID_TEST;
import static com.meihaofenqi.api.TestBase.localTestInit;
import static com.meihaofenqi.api.TestBase.testConf;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/19
 **/
@Slf4j
public class AccessTokenApiTest {


    @Before
    public void init() {
        localTestInit();
    }

    /**
     * 获取access_token
     */
    @Test
    public void test() {
        String actualToken = "36_Qc121wtMQVLXdo15QLAlrvn8AbRtvWH-1N_e4e_67QZ1au0uQIf7wpenfgh6r9wPmP1z8MYYRPjRkcMyBUMhYX590JQuDvDERYTPTSQbbIrt2ZQc4Ln3Q7H9VH9OYf-oNQgkClxsc641H0oLMOObABARTT";
        AccessToken at = AccessTokenApi.getAccessToken(APPID_TEST);
        if (at.isAvailable()) {
            log.info("access_token : {}", at.getAccessToken());
            Assert.assertEquals(at.getAccess_token(), actualToken);
        } else {
            log.error(at.getErrcode() + " : " + at.getErrorMsg());
        }
        String token = AccessTokenApi.getAccessTokenStr(testConf);
        Assert.assertEquals(token, actualToken);

    }
}
