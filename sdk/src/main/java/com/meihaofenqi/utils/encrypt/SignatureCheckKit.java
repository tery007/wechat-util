package com.meihaofenqi.utils.encrypt;

import java.util.Arrays;

public class SignatureCheckKit {

    public static final SignatureCheckKit me = new SignatureCheckKit();

    /**
     * </pre>
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce 随机字符串
     * @return {boolean}
     */
    public boolean checkSignature(String signature, String token, String timestamp, String nonce) {
        String[] array = {token, timestamp, nonce};
        Arrays.sort(array);
        String tempStr = array[0] + array[1] + array[2];
        tempStr = HashKit.sha1(tempStr);
        return tempStr.equalsIgnoreCase(signature);
    }

}
