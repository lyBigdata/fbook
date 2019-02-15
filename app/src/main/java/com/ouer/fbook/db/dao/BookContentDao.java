package com.ouer.fbook.db.dao;

import android.content.Context;

import com.j256.ormlite.support.ConnectionSource;
import com.ouer.fbook.db.BaseDao;
import com.ouer.fbook.db.DaoFactory;
import com.ouer.fbook.db.bean.BookContent;
import com.ouer.fbook.db.bean.BookContent;
import com.ouer.fbook.db.bean.CstColumn;
import com.ouer.fbook.util.LogUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/27.
 */

public class BookContentDao extends BaseDao<BookContent> {

    private static BookContentDao mBookSpiderBookDao;

    public static BookContentDao getInstance(Context ctx) {
        if (mBookSpiderBookDao == null) {
            synchronized (BookContentDao.class) {
                try {
                    mBookSpiderBookDao = new BookContentDao(DaoFactory.getInstance(ctx).getSource(), BookContent.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return mBookSpiderBookDao;
    }


    public BookContentDao(ConnectionSource connectionSource, Class<BookContent> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }


    public BookContent findBookByName(String sourceType, String bookName) {
        List<BookContent> beans = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put(CstColumn.BookContent.BookName, bookName);
            map.put(CstColumn.BookContent.SourceType,sourceType);
            beans = queryForFieldValues(map);
        } catch (SQLException e) {
            LogUtils.d(TAG, e.getMessage());
        }
        return limitOne(beans);
    }


    public List<BookContent> findAll() {
        List<BookContent> beans = null;
        try {
            beans = queryBuilder().groupBy(CstColumn.BookContent.UpdateAt).query();
        } catch (SQLException e) {
            LogUtils.d(TAG, e.getMessage());
        }
        return beans;

    }
}
