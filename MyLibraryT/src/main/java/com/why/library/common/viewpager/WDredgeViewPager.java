package com.why.library.common.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
/**
 * @ClassName WDredgeViewPager
 * @author  WangHuanyu
 * @todo 禁滑viewpager
 * @date 2016/11/9 9:44
 */
public class WDredgeViewPager extends ViewPager{

	
	public WDredgeViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override  
	public boolean onInterceptTouchEvent(MotionEvent ev) {  
	    return false;  
	}  

}
