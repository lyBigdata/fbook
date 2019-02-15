package com.ouer.fbook.db.bean;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;
/**
 * @author hetao
 */
public class BookSpiderBook extends BaseDbBean {

    /** 下次执行时间 */
    @DatabaseField(columnName = CstColumn.BookSpiderBook.NextAt)
    private Date nextAt;

    /** 状态 0有效  1无效 */
    @DatabaseField(columnName = CstColumn.BookSpiderBook.Valid)
    private Integer valid;

    @DatabaseField(columnName = CstColumn.BookSpiderBook.BookName)
    private String bookName;

    /** 记录更新时间 */
    @DatabaseField(columnName = CstColumn.BookSpiderBook.UpdateAt)
    private Date updateAt;

    @DatabaseField(columnName = CstColumn.BookSpider.Link)
    private String link;

    public Date getNextAt() {
        return nextAt;
    }

    public void setNextAt(Date nextAt) {
        this.nextAt = nextAt;
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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}