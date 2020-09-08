package com.meihaofenqi.base.msg.out;

import com.meihaofenqi.base.msg.in.InMsg;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/31
 **/
public class OutTextMsg extends OutMsg{
    private String content;

    public OutTextMsg() {
        this.msgType = "text";
    }

    public OutTextMsg(InMsg inMsg) {
        super(inMsg);
        this.msgType = "text";
    }

    @Override
    protected void subXml(StringBuilder sb) {
        if (null == content) {
            throw new NullPointerException("content is null");
        }
        sb.append("<Content><![CDATA[").append(content).append("]]></Content>\n");
    }

    public String getContent() {
        return content;
    }

    public OutTextMsg setContent(String content) {
        this.content = content;
        return this;
    }
}
