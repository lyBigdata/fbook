package com.ouer.fbook.db.bean;


import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * @author hetao
 */
public class BookContent extends BaseDbBean {
    /** 来源 */
    @DatabaseField(columnName = CstColumn.BookContent.SourceType)
    private String sourceType;

    /** 书名 */
    @DatabaseField(columnName = CstColumn.BookContent.BookName)
    private String bookName;

    /** 作者 */
    @DatabaseField(columnName = CstColumn.BookContent.Author)
    private String author;

    /** 是否连载 0连载  1完成 */
    @DatabaseField(columnName = CstColumn.BookContent.IsFinish)
    private Integer isFinish;

    /** 简介 */
    @DatabaseField(columnName = CstColumn.BookContent.Introduce)
    private String introduc;

    /** 封面 */
    @DatabaseField(columnName = CstColumn.BookContent.Cover)
    private String cover;

    @DatabaseField(columnName = CstColumn.BookContent.Valid)
    private Integer valid;

    @DatabaseField(columnName = CstColumn.BookContent.Category)
    private String category;

    @DatabaseField(columnName = CstColumn.BookContent.LastChapterOrder)
    private Integer lastChapterOrder;

    @DatabaseField(columnName = CstColumn.BookContent.LastChapterName)
    private String lastChapterName;

    @DatabaseField(columnName = CstColumn.BookContent.NextAt)
    private Date nextAt;

    @DatabaseField(columnName = CstColumn.BookSpider.Link)
    private String link;

    @DatabaseField(columnName = CstColumn.BookContent.UpdateAt)
    private Date updateAt;

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    public String getIntroduc() {
        return introduc;
    }

    public void setIntroduc(String introduc) {
        this.introduc = introduc;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getLastChapterOrder() {
        return lastChapterOrder;
    }

    public void setLastChapterOrder(Integer lastChapterOrder) {
        this.lastChapterOrder = lastChapterOrder;
    }

    public String getLastChapterName() {
        return lastChapterName;
    }

    public void setLastChapterName(String lastChapterName) {
        this.lastChapterName = lastChapterName;
    }

    public Date getNextAt() {
        return nextAt;
    }

    public void setNextAt(Date nextAt) {
        this.nextAt = nextAt;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}