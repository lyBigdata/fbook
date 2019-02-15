package com.ouer.fbook.spider.run;

import com.ouer.fbook.util.LogUtils;


/**
 * 任务
 *
 * @author hetao
 */
public abstract class BaseTask implements Runnable {


    /**
     * 线程方法
     */
    @Override
    public void run() {
        while (isKeep()) {
            try {
                start();
                sleep();
            } catch (Exception e) {
                LogUtils.e("{}", e);
            }
        }
    }

    protected abstract boolean isKeep();

    /**
     * 处理具体的业务
     */
    protected abstract void start() throws Exception;

    /**
     * 每次延迟
     * @throws Exception
     */
    protected abstract void sleep() throws Exception;
}
