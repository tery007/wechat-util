package com.meihaofenqi.wechat.sdk.base;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author wanglei
 * @description: 参数treeMap简单封装
 * @date Created on 2020/8/19
 **/
public class ParaMap {

    private Map<String, String> data = new TreeMap<>();

    private ParaMap() {
    }

    public static ParaMap create() {
        return new ParaMap();
    }

    public static ParaMap create(String key, String value) {
        return create().put(key, value);
    }

    public ParaMap put(String key, String value) {
        data.put(key, value);
        return this;
    }

    public Map<String, String> getData() {
        return data;
    }
}
