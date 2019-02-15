package com.ouer.fbook.spider.site;


import com.ouer.fbook.db.bean.BookContent;
import com.ouer.fbook.db.bean.BookContentChapter;
import com.ouer.fbook.db.bean.BookSpiderBook;
import com.ouer.fbook.spider.model.ParseBookInfo;
import com.ouer.fbook.spider.model.SiteEnum;

import java.util.List;

/**
 * @author hetao
 * @date 2019/1/9
 */
public interface IParseHtml {
    /**
     * 识别key
     * @return
     */
    SiteEnum getKey();

    /**
     * 从html中解析出
     * @param body
     * @return
     */
    ParseBookInfo parseBook(String body);

    /**
     * 从html中解析出章节内容
     * @param body
     * @return
     */
    BookContentChapter parseChapter(String body);


    String getBaseUrl();


    List<BookSpiderBook> findBookByCategory(String body);


    List<BookContent> searchBook(String body);

}
