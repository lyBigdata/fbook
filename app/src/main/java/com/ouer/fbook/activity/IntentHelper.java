package com.ouer.fbook.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ouer.fbook.bean.constant.CstCommon;

import androidx.fragment.app.Fragment;

public class IntentHelper {

    public static Integer INTENT_CHAPTER=1000;
    public static void goChapterList(Activity context, Long bookId) {
        goChapterList(context, bookId, 0);
    }
    public static void goChapterList(Activity context, Long bookId, Integer chapterOrder) {
        Intent intent = new Intent(context, ChapterActivity.class);
        intent.putExtra(CstCommon.TAG_BOOK_ID, bookId);
        intent.putExtra(CstCommon.TAG_CHAPTER_ID, chapterOrder);
        context.startActivityForResult(intent, INTENT_CHAPTER);
    }
    public static void goChapterList(Fragment fragment, Context context, Long bookId) {
        goChapterList(fragment, context, bookId, 0);
    }

    public static void goChapterList(Fragment fragment, Context context, Long bookId, Integer chapterOrder) {
        Intent intent = new Intent(context, ChapterActivity.class);
        intent.putExtra(CstCommon.TAG_BOOK_ID, bookId);
        intent.putExtra(CstCommon.TAG_CHAPTER_ID, chapterOrder);
        fragment.startActivityForResult(intent, INTENT_CHAPTER);
    }

    public static void goRead(Context context, Long bookId, Integer chapterOrder, Integer page) {
        Intent intent = new Intent(context, ReadActivity.class);
        intent.putExtra(CstCommon.TAG_BOOK_ID, bookId);
        intent.putExtra(CstCommon.TAG_CHAPTER_ID, chapterOrder);
        intent.putExtra(CstCommon.TAG_PAGE_ID, page);
        context.startActivity(intent);
    }

}
