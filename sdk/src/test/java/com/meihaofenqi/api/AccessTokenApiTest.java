package com.meihaofenqi.api;

import com.meihaofenqi.base.AccessToken;
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
        String actualToken = "36_iLV7lgbXgXjg8CfsZpfcF7pFdyj2zdRjUpMrlwFF7avPvl-Lxsk7riYOBKF3pvGsyUJAR9ipXwbJe-L0eaSDrfPMDBdyCQAABkQFutilFkS-QI_D2RSfEHwdKBemAIKQ-j7pCj5PVmHKL5NDGGBcAHAWWR";
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
