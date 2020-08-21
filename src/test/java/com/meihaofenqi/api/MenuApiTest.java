package com.meihaofenqi.api;

import com.alibaba.fastjson.JSON;
import com.meihaofenqi.wechat.sdk.api.AuthorizeUrlApi;
import com.meihaofenqi.wechat.sdk.api.MenuApi;
import com.meihaofenqi.wechat.sdk.base.ApiConfig;
import com.meihaofenqi.wechat.sdk.base.ApiConfigKit;
import com.meihaofenqi.wechat.sdk.base.ApiResult;
import com.meihaofenqi.wechat.sdk.base.menu.Menu;
import com.meihaofenqi.wechat.sdk.base.menu.MenuButton;
import com.meihaofenqi.wechat.sdk.base.menu.MenuType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.meihaofenqi.api.CommonApp.APPID_TEST;
import static com.meihaofenqi.api.CommonApp.localTestInit;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/20
 **/
@Slf4j
public class MenuApiTest {

    @Before
    public void init() {
        localTestInit();
    }

    public static final String BASE_URL = "https://wechat.zhoujie.meihaofenqi.net/";

    /**
     * 测试微信配置
     */
    public static ApiConfig apiConfig = ApiConfigKit.getApiConfig(APPID_TEST);

    /**
     * 获取菜单
     */
    @Test
    public void testGetMenu() {
        ApiResult result = MenuApi.getMenu(apiConfig);
        Menu menu = Menu.parseMenu(result.getJson());
        log.info("current menu:{}", JSON.toJSONString(menu));
    }

    /**
     * 创建订单
     */
    @Test
    public void testCreate() {
        MenuApi.deleteMenu(apiConfig);
        Menu menu = createMenu();
        MenuApi.createMenu(menu, apiConfig);
    }

    /**
     * 删除菜单
     */
    @Test
    public void testDeleteMenu() {
        MenuApi.deleteMenu(apiConfig);
    }

    private Menu createMenu() {
        Menu menu = new Menu();
        //一级菜单1
        MenuButton main1 = MenuButton.builder().key("main1").type(MenuType.CLICK).name("还款").build();
        MenuButton sub11 = MenuButton.builder().key("sub11").type(MenuType.VIEW).name("签约代扣(推荐)")
                .url(AuthorizeUrlApi.getAuthorizeURL(APPID_TEST, getSignJdUrl(), null, true))
                .build();
        MenuButton sub12 = MenuButton.builder().key("sub12").type(MenuType.VIEW).name("银行卡还款")
                .url(AuthorizeUrlApi.getAuthorizeURL(APPID_TEST, getRepaymentUrl(), null, true))
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

    private String getRealNameUrl() {
        return BASE_URL + "wechat-landing/bankIdentify";
    }

    private String getCancelDebitUrl() {
        return BASE_URL + "merchant?p=cancel&s=0&b=mh";
    }

    private String getMyDebitUrl() {
        return BASE_URL + "merchant?p=order&s=0&b=mh";
    }

    private String getRepaymentUrl() {
        return BASE_URL + "wechat-landing/repay/tieCard";
    }

    /**
     * 签约代扣路由
     */
    private String getSignJdUrl() {
        return BASE_URL + "wechat-landing/repay/jdrepay";
    }

}
