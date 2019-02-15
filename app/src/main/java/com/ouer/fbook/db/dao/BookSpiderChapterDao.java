package com.ouer.fbook.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.ouer.fbook.BookApplication;
import com.ouer.fbook.db.BaseDao;
import com.ouer.fbook.db.DaoFactory;
import com.ouer.fbook.db.bean.BookContent;
import com.ouer.fbook.db.bean.BookSpiderChapter;
import com.ouer.fbook.db.bean.BookSpiderChapter;
import com.ouer.fbook.db.bean.CstColumn;
import com.ouer.fbook.util.LogUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2017/4/27.
 */

public class BookSpiderChapterDao extends BaseDao<BookSpiderChapter> {

    private static BookSpiderChapterDao mBookSpiderBookDao;

    public static BookSpiderChapterDao getInstance(Context ctx) {
        if (mBookSpiderBookDao == null) {
            synchronized (BookSpiderChapterDao.class) {
                try {
                    mBookSpiderBookDao = new BookSpiderChapterDao(DaoFactory.getInstance(ctx).getSource(), BookSpiderChapter.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return mBookSpiderBookDao;
    }


    public BookSpiderChapterDao(ConnectionSource connectionSource, Class<BookSpiderChapter> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<BookSpiderChapter> findByBookId(Long bookId) {
        List<BookSpiderChapter> beans = null;
        try {
            beans = queryForEq(CstColumn.BookContentChapter.BookContentId, bookId);
        } catch (SQLException e) {
            LogUtils.d(TAG, e.getMessage());
        }
        return beans;
    }

    public BookSpiderChapter findByBookOrderId(Long bookContentId, Integer chapterOrder) {
        List<BookSpiderChapter> beans = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put(CstColumn.BookSpiderChapter.BookContentId, bookContentId);
            map.put(CstColumn.BookSpiderChapter.ChapterOrder,chapterOrder);
            beans = queryForFieldValues(map);
        } catch (SQLException e) {
            LogUtils.d(TAG, e.getMessage());
        }
        return limitOne(beans);
    }

    public void insertBatch(final List<BookSpiderChapter> list) {

        try {
            callBatchTasks(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    for (BookSpiderChapter chapter : list) {
                        add(chapter);
                    }
                    return null;
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
