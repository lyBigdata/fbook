package com.ouer.fbook.db.dao;

import android.content.Context;

import com.j256.ormlite.support.ConnectionSource;
import com.ouer.fbook.db.BaseDao;
import com.ouer.fbook.db.DaoFactory;
import com.ouer.fbook.db.bean.BookSpiderBook;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/4/27.
 */

public class BookSpiderBookDao extends BaseDao<BookSpiderBook> {

    private static BookSpiderBookDao mBookSpiderBookDao;

    public static BookSpiderBookDao getInstance(Context ctx) {
        if (mBookSpiderBookDao == null) {
            synchronized (BookSpiderBookDao.class) {
                try {
                    mBookSpiderBookDao = new BookSpiderBookDao(DaoFactory.getInstance(ctx).getSource(), BookSpiderBook.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return mBookSpiderBookDao;
    }


    public BookSpiderBookDao(ConnectionSource connectionSource, Class<BookSpiderBook> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

}
