package com.meihaofenqi.base.menu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meihaofenqi.exception.WechatException;

import java.util.List;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/20
 **/
public class Menu {

    /**
     * 一级菜单列表，最多3个
     */
    private List<MenuButton> button;

    /**
     * 菜单ID，查询时会返回，删除个性化菜单时会用到
     */
    private String menuid;

    public List<MenuButton> getButton() {
        return button;
    }

    public void setButton(List<MenuButton> button) {
        if (null == button || button.size() > 3) {
            throw new WechatException("主菜单最多3个");
        }
        this.button = button;
    }

    /**
     * 将json串转换为Menu对象
     */
    public static Menu parseMenu(String result) {
        try {
            JSONObject str = JSON.parseObject(result);
            JSONObject s = (JSONObject) str.get("menu");
            return JSON.toJavaObject(s, Menu.class);
        } catch (Exception e) {
            throw new WechatException("==>parse menu error:" + e.getMessage());
        }
    }

}
