/*
 * ========================================================
 * Copyright(c) 2014 杭州偶尔科技-版权所有
 * ========================================================
 * 本软件由杭州偶尔科技所有, 未经书面许可, 任何单位和个人不得以
 * 任何形式复制代码的部分或全部, 并以任何形式传播。
 * 公司网址
 * 
 * 			http://www.kkkd.com/
 * 
 * ========================================================
 */
package com.ouer.fbook.db;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.ouer.fbook.db.bean.BaseDbBean;
import com.ouer.fbook.db.bean.CstColumn;
import com.ouer.fbook.util.LogUtils;
import com.ouer.fbook.util.CollectionUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * @author : Zhenshui.Xia
 * @date : 2014年8月10日
 * @desc : 数据库业务逻辑基类
 */
public abstract class BaseDao<T extends BaseDbBean> extends BaseDaoImpl<T, Integer> {

    protected final String TAG=getClass().getName();

    protected BaseDao(ConnectionSource connectionSource, Class<T> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }


    public void add(T bean) {
        if (bean == null) {
            return;
        }

        try {
            T beanInDb = get(bean.getId());
            if (beanInDb == null) {
                create(bean);
            } else {
                bean.setId(beanInDb.getId());
                update(bean);
            }
        } catch (SQLException e) {
            LogUtils.d(TAG, e.getMessage());
        }
    }

    public void remove(Long id) {
        if (id == null ) {
            return;
        }
        LogUtils.e(TAG, "remove: " + id);
        try {
            T bean = get(id);
            delete(bean);
            LogUtils.e(TAG, "remove: success " + bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public T get(Long id) {
        if (id == null) {
            return null;
        }
        List<T> beans = null;
        try {
            beans = queryForEq(CstColumn.BaseBean.ID, id);
        } catch (SQLException e) {
            LogUtils.d(TAG, e.getMessage());
        }
        return limitOne(beans);
    }

    public void updateEx(T base) {
        try {
            update(base);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected T limitOne(List<T> list) {
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }
}
