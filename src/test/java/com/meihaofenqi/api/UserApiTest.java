package com.meihaofenqi.api;

import com.meihaofenqi.wechat.sdk.api.UserApi;
import com.meihaofenqi.wechat.sdk.base.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.meihaofenqi.api.TestBase.localTestInit;
import static com.meihaofenqi.api.TestBase.testConf;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/24
 **/
@Slf4j
public class UserApiTest {

    @Before
    public void init() {
        localTestInit();
    }


    /**
     * 关注用户列表
     */
    @Test
    public void testFollows() {
        ApiResult result = UserApi.getFollows(testConf);
        String json = result.getJson();
        String follows = "{\"total\":96,\"count\":96,\"data\":{\"openid\":[\"oiQcv1L2kWJnFBD8OJNoATY-ygEI\",\"oiQcv1FPrOPBR_cMFFGkKRWI8yMk\",\"oiQcv1HNuASwfgX1sTEvGVhtYQkU\",\"oiQcv1MDML-CcghyxFu4Etc1JwLI\",\"oiQcv1DeQohxRKr4XYAMhz_lSb00\",\"oiQcv1FmhT5C-UB1mX1zpzKiQETo\",\"oiQcv1Bx4iROZSKsNdtVpPK9fvZ4\",\"oiQcv1BzXgONJ7zl4U8LtRclEV7I\",\"oiQcv1HuKyBFdy7vD_fWRSK0sI9Y\",\"oiQcv1HLhHm0WfUkpUhPiduEE6sg\",\"oiQcv1A2YOL1ozX_nMveawYbksQk\",\"oiQcv1L0i-CD8iI-jkFFwvo___Qo\",\"oiQcv1C4H9IG2XJkz6x7BYQQIfRc\",\"oiQcv1Hmao2BfID2ZVzModPH4GyE\",\"oiQcv1I7M7f4g7QdL7zCnnipsi9s\",\"oiQcv1BKGcQknzhk9WE1dGdCpb9Q\",\"oiQcv1OOcqJt1EnPHTdaPKo0ZQKo\",\"oiQcv1EDKRH3M7UBrSJDVoa37E8I\",\"oiQcv1IsYv1Yx17zecdctXb0N7sA\",\"oiQcv1O5-Gu1zrQk7tAAawBTE620\",\"oiQcv1BiCx5gg-DClyaNnKgelP_U\",\"oiQcv1M2lFW33AJ_gQjcFt-1HRis\",\"oiQcv1DxH08aqIkabXXyPGJ0Qn8U\",\"oiQcv1BS9JpkoN3mu6YtgGnBzNus\",\"oiQcv1CB5qjszvxNkLkBMsyqAG_Y\",\"oiQcv1DxT4V7D38NwGJWYo3vajjo\",\"oiQcv1MMMLDytJkV46Cyqrc0hNow\",\"oiQcv1O8uY3fs2C7SV2_6NjF6ktI\",\"oiQcv1GrfvpbL0dGBCOhHFZaGQKI\",\"oiQcv1Gx5Ej-NniJ4Pv2N2VlzTIU\",\"oiQcv1DCJHJHA47JFAcDYTvIEdLY\",\"oiQcv1GwpFg8eyPtto6p5JSj-h3c\",\"oiQcv1Inq_wdojZYBPv9QmkUlDUY\",\"oiQcv1PRj1-kcqRVoOq87z5js_Rw\",\"oiQcv1N-svxTzc14ykgBAUKDMQFs\",\"oiQcv1GmIdVQiF8FACtjDiTFVif0\",\"oiQcv1AD6p3rlZLAA7HZpRj1kKHg\",\"oiQcv1NMXaNEuhq8gxCu-n5FOttE\",\"oiQcv1HopbdsrqeNaEAQutYGUQ8Q\",\"oiQcv1CvwV8Qt6Fz7fmKBWPgVZ30\",\"oiQcv1BeJz5Vomf_Xv-bhaEdGFWk\",\"oiQcv1N4912tKCvfLQfiFtDsCeBY\",\"oiQcv1FHPKumtdWojrKbpDUUdB50\",\"oiQcv1LHh5LcCn6koH09f47K3kQg\",\"oiQcv1PHBJrDTTbhCxhUFVo-snLE\",\"oiQcv1IHc5Q9zV-wzsd_JB4Vyl1Q\",\"oiQcv1Nh-f7kG0uDXncAGpKg4jOA\",\"oiQcv1HjrA0lxpXsqgjJlYDDFMLg\",\"oiQcv1H-CnvyVQdWJ14eZs6Ao1Oo\",\"oiQcv1GNI90DGC4puNThRjElJq-s\",\"oiQcv1OD-ct_7cDs2bwDgZNe1rG4\",\"oiQcv1FXKLXQD4hNceSkwVcTxf38\",\"oiQcv1IAULEWuX-Jauu0nMlDk9ww\",\"oiQcv1EdPOzE46H8p6oAzyWJbJBI\",\"oiQcv1JCaN5ZIMITbGOy5ndqjMtw\",\"oiQcv1B5YKPv0--s7BgMTyaDZG4U\",\"oiQcv1NPnvlKI86BUxJPt38_pLjA\",\"oiQcv1KYYRveQ4tqIE1Pulqyqehs\",\"oiQcv1B6M6wplJopcSi1kgEHze2g\",\"oiQcv1NoONeinRqg2O8adJgBCYDA\",\"oiQcv1J8IQWiGDxLUVnc0-DSjkhA\",\"oiQcv1Iq-cIGahj_TCuJJ9a-CJVg\",\"oiQcv1Me6seaRDpQM64AmOGLf6SQ\",\"oiQcv1F2kS5Xe4dAQ_AAM1sUtOeQ\",\"oiQcv1FMAeJOOy_M9lvAMlpTYMJ0\",\"oiQcv1GkHBg-bEm7t3aPwDgwBvfo\",\"oiQcv1LYZ945vBwFu3mwOZ8WNRqU\",\"oiQcv1BgQJmERrD34zPkZtAt8nN0\",\"oiQcv1OKgzyeo7TfIlpcLVmQqZFs\",\"oiQcv1FdSyulo6cAiBZAke7LCk10\",\"oiQcv1IKSE7qzdKV24jil3RpRbpI\",\"oiQcv1J28QLMOHtATIoomt5oYTo4\",\"oiQcv1GNh-T8RD_qWUoMsI111OaM\",\"oiQcv1KMKYmPhESxLEYLre4MAAzk\",\"oiQcv1C82sOGkHYwvKZoJfGP7bjY\",\"oiQcv1I5fX0jftQbNHXppnpqcK7o\",\"oiQcv1MZEnjOOarL0qhHXp3Q6LO8\",\"oiQcv1La1cxF_nP5szIuMqqEtPE0\",\"oiQcv1IXslelM4DFK4_RZOWMbzto\",\"oiQcv1AWI-vtXCrzXjRLgWaosmCQ\",\"oiQcv1Cod6AhGUQ_H2WLjPx5v1fQ\",\"oiQcv1EbO4p7osPP8kIGi3AylUTA\",\"oiQcv1IjA0PrDL-gn5745lh6Er1w\",\"oiQcv1DVUjreKLOHbKBrkp4thF-8\",\"oiQcv1Icarqy4x6BPQ9XjpU5X4Hk\",\"oiQcv1Pzk1pXsoPpyD9Ws0gjvvKs\",\"oiQcv1JMPdomXCvgu5e-JkJqd4UM\",\"oiQcv1EXPTmjS0kKPg_3MjfPVtak\",\"oiQcv1HrSRz1q07LkxXfFJtHrOTY\",\"oiQcv1GqCGGDwyxjltu2_UxJM_ec\",\"oiQcv1JVoyoKDbhfdOfXA5ThcL4A\",\"oiQcv1IxqRijZihindYvF2pGo5mU\",\"oiQcv1MSts2KIdOOUDCzy_iYFeMo\",\"oiQcv1P0a7RIkpVtoUGL4cxUNYZQ\",\"oiQcv1M-7iS1Jdb8TlrfdzTLJCDo\",\"oiQcv1H6bGpLx2DLBbFJq57jhAOY\"]},\"next_openid\":\"oiQcv1H6bGpLx2DLBbFJq57jhAOY\"}";
        Assert.assertEquals(json, follows);

        // 指定开始openid，拉取后面的用户
        ApiResult result1 = UserApi.getFollowers("oiQcv1HuKyBFdy7vD_fWRSK0sI9Y", testConf);
        String followsEnd = "{\"total\":96,\"count\":87,\"data\":{\"openid\":[\"oiQcv1HLhHm0WfUkpUhPiduEE6sg\",\"oiQcv1A2YOL1ozX_nMveawYbksQk\",\"oiQcv1L0i-CD8iI-jkFFwvo___Qo\",\"oiQcv1C4H9IG2XJkz6x7BYQQIfRc\",\"oiQcv1Hmao2BfID2ZVzModPH4GyE\",\"oiQcv1I7M7f4g7QdL7zCnnipsi9s\",\"oiQcv1BKGcQknzhk9WE1dGdCpb9Q\",\"oiQcv1OOcqJt1EnPHTdaPKo0ZQKo\",\"oiQcv1EDKRH3M7UBrSJDVoa37E8I\",\"oiQcv1IsYv1Yx17zecdctXb0N7sA\",\"oiQcv1O5-Gu1zrQk7tAAawBTE620\",\"oiQcv1BiCx5gg-DClyaNnKgelP_U\",\"oiQcv1M2lFW33AJ_gQjcFt-1HRis\",\"oiQcv1DxH08aqIkabXXyPGJ0Qn8U\",\"oiQcv1BS9JpkoN3mu6YtgGnBzNus\",\"oiQcv1CB5qjszvxNkLkBMsyqAG_Y\",\"oiQcv1DxT4V7D38NwGJWYo3vajjo\",\"oiQcv1MMMLDytJkV46Cyqrc0hNow\",\"oiQcv1O8uY3fs2C7SV2_6NjF6ktI\",\"oiQcv1GrfvpbL0dGBCOhHFZaGQKI\",\"oiQcv1Gx5Ej-NniJ4Pv2N2VlzTIU\",\"oiQcv1DCJHJHA47JFAcDYTvIEdLY\",\"oiQcv1GwpFg8eyPtto6p5JSj-h3c\",\"oiQcv1Inq_wdojZYBPv9QmkUlDUY\",\"oiQcv1PRj1-kcqRVoOq87z5js_Rw\",\"oiQcv1N-svxTzc14ykgBAUKDMQFs\",\"oiQcv1GmIdVQiF8FACtjDiTFVif0\",\"oiQcv1AD6p3rlZLAA7HZpRj1kKHg\",\"oiQcv1NMXaNEuhq8gxCu-n5FOttE\",\"oiQcv1HopbdsrqeNaEAQutYGUQ8Q\",\"oiQcv1CvwV8Qt6Fz7fmKBWPgVZ30\",\"oiQcv1BeJz5Vomf_Xv-bhaEdGFWk\",\"oiQcv1N4912tKCvfLQfiFtDsCeBY\",\"oiQcv1FHPKumtdWojrKbpDUUdB50\",\"oiQcv1LHh5LcCn6koH09f47K3kQg\",\"oiQcv1PHBJrDTTbhCxhUFVo-snLE\",\"oiQcv1IHc5Q9zV-wzsd_JB4Vyl1Q\",\"oiQcv1Nh-f7kG0uDXncAGpKg4jOA\",\"oiQcv1HjrA0lxpXsqgjJlYDDFMLg\",\"oiQcv1H-CnvyVQdWJ14eZs6Ao1Oo\",\"oiQcv1GNI90DGC4puNThRjElJq-s\",\"oiQcv1OD-ct_7cDs2bwDgZNe1rG4\",\"oiQcv1FXKLXQD4hNceSkwVcTxf38\",\"oiQcv1IAULEWuX-Jauu0nMlDk9ww\",\"oiQcv1EdPOzE46H8p6oAzyWJbJBI\",\"oiQcv1JCaN5ZIMITbGOy5ndqjMtw\",\"oiQcv1B5YKPv0--s7BgMTyaDZG4U\",\"oiQcv1NPnvlKI86BUxJPt38_pLjA\",\"oiQcv1KYYRveQ4tqIE1Pulqyqehs\",\"oiQcv1B6M6wplJopcSi1kgEHze2g\",\"oiQcv1NoONeinRqg2O8adJgBCYDA\",\"oiQcv1J8IQWiGDxLUVnc0-DSjkhA\",\"oiQcv1Iq-cIGahj_TCuJJ9a-CJVg\",\"oiQcv1Me6seaRDpQM64AmOGLf6SQ\",\"oiQcv1F2kS5Xe4dAQ_AAM1sUtOeQ\",\"oiQcv1FMAeJOOy_M9lvAMlpTYMJ0\",\"oiQcv1GkHBg-bEm7t3aPwDgwBvfo\",\"oiQcv1LYZ945vBwFu3mwOZ8WNRqU\",\"oiQcv1BgQJmERrD34zPkZtAt8nN0\",\"oiQcv1OKgzyeo7TfIlpcLVmQqZFs\",\"oiQcv1FdSyulo6cAiBZAke7LCk10\",\"oiQcv1IKSE7qzdKV24jil3RpRbpI\",\"oiQcv1J28QLMOHtATIoomt5oYTo4\",\"oiQcv1GNh-T8RD_qWUoMsI111OaM\",\"oiQcv1KMKYmPhESxLEYLre4MAAzk\",\"oiQcv1C82sOGkHYwvKZoJfGP7bjY\",\"oiQcv1I5fX0jftQbNHXppnpqcK7o\",\"oiQcv1MZEnjOOarL0qhHXp3Q6LO8\",\"oiQcv1La1cxF_nP5szIuMqqEtPE0\",\"oiQcv1IXslelM4DFK4_RZOWMbzto\",\"oiQcv1AWI-vtXCrzXjRLgWaosmCQ\",\"oiQcv1Cod6AhGUQ_H2WLjPx5v1fQ\",\"oiQcv1EbO4p7osPP8kIGi3AylUTA\",\"oiQcv1IjA0PrDL-gn5745lh6Er1w\",\"oiQcv1DVUjreKLOHbKBrkp4thF-8\",\"oiQcv1Icarqy4x6BPQ9XjpU5X4Hk\",\"oiQcv1Pzk1pXsoPpyD9Ws0gjvvKs\",\"oiQcv1JMPdomXCvgu5e-JkJqd4UM\",\"oiQcv1EXPTmjS0kKPg_3MjfPVtak\",\"oiQcv1HrSRz1q07LkxXfFJtHrOTY\",\"oiQcv1GqCGGDwyxjltu2_UxJM_ec\",\"oiQcv1JVoyoKDbhfdOfXA5ThcL4A\",\"oiQcv1IxqRijZihindYvF2pGo5mU\",\"oiQcv1MSts2KIdOOUDCzy_iYFeMo\",\"oiQcv1P0a7RIkpVtoUGL4cxUNYZQ\",\"oiQcv1M-7iS1Jdb8TlrfdzTLJCDo\",\"oiQcv1H6bGpLx2DLBbFJq57jhAOY\"]},\"next_openid\":\"oiQcv1H6bGpLx2DLBbFJq57jhAOY\"}";
        Assert.assertEquals(result1.getJson(), followsEnd);
    }


    /**
     * 用户信息
     */
    @Test
    public void testUserInfo() {
        ApiResult result = UserApi.getUserInfo("o8MHVv3TwXf0b4OyPS8z43f_GErQ", testConf);
        log.info(result.getJson());
        String expectR = "{\"subscribe\":1,\"openid\":\"o8MHVv3TwXf0b4OyPS8z43f_GErQ\",\"nickname\":\"王小磊\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"\",\"province\":\"\",\"country\":\"中国\",\"headimgurl\":\"http:\\/\\/thirdwx.qlogo.cn\\/mmopen\\/PiajxSqBRaELvb3IGaON6q7MbrVUYxzUVIWrfeCLcKGISKW8fBYia3jdpGvaKPFZYEP4Qx2LsamBYTDicUrsQJrAw\\/132\",\"subscribe_time\":1597908253,\"unionid\":\"o25ve09CciFcDqS0HETFH6nH8_jA\",\"remark\":\"\",\"groupid\":0,\"tagid_list\":[],\"subscribe_scene\":\"ADD_SCENE_SEARCH\",\"qr_scene\":0,\"qr_scene_str\":\"\"}";
        Assert.assertEquals(expectR, result.getJson());

    }
}
