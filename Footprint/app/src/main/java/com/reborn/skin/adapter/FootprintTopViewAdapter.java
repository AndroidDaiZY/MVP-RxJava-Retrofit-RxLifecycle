package com.reborn.skin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.reborn.skin.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 戴震宇 on 2018/8/14 0014.
 */
public class FootprintTopViewAdapter extends DelegateAdapter.Adapter {
    private Context mContext;

    private LayoutInflater mInflater;
    public FootprintTopViewAdapter(Context mContext) {
        this.mContext = mContext;
        this.mInflater=LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_footprint_top_view_item,parent,false);
        TopViewHolder viewHolder=new TopViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TopViewHolder viewHolder = (TopViewHolder) holder;
        viewHolder.ivBackground.setBackgroundResource(R.drawable.banner);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }

    static class TopViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recycler_footprint_top_view_item_bg_iv)
        ImageView ivBackground;
        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
