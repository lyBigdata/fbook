package com.ouer.fbook.db.bean;


import com.j256.ormlite.field.DatabaseField;

/**
 * @author hetao
 * @date 2019/1/9
 */
public class BookSpider extends BaseDbBean {
    /** 来源 */
    @DatabaseField(columnName = CstColumn.BookSpider.SourceType)
    private String sourceType;
    /** 链接 */
    @DatabaseField(columnName = CstColumn.BookSpider.Link)
    private String link;
    /** 链接类型 */
    @DatabaseField(columnName = CstColumn.BookSpiderBook.LinkType)
    private String linkType;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }
}
