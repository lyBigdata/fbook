package com.ouer.fbook;

import android.app.Application;

import com.ouer.fbook.db.dao.BookContentChapterDao;
import com.ouer.fbook.db.dao.BookContentDao;
import com.ouer.fbook.db.dao.BookSpiderBookDao;
import com.ouer.fbook.db.dao.BookSpiderChapterDao;
import com.ouer.fbook.db.dao.BookViewRecordDao;
import com.ouer.fbook.spider.site.SiteParseHelper;
import com.ouer.fbook.util.PageFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class BookApplication extends Application {

    public static BookContentChapterDao chapterDao;
    public static BookContentDao contentDao;
    public static BookSpiderBookDao bookDao;
    public static BookSpiderChapterDao spiderChapterDao;
    public static BookViewRecordDao viewRecordDao;

    public static SiteParseHelper parseHelper;
    public static ExecutorService threadPool;
    public static UserConfig userConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        Config.createConfig(this);
        PageFactory.createPageFactory(this);

    }

    private void init() {
        userConfig = UserConfig.getInstance();
        chapterDao = BookContentChapterDao.getInstance(getApplicationContext());
        contentDao = BookContentDao.getInstance(getApplicationContext());
        bookDao = BookSpiderBookDao.getInstance(getApplicationContext());
        spiderChapterDao = BookSpiderChapterDao.getInstance(getApplicationContext());
        viewRecordDao = BookViewRecordDao.getInstance(getApplicationContext());

        parseHelper = SiteParseHelper.getInstance();
        parseHelper.init();
        threadPool = Executors.newFixedThreadPool(8);

    }
}
