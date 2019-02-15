package com.ouer.fbook.network.bean;

/**
 * Created by yan on 2018/2/2 0002.
 */

public interface IBaseDataView<T> extends IBaseView {
    void onGetDataSuccess(T data, int page, String tag);

    /**
     * @param code -1 获取到的数据为空  -2 数据解析出错  -3 网络访问出错  -4 联网超时  -5 网络连接异常
     * @param fail 错误描述
     */
    void onGetDataFail(int code, String fail);

}