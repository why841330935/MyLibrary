package com.neusoft.sample.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.neusoft.sample.MainActivity;
import com.neusoft.sample.R;
import com.neusoft.sample.customview.CustomPopupWindow;
import com.neusoft.sample.customview.adapter.CommonViewHolder;
import com.neusoft.sample.entity.Title;
import com.neusoft.sample.ui.fragmentitem.FragmentTab1;
import com.neusoft.sample.ui.fragmentitem.FragmentTab2;
import com.neusoft.sample.ui.fragmentitem.FragmentTab3;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName BookCourseFragment
 * @author  WangHuanyu
 * @todo 
 * @date 2016/9/9 14:37
 */
public class BookCourseFragment extends Fragment{
	@Bind(R.id.tv_titlename)
	TextView tv_titlename;
	@Bind(R.id.img_search)
	ImageView img_search;
	@Bind(R.id.fl_fcontainer)
	FrameLayout fl_fcontainer;
	private CustomPopupWindow customPopupWindow;
	private TabController mTabController;
	private List<Title> listTitleNames = new ArrayList<Title>(){{
		add(new Title("找老师",true));
		add(new Title("选课程"));
		add(new Title("寻机构"));
	}};
	private List<Class<? extends Fragment>> listFragment = new ArrayList<Class<? extends Fragment>>(){
		{
			add(FragmentTab1.class);
			add(FragmentTab2.class);
			add(FragmentTab3.class);
		}
	};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_book_course, container, false);
		ButterKnife.bind(this,view);
		initView();
		return view;
	}

	private void initView() {
		tv_titlename.setText(listTitleNames.get(0).getName());
		mTabController = new TabController((MainActivity)getActivity(),((MainActivity)getActivity()).getSupportFragmentManager(), R.id.fl_fcontainer);
		mTabController.addTabViewItem(mTabController.new TabItem(listFragment.get(0)));
		mTabController.addTabViewItem(mTabController.new TabItem(listFragment.get(1)));
		mTabController.addTabViewItem(mTabController.new TabItem(listFragment.get(2)));
		mTabController.setCurrentTab(FragmentTab1.class);
	}


	/**
	 * title点击
	 */
	@OnClick(R.id.tv_titlename)
	public void onClickTitleName(){
		if(customPopupWindow == null){
			customPopupWindow = new CustomPopupWindow<Title>(getActivity(),listTitleNames, R.layout.popupwindow_up_list_item, new CustomPopupWindow.PopupListOnItemClick<Title>() {
				@Override
				public void onItemClick(Title title,int position) {
					tv_titlename.setText(title.getName());
					for(int i = 0;i < listTitleNames.size();i++){
						if(i == position){
							listTitleNames.get(i).setShow(true);
							mTabController.setCurrentTab(listFragment.get(position));
						}else{
							listTitleNames.get(i).setShow(false);
						}
					}
					customPopupWindow.adapter.notifyDataSetChanged();
				}
			}) {
				@Override
				public void setListElement(CommonViewHolder helper, Title item) {
					final TextView tv_select_name = helper.getView(R.id.tv_select_name);
					final ImageView img_icon = helper.getView(R.id.img_icon);
					tv_select_name.setText(item.getName());
					if(item.isShow()){
						img_icon.setVisibility(View.VISIBLE);
						tv_select_name.setTextColor(getContext().getResources().getColor(R.color.color_title_text_select));
					}else{
						img_icon.setVisibility(View.GONE);
						tv_select_name.setTextColor(getContext().getResources().getColor(R.color.color_title_text));
					}
				}
			};
		}
		customPopupWindow.showTopPopupWindow(tv_titlename);
	}

	/**
	 * 搜索按钮点击
	 */
	@OnClick(R.id.img_search)
	public void onClickSearch(){

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}
