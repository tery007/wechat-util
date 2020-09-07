package com.meihaofenqi.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.template.Engine;
import com.meihaofenqi.service.SpringJFinalFilter;
import com.meihaofenqi.web.WeixinMsgController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Collections;

/**
 * @author wanglei
 * @description：使用JFinal框架对微信消息统一拦截的配置
 * @date Created on 2020/9/1
 **/
@Slf4j
@Configuration
public class WeixinConfig extends JFinalConfig {

    @Override
    public void configConstant(Constants me) {
    }

    @Override
    public void configRoute(Routes me) {
        /**
         * 如果有控制器继承了 MsgController 就必须
         * 要添加下面的配置，该配置才能将超类 MsgController 中的 index() 方法
         * 映射为 action
         */
        me.setMappingSuperClass(true);
        me.add("/wechat/home", WeixinMsgController.class);
    }

    @Override
    public void configPlugin(Plugins me) {
    }

    @Override
    public void configInterceptor(Interceptors me) {
        // 设置默认的 appId 规则，默认值为appId，可采用url挂参数 ?appId=xxx 切换多公众号
        // MsgInterceptor.setAppIdParser(new AppIdParser.DefaultParameterAppIdParser("appId")); 默认无需设置
    }

    @Override
    public void configEngine(Engine engine) {
    }

    @Override
    public void configHandler(Handlers me) {
    }

    @Override
    public void onStart() {
    }

    /**
     * springboot-注入SpringJFinalFilter、
     * MsgInterceptor注入，拦截configRoute()对应的url
     */
    @Bean
    public FilterRegistrationBean<Filter> registrationBean() {
        FilterRegistrationBean<Filter> f = new FilterRegistrationBean<>();
        f.setFilter(new SpringJFinalFilter());
        f.setUrlPatterns(Collections.singletonList("/wechat/*"));
        f.addInitParameter("configClass", WeixinConfig.class.getName());
        f.setOrder(2);
        return f;
    }
}
