package com.meihaofenqi.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meihaofenqi.utils.RetryUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/9/3
 **/
@Data
public class SnsAccessToken implements RetryUtils.ResultCheck, Serializable {

    private static final long serialVersionUID = 6369625123403343963L;

    private String  access_token;
    private Integer expires_in;
    private String  refresh_token;
    private String  openid;
    private String  scope;
    private String  unionid;
    private Integer errcode;
    private String  errmsg;
    private Long    expiredTime;
    private String  json;

    public SnsAccessToken(String jsonStr) {
        this.json = jsonStr;
        try {
            JSONObject temp = JSON.parseObject(jsonStr);
            access_token = temp.getString("access_token");
            expires_in = temp.getInteger("expires_in");
            refresh_token = temp.getString("refresh_token");
            openid = temp.getString("openid");
            unionid = temp.getString("unionid");
            scope = temp.getString("scope");
            errcode = temp.getInteger("errcode");
            errmsg = temp.getString("errmsg");

            if (expires_in != null) {
                expiredTime = System.currentTimeMillis() + ((expires_in - 5) * 1000);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getJson() {
        return json;
    }

    private Integer getInt(Map<String, Object> temp, String key) {
        Number number = (Number) temp.get(key);
        return number == null ? null : number.intValue();
    }

    public boolean isAvailable() {
        if (expiredTime == null) {
            return false;
        }
        if (errcode != null) {
            return false;
        }
        if (expiredTime < System.currentTimeMillis()) {
            return false;
        }
        return access_token != null;
    }

    public String getAccessToken() {
        return access_token;
    }

    public Integer getExpiresIn() {
        return expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public String getScope() {
        return scope;
    }

    public Integer getErrorCode() {
        return errcode;
    }

    public String getErrorMsg() {
        if (errcode != null) {
            String result = ReturnCode.get(errcode);
            if (result != null) {
                return result;
            }
        }
        return errmsg;
    }

    public String getUnionid() {
        return unionid;
    }

    @Override
    public boolean matching(ApiConfig ac) {
        return isAvailable();
    }
}
