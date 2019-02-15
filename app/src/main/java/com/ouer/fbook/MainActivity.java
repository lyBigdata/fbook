package com.ouer.fbook;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.android.material.tabs.TabLayout;
import com.ouer.fbook.activity.base.BaseActivity;
import com.ouer.fbook.adapter.TabAdapter;
import com.ouer.fbook.event.MessageEvent;
import com.ouer.fbook.fragment.BookShelfFragment;
import com.ouer.fbook.fragment.SearchBookFragment;
import com.ouer.fbook.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tl_main)
    TabLayout tabLayout;
    @BindView(R.id.vp_main)
    ViewPager viewPager;
    @BindView(R.id.ll_container)
    View llContainer;

    private TabItem[] tabItems;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_real_main;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);

        tabItems = new TabItem[]{new TabItem(getString(R.string.main_book), R.drawable.selector_shop_icon),
                new TabItem(getString(R.string.main_find), R.drawable.selector_descovery_icon),
                new TabItem(getString(R.string.main_mine), R.drawable.selector_mine_icon)};

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new BookShelfFragment());
        fragments.add(new SearchBookFragment());
//        fragments.add(new BookShelfFragment());
        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), fragments));
        loadTabLayout(tabItems);


        EventBus.getDefault().post(new MessageEvent("name","password"));
    }

    private void loadTabLayout(TabItem[] tabItems) {
        tabLayout.setupWithViewPager(viewPager);
        int selectPosition = tabLayout.getSelectedTabPosition();
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(R.layout.widget_tab_main);
            View tabContainer = tab.getCustomView();
            if (i >= tabItems.length) {
                return;
            }
            TextView tvBottom = tabContainer.findViewById(R.id.tv_bottom);
            tvBottom.setText(tabItems[i].getTitle());
            ImageView ivTop = tabContainer.findViewById(R.id.iv_top);
            ivTop.setImageDrawable(ContextCompat.getDrawable(this, tabItems[i].getRes()));
            if (i == selectPosition) {
                tabContainer.setSelected(true);
            }
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    tab.getCustomView().setSelected(true);
                }
                if (viewPager.getCurrentItem() == 1) {
                } else if (viewPager.getCurrentItem() == 3) {
                }
            }

            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    tab.getCustomView().setSelected(false);
                }
            }

            public void onTabReselected(TabLayout.Tab tab) {
                if (viewPager.getCurrentItem() == 0) {
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus(MessageEvent event){
        LogUtils.d("event", JSON.toJSONString(event));
    }

    private class TabItem {
        private String title;
        private int res;

        public TabItem(String title, int res) {
            this.title = title;
            this.res = res;
        }

        public String getTitle() {
            return title;
        }

        public int getRes() {
            return res;
        }
    }
}
