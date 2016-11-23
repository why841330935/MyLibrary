package com.neusoft.sample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.neusoft.sample.R;
import com.neusoft.sample.WApiCompatActivity;
import com.neusoft.sample.ui.fragment.MoveFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * on 2016/8/29.
 * Created by WangHuanyu 外包
 */
public class ViewPagerActivity extends WApiCompatActivity {
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    private static final String[] titles = new String[]{"Tab 1", "Tab 2", "Tab 3", "Tab 4"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        ButterKnife.bind(this);
        showViewPager();
    }

    void showViewPager(){
        Fragment[] fragments = new Fragment[titles.length];
        fragments[0] = MoveFragment.newInstance(titles[0]);
        fragments[1] = MoveFragment.newInstance(titles[1]);
        fragments[2] = MoveFragment.newInstance(titles[2]);
        fragments[3] = MoveFragment.newInstance(titles[3]);
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.length;
            }

            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        };
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
