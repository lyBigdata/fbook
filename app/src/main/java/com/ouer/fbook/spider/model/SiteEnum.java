package com.ouer.fbook.spider.model;

import java.util.HashMap;

/**
 * @author hetao
 * @date 2019/1/9
 */
public enum SiteEnum {

    UNKNOWN(-1,"未知"), YANGGUI(1,"养鬼为患"), QIDIAN(2,"起点"),
    UPLOAD(100, "bos上传");
    private Integer value = 0;
    private String desc;

    private static HashMap<Integer, SiteEnum> map = new HashMap<>(4);

    static {
        for (SiteEnum type : SiteEnum.values()) {
            map.put(type.value, type);
        }
    }

    private SiteEnum(Integer value,String desc) {
        this.value = value;
        this.desc =  desc;
    }

    public static SiteEnum valueOf(Integer value) {
        if(map.containsKey(value)) {
            return map.get(value);
        } else {
            return UNKNOWN;
        }
    }

    public Integer value() {
        return value;
    }

    public String getDesc(){
        return desc;
    }
}
