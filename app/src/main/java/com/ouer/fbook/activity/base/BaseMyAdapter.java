package com.ouer.fbook.activity.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ouer.fbook.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseMyAdapter extends BaseAdapter {

    @Override
    public View getView(int position, View view, ViewGroup parent) {
//        ViewHolder holder;
//        if (view != null) {
//            holder = (ViewHolder) view.getTag();
//        } else {
//            view = inflater.inflate(R.layout.item, parent, false);
//            holder = new ViewHolder(view);
//            view.setTag(holder);
//        }
//
//        holder.name.setText("hello");
        return view;
    }



//    static class ViewHolder {
//        @BindView(R.id.name)
//        TextView name;
//
//        public ViewHolder(View view) {
//            ButterKnife.bind(view);
//        }
//    }

}
