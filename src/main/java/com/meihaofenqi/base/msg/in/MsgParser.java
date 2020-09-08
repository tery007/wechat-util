package com.meihaofenqi.base.msg.in;

import com.meihaofenqi.base.event.InFollowEvent;
import com.meihaofenqi.base.event.NotDefinedEvent;
import com.meihaofenqi.utils.XmlHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/27
 **/
@Slf4j
public class MsgParser {

    /**
     * 从 xml 中解析出各类消息与事件
     * @param xml xml字符串
     * @return {InMsg}
     */
    public static InMsg parse(String xml) {
        XmlHelper xmlHelper = XmlHelper.of(xml);
        return doParse(xmlHelper);
    }


    /**
     * 消息类型
     * 1：text 文本消息
     * 2：image 图片消息
     * 3：voice 语音消息
     * 4：video 视频消息
     *    shortvideo 小视频消息
     * 5：location 地址位置消息
     * 6：link 链接消息
     * 7：event 事件
     */
    private static InMsg doParse(XmlHelper xmlHelper) {
        String toUserName = xmlHelper.getString("//ToUserName");
        String fromUserName = xmlHelper.getString("//FromUserName");
        Integer createTime = xmlHelper.getNumber("//CreateTime").intValue();
        String msgType = xmlHelper.getString("//MsgType");
        if ("text".equals(msgType)) {
            return parseInTextMsg(xmlHelper, toUserName, fromUserName, createTime, msgType);
        }
        if ("event".equals(msgType)) {
            return parseEvent(xmlHelper, toUserName, fromUserName, createTime, msgType);
        } else {
            log.error("尚未实现该消息类型 " + msgType + "，请查阅微信公众平台开发文档");
            return parseInNotDefinedMsg(xmlHelper, toUserName, fromUserName, createTime, msgType);
        }
    }

    private static InMsg parseInTextMsg(XmlHelper xmlHelper, String toUserName, String fromUserName, Integer createTime, String msgType) {
        InTextMsg msg = new InTextMsg(toUserName, fromUserName, createTime, msgType);
        msg.setContent(xmlHelper.getString("//Content"));
        msg.setMsgId(xmlHelper.getString("//MsgId"));
        return msg;
    }


    // 解析各种事件
    private static InMsg parseEvent(XmlHelper xmlHelper, String toUserName, String fromUserName, Integer createTime, String msgType) {
        String event = xmlHelper.getString("//Event");
        String eventKey = xmlHelper.getString("//EventKey");

        /**
         * 取消关注事件
         * 注意：由于微信平台出现bug， unsubscribe 事件在有些时候居然多出了 eventKey 值，
         * 多出来的eventKey值例如：last_trade_no_4003942001201604205023621558
         *     所以 unsubscribe 与 subscribe 判断进行了拆分，并且将 unsubscribe
         *     挪至最前面进行判断，以便消除微信平台 bug 的影响
         */
        if ("unsubscribe".equals(event)) {
            return new InFollowEvent(toUserName, fromUserName, createTime, event,eventKey);
        }

        if ("subscribe".equals(event)) {
            return new InFollowEvent(toUserName, fromUserName, createTime, event,eventKey);
        }

        log.error("无法识别的事件类型" + event + "，请查阅微信公众平台开发文档");
        NotDefinedEvent e = new NotDefinedEvent(toUserName, fromUserName, createTime, event);
        e.setXmlHelper(xmlHelper);
        return e;
    }

    private static InMsg parseInNotDefinedMsg(XmlHelper xmlHelper, String toUserName, String fromUserName, Integer createTime, String msgType) {
        InNotDefinedMsg msg = new InNotDefinedMsg(toUserName, fromUserName, createTime, msgType);
        msg.setXmlHelper(xmlHelper);
        return msg;
    }
}
