package com.ouer.fbook.spider.task;

import com.ouer.fbook.spider.run.BaseTask;

public class BookChapterTask extends BaseTask {

    public static boolean isKeep=false;

    @Override
    protected boolean isKeep() {
        return isKeep;
    }

    @Override
    protected void start() throws Exception {

    }

    @Override
    protected void sleep() throws Exception {

    }
}
