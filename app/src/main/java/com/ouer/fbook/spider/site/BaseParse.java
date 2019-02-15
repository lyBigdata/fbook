package com.ouer.fbook.spider.site;



import com.ouer.fbook.db.bean.BookSpiderBook;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hetao
 * @date 2019/1/9
 */
public abstract class BaseParse implements IParseHtml {

    public static Pattern r2 = Pattern.compile("/([0-9]+).html");
    protected Integer getHtmlIndex(String str) {

        Matcher m = r2.matcher(str);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        }
        return null;
    }


    public static Pattern r = Pattern.compile("第(.*)章");
    protected Integer getChapterNum(String chapterName) {

        Matcher m = r.matcher(chapterName);
        if (m.find()) {
            return chineseNumber2Int(m.group(1));
        }
        return null;
    }

    @SuppressWarnings("unused")
    protected int chineseNumber2Int(String chineseNumber){
        int result = 0;
        int temp = 1;//存放一个单位的数字如：十万
        int count = 0;//判断是否有chArr
        char[] cnArr = new char[]{'一','二','三','四','五','六','七','八','九'};
        char[] chArr = new char[]{'十','百','千','万','亿'};
        for (int i = 0; i < chineseNumber.length(); i++) {
            boolean b = true;//判断是否是chArr
            char c = chineseNumber.charAt(i);
            for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
                if (c == cnArr[j]) {
                    if(0 != count){//添加下一个单位之前，先把上一个单位值添加到结果中
                        result += temp;
                        temp = 1;
                        count = 0;
                    }
                    // 下标+1，就是对应的值
                    temp = j + 1;
                    b = false;
                    break;
                }
            }
            if(b){//单位{'十','百','千','万','亿'}
                for (int j = 0; j < chArr.length; j++) {
                    if (c == chArr[j]) {
                        switch (j) {
                            case 0:
                                temp *= 10;
                                break;
                            case 1:
                                temp *= 100;
                                break;
                            case 2:
                                temp *= 1000;
                                break;
                            case 3:
                                temp *= 10000;
                                break;
                            case 4:
                                temp *= 100000000;
                                break;
                            default:
                                break;
                        }
                        count++;
                    }
                }
            }
            if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
                result += temp;
            }
        }
        return result;
    }

    @Override
    public List<BookSpiderBook> findBookByCategory(String body) {
        return null;
    }
}
