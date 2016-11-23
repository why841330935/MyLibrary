package com.neusoft.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neusoft.sample.annotation.AcceptElement;
import com.neusoft.sample.annotation.AcceptType;
import com.neusoft.sample.customview.CustomPopupWindow;
import com.neusoft.sample.customview.ReplyPopupWindow;
import com.neusoft.sample.customview.adapter.CommonViewHolder;
import com.neusoft.sample.module.ImpLoginUser;
import com.neusoft.sample.persenter.LoginPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 外包
 *         PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * @ClassName: MainActivity
 * @time 2016/8/11 10:46
 */
public class MainActivity extends WApiCompatActivity<LoginPresenter, ImpLoginUser> {
//    @Bind(R.id.bt_sample)
//    Button bt_sample;
//    @Bind(R.id.ll_title)
//    LinearLayout ll_title;
    CustomPopupWindow customPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter.login("Rxjava post content");
    }

//    @OnClick(R.id.bt_sample)
    public void onClick() {
//        ReplyPopupWindow takePhotoPopWin = new ReplyPopupWindow(this,ll_title.getHeight(), new ReplyPopupWindow.OpState() {
//            @Override
//            public void reply() {
//
//            }
//
//            @Override
//            public void delete() {
//
//            }
//
//            @Override
//            public void copy() {
//
//            }
//        });
//        int[] location = new int[2];
//        bt_sample.getLocationOnScreen(location);
//        takePhotoPopWin.showAtLocation(bt_sample, Gravity.BOTTOM, 0,0);
//        List list = new ArrayList() {
//            {
//                add("��һ��");
//                add("�ڶ���");
//            }
//        };
//        if(customPopupWindow == null){
//            customPopupWindow = new CustomPopupWindow<String>(this, list, new CustomPopupWindow.PopupListOnItemClick<String>() {
//                @Override
//                public void onItemClcik(String s) {
//
//                }
//            }){
//
//                @Override
//                public void setListElement(CommonViewHolder helper, String item) {
//                    TextView tv = helper.getView(android.R.id.text1);
//                    tv.setText(item);
//                }
//            };
//        }
//        customPopupWindow.showTopPopupWindow(bt_sample);
    }

    @AcceptElement(value = {
            @AcceptType(tag = "RX", clazz = String.class)
    })
    public void onTest(Object tag, String event) {
        Log.e("--------",event);
    }



}
