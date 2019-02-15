package com.ouer.fbook.activity;

import android.content.Intent;
import android.view.View;

import com.ouer.fbook.BookApplication;
import com.ouer.fbook.R;
import com.ouer.fbook.activity.base.BaseActivity;
import com.ouer.fbook.adapter.ChapterAdapter;
import com.ouer.fbook.bean.constant.CstCommon;
import com.ouer.fbook.db.bean.BookSpiderChapter;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class ChapterActivity extends BaseActivity {

    @BindView(R.id.shelf_list)
    RecyclerView recyclerView;

    private ChapterAdapter chapterAdapter;

    private Long mBookId;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_shelf;
    }

    @Override
    protected void initData() {
        Intent intent=getIntent();
        mBookId = intent.getLongExtra(CstCommon.TAG_BOOK_ID, -1);
        int chapterOrder = intent.getIntExtra(CstCommon.TAG_CHAPTER_ID, 0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        chapterAdapter= new ChapterAdapter(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BookSpiderChapter chapter = (BookSpiderChapter)v.getTag();

                Intent intent=new Intent();
                intent.putExtra(CstCommon.TAG_BOOK_ID,chapter.getBookContentId());
                intent.putExtra(CstCommon.TAG_CHAPTER_ID,chapter.getChapterOrder());
                intent.putExtra(CstCommon.TAG_PAGE_ID,0);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        recyclerView.setAdapter(chapterAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.scrollToPosition(chapterOrder);
        refreshChapter();
    }

    private void refreshChapter() {
        chapterAdapter.setList(BookApplication.spiderChapterDao.findByBookId(mBookId));
    }

}
