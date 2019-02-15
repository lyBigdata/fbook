package com.ouer.fbook.spider.model;

import java.util.Calendar;

/**
 * @author hetao
 * @date 2018/2/9
 */
public class ExpiredDateCst {

    /*
     * ========================================================================== ==
     */
    /* 定义时间常量，毫秒为单位。 */
    /*
     * ========================================================================== ==
     */
    /**
     * 一秒
     */
    public static final long   SECOND_MIS                = 1000L;

    /**
     * 一分钟
     */
    public static final long   MINUTE_MIS                = SECOND_MIS * 60;

    /**
     * 一小时
     */
    public static final long   HOUR_MIS                 = MINUTE_MIS * 60;

    /**
     * 一小时
     */
    public static final long   HALF_HOUR_MIS                 = MINUTE_MIS * 30;
    /**
     * 一天
     */
    public static final long   DAY_MIS                  = 24 * HOUR_MIS;
    /**
     * 一月
     */
    public static final long   MONTH_MIS                  = DAY_MIS * 30;
    /*
     * ========================================================================== ==
     */
    /* 定义时间常量，秒为单位。 */
    /*
     * ========================================================================== ==
     */
    /**
     * 一秒
     */
    public static final int   SECOND                = 1;

    /**
     * 一分钟
     */
    public static final int   MINUTE                = SECOND * 60;

    /**
     * 半分钟
     */
    public static final int   HALF_HOUR             = MINUTE * 30;

    /**
     * 一小时
     */
    public static final int   HOUR                  = MINUTE * 60;
    /**
     * 一天
     */
    public static final int   DAY                   = 24 * HOUR;


    /**
     * 计算佣金到00:00:00是还剩多少秒
     *
     * @return
     */
    public static Integer getAlternateDayExpiredSecond() {
        int expiredSecond = 3*HOUR;
        try {

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);

            expiredSecond = ((23 - hour) * 3600) + ((59 - min) * 60) + (59 - second) + 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return expiredSecond;
    }
}
