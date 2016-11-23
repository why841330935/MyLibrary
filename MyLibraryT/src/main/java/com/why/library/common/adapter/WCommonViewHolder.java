package com.why.library.common.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @ClassName WCommonViewHolder
 * @author  WangHuanyu
 * @todo holder
 * @date 2016/10/28 14:26
 */
public class WCommonViewHolder {
	private final SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;

	private WCommonViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		// setTag
		mConvertView.setTag(this);
	}

	/**
	 * 拿到一个ViewHolder对象
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static WCommonViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
		WCommonViewHolder holder = null;
		if (convertView == null) {
			holder = new WCommonViewHolder(context, parent, layoutId, position);
		} else {
			holder = (WCommonViewHolder) convertView.getTag();
			holder.mPosition = position;
		}
		return holder;
	}

	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 通过控件的Id获取对于的控件，如果没有则加入views
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * 获取tv
	 * @param viewId
	 * @return
     */
	public TextView getTextView(int viewId){
		TextView view = getView(viewId);
		return view;
	}

	/**
	 * 获取img
	 * @param viewId
	 * @return
     */
	public ImageView getImageView(int viewId){
		ImageView img = getView(viewId);
		return img;
	}
	/**
	 * 为TextView设置字符串
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public WCommonViewHolder setText(int viewId, String text) {
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public WCommonViewHolder setImageResource(int viewId, int drawableId) {
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);

		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @return
	 */
	public WCommonViewHolder setImageBitmap(int viewId, Bitmap bm) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}

//	/**
//	 * 为ImageView设置图片
//	 *
//	 * @param viewId
//	 * @param drawableId
//	 * @return
//	 */
//	public WCommonViewHolder setImageByUrlId(int viewId, String url) {
//		ImageLoader.getInstance(3, Type.LIFO).loadImage(url, (ImageView) getView(viewId));
//		return this;
//	}

//	/**
//	 * 为ImageView设置网络图片
//	 *
//	 * @param viewId
//	 * @param url
//	 */
//	public void setImageByUrl(int viewId, String url) {
//		ImageView view = getView(viewId);
//		ImageLoaderTool.imageLoader.displayImage(url, view, ImageLoaderTool.options);
//	}

	/**
	 * 
	 * @param viewId
	 * @param state
	 *            1隐藏 0显示
	 */
	public void setViewVisible(int viewId, String state) {
		if (state.equals("1")) {
			getView(viewId).setVisibility(View.GONE);
		} else {
			getView(viewId).setVisibility(View.VISIBLE);
		}
	}

	public int getPosition() {
		return mPosition;
	}

}
