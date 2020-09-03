package com.meihaofenqi;

import com.jfinal.core.JFinalFilter;
import com.meihaofenqi.config.WeixinConfig;
import com.meihaofenqi.service.SpringJFinalFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/27
 **/
@SpringBootApplication
public class WechatSpringbootDemoApp {


    public static void main(String[] args) {
        SpringApplication.run(WechatSpringbootDemoApp.class, args);
    }
}
