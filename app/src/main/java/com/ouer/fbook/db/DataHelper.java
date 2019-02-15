package com.ouer.fbook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ouer.fbook.db.bean.BookContent;
import com.ouer.fbook.db.bean.BookContentChapter;
import com.ouer.fbook.db.bean.BookSpiderBook;
import com.ouer.fbook.db.bean.BookSpiderChapter;
import com.ouer.fbook.db.bean.BookViewRecord;
import com.ouer.fbook.util.LogUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


/**
 * @author : Zhenshui.Xia
 * @date : 	  2014年6月19日
 * @desc :
 */

public class DataHelper extends OrmLiteSqliteOpenHelper {

    // 数据库名
    private static final String DB_NAME =  "fbook.db";
    public static final int DB_VERSION = 11;

    private final Map<String, Dao> daos = new HashMap<>();

    private final static HashSet<String> classesStr = new HashSet<>();
    private static final List<OnDatabaseCall> onDatabaseCalls = new ArrayList<>();

    public static void addOnDatabaseCall(OnDatabaseCall onDatabaseCall) {
        onDatabaseCalls.add(onDatabaseCall);
    }

    public static void putClass(String clazz) {
        classesStr.add(clazz);
    }

    public DataHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            LogUtils.e(DB_NAME, "create user table");
            TableUtils.createTable(connectionSource, BookSpiderBook.class);
            TableUtils.createTable(connectionSource, BookSpiderChapter.class);
            TableUtils.createTable(connectionSource, BookContent.class);
            TableUtils.createTable(connectionSource, BookContentChapter.class);
            TableUtils.createTable(connectionSource, BookViewRecord.class);

            for (OnDatabaseCall onDatabaseCall : onDatabaseCalls) {
                onDatabaseCall.onDatabaseCall(classesStr);
            }
            LogUtils.e(DB_NAME,"drop user table --------------- " + classesStr.size());
            for (String c : classesStr) {
                try {
                    TableUtils.createTable(connectionSource, Class.forName(c));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            LogUtils.e(DB_NAME, "create database error");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int arg2,
                          int arg3) {
        //根据实际情况来处理
        try {
            LogUtils.e(DB_NAME, "drop user table");
            TableUtils.dropTable(connectionSource, BookSpiderBook.class, true);
            TableUtils.dropTable(connectionSource, BookSpiderChapter.class, true);
            TableUtils.dropTable(connectionSource, BookContent.class, true);
            TableUtils.dropTable(connectionSource, BookContentChapter.class, true);
            TableUtils.dropTable(connectionSource, BookViewRecord.class, true);
            for (OnDatabaseCall onDatabaseCall : onDatabaseCalls) {
                onDatabaseCall.onDatabaseCall(classesStr);
            }
            LogUtils.e(DB_NAME, "drop user table --------------- " + classesStr.size());
            for (String c : classesStr) {
                try {
                    TableUtils.dropTable(connectionSource, Class.forName(c), true);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            LogUtils.e(DB_NAME, "update database error");
        }
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();

        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }

    public interface OnDatabaseCall {
        void onDatabaseCall(HashSet<String> sqlTableName);
    }

}
