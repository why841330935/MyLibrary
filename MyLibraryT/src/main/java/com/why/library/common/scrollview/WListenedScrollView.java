package com.why.library.common.scrollview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * @ClassName ListenedScrollView
 * @author  WangHuanyu
 * @todo 滚动监听scrollview屏蔽粘性滑动不顺畅场景
 * @date 2016/9/20 14:07
 */
public class WListenedScrollView extends ScrollView {
    private static final String TAG = WListenedScrollView.class.getSimpleName();
    // 检查ScrollView的最终状态
    private static final int CHECK_STATE = 0;
    // 外部设置的监听方法
    private OnScrollListener onScrollListener;
    // 是否在触摸状态
    private boolean inTouch = false;
    // 上次滑动的最后位置
    private int lastT = 0;

    /**
     * 构造方法
     *
     * @param context
     */
    public WListenedScrollView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 构造方法
     *
     * @param context
     * @param attrs
     */
    public WListenedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 构造方法
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public WListenedScrollView(Context context, AttributeSet attrs,
                               int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                inTouch = true;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                inTouch = false;

                lastT = getScrollY();
                checkStateHandler.removeMessages(CHECK_STATE);// 确保只在最后一次做这个check
                checkStateHandler.sendEmptyMessageDelayed(CHECK_STATE, 5);// 5毫秒检查一下

                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener == null) {
            return;
        }

        Log.d(TAG, "t = " + t + ", oldt = " + oldt);

        if (inTouch) {
            if (t != oldt) {
                // 有手指触摸，并且位置有滚动
//                Log.i(TAG, "SCROLL_STATE_TOUCH_SCROLL");
                onScrollListener.onScrollStateChanged(this,
                        OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
            }
        } else {
            if (t != oldt) {
                // 没有手指触摸，并且位置有滚动，就可以简单的认为是在fling
//                Log.w(TAG, "SCROLL_STATE_FLING");
                onScrollListener.onScrollStateChanged(this,
                        OnScrollListener.SCROLL_STATE_FLING);
                // 记住上次滑动的最后位置
                lastT = t;
                checkStateHandler.removeMessages(CHECK_STATE);// 确保只在最后一次做这个check
                checkStateHandler.sendEmptyMessageDelayed(CHECK_STATE, 5);// 5毫秒检查一下
            }
        }
        onScrollListener.onScrollChanged(l, t, oldl, oldt);
    }

    private Handler checkStateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (lastT == getScrollY()) {
                if(onScrollListener != null){
                    // 如果上次的位置和当前的位置相同，可认为是在空闲状态
//                Log.e(TAG, "SCROLL_STATE_IDLE");
                    onScrollListener.onScrollStateChanged(WListenedScrollView.this,
                            OnScrollListener.SCROLL_STATE_IDLE);
                    // from http://blog.chinaunix.net/uid-20782417-id-1645164.html
                    if (getScrollY() + getHeight() >= computeVerticalScrollRange()) {
                        onScrollListener.onBottomArrived();
                    } else {
                        Log.d(TAG, "没有到最下方");
                    }
                }
            }
        }
    };

    /**
     * 设置滚动监听事件
     *
     * @param onScrollListener
     *            {@link OnScrollListener} 滚动监听事件（注意类的不同，虽然名字相同）
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    /**
     * 滚动监听事件
     *
     * @author 拉风的道长
     *
     */
    public interface OnScrollListener {
        /**
         * The view is not scrolling. Note navigating the list using the
         * trackball counts as being in the idle state since these transitions
         * are not animated.
         */
        public static int SCROLL_STATE_IDLE = 0;

        /**
         * The user is scrolling using touch, and their finger is still on the
         * screen
         */
        public static int SCROLL_STATE_TOUCH_SCROLL = 1;

        /**
         * The user had previously been scrolling using touch and had performed
         * a fling. The animation is now coasting to a stop
         */
        public static int SCROLL_STATE_FLING = 2;

        /**
         * 滑动到底部回调
         */
        public void onBottomArrived();

        /**
         * 滑动状态回调
         *
         * @param view
         *            当前的scrollView
         * @param scrollState
         *            当前的状态
         */
        public void onScrollStateChanged(WListenedScrollView view,
                                         int scrollState);

        /**
         * 滑动位置回调
         *
         * @param l
         * @param t
         * @param oldl
         * @param oldt
         */
        public void onScrollChanged(int l, int t, int oldl, int oldt);
    }
    private int downX;
    private int downY;
    private int mTouchSlop;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }
}
