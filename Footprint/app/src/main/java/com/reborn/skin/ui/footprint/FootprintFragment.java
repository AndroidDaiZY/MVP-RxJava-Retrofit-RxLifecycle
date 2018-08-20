package com.reborn.skin.ui.footprint;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.gyf.barlibrary.ImmersionBar;
import com.reborn.skin.R;
import com.reborn.skin.adapter.FootprintTopViewAdapter;
import com.reborn.skin.ui.Json2ViewActivity;
import com.reborn.skin.ui.base.BaseFragment;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 戴震宇 on 2018/8/13 0013.
 */
public class FootprintFragment extends BaseFragment {

    @BindView(R.id.fragment_footprint_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.include_title_two_right_image_ll)
    LinearLayout linearLayout;

    @BindView(R.id.fragment_footprint_btn)
    Button btn;
    VirtualLayoutManager layoutManager;

    @Override
    protected Object getContentLayout() {
        return R.layout.fragment_footprint;
    }

    @Override
    protected void initView(View contentView) {
        if (linearLayout != null) {
            ImmersionBar.setTitleBar(getActivity(), linearLayout);
        }

    }

    @Override
    protected void initData() {
        layoutManager = new VirtualLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        //type=0 的item 设置了复用池大小，多种type ,需要为每一种类型分别调整复用池大小
        viewPool.setMaxRecycledViews(0, 10);
        final DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);
        mRecyclerView.setAdapter(delegateAdapter);
        final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        adapters.add(new FootprintTopViewAdapter(getActivity()));
        adapters.add(new FootprintTopViewAdapter(getActivity()));
        adapters.add(new FootprintTopViewAdapter(getActivity()));
        adapters.add(new FootprintTopViewAdapter(getActivity()));
        adapters.add(new FootprintTopViewAdapter(getActivity()));
        adapters.add(new FootprintTopViewAdapter(getActivity()));
        adapters.add(new FootprintTopViewAdapter(getActivity()));
        adapters.add(new FootprintTopViewAdapter(getActivity()));
        adapters.add(new FootprintTopViewAdapter(getActivity()));
        adapters.add(new FootprintTopViewAdapter(getActivity()));
        delegateAdapter.addAdapters(adapters);


        /**
         *  滑动逻辑    当滑动到对应position第1个时 显示 动画从右到左滑动 并获取 当前adapter 的时间 来显示
         *              判断 上下滑动  区分显示的时机
         */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int position = layoutManager.findFirstVisibleItemPosition();
                if (position > 0) {
                    linearLayout.setAlpha(1);
                } else {
                    View firstView = layoutManager.findViewByPosition(position);
                    int top = firstView.getTop();
                    int height = linearLayout.getHeight();
                    int firstHeight = firstView.getHeight();
                    linearLayout.setAlpha(Math.min(1f, (float) -top / (float) (firstHeight - height)));
                }
            }
        });
    }


//            SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);//night表示colors中background_night的后缀
//            SkinCompatManager.getInstance().restoreDefaultTheme();

    //解决fragment重叠现象
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);//将这一行注释掉，阻止activity保存fragment的状态
    }


    @OnClick(R.id.fragment_footprint_btn)
    public void onViewClicked() {
        startActivity(Json2ViewActivity.class);
    }
}
