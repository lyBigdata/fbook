package com.ouer.fbook.spider.site;

import com.ouer.fbook.BookApplication;
import com.ouer.fbook.db.bean.BookContent;
import com.ouer.fbook.db.bean.BookContentChapter;
import com.ouer.fbook.db.bean.BookSpiderChapter;
import com.ouer.fbook.db.dao.BookContentChapterDao;
import com.ouer.fbook.db.dao.BookContentDao;
import com.ouer.fbook.db.dao.BookSpiderBookDao;
import com.ouer.fbook.db.dao.BookSpiderChapterDao;
import com.ouer.fbook.event.RefreshBook;
import com.ouer.fbook.spider.model.ExpiredDateCst;
import com.ouer.fbook.spider.model.ParseBookInfo;
import com.ouer.fbook.spider.model.SiteEnum;
import com.ouer.fbook.spider.model.ValidStatus;
import com.ouer.fbook.util.CollectionUtils;
import com.ouer.fbook.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author hetao
 * @date 2019/1/9
 */
public class SiteParseHelper {

    private BookSpiderBookDao spiderBookService         = BookApplication.bookDao;

    private BookSpiderChapterDao chapterService         = BookApplication.spiderChapterDao;

    private BookContentDao bookContentService           = BookApplication.contentDao;

    private BookContentChapterDao contentChapterService = BookApplication.chapterDao;


    private Map<SiteEnum, IParseHtml> parseMap = new HashMap<>(4);
    private static SiteParseHelper instance;

