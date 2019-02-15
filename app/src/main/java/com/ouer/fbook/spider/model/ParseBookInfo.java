package com.ouer.fbook.spider.model;


import com.ouer.fbook.db.bean.BookContent;
import com.ouer.fbook.db.bean.BookSpiderChapter;

import java.util.List;

/**
 * @author hetao
 * @date 2019/1/10
 */
public class ParseBookInfo {

    BookContent bookContent;

    List<BookSpiderChapter> chapterList;

    public BookContent getBookContent() {
        return bookContent;
    }

    public void setBookContent(BookContent bookContent) {
        this.bookContent = bookContent;
    }

    public List<BookSpiderChapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<BookSpiderChapter> chapterList) {
        this.chapterList = chapterList;
    }
}
