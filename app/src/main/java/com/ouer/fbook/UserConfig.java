package com.ouer.fbook;

import com.ouer.fbook.spider.model.SiteEnum;

public class UserConfig {

    private static UserConfig instance;

    public static UserConfig getInstance() {
        if (instance == null) {
            synchronized (UserConfig.class) {
                try {
                    instance = new UserConfig();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return instance;
    }

    private UserConfig() {
        line = SiteEnum.YANGGUI;
    }

    public SiteEnum line;


}
