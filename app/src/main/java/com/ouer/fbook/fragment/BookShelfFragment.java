package com.ouer.fbook.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.ouer.fbook.BookApplication;
import com.ouer.fbook.R;
import com.ouer.fbook.activity.ChapterActivity;
import com.ouer.fbook.activity.IntentHelper;
import com.ouer.fbook.activity.ReadActivity;
import com.ouer.fbook.activity.base.BaseFragment;
import com.ouer.fbook.adapter.SearchBookAdapter;
import com.ouer.fbook.bean.constant.CstCommon;
import com.ouer.fbook.db.bean.BookContent;
import com.ouer.fbook.db.bean.BookViewRecord;
import com.ouer.fbook.event.RefreshBook;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

public class BookShelfFragment extends BaseFragment {

    @BindView(R.id.shelf_list)
    RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private SearchBookAdapter<BookContent> bookAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_shelf;
    }


    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        bookAdapter= new SearchBookAdapter<>(getContext(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookContent bookContent = (BookContent)v.getTag();
                if(bookContent == null) {
                    Toast.makeText(getContext(), "数据未空", Toast.LENGTH_SHORT).show();
                    return;
                }
                BookViewRecord viewRecord = BookApplication.viewRecordDao.findByBookId(bookContent.getId());
                if(viewRecord != null) {
                    IntentHelper.goRead(getActivity(),
                            viewRecord.getBookContentId(),viewRecord.getChapterOrder(), viewRecord.getPage());
                } else {
                    IntentHelper.goChapterList(BookShelfFragment.this, getActivity(), bookContent.getId());
                }
            }
        });

        bookAdapter.setLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getActivity(), "long click", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        recyclerView.setAdapter(bookAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshShelf();
                refreshLayout.finishRefresh();
            }
        });
        refreshShelf();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshBook(RefreshBook event){
        if(event.isRefresh) {
            refreshShelf();
        }
    }

    private void refreshShelf() {
        bookAdapter.setList(BookApplication.contentDao.findAll());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IntentHelper.INTENT_CHAPTER){
            if(resultCode == RESULT_OK) {
                Long bookId = data.getLongExtra(CstCommon.TAG_BOOK_ID, -1);
                Integer chapterOrder = data.getIntExtra(CstCommon.TAG_CHAPTER_ID, -1);
                Integer page = data.getIntExtra(CstCommon.TAG_PAGE_ID, 0);

                IntentHelper.goRead(getActivity(), bookId, chapterOrder, page);
            }
        }
    }
}
