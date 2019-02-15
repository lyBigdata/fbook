package com.ouer.fbook.db.dao;

import android.content.Context;

import com.j256.ormlite.support.ConnectionSource;
import com.ouer.fbook.db.BaseDao;
import com.ouer.fbook.db.DaoFactory;
import com.ouer.fbook.db.bean.BookViewRecord;
import com.ouer.fbook.db.bean.CstColumn;
import com.ouer.fbook.util.LogUtils;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2017/4/27.
 */

public class BookViewRecordDao extends BaseDao<BookViewRecord> {

    private static BookViewRecordDao instance;

    public static BookViewRecordDao getInstance(Context ctx) {
        if (instance == null) {
            synchronized (BookViewRecordDao.class) {
                try {
                    instance = new BookViewRecordDao(DaoFactory.getInstance(ctx).getSource(), BookViewRecord.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return instance;
    }

    public BookViewRecordDao(ConnectionSource connectionSource, Class<BookViewRecord> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }


    public void addOrUpdate(Long bookId, Integer chapterOrder, Integer page) {

        BookViewRecord viewRecord = findByBookId(bookId);
        if (viewRecord == null) {
            viewRecord = new BookViewRecord();
            viewRecord.setPage(page);
            viewRecord.setBookContentId(bookId);
            viewRecord.setChapterOrder(chapterOrder);
            viewRecord.setUpdateAt(new Date());
            add(viewRecord);
        } else {
            viewRecord.setPage(page);
            viewRecord.setChapterOrder(chapterOrder);
            viewRecord.setUpdateAt(new Date());
            updateEx(viewRecord);
        }

    }

    public BookViewRecord findByBookId(Long bookId) {
        List<BookViewRecord> beans = null;
        try {
            beans = queryForEq(CstColumn.BookViewRecord.BookContentId, bookId);
        } catch (SQLException e) {
            LogUtils.d(TAG, e.getMessage());
        }
        return limitOne(beans);
    }



}
