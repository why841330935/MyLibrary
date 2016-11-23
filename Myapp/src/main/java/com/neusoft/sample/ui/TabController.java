package com.neusoft.sample.ui;


import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;


import com.neusoft.sample.MainActivity;

import java.util.ArrayList;

/**
 * Created by jian-shi on 2016/9/7.
 */
public class TabController {
    private MainActivity mContext;
    private FragmentManager mFragmentManager;
    private ArrayList<TabItem> mTabViewItems;
    private Fragment mCurrentFragment;
    public TabItem mCurrentTabItem;
    public TabItem mSelectTabItem;
    private int containerViewId;

    public TabController(MainActivity context, @NonNull FragmentManager fragmentManager, @NonNull
    int containerViewId)
    {
        mContext = context;
        mFragmentManager = fragmentManager;
        this.containerViewId = containerViewId;
    }


    public TabController addTabViewItem(TabItem tabItem)
    {
        if(mTabViewItems == null){
            mTabViewItems = new ArrayList<>();
        }
        for(TabItem item : mTabViewItems){
            if(item == tabItem){
                return this;
            }
        }
        mTabViewItems.add(tabItem);
        return this;
    }

    public ArrayList<TabItem> getTabViewItems()
    {
        return mTabViewItems;
    }

    public void setCurrentTab(Class<? extends Fragment> fragmentClass){
        final FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentClass.toString());
        findSelectedTabByFragment(fragmentClass);
        if(fragment == null){
            try
            {
                fragment = fragmentClass.newInstance();
                if(mCurrentFragment != null){
                    mFragmentTransaction.hide(mCurrentFragment);
                }
                mFragmentTransaction.add(containerViewId, fragment, fragmentClass.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(fragment == mCurrentFragment){
            //点击的为当前fragment,不作任何操作
            return;
        }else{
            mFragmentTransaction.hide(mCurrentFragment);
            mFragmentTransaction.show(fragment);
        }
        mFragmentTransaction.commit();
        mCurrentFragment = fragment;
        mCurrentTabItem = mSelectTabItem;
    }

    private void findSelectedTabByFragment(Class<? extends Fragment> fragmentClass){
        Log.e("jian.shi", "selected Fragment = " + fragmentClass);
        if(mSelectTabItem == null){
            for(TabItem tabItem : mTabViewItems){
                Log.e("jian.shi", "Fragments = " + tabItem.getFragmentClass());
                if(tabItem.getFragmentClass() == fragmentClass){
                    Log.e("jian.shi", "find selected Fragment = " + tabItem.getFragmentClass());
                    mSelectTabItem = tabItem;
                }
            }
        }
    }

    public class TabItem
    {
        private Class<? extends Fragment> mFragmentClass;

        public TabItem(@NonNull Class<? extends Fragment> fragmentClass)
        {
            mFragmentClass = fragmentClass;
        }

        public Class<? extends Fragment> getFragmentClass()
        {
            return mFragmentClass;
        }

    }
}
