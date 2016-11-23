package com.neusoft.sample.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.neusoft.sample.R;
import com.neusoft.sample.customview.adapter.CommonAdapter;
import com.neusoft.sample.customview.adapter.CommonViewHolder;
import com.neusoft.sample.utils.TUtil;

import java.util.List;

public abstract class CustomPopupWindow<T> extends PopupWindow {
    private List<T> mData;
    public CommonAdapter<T> adapter;
    private PopupListOnItemClick<T> popupListOnItemClick;
    private Activity context;
    private ListView listview;

    public abstract void setListElement(CommonViewHolder helper, T item);

    public void setPopupListOnItemClick(PopupListOnItemClick<T> popupListOnItemClick) {
        this.popupListOnItemClick = popupListOnItemClick;
    }

    public interface PopupListOnItemClick<T> {
        void onItemClick(T t,int position);
    }


    public CustomPopupWindow(Activity context, List<T> mData, int layoutId, PopupListOnItemClick<T> popupListOnItemClick) {
        super(context);
        this.mData = mData;
        this.context = context;
        this.popupListOnItemClick = popupListOnItemClick;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mMenuView = inflater.inflate(R.layout.popupwindow_list, null);
        listview = (ListView) mMenuView.findViewById(R.id.listview);
        this.setContentView(mMenuView);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.take_photo_anim);
        this.setTouchable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getBottom();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        adapter = new CommonAdapter<T>(context, mData, layoutId) {
            @Override
            public void convert(CommonViewHolder helper, T item, ViewGroup parent) {
                setListElement(helper, item);
            }
        };
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (popupListOnItemClick != null) {
                    popupListOnItemClick.onItemClick(mData.get(position),position);
                    CustomPopupWindow.this.dismiss();
                } else {
                    Log.e(getClass().getName(), "this item listener is null");
                }
            }
        });
    }

    public void showTopPopupWindow(View... parent) {
        int cutHeight = 0;
        for (View view : parent) {
            cutHeight = cutHeight + view.getMeasuredHeight();
        }
        this.setHeight(context.getWindowManager().getDefaultDisplay().getHeight() - TUtil.getStatusHeight(context) - cutHeight);
        this.showAtLocation(parent[0], Gravity.NO_GRAVITY, 0, 0);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        x = location[0];
        y = location[1];
        int h = parent.getHeight();
        super.showAtLocation(parent, gravity, x, h + y);
    }

}
