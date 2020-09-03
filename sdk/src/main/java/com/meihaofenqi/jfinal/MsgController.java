package com.meihaofenqi.jfinal;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;
import com.meihaofenqi.base.ApiConfig;
import com.meihaofenqi.base.ApiConfigKit;
import com.meihaofenqi.base.event.InFollowEvent;
import com.meihaofenqi.base.msg.in.InMsg;
import com.meihaofenqi.base.msg.in.InNotDefinedMsg;
import com.meihaofenqi.base.msg.in.InTextMsg;
import com.meihaofenqi.base.msg.in.MsgParser;
import com.meihaofenqi.base.msg.out.OutMsg;
import com.meihaofenqi.utils.HttpUtils;
import com.meihaofenqi.utils.MsgEncryptKit;
import com.meihaofenqi.utils.encrypt.SignatureCheckKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/26
 **/
@Slf4j
public abstract class MsgController extends Controller {

    // 本次请求 xml数据
    private String inMsgXml = null;

    // 本次请求 xml 解析后的 Msg 对象
    private InMsg inMsg = null;

    @Override
    protected void _clear_() {
        super._clear_();
        this.inMsgXml = null;
        this.inMsg = null;
    }

    /**
     * 处理接收到的文本消息
     *
     * @param textMsg 处理接收到的文本消息
     */
    protected abstract void processInTextMsg(InTextMsg textMsg);


    /**
     * 处理接收到的关注/取消关注事件
     *
     * @param followEvent 处理接收到的关注/取消关注事件
     */
    protected abstract void processInFollowEvent(InFollowEvent followEvent);

    /**
     * 没有找到对应的消息
     *
     * @param notDefinedMsg 没有对应消息
     */
    protected abstract void processIsNotDefinedMsg(InNotDefinedMsg notDefinedMsg);

    /**
     * weixin 公众号服务器调用唯一入口
     */
    @Before(MsgInterceptor.class)
    public void index() {
        // 解析消息并根据消息类型分发到相应的处理方法
        InMsg msg = getMsg();

        if (msg instanceof InTextMsg) {
            processInTextMsg((InTextMsg) msg);
        } else if (msg instanceof InFollowEvent) {
            processInFollowEvent((InFollowEvent) msg);
        } else {
            log.error("尚未实现的消息类型。 消息 xml 内容为：\n" + getMsgXml());
            processIsNotDefinedMsg((InNotDefinedMsg) msg);
        }
    }

    public InMsg getMsg() {
        if (inMsg == null) {
            inMsg = MsgParser.parse(getMsgXml());
        }
        return inMsg;
    }

    public String getMsgXml() {
        if (inMsgXml == null) {
            inMsgXml = HttpUtils.readData(getRequest());
            // 是否需要解密消息
            if (getApiConfig().isMessageEncrypt()) {
                inMsgXml = MsgEncryptKit.decrypt(inMsgXml, getPara("timestamp"), getPara("nonce"), getPara("msg_signature"), getApiConfig());
            }
        }
        if (StringUtils.isBlank(inMsgXml)) {
            throw new RuntimeException("不要在浏览器中请求该连接");
        }
        return inMsgXml;
    }

    private ApiConfig getApiConfig() {
        return ApiConfigKit.getApiConfig(getPara("appId"));
    }

    /**
     * 配置开发者中心微信服务器所需的 url 与 token
     *
     * @param ac 控制器
     */
    public String configServer(HttpServletRequest request, ApiConfig ac) {
        // 通过 echostr 判断请求是否为配置微信服务器回调所需的 url 与 token
        String echostr = request.getParameter("echostr");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        boolean isOk = SignatureCheckKit.me.checkSignature(signature, ac.getToken(), timestamp, nonce);
        if (isOk) {
            return echostr;
        } else {
            log.error("验证失败：configServer");
            return "";
        }
    }

    /**
     * 检测签名
     */
    public boolean checkSignature(HttpServletRequest request, ApiConfig ac) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        if (StringUtils.isBlank(signature) || StringUtils.isBlank(timestamp) || StringUtils.isBlank(nonce)) {
            log.error("==>request params has null value");
            return false;
        }

        if (SignatureCheckKit.me.checkSignature(signature, ac.getToken(), timestamp, nonce)) {
            return true;
        } else {
            log.error("check signature failure: " +
                    " signature = " + request.getParameter("signature") +
                    " timestamp = " + request.getParameter("timestamp") +
                    " nonce = " + request.getParameter("nonce"));

            return false;
        }
    }

    /**
     * 在接收到微信服务器的 InMsg 消息后后响应 OutMsg 消息
     *
     * @param outMsg 输出对象
     */
    @NotAction
    public void render(OutMsg outMsg) {
        String outMsgXml = outMsg.toXml();
        // 是否需要加密消息
        if (getApiConfig().isMessageEncrypt()) {
            outMsgXml = MsgEncryptKit.encrypt(outMsgXml, getPara("timestamp"), getPara("nonce"), getApiConfig());
        }

        renderText(outMsgXml, "text/xml");
    }

}
