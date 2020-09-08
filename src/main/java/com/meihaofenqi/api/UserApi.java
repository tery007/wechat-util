package com.meihaofenqi.api;

import com.alibaba.fastjson.JSON;
import com.meihaofenqi.base.ApiConfig;
import com.meihaofenqi.base.ApiResult;
import com.meihaofenqi.base.ParaMap;
import com.meihaofenqi.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/24
 **/
public class UserApi {

    private static String GET_USER_INFO      = "https://api.weixin.qq.com/cgi-bin/user/info";
    private static String GET_FOLLOWERS      = "https://api.weixin.qq.com/cgi-bin/user/get";
    private static String BATCH_GET_USERINFO = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=";

    /**
     * 获取用户基本信息（包括UnionID机制）
     *
     * @param openId 普通用户的标识，对当前公众号唯一
     * @return ApiResult
     */
    public static ApiResult getUserInfo(String openId, ApiConfig ac) {
        ParaMap pm = ParaMap.create("access_token", AccessTokenApi.getAccessTokenStr(ac)).put("openid", openId).put("lang", "zh_CN");
        return new ApiResult(HttpUtils.get(GET_USER_INFO, pm.getData()), ac);
    }

    /**
     * 获取用户列表
     *
     * @param nextOpenid 第一个拉取的OPENID，不填默认从头开始拉取
     * @return ApiResult
     */
    public static ApiResult getFollowers(String nextOpenid, ApiConfig ac) {
        ParaMap pm = ParaMap.create("access_token", AccessTokenApi.getAccessTokenStr(ac));
        if (StringUtils.isNotBlank(nextOpenid)) {
            pm.put("next_openid", nextOpenid);
        }
        return new ApiResult(HttpUtils.get(GET_FOLLOWERS, pm.getData()), ac);
    }

    /**
     * 获取用户列表
     *
     * @return ApiResult
     */
    public static ApiResult getFollows(ApiConfig ac) {
        return getFollowers(null, ac);
    }

    /**
     * 批量获取用户基本信息
     *
     * @param jsonStr json字符串
     * @return ApiResult
     */
    public static ApiResult batchGetUserInfo(String jsonStr, ApiConfig ac) {
        String jsonResult = HttpUtils.post(BATCH_GET_USERINFO + AccessTokenApi.getAccessTokenStr(ac), jsonStr);
        return new ApiResult(jsonResult, ac);
    }

    /**
     * 批量获取用户基本信息
     *
     * @param openIdList openid列表
     * @return ApiResult
     */
    public static ApiResult batchGetUserInfo(List<String> openIdList, ApiConfig ac) {
        Map<String, List<Map<String, Object>>> userListMap = new HashMap<String, List<Map<String, Object>>>();

        List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
        for (String openId : openIdList) {
            Map<String, Object> mapData = new HashMap<String, Object>();
            mapData.put("openid", openId);
            mapData.put("lang", "zh_CN");
            userList.add(mapData);
        }
        userListMap.put("user_list", userList);

        return batchGetUserInfo(JSON.toJSONString(userListMap), ac);
    }

}
