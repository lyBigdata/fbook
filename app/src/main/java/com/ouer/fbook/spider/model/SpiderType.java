package com.ouer.fbook.spider.model;

/**
 * @author hetao
 * @date 2019/1/9
 */
public enum SpiderType {

    BOOK(0,"书地址"), CHAPTER(1,"书目录"), SEARCH_DETAIL(10, "搜索书简介");
    private Integer value = 0;
    private String desc;


    private SpiderType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;

    }

    public Integer value() {
        return value;
    }

    public String getDesc(){
        return desc;
    }
}
