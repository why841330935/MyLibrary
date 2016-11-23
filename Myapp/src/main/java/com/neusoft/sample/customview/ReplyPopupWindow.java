package com.neusoft.sample.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.neusoft.sample.R;
import com.neusoft.sample.utils.TUtil;

/**
 * on 2016/8/30.
 * Created by WangHuanyu
 */
public class ReplyPopupWindow<T> extends PopupWindow implements View.OnClickListener {

    private TextView tv_reply, tv_copy, tv_delete, tv_cancel;
    private View mMenuView;
    OpState opState;

    public ReplyPopupWindow(Activity context,int height, OpState opState) {
        super(context);
        this.opState = opState;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.reply_bottom, null);
        tv_reply = (TextView) mMenuView.findViewById(R.id.tv_reply);
        tv_copy = (TextView) mMenuView.findViewById(R.id.tv_copy);
        tv_delete = (TextView) mMenuView.findViewById(R.id.tv_delete);
        tv_cancel = (TextView) mMenuView.findViewById(R.id.tv_cancel);
        tv_reply.setOnClickListener(this);
        tv_copy.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        this.setContentView(mMenuView);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(context.getWindowManager().getDefaultDisplay().getHeight() - height - TUtil.getStatusHeight(context));
        this.setFocusable(true);
        this.setAnimationStyle(R.style.take_photo_anim);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

    public interface OpState {
        void reply();

        void delete();

        void copy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_reply:
                opState.reply();
                dismiss();
                break;
            case R.id.tv_copy:
                opState.copy();
                break;
            case R.id.tv_delete:
                opState.delete();
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

}
