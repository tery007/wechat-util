package com.meihaofenqi.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/18
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiConfig implements Serializable {

    private static final long serialVersionUID = 5243926308290263767L;

    private String  token          = null;
    private String  appId          = null;
    private String  appSecret      = null;
    private String  encodingAesKey = null;
    /** 消息是否加密 */
    private boolean messageEncrypt = false;

}
