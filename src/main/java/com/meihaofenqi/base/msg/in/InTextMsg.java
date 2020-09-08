package com.meihaofenqi.base.msg.in;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/26
 **/
public class InTextMsg extends InMsg {

    private String content;
    private String msgId;

    public InTextMsg(String toUserName, String fromUserName, Integer createTime, String msgType) {
        super(toUserName, fromUserName, createTime, msgType);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

}
