package com.ouer.fbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ouer.fbook.R;
import com.ouer.fbook.db.bean.BookContent;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnItemLongClick;
import butterknife.OnLongClick;

public class SearchBookAdapter<T extends BookContent> extends BaseRecyclerAdapter<SearchBookAdapter.VH> {

    public static class VH extends BaseRecyclerAdapter.BaseVH{
        @BindView(R.id.search_book_name)
        public TextView bookName;

        @BindView(R.id.search_author)
        public TextView author;

        @BindView(R.id.search_last)
        public TextView chapter;

        @BindView(R.id.search_img)
        public ImageView cover;

        @BindView(R.id.parent)
        public View parent;

        public VH(View view) {
            super(view);
        }
    }

    private List<T> mDatas;
    private Context mContext;
    public SearchBookAdapter(Context context, View.OnClickListener clickListener) {
        mDatas = new ArrayList<>(4);
        mContext= context;
        mOnClickListener = clickListener;
    }

    private View.OnLongClickListener longClickListener;
    public void setLongClickListener(View.OnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_book, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        BookContent bookContent = mDatas.get(position);
        holder.bookName.setText(bookContent.getBookName());
        holder.chapter.setText(bookContent.getLastChapterName());
        holder.author.setText(bookContent.getAuthor());
        Glide.with(mContext).load(bookContent.getCover()).apply(RequestOptions.placeholderOf(R.mipmap.ic_launcher).centerCrop()).into(holder.cover);

        holder.parent.setOnClickListener(mOnClickListener);
        if(longClickListener != null) {
            holder.parent.setOnLongClickListener(longClickListener);
        }
        holder.parent.setTag(bookContent);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public void setList(List<T> list) {
        this.mDatas = list;
        notifyDataSetChanged();
    }
}
