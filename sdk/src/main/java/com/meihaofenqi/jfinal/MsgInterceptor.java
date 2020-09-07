package com.meihaofenqi.jfinal;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.meihaofenqi.base.ApiConfig;
import com.meihaofenqi.base.ApiConfigKit;
import com.meihaofenqi.utils.encrypt.SignatureCheckKit;

public class MsgInterceptor implements Interceptor {


    private static final Log log = Log.getLog(MsgInterceptor.class);

    private static AppIdParser _parser = new AppIdParser.DefaultParameterAppIdParser();


    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        if (!(controller instanceof MsgController)) {
            throw new RuntimeException("控制器需要继承 MsgController");
        }
        try {
            String appId = _parser.getAppId(controller);
            // 获取配置
            ApiConfig apiConfig = ApiConfigKit.getApiConfig(appId);
            String token = apiConfig.getToken();
            // 如果是服务器配置请求，则配置服务器并返回
            if (isConfigServerRequest(controller)) {
                configServer(controller, token);
                return;
            }
            inv.invoke();
            // 签名检测
//            if (checkSignature(controller, token)) {
//                inv.invoke();
//            } else {
//                controller.renderText("签名验证失败，请确定是微信服务器在发送消息过来");
//            }
        } catch (Exception e) {
            log.error("intercept error:{}", e.getMessage());
        }
    }

    /**
     * 检测签名
     */
    private boolean checkSignature(Controller controller, String token) {
        String signature = controller.getPara("signature");
        String timestamp = controller.getPara("timestamp");
        String nonce = controller.getPara("nonce");
        if (StrKit.isBlank(signature) || StrKit.isBlank(timestamp) || StrKit.isBlank(nonce)) {
            controller.renderText("check signature failure");
            return false;
        }

        if (SignatureCheckKit.me.checkSignature(signature, token, timestamp, nonce)) {
            return true;
        } else {
            log.error("check signature failure: " +
                    " signature = " + controller.getPara("signature") +
                    " timestamp = " + controller.getPara("timestamp") +
                    " nonce = " + controller.getPara("nonce"));

            return false;
        }
    }

    /**
     * 是否为开发者中心保存服务器配置的请求
     */
    private boolean isConfigServerRequest(Controller controller) {
        return StrKit.notBlank(controller.getPara("echostr"));
    }

    /**
     * 配置开发者中心微信服务器所需的 url 与 token
     *
     * @param c 控制器
     */
    public void configServer(Controller c, String token) {
        // 通过 echostr 判断请求是否为配置微信服务器回调所需的 url 与 token
        String echostr = c.getPara("echostr");
        String signature = c.getPara("signature");
        String timestamp = c.getPara("timestamp");
        String nonce = c.getPara("nonce");
        boolean isOk = SignatureCheckKit.me.checkSignature(signature, token, timestamp, nonce);
        if (isOk) {
            c.renderText(echostr);
        } else {
            log.error("验证失败：configServer");
        }
    }
}
