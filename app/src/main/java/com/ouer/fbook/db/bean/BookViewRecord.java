package com.ouer.fbook.db.bean;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class BookViewRecord extends BaseDbBean {

    @DatabaseField(columnName = CstColumn.BookViewRecord.BookContentId)
    private Long bookContentId;
    @DatabaseField(columnName = CstColumn.BookViewRecord.ChapterOrder)
    private Integer chapterOrder;
    @DatabaseField(columnName = CstColumn.BookViewRecord.ChapterName)
    private String chapterName;
    @DatabaseField(columnName = CstColumn.BookViewRecord.IsShelf)
    private Integer isShelf;
    @DatabaseField(columnName = CstColumn.BookViewRecord.Page)
    private Integer page;
    @DatabaseField(columnName = CstColumn.BookViewRecord.UpdateAt)
    private Date updateAt;

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

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Integer getIsShelf() {
        return isShelf;
    }

    public void setIsShelf(Integer isShelf) {
        this.isShelf = isShelf;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
