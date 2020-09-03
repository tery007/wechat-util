package com.meihaofenqi.base.msg.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/26
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InMsg {

    /** 开发者微信号 */
    protected String toUserName;

    /** 发送方帐号（一个OpenID） */
    protected String fromUserName;

    /** 消息创建时间 （整型） */
    protected Integer createTime;

    /**
     * 消息类型
     * 1：text 文本消息
     * 2：image 图片消息
     * 3：voice 语音消息
     * 4：video 视频消息
     * 5：location 地址位置消息
     * 6：link 链接消息
     * 7：event 事件
     */
    protected String msgType;
}
