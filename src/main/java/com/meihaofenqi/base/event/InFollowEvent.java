package com.meihaofenqi.base.event;


/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/27
 **/
public class InFollowEvent extends EventMsg {

    // 订阅：subscribe
    public static final String EVENT_INFOLLOW_SUBSCRIBE   = "subscribe";
    // 取消订阅：unsubscribe
    public static final String EVENT_INFOLLOW_UNSUBSCRIBE = "unsubscribe";

    private String eventKey;

    public InFollowEvent(String toUserName, String fromUserName, Integer createTime, String event) {
        super(toUserName, fromUserName, createTime, event);
    }

    public InFollowEvent(String toUserName, String fromUserName, Integer createTime, String event, String eventKey) {
        super(toUserName, fromUserName, createTime, event);
        this.eventKey = eventKey;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
