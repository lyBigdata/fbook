package com.ouer.fbook.db.bean;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;
/**
 * @author hetao
 */
public class BookSpiderChapter extends BookSpider {

    /** 书主键 */
    @DatabaseField(columnName = CstColumn.BookSpiderChapter.BookContentId)
    private Long bookContentId;

    /** 链接 */
    @DatabaseField(columnName = CstColumn.BookSpiderChapter.ChapterOrder)
    private Integer chapterOrder;

    /** 爬取状态 0未完成  1已完成 */
    @DatabaseField(columnName = CstColumn.BookSpiderChapter.Valid)
    private Integer valid;

    /** 记录更新时间 */
    @DatabaseField(columnName = CstColumn.BookSpiderChapter.UpdateAt)
    private Date updateAt;

    /** 记录更新时间 */
    @DatabaseField(columnName = CstColumn.BookSpiderChapter.ChapterName)
    private String chapterName;

    public Long getBookContentId() {
        return bookContentId;
    }

    public void setBookContentId(Long bookContentId) {
        this.bookContentId = bookContentId;
    }

    public Integer getChapterOrder() {
        return chapterOrder;
    }

    public void setChapterOrder(Integer chapterOrder) {
        this.chapterOrder = chapterOrder;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}