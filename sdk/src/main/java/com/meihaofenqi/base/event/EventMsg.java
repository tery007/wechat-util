package com.meihaofenqi.base.event;

import com.meihaofenqi.base.msg.in.InMsg;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/27
 **/
public abstract class EventMsg extends InMsg {

    private static final String MSG_TYPE = "event";
    protected String event;

    public EventMsg(String toUserName, String fromUserName, Integer createTime, String event) {
        super(toUserName, fromUserName, createTime, MSG_TYPE);
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
