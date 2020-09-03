package com.meihaofenqi.api;

import com.alibaba.fastjson.JSON;
import com.meihaofenqi.base.ApiConfig;
import com.meihaofenqi.base.ApiResult;
import com.meihaofenqi.base.menu.Menu;
import com.meihaofenqi.utils.HttpUtils;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/20
 **/
public class MenuApi {


    /** 获取菜单 */
    private static String GET_MENU_URL    = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=";
    /** 创建普通菜单 */
    private static String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
    /** 删除菜单 */
    private static String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";

    /**
     * 查询自定义菜单
     *
     * @param cf
     * @return {ApiResult}
     */
    public static ApiResult getMenu(ApiConfig cf) {
        String jsonResult = HttpUtils.get(GET_MENU_URL + AccessTokenApi.getAccessTokenStr(cf));
        return new ApiResult(jsonResult, cf);
    }

    /**
     * 创建自定义菜单
     *
     * @param menu 菜单
     * @return {ApiResult}
     */
    public static ApiResult createMenu(Menu menu, ApiConfig cf) {
        String jsonResult = HttpUtils.post(CREATE_MENU_URL + AccessTokenApi.getAccessTokenStr(cf), JSON.toJSONString(menu));
        return new ApiResult(jsonResult, cf);
    }

    /**
     * 自定义菜单删除接口
     *
     * @return ApiResult
     */
    public static ApiResult deleteMenu(ApiConfig cf) {
        String jsonResult = HttpUtils.get(DELETE_MENU_URL + AccessTokenApi.getAccessTokenStr(cf));
        return new ApiResult(jsonResult, cf);
    }


}
