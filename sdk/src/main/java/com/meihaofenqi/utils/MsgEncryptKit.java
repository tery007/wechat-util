package com.meihaofenqi.utils;

import com.meihaofenqi.base.ApiConfig;
import com.meihaofenqi.exception.AesException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class MsgEncryptKit {

    private static final String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";

    /**
     * 加密公众号消息
     *
     * @param msg
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String encrypt(String msg, String timestamp, String nonce, ApiConfig ac) {
        try {
            return encryptMsg(msg, timestamp, nonce, ac.getAppId(), ac.getToken(), ac.getEncodingAesKey());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密公众号消息
     *
     * @param encryptedMsg
     * @param timestamp
     * @param nonce
     * @param msgSignature
     * @return
     */
    public static String decrypt(String encryptedMsg, String timestamp, String nonce, String msgSignature, ApiConfig ac) {
        try {
            return decryptMsg(encryptedMsg, timestamp, nonce, msgSignature, ac.getAppId(), ac.getToken(), ac.getEncodingAesKey());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 解密消息
     *
     * @param encryptedMsg
     * @param timestamp
     * @param nonce
     * @param msgSignature
     * @param appId
     * @param token
     * @param encodingAesKey
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws AesException
     */
    private static String decryptMsg(String encryptedMsg, String timestamp, String nonce, String msgSignature,
                                     String appId, String token, String encodingAesKey)
            throws ParserConfigurationException, SAXException, IOException, AesException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        StringReader sr = new StringReader(encryptedMsg);
        InputSource is = new InputSource(sr);
        Document document = db.parse(is);

        Element root = document.getDocumentElement();
        NodeList nodelist1 = root.getElementsByTagName("Encrypt");
        // NodeList nodelist2 = root.getElementsByTagName("MsgSignature");

        String encrypt = nodelist1.item(0).getTextContent();
        // String msgSignature = nodelist2.item(0).getTextContent();

        String fromXML = String.format(format, encrypt);

        if (encodingAesKey == null) {
            throw new IllegalStateException("encodingAesKey can not be null, config encodingAesKey first.");
        }

        WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
        // 此处 timestamp 如果与加密前的不同则报签名不正确的异常
        return pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
    }

    /**
     * 加密消息
     *
     * @param msg
     * @param timestamp
     * @param nonce
     * @param appId
     * @param token
     * @param encodingAesKey
     * @return
     * @throws AesException
     */
    private static String encryptMsg(String msg, String timestamp, String nonce, String appId, String token,
                                     String encodingAesKey) throws AesException {
        WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
        return pc.encryptMsg(msg, timestamp, nonce);
    }
}
