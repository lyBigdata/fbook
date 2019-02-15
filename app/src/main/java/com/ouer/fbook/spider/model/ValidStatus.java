package com.ouer.fbook.spider.model;

/**
 * @author hetao
 * @date 2019/1/9
 */
public enum ValidStatus {

    READY(0,"准备"), RUN(1,"运行中"),FINISH(2,"完成"),STOP(3, "停止");
    private Integer value = 0;
    private String desc;


    private ValidStatus(Integer value, String desc) {
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
