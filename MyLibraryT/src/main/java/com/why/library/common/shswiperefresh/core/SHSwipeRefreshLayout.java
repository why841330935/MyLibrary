package com.why.library.common.shswiperefresh.core;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import com.why.library.R;
import com.why.library.common.recycleview.WEmptyAndErrorView;
import com.why.library.tool.WLogTool;


/**
 * @ClassName SHSwipeRefreshLayout
 * @author  WangHuanyu
 * @todo
 * @date 2016/11/9 13:38
 */
public class SHSwipeRefreshLayout extends FrameLayout implements NestedScrollingParent {

    private NestedScrollingParentHelper parentHelper;
    private SHSOnRefreshListener onRefreshListener;

    public static final int NOT_OVER_TRIGGER_POINT = 1;
    public static final int OVER_TRIGGER_POINT = 2;
    public static final int START = 3;

    /**
     * On refresh Callback, call on start refresh
     */
    public interface SHSOnRefreshListener {

        void onRefresh();

        void onLoading();

        void onRefreshPulStateChange(float percent, int state);

        void onLoadmorePullStateChange(float percent, int state);
    }

    static class WXRefreshAnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }

    private SHGuidanceView headerView;//头
    private SHGuidanceView footerView;//尾
    private View mTargetView;
//    private WEmptyAndErrorView emptyAndErrorView;

    private static final int GUIDANCE_VIEW_HEIGHT = 80;//上下拉的引导高度（上下拉的最大高度）
    private static final int ACTION_PULL_REFRESH = 0;
    private static final int ACTION_LOADMORE = 1;

    // Enable PullRefresh and Loadmore
    private boolean mPullRefreshEnable = true;//下拉刷新状态
    private boolean mPullLoadEnable = true;//加载更多状态

    // Is Refreshing
    volatile private boolean mRefreshing = false;//刷新状态

    // RefreshView Height
    private float guidanceViewHeight = 0;//上下拉高度

    // RefreshView Over Flow Height
    private float guidanceViewFlowHeight = 0;//上下拉引导高度最大值（为上下拉高度的1.5倍）

    // Drag Action
    private int mCurrentAction = -1;
    private boolean isConfirm = false;

    // RefreshView Attrs
    private int mGuidanceViewBgColor;
    private int mGuidanceViewTextColor;
    private int mProgressBgColor;
    private int mProgressColor;
    private String mRefreshDefaulText = "下拉刷新";
    private String mLoadDefaulText = "加载更多";
    private Context context;


    public SHSwipeRefreshLayout(Context context) {
        super(context);
        initAttrs(context, null);
    }

    public SHSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public SHSwipeRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SHSwipeRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        WLogTool.e("----","initAttrs");
        this.context = context;
        if (getChildCount() > 1) {//嵌套子view必须大于1
            throw new RuntimeException("WXSwipeLayout should not have more than one child");
        }
        parentHelper = new NestedScrollingParentHelper(this);
        guidanceViewHeight = dipToPx(context, GUIDANCE_VIEW_HEIGHT);
        guidanceViewFlowHeight = guidanceViewHeight * (float)1.5;

        if (isInEditMode() && attrs == null) {
            return;
        }

        int resId;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SHSwipeRefreshLayout);
        Resources resources = context.getResources();

        //Indicator背景颜色
        resId = ta.getResourceId(R.styleable.SHSwipeRefreshLayout_guidance_view_bg_color, -1);
        if (resId == -1) {
            mGuidanceViewBgColor = ta.getColor(R.styleable.SHSwipeRefreshLayout_guidance_view_bg_color,
                    Color.WHITE);
        } else {
            mGuidanceViewBgColor = resources.getColor(resId);
        }

        //加载文字颜色
        resId = ta.getResourceId(R.styleable.SHSwipeRefreshLayout_guidance_text_color, -1);
        if (resId == -1) {
            mGuidanceViewTextColor = ta.getColor(R.styleable.SHSwipeRefreshLayout_guidance_text_color,
                    ContextCompat.getColor(context,R.color.rll_text_color));
        } else {
            mGuidanceViewTextColor = resources.getColor(resId);
        }

        //进度条背景颜色
        resId = ta.getResourceId(R.styleable.SHSwipeRefreshLayout_progress_bg_color, -1);
        if (resId == -1) {
            mProgressBgColor = ta.getColor(R.styleable.SHSwipeRefreshLayout_progress_bg_color,
                    Color.WHITE);
        } else {
            mProgressBgColor = resources.getColor(resId);
        }

        //进度条颜色
        resId = ta.getResourceId(R.styleable.SHSwipeRefreshLayout_progress_bar_color, -1);
        if (resId == -1) {
            mProgressColor = ta.getColor(R.styleable.SHSwipeRefreshLayout_progress_bar_color,
                    ContextCompat.getColor(context,R.color.rll_text_color));
        } else {
            mProgressColor = resources.getColor(resId);
        }

        //下拉刷新文字描述
        String refresh_text = ta.getString(R.styleable.SHSwipeRefreshLayout_refresh_text);
        if (refresh_text != null) {
            mRefreshDefaulText = refresh_text;
        }

        //上拉加载文字描述
        String load_text = ta.getString(R.styleable.SHSwipeRefreshLayout_load_text);
        if(load_text != null){
            mLoadDefaulText = load_text;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        WLogTool.e("----","onAttachedToWindow");
        super.onAttachedToWindow();
        mTargetView = getChildAt(0);
//        emptyAndErrorView = (WEmptyAndErrorView) getChildAt(1);
        if(headerView == null && footerView == null){//towindow时初始化，防止viewpager销毁提示frist remove view
            setGuidanceView();
        }
    }

    /**
     * Init refresh view or loading view
     */
    private void setGuidanceView() {
        if(headerView == null){
            headerView  = new SHGuidanceView(getContext());
        }
        if(footerView == null){
            footerView  = new SHGuidanceView(getContext());
        }
        // SetUp HeaderView
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        headerView.setStartEndTrim(0, 0.75f);
        headerView.setText(mRefreshDefaulText);
        headerView.setTextColor(mGuidanceViewTextColor);
        headerView.setBackgroundColor(mGuidanceViewBgColor);
        headerView.setProgressBgColor(mProgressBgColor);
        headerView.setProgressColor(mProgressColor);
//        addView(headerView, lp);
        addView(headerView,0,lp);

        // SetUp FooterView
        lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        lp.gravity = Gravity.BOTTOM;
        footerView.setStartEndTrim(0.5f, 1.25f);
        footerView.setText(mLoadDefaulText);
        footerView.setTextColor(mGuidanceViewTextColor);
        footerView.setBackgroundColor(mGuidanceViewBgColor);
        footerView.setProgressBgColor(mProgressBgColor);
        footerView.setProgressColor(mProgressColor);
        addView(footerView, lp);
//        addView(footerView, lp);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if ((!mPullRefreshEnable && !mPullLoadEnable)) {
            return false;
        }
        if (mRefreshing) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /*********************************** NestedScrollParent *************************************/

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        parentHelper.onNestedScrollAccepted(child, target, axes);
    }

    /**
     * Callback on TouchEvent.ACTION_CANCLE or TouchEvent.ACTION_UP
     * handler : refresh or loading
     * @param child : child view of SwipeLayout,RecyclerView or Scroller
     */
    @Override
    public void onStopNestedScroll(View child) {
        parentHelper.onStopNestedScroll(child);
        handlerAction();
    }

    /**
     * With child view to processing move events
     * @param target the child view
     * @param dx move x
     * @param dy move y
     * @param consumed parent consumed move distance
     */
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if ((!mPullRefreshEnable && !mPullLoadEnable)) {
            return;
        }

        // Prevent Layout shake
        if (Math.abs(dy) > 200) {
            return;
        }

        if(mRefreshing){//刷新时不接受参数（为了防止拦截事件，在捕获刷新状态下的问题）
            return;
        }

        if (!isConfirm) {
            if (dy < 0 && !canChildScrollUp()) {
                mCurrentAction = ACTION_PULL_REFRESH;
                isConfirm = true;
            } else if (dy > 0 && !canChildScrollDown()) {
                mCurrentAction = ACTION_LOADMORE;
                isConfirm = true;
            }
        }
        if (moveGuidanceView(-dy)) {
            consumed[1] += dy;
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public int getNestedScrollAxes() {
        return parentHelper.getNestedScrollAxes();
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    /**
     * Adjust the refresh or loading view according to the size of the gesture
     *
     * @param distanceY move distance of Y
     */
    private boolean moveGuidanceView(float distanceY) {

        if (mRefreshing) {
            return false;
        }

        if (!canChildScrollUp() && mPullRefreshEnable && mCurrentAction == ACTION_PULL_REFRESH) {
            // Pull Refresh
            LayoutParams lp = (LayoutParams) headerView.getLayoutParams();
            lp.height += distanceY;
            if (lp.height < 0) {
                lp.height = 0;
            }
            if (lp.height > guidanceViewFlowHeight) {
                lp.height = (int) guidanceViewFlowHeight;
            }
            if (onRefreshListener != null) {
                if (lp.height >= guidanceViewHeight) {
                    onRefreshListener.onRefreshPulStateChange(lp.height / guidanceViewHeight, OVER_TRIGGER_POINT);
                } else {
                    onRefreshListener.onRefreshPulStateChange(lp.height / guidanceViewHeight, NOT_OVER_TRIGGER_POINT);
                }
            }

            if (lp.height == 0) {
                isConfirm = false;
                mCurrentAction = -1;
            }
            headerView.setLayoutParams(lp);
            headerView.setProgressRotation(lp.height / guidanceViewFlowHeight);
            moveTargetView(lp.height);
            return true;
        } else if (!canChildScrollDown() && mPullLoadEnable && mCurrentAction == ACTION_LOADMORE) {
            // Load more
            LayoutParams lp = (LayoutParams) footerView.getLayoutParams();
            lp.height -= distanceY;
            if (lp.height < 0) {
                lp.height = 0;
            }
            if (lp.height > guidanceViewFlowHeight) {
                lp.height = (int) guidanceViewFlowHeight;
            }

            if (onRefreshListener != null) {
                if (lp.height >= guidanceViewHeight) {
                    onRefreshListener.onLoadmorePullStateChange(lp.height / guidanceViewHeight, OVER_TRIGGER_POINT);
                } else {
                    onRefreshListener.onLoadmorePullStateChange(lp.height / guidanceViewHeight, NOT_OVER_TRIGGER_POINT);
                }
            }

            if (lp.height == 0) {
                isConfirm = false;
                mCurrentAction = -1;
            }
            footerView.setLayoutParams(lp);
            footerView.setProgressRotation(lp.height / guidanceViewFlowHeight);
            moveTargetView(-lp.height);
            return true;
        }
        return false;
    }

    /**
     * Adjust contentView(Scroller or List) at refresh or loading time
     * @param h Height of refresh view or loading view
     */
    private void moveTargetView(float h) {
//        if(emptyAndErrorView.getVisibility() == View.VISIBLE){
//            mTargetView = emptyAndErrorView;
//        }else{
//            WLogTool.e("--------------------------1111111111111111111111");
//            mTargetView = getChildAt(1);
//        }
        mTargetView.setTranslationY(h);//目标view移动
    }

    /**
     * Decide on the action refresh or loadmore
     */
    private void handlerAction() {
        if (isRefreshing()) {
            return;
        }
        isConfirm = false;

        LayoutParams lp;
        if (mPullRefreshEnable && mCurrentAction == ACTION_PULL_REFRESH) {
            lp = (LayoutParams) headerView.getLayoutParams();
            if (lp.height >= guidanceViewHeight) {
                startRefresh(lp.height);
                if (onRefreshListener != null)
                    onRefreshListener.onRefreshPulStateChange(1,START);
            } else if (lp.height > 0) {
                resetHeaderView(lp.height);
            } else {
                resetRefreshState();
            }
        }

        if (mPullLoadEnable && mCurrentAction == ACTION_LOADMORE) {
            lp = (LayoutParams) footerView.getLayoutParams();
            if (lp.height >= guidanceViewHeight) {
                startLoadmore(lp.height);
                if (onRefreshListener != null)
                    onRefreshListener.onLoadmorePullStateChange(1,START);
            } else if (lp.height > 0) {
                resetFootView(lp.height);
            } else {
                resetLoadmoreState();
            }
        }
    }


    /**
     * Start Refresh
     * @param headerViewHeight
     */
    private void startRefresh(int headerViewHeight) {
        mRefreshing = true;
        ValueAnimator animator = ValueAnimator.ofFloat(headerViewHeight, guidanceViewHeight);//补间动画
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                LayoutParams lp = (LayoutParams) headerView.getLayoutParams();
                lp.height = (int) ((Float) animation.getAnimatedValue()).floatValue();
                headerView.setLayoutParams(lp);
                moveTargetView(lp.height);
            }
        });
        animator.addListener(new WXRefreshAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                headerView.startAnimation();
                //TODO updateLoadText
                if (onRefreshListener != null) {
                    onRefreshListener.onRefresh();
                }
            }
        });
        animator.setDuration(300);
        animator.start();
    }

    /**
     * Reset refresh state
     * @param headerViewHeight
     */
    private void resetHeaderView(int headerViewHeight) {
        headerView.stopAnimation();
        headerView.setStartEndTrim(0, 0.75f);
        ValueAnimator animator = ValueAnimator.ofFloat(headerViewHeight, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                LayoutParams lp = (LayoutParams) headerView.getLayoutParams();
                lp.height = (int) ((Float) animation.getAnimatedValue()).floatValue();
                headerView.setLayoutParams(lp);
                moveTargetView(lp.height);
            }
        });
        animator.addListener(new WXRefreshAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                resetRefreshState();

            }
        });
        animator.setDuration(300);
        animator.start();
    }

    private void resetRefreshState() {
        mRefreshing = false;
        isConfirm = false;
        mCurrentAction = -1;
        //TODO updateLoadText
    }

    /**
     * Start loadmore
     * @param headerViewHeight
     */
    private void startLoadmore(int headerViewHeight) {
        mRefreshing = true;
        ValueAnimator animator = ValueAnimator.ofFloat(headerViewHeight, guidanceViewHeight);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                LayoutParams lp = (LayoutParams) footerView.getLayoutParams();
                lp.height = (int) ((Float) animation.getAnimatedValue()).floatValue();
                footerView.setLayoutParams(lp);
                moveTargetView(-lp.height);
            }
        });
        animator.addListener(new WXRefreshAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                footerView.startAnimation();
                //TODO updateLoadText
                if (onRefreshListener != null) {
                    onRefreshListener.onLoading();
                }
            }
        });
        animator.setDuration(300);
        animator.start();
    }

    /**
     * Reset loadmore state
     * @param headerViewHeight
     */
    private void resetFootView(int headerViewHeight) {
        footerView.stopAnimation();
        footerView.setStartEndTrim(0.5f, 1.25f);
        ValueAnimator animator = ValueAnimator.ofFloat(headerViewHeight, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                LayoutParams lp = (LayoutParams) footerView.getLayoutParams();
                lp.height = (int) ((Float) animation.getAnimatedValue()).floatValue();
                footerView.setLayoutParams(lp);
                moveTargetView(-lp.height);
            }
        });
        animator.addListener(new WXRefreshAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                resetLoadmoreState();

            }
        });
        animator.setDuration(300);
        animator.start();
    }

    private void resetLoadmoreState() {
        mRefreshing = false;
        isConfirm = false;
        mCurrentAction = -1;
        //TODO updateLoadText
    }

    /**
     * Whether child view can scroll up
     * @return
     */
    public boolean canChildScrollUp() {
        if (mTargetView == null) {
            return false;
        }
       if (Build.VERSION.SDK_INT < 14) {
            if (mTargetView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTargetView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mTargetView, -1) || mTargetView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTargetView, -1);
        }
    }

    /**
     * Whether child view can scroll down
     * @return
     */
    public boolean canChildScrollDown() {
        if (mTargetView == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 14) {
            if (mTargetView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTargetView;
                if (absListView.getChildCount() > 0) {
                    int lastChildBottom = absListView.getChildAt(absListView.getChildCount() - 1)
                            .getBottom();
                    return absListView.getLastVisiblePosition() == absListView.getAdapter().getCount() - 1
                            && lastChildBottom <= absListView.getMeasuredHeight();
                } else {
                    return false;
                }

            } else {
                return ViewCompat.canScrollVertically(mTargetView, 1) || mTargetView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTargetView, 1);
        }
    }

    /**
     * px转换dip
     * @param context
     * @param value
     * @return
     */
    public float dipToPx(Context context, float value) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics);
    }

    public void setOnRefreshListener(SHSOnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    /**
     * Callback on refresh finish
     */
    public void finishRefresh() {
        if (mCurrentAction == ACTION_PULL_REFRESH) {
            resetHeaderView(headerView == null ? 0 : headerView.getMeasuredHeight());
        }
    }

    /**
     * Callback on loadmore finish
     */
    public void finishLoadmore() {
        if (mCurrentAction == ACTION_LOADMORE) {
            resetFootView(footerView == null ? 0 : footerView.getMeasuredHeight());
        }
    }

    public boolean isLoadmoreEnable() {
        return mPullLoadEnable;
    }

    public void setLoadmoreEnable(boolean mPullLoadEnable) {
        this.mPullLoadEnable = mPullLoadEnable;
    }

    public boolean isRefreshEnable() {
        return mPullRefreshEnable;
    }

    public void setRefreshEnable(boolean mPullRefreshEnable) {
        this.mPullRefreshEnable = mPullRefreshEnable;
    }

    public boolean isRefreshing() {
        return mRefreshing;
    }

    public void setHeaderView(@LayoutRes int layoutResID) {
        headerView.setGuidanceView(layoutResID);
    }

    public void setHeaderView(View view) {
        headerView.setGuidanceView(view);
    }

    public void setFooterView(@LayoutRes int layoutResID) {
        footerView.setGuidanceView(layoutResID);
    }

    public void setFooterView(View view) {
        footerView.setGuidanceView(view);
    }

    public void setRefreshViewText(String text) {
        headerView.setText(text);
    }

    public void setLoaderViewText(String text) {
        footerView.setText(text);
    }
}
