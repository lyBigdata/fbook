package com.ouer.fbook.db.bean;


import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class BookContentChapter extends BaseDbBean {
    /** 书主键 */
    @DatabaseField(columnName = CstColumn.BookContentChapter.BookContentId)
    private Long bookContentId;

    /** 第几章 */
    @DatabaseField(columnName = CstColumn.BookContentChapter.ChapterOrder)
    private Integer chapterOrder;

    /** 章节名 */
    @DatabaseField(columnName = CstColumn.BookContentChapter.ChapterName)
    private String chapterName;

    /** 字数 */
    @DatabaseField(columnName = CstColumn.BookContentChapter.WordsCount)
    private Long wordsCount;

    /** 文本 */
    @DatabaseField(columnName = CstColumn.BookContentChapter.Content)
    private String content;
    @DatabaseField(columnName = CstColumn.BookContentChapter.UpdatedAt)
    private Date updatedAt;


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

    public Long getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(Long wordsCount) {
        this.wordsCount = wordsCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}