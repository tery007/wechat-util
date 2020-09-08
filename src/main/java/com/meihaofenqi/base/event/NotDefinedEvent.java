package com.meihaofenqi.base.event;

import com.meihaofenqi.utils.XmlHelper;

/**
 * @author wanglei
 * @description:未适配类型时的事件
 * @date Created on 2020/8/27
 **/
public class NotDefinedEvent extends EventMsg {
    /**
     * 新增xmlHelper，用于用户扩展。
     * 对于不支持的事件类型中，获取xml中想要的参数。
     */
    protected transient XmlHelper xmlHelper;

    public NotDefinedEvent(String toUserName, String fromUserName, Integer createTime, String event) {
        super(toUserName, fromUserName, createTime, event);
    }

    public XmlHelper getXmlHelper() {
        return xmlHelper;
    }

    public void setXmlHelper(XmlHelper xmlHelper) {
        this.xmlHelper = xmlHelper;
    }
}
