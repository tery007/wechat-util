package com.meihaofenqi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/28
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "wechat")
public class WechatAppConfig {

    private String appId;
    private String appSecret;
    private String token;


}
