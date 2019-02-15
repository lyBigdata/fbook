package com.ouer.fbook.spider.model;


import com.ouer.fbook.db.bean.BookSpider;

import java.io.Serializable;

/**
 * @author hetao
 * @date 2019/1/9
 */
public class BookParse implements Serializable {

    private BookSpider bookSpider;
    /** 链接 */
    private String body;

    public BookParse(BookSpider sourceType, String body) {
        this.bookSpider = sourceType;
        this.body = body;
    }

    public BookSpider getBookSpider() {
        return bookSpider;
    }

    public void setBookSpider(BookSpider bookSpider) {
        this.bookSpider = bookSpider;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
