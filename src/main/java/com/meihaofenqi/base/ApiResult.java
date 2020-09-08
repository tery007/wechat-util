package com.meihaofenqi.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meihaofenqi.api.AccessTokenApi;
import com.meihaofenqi.exception.WechatException;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/20
 **/
@Data
public class ApiResult implements Serializable {

    private static final long serialVersionUID = 722417391137943513L;

    private JSONObject attrs;
    private String     json;

    public ApiResult(String jsonStr, ApiConfig cf) {
        this.json = jsonStr;
        try {
            this.attrs = JSON.parseObject(jsonStr);
            // token过期，一定要重新获取，刷新缓存
            if (AccessToken.tokenInvalid(attrs.getInteger("errcode"))) {
                AccessTokenApi.refreshAccessToken(cf);
            }
        } catch (Exception e) {
            throw new WechatException(e.getMessage());
        }
    }


}
