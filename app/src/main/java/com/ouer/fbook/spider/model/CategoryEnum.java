package com.ouer.fbook.spider.model;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hetao
 * @date 2019/1/10
 */
public enum  CategoryEnum {

    OTHER(0,"其他类目"), XUANHUAN(1,"玄幻小说"), XIUZHEN(2,"修真小说"), DUSHI(3,"都市小说"),
    LISHI(1,"历史小说"), WANGYOU(1,"网游小说"), KEHUAN(1,"科幻小说");
    private Integer value = 0;
    private String desc;

    private static SparseArray<CategoryEnum> map = new SparseArray<CategoryEnum>(4);
    private static Map<String , CategoryEnum> map2 = new HashMap<>(4);

    static {
        for (CategoryEnum type : CategoryEnum.values()) {
            map.put(type.value, type);
            map2.put(type.desc, type);
        }
    }

    private CategoryEnum(Integer value,String desc) {
        this.value = value;
        this.desc =  desc;
    }

    public static CategoryEnum valueOf(Integer value) {
        return map.get(value,OTHER);
    }


    public static CategoryEnum parseDesc(String value) {
        return map2.containsKey(value)?map2.get(value):OTHER;
    }


    public Integer value() {
        return value;
    }

    public String getDesc(){
        return desc;
    }
}
