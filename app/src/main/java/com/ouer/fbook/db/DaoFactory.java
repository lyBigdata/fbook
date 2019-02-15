package com.ouer.fbook.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.support.ConnectionSource;

/**
 * @date : 2014年8月10日
 * @desc : Dao工厂
 */
public class DaoFactory {

    private static DaoFactory mInstance;
    private ConnectionSource mSource;

    public ConnectionSource getSource() {
        return mSource;
    }

    private DaoFactory(Context ctx) {
        this.mSource = OpenHelperManager.getHelper(ctx, DataHelper.class).getConnectionSource();
    }


    /**
     * 单例模式
     *
     * @param ctx 上下文
     * @return
     */
    public synchronized static DaoFactory getInstance(Context ctx) {
        if (null == mInstance) {
            synchronized (DaoFactory.class) {
                if (mInstance == null) {
                    mInstance = new DaoFactory(ctx);
                }
            }
        }
        return mInstance;
    }

}