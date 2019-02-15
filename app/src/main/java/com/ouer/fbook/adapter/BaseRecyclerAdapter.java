package com.ouer.fbook.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

public abstract class BaseRecyclerAdapter<T extends BaseRecyclerAdapter.BaseVH> extends RecyclerView.Adapter<T>  {

    public static class BaseVH extends RecyclerView.ViewHolder{
        public BaseVH(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    protected View.OnClickListener mOnClickListener;

    public void setOnItemClickListener(View.OnClickListener listener) {
        mOnClickListener = listener;
    }
}