    public static SiteParseHelper getInstance() {
        if (instance == null) {
            synchronized (SiteParseHelper.class) {
                try {
                    instance = new SiteParseHelper();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return instance;
    }

    public void init() {
        List<IParseHtml> parseList = new ArrayList<>();
        parseList.add(new YGWHParse());

        for(IParseHtml parse : parseList){
            if(parse.getKey()!=null){
                LogUtils.i("SiteParseHelper --keyName:{}", parse.getKey().name());
                parseMap.put(parse.getKey(), parse);
            }
        }
    }

    public List<BookContent> getSearchDetail(String body) {

        IParseHtml baseParse = parseMap.get(BookApplication.userConfig.line);
        if(baseParse == null) {
            LogUtils.e("BookHelper baseParse is null", BookApplication.userConfig.line.name());
            return null;
        }

        return baseParse.searchBook(body);

    }

    public BookContentChapter getChapterDetail(BookSpiderChapter spiderChapter, String body) throws Exception{
        String source = spiderChapter.getSourceType();
        IParseHtml baseParse = parseMap.get(SiteEnum.valueOf(source));
        if(baseParse == null) {
            LogUtils.e("BookHelper baseParse is null", source);
            return null;
        }

        BookContentChapter chapter= baseParse.parseChapter(body);
        handlerChapterDetail(spiderChapter, chapter);
        return chapter;
    }

    public BookContent getSpiderChapter(BookContent detail, String body) throws Exception{

        IParseHtml baseParse = parseMap.get(BookApplication.userConfig.line);
        if(baseParse == null) {
            throw new Exception("BookHelper baseParse is null" + BookApplication.userConfig.line);
        }

        ParseBookInfo parseBookInfo = baseParse.parseBook(body);
        return handlerUpdateChapterList(detail, parseBookInfo);

    }


    public BookContent handlerUpdateChapterList(BookContent detail, ParseBookInfo bookInfo) throws Exception {
        if(bookInfo == null || bookInfo.getBookContent() == null || CollectionUtils.isEmpty(bookInfo.getChapterList())) {
            throw new Exception("SiteParseHelper parse fail");
        }

        BookContent bookContent = bookInfo.getBookContent();
        List<BookSpiderChapter> chapterList = bookInfo.getChapterList();

        BookSpiderChapter lastChapter = chapterList.get(chapterList.size()-1);
        //源+书名找是否已经存在这本书，如果存在，则是更新逻辑，若不存在，需要先新增数
        BookContent dbBookContent = bookContentService.findBookByName(bookContent.getSourceType(),
                bookContent.getBookName());
        if(dbBookContent == null) {
            dbBookContent = bookContent;
            dbBookContent.setSourceType(bookContent.getSourceType());
            dbBookContent.setValid(ValidStatus.READY.value());
            if(bookContent.getUpdateAt() == null) {
                dbBookContent.setUpdateAt(new Date());
            } else {
                dbBookContent.setUpdateAt(bookContent.getUpdateAt());
            }
            dbBookContent.setLink(detail.getLink());
            dbBookContent.setLastChapterName(lastChapter.getChapterName());
            dbBookContent.setLastChapterOrder(chapterList.size()-1);
            dbBookContent.setNextAt(new Date(System.currentTimeMillis() + ExpiredDateCst.DAY_MIS));
            bookContentService.add(dbBookContent);
        } else {
            //这些数据都会自动计算
            dbBookContent.setCover(bookContent.getCover());
            dbBookContent.setIntroduc(bookContent.getIntroduc());
            dbBookContent.setUpdateAt(new Date());
            dbBookContent.setLink(detail.getLink());
            dbBookContent.setLastChapterName(lastChapter.getChapterName());
            dbBookContent.setLastChapterOrder(chapterList.size()-1);
            bookContentService.update(dbBookContent);
        }

        if(dbBookContent.getId() == null) {
            throw new Exception("SiteParseHelper save db fail");
        }

        //新增，更新章节爬取字段
        List<BookSpiderChapter> dbChapterList= chapterService.findByBookId(dbBookContent.getId());
        List<BookSpiderChapter> addList = new ArrayList<>();
        List<BookSpiderChapter> updateList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(dbChapterList)) {
            for (BookSpiderChapter chapter : chapterList) {
                boolean isSave = false;
                for (BookSpiderChapter dbChapter : dbChapterList) {
                    //找到对应章节
                    if (dbChapter.getChapterOrder().compareTo(chapter.getChapterOrder()) == 0) {

                        //比较链接是否更新
                        if(!dbChapter.getLink().equalsIgnoreCase(chapter.getLink())) {
                            //已更新，需要替换
                            dbChapter.setLink(chapter.getLink());
                            updateList.add(dbChapter);
                        }

                        isSave=true;
                        break;
                    }
                }

                if (!isSave) {
                    addList.add(chapter);
                }
            }
        } else{
            addList = chapterList;
        }

        if(CollectionUtils.isNotEmpty(addList)) {
            for (BookSpiderChapter chapter:addList) {
                chapter.setBookContentId(dbBookContent.getId());
                chapter.setSourceType(dbBookContent.getSourceType());
                chapter.setValid(ValidStatus.READY.value());
            }

            chapterService.insertBatch(addList);
        }
        if(CollectionUtils.isNotEmpty(updateList)) {
            for (BookSpiderChapter chapter:updateList) {
                chapterService.update(chapter);
            }
        }

        EventBus.getDefault().post(new RefreshBook(true));
        return dbBookContent;
    }


    public void handlerChapterDetail(BookSpiderChapter spiderChapter, BookContentChapter chapter) throws Exception{
        if(chapter == null || chapter.getContent() == null) {
            LogUtils.i("SiteParseHelper parse fail");
            return;
        }

        //源+书名找是否已经存在这本书，如果存在，则是更新逻辑，若不存在，需要先新增数
        BookContentChapter dbChapter = contentChapterService.findByBookIdOrder(spiderChapter.getBookContentId(),
                spiderChapter.getChapterOrder());
        if(dbChapter == null) {
            dbChapter = chapter;
            dbChapter.setBookContentId(spiderChapter.getBookContentId());
            dbChapter.setChapterOrder(spiderChapter.getChapterOrder());
            dbChapter.setUpdatedAt(new Date());
            contentChapterService.add(dbChapter);
        } else {
            dbChapter.setWordsCount(chapter.getWordsCount());
            dbChapter.setBookContentId(chapter.getBookContentId());
            dbChapter.setChapterOrder(chapter.getChapterOrder());
            dbChapter.setContent(chapter.getContent());
            dbChapter.setChapterName(chapter.getChapterName());
            dbChapter.setUpdatedAt(new Date());
            contentChapterService.update(dbChapter);
        }

        if(dbChapter.getId() == null) {
            LogUtils.i("SiteParseHelper save db fail");
            return;
        }

        spiderChapter.setValid(ValidStatus.FINISH.value());
        chapterService.update(spiderChapter);
    }


}
