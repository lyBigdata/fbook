package com.ouer.fbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ouer.fbook.R;
import com.ouer.fbook.db.bean.BookSpiderChapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnItemLongClick;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.VH> {

    public static class VH extends BaseRecyclerAdapter.BaseVH{
        @BindView(R.id.chapter_title)
        public TextView title;

        @BindView(R.id.chapter_valid)
        public TextView valid;

        @BindView(R.id.parent)
        public View parent;

        public VH(View view) {
            super(view);
        }
    }

    private List<BookSpiderChapter> mDatas;
    private Context mContext;
    private View.OnClickListener mListener;
    public ChapterAdapter(Context context, View.OnClickListener listener) {
        mDatas = new ArrayList<>(4);
        mContext= context;
        mListener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chapter, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        BookSpiderChapter data = mDatas.get(position);
        String [] texts = mContext.getResources().getStringArray(R.array.down_status);

        holder.title.setText(data.getChapterName());
        holder.valid.setText(texts[data.getValid()]);
        holder.parent.setTag(data);
        holder.parent.setOnClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public void setList(List<BookSpiderChapter> list) {
        this.mDatas = list;
        notifyDataSetChanged();
    }
}
