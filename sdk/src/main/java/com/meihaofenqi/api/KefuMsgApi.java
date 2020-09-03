package com.meihaofenqi.api;

import com.alibaba.fastjson.JSON;
import com.meihaofenqi.base.ApiConfig;
import com.meihaofenqi.base.ApiResult;
import com.meihaofenqi.utils.HttpUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wanglei
 * @description: 发送客服消息
 * @date Created on 2020/8/20
 **/
@Slf4j
@Data
public class KefuMsgApi {

    private static ApiConfig apiConfig;

    private KefuMsgApi(ApiConfig ac) {
        apiConfig = ac;
    }

    public static KefuMsgApi newInstance(ApiConfig af) {
        return new KefuMsgApi(af);
    }

    private static String CUSTOM_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";


    /**
     * 发送文本消息
     *
     * @param openId 用户openid
     * @param text   文本
     * @return
     */
    public ApiResult sendText(String openId, String text) {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("touser", openId);
        json.put("msgtype", "text");
        Map<String, Object> textObj = new HashMap<String, Object>();
        textObj.put("content", text);
        json.put("text", textObj);
        return sendMsg(json);
    }


    /**
     * 发送客服消息
     *
     * @param message 消息封装
     * @return ApiResult
     */
    private ApiResult sendMsg(Map<String, Object> message) {
        String accessToken = AccessTokenApi.getAccessTokenStr(apiConfig);
        String jsonResult = HttpUtils.post(CUSTOM_MESSAGE_URL + accessToken, JSON.toJSONString(message));
        log.info("send kefu msg resp:{}", jsonResult);
        return new ApiResult(jsonResult, apiConfig);
    }

    /**
     * todo 发送图片消息
     *
     * @param openId
     * @param media_id
     * @return
     */
    public ApiResult sendImage(String openId, String media_id) {
        return null;
    }


}
