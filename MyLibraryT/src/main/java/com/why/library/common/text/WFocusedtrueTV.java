package com.why.library.common.text;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @ClassName WFocusedtrueTV
 * @author  WangHuanyu
 * @todo 走马灯效果自定义
 * @date 2016/11/9 9:42
 */
public class WFocusedtrueTV extends TextView {

	public WFocusedtrueTV(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WFocusedtrueTV(Context context) {
		super(context);
	}

	public WFocusedtrueTV(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean isFocused() {
		return true;
	}

}
