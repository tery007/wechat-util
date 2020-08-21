package com.meihaofenqi.wechat.sdk.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meihaofenqi.wechat.sdk.exception.WechatException;
import com.meihaofenqi.wechat.sdk.utils.RetryUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author wanglei
 * @description:获取access_token <pre>
 *     返回json格式数据：
 *     正常返回：{"access_token":"ACCESS_TOKEN","expires_in":7200}
 *     异常返回：{"errcode":40013,"errmsg":"invalid appid"}
 * </pre>
 * @date Created on 2020/8/18
 **/
@Data
public class AccessToken implements RetryUtils.ResultCheck, Serializable {

    private static final long serialVersionUID = -822464425433824314L;

    /** 正确获取到 access_token 时有值 */
    private String  access_token;
    /** 正确获取到 access_token 时有值 */
    private Integer expires_in;
    /** 出错时有值 */
    private Integer errcode;
    /** 出错时有值 */
    private String  errmsg;
    /** 正确获取到 access_token 时有值，过期时间戳 */
    private Long    expiredTime;
    private String  result;


    /**
     * 生成AccessToken对象，
     * 时间戳expiredTime设值规则：
     * 数据中expires_in存在，说明尚未存进缓存 ==> 设置过期时间戳expiredTime
     * 数据中不存在expires_in，说明已经存在缓存中 ==> 获取缓存中的expiredTime
     *
     * @param result 缓存数据
     */
    public AccessToken(String result) {
        this.result = result;
        try {
            JSONObject temp = JSON.parseObject(result);
            access_token = temp.getString("access_token");
            expires_in = temp.getInteger("expires_in");
            errcode = temp.getInteger("errcode");
            errmsg = temp.getString("errmsg");
            if (Objects.nonNull(expires_in)) {
                expiredTime = System.currentTimeMillis() + ((expires_in - 9) * 1000);
            } else {
                expiredTime = temp.getLong("expiredTime");
            }
        } catch (Exception e) {
            throw new WechatException(e.getMessage());
        }
    }

    /**
     * access_token是否有效存在
     */
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

    @Override
    public boolean matching(ApiConfig ac) {
        return isAvailable();
    }

    @Override
    public String getJson() {
        return this.result;
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

    public String getAccessToken() {
        return this.access_token;
    }


    /**
     * 用expiredTime存储过期时间戳
     * 将接口返回的expires_in删除
     *
     * @return 接口返回结果
     */
    public String cacheJson() {
        JSONObject temp = JSON.parseObject(result);
        temp.put("expiredTime", expiredTime);
        temp.remove("expires_in");
        return temp.toJSONString();
    }

    /**
     * 设置缓存过期时间，7200-9
     */
    public int cacheExpireTime() {
        return getExpires_in() - 9;
    }

    /**
     * 当微信返回token过期时，需调用此方法刷新token，更新缓存
     *
     * @param ori
     * @return
     */
    public static boolean tokenInvalid(Integer ori) {
        return Objects.equals(ori, 40001);
    }
}
