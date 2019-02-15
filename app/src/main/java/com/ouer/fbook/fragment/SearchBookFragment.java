package com.ouer.fbook.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ouer.fbook.BookApplication;
import com.ouer.fbook.R;
import com.ouer.fbook.activity.ChapterActivity;
import com.ouer.fbook.activity.base.BaseFragment;
import com.ouer.fbook.adapter.SearchBookAdapter;
import com.ouer.fbook.bean.constant.CstCommon;
import com.ouer.fbook.db.bean.BookContent;
import com.ouer.fbook.network.HttpHelper;
import com.ouer.fbook.network.bean.IBaseDataView;
import com.ouer.fbook.util.CommonUtil;
import com.ouer.fbook.util.LogUtils;
import com.ouer.fbook.util.StringUtils;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

public class SearchBookFragment extends BaseFragment {

    @BindView(R.id.shelf_list)
    RecyclerView recyclerView;

    @BindView(R.id.search_edit)
    EditText editText;

    @BindView(R.id.search_book)
    Button searchBook;

    private SearchBookAdapter<BookContent> bookAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_search_book;
    }


    @Override
    protected void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        bookAdapter = new SearchBookAdapter<>(getContext(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookContent detail = (BookContent)v.getTag();
                if(detail == null) {
                    Toast.makeText(getContext(), "数据为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                HttpHelper.getOkhttp(detail.getLink(), new IBaseDataView<String>() {
                    @Override
                    public void onGetDataSuccess(String data, int page, String tag) {


                        try {
                            BookContent bookIContent= BookApplication.parseHelper.getSpiderChapter(detail, data);

                            Intent intent = new Intent(getActivity(), ChapterActivity.class);
                            intent.putExtra(CstCommon.TAG_BOOK_ID, bookIContent.getId());
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getBaseActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }

                    @Override
                    public void onGetDataFail(int code, String fail) {
                        LogUtils.d("event", fail);
                    }

                    @Override
                    public void addDisposable(Disposable disposable) {
                        SearchBookFragment.this.addDisposable(disposable);
                    }
                });


            }
        });
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(bookAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

    }

    @OnClick(R.id.search_book)
    public void onClick(View view){
        String bookName=editText.getText().toString();
        if(StringUtils.isBlank(bookName)) {
            Toast.makeText(getContext(), "名字不能为空", Toast.LENGTH_SHORT).show();
            return ;
        }
        try {
            final String url = "https://www.yangguiweihuo.com/s.php?ie=gbk&q=" + CommonUtil.toGBK(bookName);
            HttpHelper.getOkhttp(url, new IBaseDataView<String>() {
                @Override
                public void onGetDataSuccess(String data, int page, String tag) {
                    bookAdapter.setList(BookApplication.parseHelper.getSearchDetail(data));
                }

                @Override
                public void onGetDataFail(int code, String fail) {
                    LogUtils.d("event", fail);
                }

                @Override
                public void addDisposable(Disposable disposable) {
                    SearchBookFragment.this.addDisposable(disposable);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
