package com.meihaofenqi.wechat.sdk.base.menu;

import com.meihaofenqi.wechat.sdk.exception.WechatException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/20
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuButton {

    /**
     * 菜单类别
     */
    private MenuType type;

    /**
     * 菜单上显示的文字
     */
    private String name;

    /**
     * 菜单key，当MenuType值为CLICK时用于区别菜单
     */
    private String key;

    /**
     * 菜单跳转的URL，当MenuType值为VIEW时用
     */
    private String url;

    /**
     * 菜单显示的永久素材的MaterialID,当MenuType值为media_id和view_limited时必需
     */
    private String media_id;

    /**
     * 小程序appid
     */
    private String appid;

    /**
     * 小程序pagepath
     */
    private String pagepath;

    /**
     * 二级菜单列表，每个一级菜单下最多5个
     */
    private List<MenuButton> sub_button;

    public void setSubButton(List<MenuButton> subButton) {
        if (null == subButton || subButton.size() > 5) {
            throw new WechatException("子菜单最多只有5个");
        }
        this.sub_button = subButton;
    }
}
