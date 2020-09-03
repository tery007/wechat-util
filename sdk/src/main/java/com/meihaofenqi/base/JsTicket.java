package com.meihaofenqi.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meihaofenqi.api.AccessTokenApi;
import com.meihaofenqi.exception.WechatException;
import com.meihaofenqi.utils.RetryUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/19
 **/
@Data
public class JsTicket implements RetryUtils.ResultCheck, Serializable {

    private static final long serialVersionUID = 6600179487477942329L;

    /** 正确获取到 ticket 时有值 */
    private String  ticket;
    /** 正确获取到 access_token 时有值 */
    private Integer expires_in;
    /** 出错时有值 */
    private Integer errcode;
    /** 出错时有值 */
    private String  errmsg;
    /** 缓存中存储的过期时间戳 */
    private Long    expiredTime;
    private String  result;

    public JsTicket(String result) {
        this.result = result;
        try {
            JSONObject temp = JSON.parseObject(result);
            ticket = temp.getString("ticket");
            expires_in = temp.getInteger("expires_in");
            errcode = temp.getInteger("errcode");
            errmsg = temp.getString("errmsg");
            if (Objects.nonNull(expires_in)) {
                expiredTime = System.currentTimeMillis() + ((expires_in - 7) * 1000);
            } else {
                expiredTime = temp.getLong("expiredTime");
            }
        } catch (Exception e) {
            throw new WechatException(e.getMessage());
        }
    }

    @Override
    public String getJson() {
        return this.result;
    }

    /**
     * 过期时间戳处理
     */
    public String cacheJson() {
        JSONObject temp = JSON.parseObject(result);
        temp.put("expiredTime", expiredTime);
        temp.remove("expires_in");
        return temp.toJSONString();
    }

    public boolean isAvailable() {
        if (expiredTime == null) {
            return false;
        }
        if (!isSucceed()) {
            return false;
        }
        if (expiredTime < System.currentTimeMillis()) {
            return false;
        }
        return StringUtils.isNotBlank(ticket);
    }

    public String getTicket() {
        return ticket;
    }

    /**
     * APi 请求是否成功返回
     *
     * @return boolean
     */
    public boolean isSucceed() {
        Integer errorCode = getErrcode();
        return (Objects.isNull(errorCode) || Objects.equals(errorCode, 0));
    }

    @Override
    public boolean matching(ApiConfig ac) {
        // access_token过期则刷新缓存
        if (AccessToken.tokenInvalid(getErrcode())) {
            AccessTokenApi.refreshAccessToken(ac);
        }
        return isAvailable();
    }

    /**
     * 设置缓存过期时间，7200-7
     */
    public int cacheExpireTime() {
        return getExpires_in() - 7;
    }
}
