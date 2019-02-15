package com.ouer.fbook.util;



/**
 * Created by ouer on 2018/3/5.
 */

public class Splits {

    public static String with(Object... strs) {
        return with(new StringBuilder(), strs);
    }

    public static String with(StringBuilder stringBuilder, Object... strs) {
        stringBuilder.delete(0, stringBuilder.length());
        stringBuilder.append("");
        for (Object s : strs) {
            if (s != null) {
                stringBuilder.append(s);
            }
        }
        return stringBuilder.toString();
    }


}
