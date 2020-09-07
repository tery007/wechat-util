package com.meihaofenqi.web;

import com.alibaba.fastjson.JSON;
import com.meihaofenqi.base.event.InFollowEvent;
import com.meihaofenqi.base.msg.in.InTextMsg;
import com.meihaofenqi.base.msg.out.OutTextMsg;
import com.meihaofenqi.jfinal.MsgControllerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/27
 **/
@Slf4j
public class WeixinMsgController extends MsgControllerAdapter {

    /**
     * {"content":"你好，我是xxx","createTime":1472551036,"fromUserName":"oiQcv1MDML-CcghyxFu4Etc1JwLI","msgId":"6065077606992462276","msgType":"text","toUserName":"shoushitou"}
     */
    @Override
    protected void processInTextMsg(InTextMsg textMsg) {
        log.info("==>recive text msg:{}", JSON.toJSONString(textMsg));
        OutTextMsg outMsg = new OutTextMsg(textMsg);
        outMsg.setContent("文本消息~");
        /**
         * 微信端接收到的返回结果:
         * 200 OK
         * Connection: keep-alive
         * Date: Thu, 03 Sep 2020 02:54:28 GMT
         * Transfer-Encoding: chunked
         * Content-Type: text/xml;charset=UTF-8
         * <xml>
         *     <ToUserName>
         *         <![CDATA[oiQcv1MDML-CcghyxFu4Etc1JwLI]]>
         *     </ToUserName>
         *     <FromUserName>
         *         <![CDATA[shoushitou]]>
         *     </FromUserName>
         *     <CreateTime>1599101668</CreateTime>
         *     <MsgType>
         *         <![CDATA[text]]>
         *     </MsgType>
         *     <Content>
         *         <![CDATA[文本消息~]]>
         *     </Content>
         * </xml>
         * */
        render(outMsg);
    }

    @Override
    protected void processInFollowEvent(InFollowEvent inFollowEvent) {
        if (InFollowEvent.EVENT_INFOLLOW_SUBSCRIBE.equals(inFollowEvent.getEvent())) {
            log.debug("关注：" + inFollowEvent.getFromUserName());
            OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
            outMsg.setContent("感谢您的关注");
            render(outMsg);
        }
        // 如果为取消关注事件，将无法接收到传回的信息
        if (InFollowEvent.EVENT_INFOLLOW_UNSUBSCRIBE.equals(inFollowEvent.getEvent())) {
            log.debug("取消关注：" + inFollowEvent.getFromUserName());
        }
    }


}
