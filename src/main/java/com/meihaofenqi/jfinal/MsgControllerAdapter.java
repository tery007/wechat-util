package com.meihaofenqi.jfinal;

import com.meihaofenqi.base.event.InFollowEvent;
import com.meihaofenqi.base.msg.in.InNotDefinedMsg;
import com.meihaofenqi.base.msg.in.InTextMsg;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/9/1
 **/
public abstract class MsgControllerAdapter extends MsgController {


    @Override
    protected abstract void processInTextMsg(InTextMsg textMsg);

    @Override
    protected abstract void processInFollowEvent(InFollowEvent followEvent);

    @Override
    protected void processIsNotDefinedMsg(InNotDefinedMsg notDefinedMsg) {
        renderDefault();
    }

    /**
     * 方便没有使用的api返回“”,避免出现:该公众号暂时不能提供服务
     * <p>
     * 可重写该方法
     */
    protected void renderDefault() {
        renderText("");
    }
}
