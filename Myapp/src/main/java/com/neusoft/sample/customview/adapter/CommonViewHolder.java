package com.neusoft.sample.customview.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CommonViewHolder {
	private final SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;

	private CommonViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		// setTag
		mConvertView.setTag(this);
	}

	/**
	 * �õ�һ��ViewHolder����
	 *
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static CommonViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
		CommonViewHolder holder = null;
		if (convertView == null) {
			holder = new CommonViewHolder(context, parent, layoutId, position);
		} else {
			holder = (CommonViewHolder) convertView.getTag();
			holder.mPosition = position;
		}
		return holder;
	}

	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * ͨ���ؼ���Id��ȡ���ڵĿؼ������û�������views
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
	 * ΪTextView�����ַ���
	 *
	 * @param viewId
	 * @param text
	 * @return
	 */
	public CommonViewHolder setText(int viewId, String text) {
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}

	/**
	 * ΪImageView����ͼƬ
	 *
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public CommonViewHolder setImageResource(int viewId, int drawableId) {
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);

		return this;
	}
//
//	/**
//	 * ΪImageView����ͼƬ
//	 *
//	 * @param viewId
//	 * @param drawableId
//	 * @return
//	 */
//	public CommonViewHolder setImageBitmap(int viewId, Bitmap bm) {
//		ImageView view = getView(viewId);
//		view.setImageBitmap(bm);
//		return this;
//	}

//	/**
//	 * ΪImageView����ͼƬ
//	 *
//	 * @param viewId
//	 * @param drawableId
//	 * @return
//	 */
//	public CommonViewHolder setImageByUrlId(int viewId, String url) {
//		ImageLoader.getInstance(3, Type.LIFO).loadImage(url, (ImageView) getView(viewId));
//		return this;
//	}
//
//	/**
//	 * ΪImageView��������ͼƬ
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
	 *            1���� 0��ʾ
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
