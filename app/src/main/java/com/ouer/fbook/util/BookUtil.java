package com.ouer.fbook.util;


import android.graphics.Paint;
import android.util.Log;

import com.ouer.fbook.BookApplication;
import com.ouer.fbook.db.bean.BookContent;
import com.ouer.fbook.db.bean.BookContentChapter;
import com.ouer.fbook.db.bean.BookSpiderChapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/11 0011.
 */
public class BookUtil {


    private String bookName;

    private int bookLen;
    private int position;
    private BookContentChapter mChapter;

    public BookUtil(){}

    public void openBook(Long bookId, Integer chapterOrder) throws Exception {

        mChapter = BookApplication.chapterDao.findByBookIdOrder(bookId, chapterOrder);

        if(mChapter == null) {
            throw new Exception("未找到数据");
        }

        String content = mChapter.getContent();
        position=0;
        bookLen = content.length()-1;

    }


    public int next(boolean back){
        position += 1;
        if (position > bookLen){
            position = bookLen;
            return -1;
        }
        char result = current();
        if (back) {
            position -= 1;
        }
        return result;
    }

    public char[] nextLine(){
        if (position >= bookLen){
            return null;
        }
        String line = "";
        while (position < bookLen){
            int word = next(false);
            if (word == -1){
                break;
            }
            char wordChar = (char) word;
            if ((wordChar + "").equals("\r") && (((char)next(true)) + "").equals("\n")){
                next(false);
                break;
            }
            line += wordChar;
        }
        return line.toCharArray();
    }

    public char[] preLine(){
        if (position <= 0){
            return null;
        }
        String line = "";
        while (position >= 0){
            int word = pre(false);
            if (word == -1){
                break;
            }
            char wordChar = (char) word;
            if (word==10||
                    (wordChar==10 && ((char)pre(true))==14)){
                pre(false);
//                line = "\r\n" + line;
                break;
            }
            line = wordChar + line;
        }
        return line.toCharArray();
    }

    public char current(){
        if(position < bookLen) {
            return mChapter.getContent().charAt(position);
        }

        return 0;
    }

    public int pre(boolean back){
        position -= 1;
        if (position < 0){
            position = 0;
            return -1;
        }
        char result = current();
        if (back) {
            position += 1;
        }
        return result;
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public int getBookLen(){
        return bookLen;
    }


    public List<TRPage> initPage(Paint paint, float visibleWidth, int lineCount){
        List<TRPage> trPageList = new ArrayList<>(4);
        position=0;
        while(true) {
            List<String> lines = getNextLines(paint, visibleWidth, lineCount);
            if(lines == null || lines.isEmpty()) {
                break;
            }
            TRPage trPage = new TRPage();
            trPage.setLines(lines);
            trPageList.add(trPage);
        }
        return trPageList;
    }

    private List<String> getNextLines(Paint paint, float visibleWidth, int lineCount){
        List<String> lines = new ArrayList<>();
        float width = 0;
        String line = "";
        while (next(true) != -1){
            char word = (char) next(false);
            //判断是否换行
            if (word ==10|| (word ==14 && next(true)==10)){
                next(false);
                if (!line.isEmpty()){
                    lines.add(line);
                    line = "";
                    width = 0;
//                    height +=  paragraphSpace;
                    if (lines.size() == lineCount){
                        break;
                    }
                }
            }else {
                float widthChar = paint.measureText(word + "");
                width += widthChar;
                if (width > visibleWidth) {
                    width = widthChar;
                    lines.add(line);
                    line = word + "";
                } else {
                    line += word;
                }
            }

            if (lines.size() == lineCount){
                if (!line.isEmpty()){
                    setPosition(getPosition() - 1);
                }
                break;
            }
        }

        if (!line.isEmpty() && lines.size() < lineCount){
            lines.add(line);
        }
//        for (String str : lines){
//            Log.e(TAG,str + "   ");
//        }
        return lines;
    }
}
