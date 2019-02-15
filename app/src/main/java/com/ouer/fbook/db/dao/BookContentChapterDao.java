package com.ouer.fbook.db.dao;

import android.content.Context;

import com.j256.ormlite.support.ConnectionSource;
import com.ouer.fbook.db.BaseDao;
import com.ouer.fbook.db.DaoFactory;
import com.ouer.fbook.db.bean.BookContentChapter;
import com.ouer.fbook.db.bean.CstColumn;
import com.ouer.fbook.util.LogUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/27.
 */

public class BookContentChapterDao extends BaseDao<BookContentChapter> {

    private static BookContentChapterDao mBookSpiderBookDao;

    public static BookContentChapterDao getInstance(Context ctx) {
        if (mBookSpiderBookDao == null) {
            synchronized (BookContentChapterDao.class) {
                try {
                    mBookSpiderBookDao = new BookContentChapterDao(DaoFactory.getInstance(ctx).getSource(), BookContentChapter.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return mBookSpiderBookDao;
    }


    public BookContentChapterDao(ConnectionSource connectionSource, Class<BookContentChapter> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public BookContentChapter findByBookIdOrder(Long bookId, Integer chapterOrder) {
        List<BookContentChapter> beans = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put(CstColumn.BookContentChapter.BookContentId, bookId);
            map.put(CstColumn.BookContentChapter.ChapterOrder,chapterOrder);
            beans = queryForFieldValues(map);
        } catch (SQLException e) {
            LogUtils.d(TAG, e.getMessage());
        }
        return limitOne(beans);
    }
}
