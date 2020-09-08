package com.meihaofenqi.utils;


import com.meihaofenqi.base.ApiConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * @author wanglei
 * @description
 * @date Created on 2020/8/18
 **/
@Slf4j
public class RetryUtils {


    public interface ResultCheck {
        /** 数据是否匹配 */
        boolean matching(ApiConfig ac);

        String getJson();
    }

    /**
     * 遇到异常时尝试重试
     *
     * @param <V>        结果值范型
     * @param retryCount 重试次数
     * @param callable   重试次数
     * @param ac
     * @return 结果值
     */
    public static <V extends ResultCheck> V retryOnException(int retryCount, Callable<V> callable, ApiConfig ac) {
        V v = null;
        for (int i = 0; i < retryCount; i++) {
            try {
                v = callable.call();
            } catch (Exception e) {
                log.warn("retry on " + (i + 1) + " times v = " + (v == null ? null : v.getJson()), e);
            }
            if (Objects.nonNull(v) && v.matching(ac)) {
                break;
            }
            log.error("retry on " + (i + 1) + " times but not matching v = " + (v == null ? null : v.getJson()));
        }
        return v;
    }


}
