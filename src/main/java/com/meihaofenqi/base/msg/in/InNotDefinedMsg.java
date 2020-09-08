package com.meihaofenqi.base.msg.in;

import com.meihaofenqi.utils.XmlHelper;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/27
 **/
public class InNotDefinedMsg extends InMsg {

    /**
     * 新增xmlHelper，用于用户扩展。
     * 对于不支持的消息类型中，获取xml中想要的参数。
     */
    protected transient XmlHelper xmlHelper;

    public InNotDefinedMsg(String toUserName, String fromUserName, Integer createTime, String msgType) {
        super(toUserName, fromUserName, createTime, msgType);
    }

    public XmlHelper getXmlHelper() {
        return xmlHelper;
    }

    public void setXmlHelper(XmlHelper xmlHelper) {
        this.xmlHelper = xmlHelper;
    }
}
