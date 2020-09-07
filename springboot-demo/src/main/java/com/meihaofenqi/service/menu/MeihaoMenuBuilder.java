package com.meihaofenqi.service.menu;

import com.meihaofenqi.api.AuthorizeUrlApi;
import com.meihaofenqi.base.ApiConfig;
import com.meihaofenqi.base.menu.Menu;
import com.meihaofenqi.base.menu.MenuButton;
import com.meihaofenqi.base.menu.MenuType;

import java.util.Arrays;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/9/3
 **/
public class MeihaoMenuBuilder {

    public static final String BASE_URL = "https://wechat.zhoujie.meihaofenqi.net/";


    public static Menu createMenu(ApiConfig apiConfig) {
        Menu menu = new Menu();
        //一级菜单1
        MenuButton main1 = MenuButton.builder().key("main1").type(MenuType.CLICK).name("还款").build();

        MenuButton sub11 = MenuButton.builder().key("sub11").type(MenuType.VIEW).name("签约代扣(推荐)")
                .url(AuthorizeUrlApi.getAuthorizeURL(apiConfig.getAppId(), getSignJdUrl(), null, true))
                .build();

        MenuButton sub12 = MenuButton.builder().key("sub12").type(MenuType.VIEW).name("银行卡还款")
                .url(AuthorizeUrlApi.getAuthorizeURL(apiConfig.getAppId(), getRepaymentUrl(), null, true))
                .build();

        MenuButton sub14 = MenuButton.builder().key("sub14").type(MenuType.VIEW).name("还款帮助")
                .url("https://app.meihaofenqi.com/article/appArticleInfo.html?id=12745")
                .build();

        main1.setSubButton(Arrays.asList(sub11, sub12, sub14));

        //一级菜单2
        MenuButton main2 = MenuButton.builder().key("main2").type(MenuType.CLICK).name("我的").build();

        //二级菜单
        MenuButton sub21 = MenuButton.builder().key("sub21").type(MenuType.VIEW).name("我的分期").url(getMyDebitUrl()).build();
        MenuButton sub22 = MenuButton.builder().key("sub22").type(MenuType.VIEW).name("取消分期").url(getCancelDebitUrl()).build();
        MenuButton sub23 = MenuButton.builder().key("sub23").type(MenuType.VIEW).name("实名认证").url(getRealNameUrl()).build();
        main2.setSubButton(Arrays.asList(sub21, sub22, sub23));

        menu.setButton(Arrays.asList(main1, main2));
        return menu;
    }

    private static String getRealNameUrl() {
        return BASE_URL + "wechat-landing/bankIdentify";
    }

    private static String getCancelDebitUrl() {
        return BASE_URL + "merchant?p=cancel&s=0&b=mh";
    }

    private static String getMyDebitUrl() {
        return BASE_URL + "merchant?p=order&s=0&b=mh";
    }

    private static String getRepaymentUrl() {
        return BASE_URL + "wechat-landing/repay/tieCard";
    }

    /**
     * 签约代扣路由
     */
    private static String getSignJdUrl() {
        return BASE_URL + "wechat-landing/repay/jdrepay";
    }
}
