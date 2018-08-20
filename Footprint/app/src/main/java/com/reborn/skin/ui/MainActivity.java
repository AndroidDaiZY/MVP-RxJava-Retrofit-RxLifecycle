package com.reborn.skin.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.reborn.skin.R;
import com.reborn.skin.custom.SpecialTab;
import com.reborn.skin.custom.SpecialTabRound;
import com.reborn.skin.ui.base.BaseFragmentActivity;
import com.reborn.skin.ui.community.CommunityFragment;
import com.reborn.skin.ui.find.FindFragment;
import com.reborn.skin.ui.footprint.FootprintFragment;
import com.reborn.skin.ui.mine.MyFragment;
import com.reborn.skin.ui.record.RecordFragment;

import butterknife.BindView;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class MainActivity extends BaseFragmentActivity {

    private static final String TAG=MainActivity.class.getSimpleName();

    private static final int FOOTPRINT_INDEX = 0;
    private static final int COMMUNITY_INDEX = 1;
    private static final int RECORD_INDEX = 2;
    private static final int FIND_INDEX = 3;
    private static final int MY_INDEX = 4;


    @BindView(R.id.activity_main_page_view)
    PageNavigationView mPageNavigationView ;

    private NavigationController mNavigationController;
    private FootprintFragment footprintFragment;
    private CommunityFragment communityFragment;
    private RecordFragment recordFragment;
    private FindFragment findFragment;
    private MyFragment myFragment;


    @Override
    protected Object getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initBundleData() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mNavigationController=mPageNavigationView.custom()
                .addItem(newItem(R.drawable.ic_restore_gray_24dp,R.drawable.ic_restore_teal_24dp,"足迹"))
                .addItem(newItem(R.drawable.ic_favorite_gray_24dp,R.drawable.ic_favorite_teal_24dp,"社区"))
                .addItem(newRoundItem(R.drawable.ic_nearby_gray_24dp,R.drawable.ic_nearby_teal_24dp,"记一账"))
                .addItem(newItem(R.drawable.ic_favorite_gray_24dp,R.drawable.ic_favorite_teal_24dp,"发现"))
                .addItem(newItem(R.drawable.ic_restore_gray_24dp,R.drawable.ic_restore_teal_24dp,"我的"))
                .build();
    }

    @Override
    protected void initData() {
        setTabSelection(0);
        mNavigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                setTabSelection(index);
            }
            @Override
            public void onRepeat(int index) {
            }
        });
    }
    private void setTabSelection(int position){
        //更改底部导航栏按钮状态
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        transaction = getSupportFragmentManager().beginTransaction();
        switch (position){
            case FOOTPRINT_INDEX:
                if (footprintFragment == null){
                    footprintFragment = new FootprintFragment();
                    transaction.add(R.id.activity_main_fl,footprintFragment,"FootprintFragment");
                }else {
                    transaction.show(footprintFragment);
                }
                break;
            case COMMUNITY_INDEX:
                if (communityFragment == null){
                    communityFragment = new CommunityFragment();
                    transaction.add(R.id.activity_main_fl,communityFragment,"CommunityFragment");
                }else {
                    transaction.show(communityFragment);
                }
                break;
            case RECORD_INDEX:
                if (recordFragment == null){
                    recordFragment = new RecordFragment();
                    transaction.add(R.id.activity_main_fl,recordFragment,"RecordFragment");
                }else {
                    transaction.show(recordFragment);
                }
                break;
            case FIND_INDEX:
                if (findFragment == null){
                    findFragment = new FindFragment();
                    transaction.add(R.id.activity_main_fl,findFragment,"FindFragment");
                }else {
                    transaction.show(findFragment);
                }
                break;
            case MY_INDEX:
                if (myFragment == null){
                    myFragment = new MyFragment();
                    transaction.add(R.id.activity_main_fl,myFragment,"MyFragment");
                }else {
                    transaction.show(myFragment);
                }
                break;
        }
        transaction.commit();
    }
    /**
     * 隐藏隐藏fragment
     * @param transaction 事物提交
     */
    private void hideFragments(FragmentTransaction transaction) {
        if(footprintFragment!=null)
            transaction.hide(footprintFragment);
        if (communityFragment!=null)
            transaction.hide(communityFragment);
        if (recordFragment != null){
            transaction.hide(recordFragment);
        }
        if(findFragment!=null)
            transaction.hide(findFragment);
        if(myFragment!=null)
            transaction.hide(myFragment);
        transaction.commit();
    }
    /**
     * 正常tab
     */
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text){
        SpecialTab mainTab = new SpecialTab(this);
        mainTab.initialize(drawable,checkedDrawable,text);
        mainTab.setTextDefaultColor(0xFF888888);
        mainTab.setTextCheckedColor(0xFF009688);
        return mainTab;
    }

    /**
     * 圆形tab
     */
    private BaseTabItem newRoundItem(int drawable,int checkedDrawable,String text){
        SpecialTabRound mainTab = new SpecialTabRound(this);
        mainTab.initialize(drawable,checkedDrawable,text);
        mainTab.setTextDefaultColor(0xFF888888);
        mainTab.setTextCheckedColor(0xFF009688);
        return mainTab;
    }

}
