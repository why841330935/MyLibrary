package com.why.library.tool;

import android.util.Log;

/**
 * @ClassName WLogTool
 * @author  WangHuanyu
 * @todo log
 * @date 2016/11/8 16:45
 */
public final class WLogTool {
	private static String LOGTAG = "WLog_AS";
	public static final boolean LOGSTATE = true;// true正常输出日志，false停止输出日志

	public static void d(String str) {
		if(LOGSTATE){
			Log.d(LOGTAG, str);
		}
	}
	public static void e(String str) {
		if(LOGSTATE){
			Log.e(LOGTAG, str);
		}
	}
	public static void w(String str) {
		if(LOGSTATE){
			Log.w(LOGTAG, str);
		}
	}
	public static void i(String str) {
		if(LOGSTATE){
			Log.i(LOGTAG, str);
		}
	}
	public static void v(String str) {
		if(LOGSTATE){
			Log.v(LOGTAG, str);
		}
	}
	public static void D(String tag,String msg){
		if(LOGSTATE){
			Log.d(tag, msg);
		}
	}
	public static void e(String tag,String msg){
		if(LOGSTATE){
			Log.e(tag, msg);
		}
	}
	public static void w(String tag,String msg){
		if(LOGSTATE){
			Log.w(tag, msg);
		}
	}
	public static void i(String tag,String msg){
		if(LOGSTATE){
			Log.i(tag, msg);
		}
	}
	public static void v(String tag,String msg){
		if(LOGSTATE){
			Log.v(tag, msg);
		}
	}

}
