package com.why.library.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @ClassName WCommonAdapter
 * @author  WangHuanyu
 * @todo base adapter
 * @date 2016/10/28 14:26
 */
public abstract class WCommonAdapter<T> extends BaseAdapter {
	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDatas;//数据源
	protected final int mItemLayoutId;//xml布局文件id

	public WCommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = mDatas;
		this.mItemLayoutId = itemLayoutId;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final WCommonViewHolder viewHolder = getViewHolder(position, convertView, parent);
		convert(viewHolder, getItem(position),parent);
		return viewHolder.getConvertView();

	}

	public abstract void convert(WCommonViewHolder helper, T item, ViewGroup parent);

	private WCommonViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
		return WCommonViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
	}

	public List<T> getmDatas() {
		return mDatas;
	}

	public void setmDatas(List<T> mDatas) {
		this.mDatas = mDatas;
	}

	public boolean getDataStatus(){
		if(mDatas == null){
			return true;
		}else{
			return false;
		}
	}

}
