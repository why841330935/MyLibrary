package com.neusoft.sample.ui.fragmentitem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neusoft.sample.R;
import com.neusoft.sample.customview.CustomPopupWindow;
import com.neusoft.sample.customview.adapter.CommonViewHolder;
import com.neusoft.sample.entity.Title;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2016/9/9.
 */
public class FragmentTab1 extends Fragment{
    @Bind(R.id.ll_tab1)
    LinearLayout ll_tab1;
    @Bind(R.id.ll_tab2)
    LinearLayout ll_tab2;
    @Bind(R.id.ll_tab3)
    LinearLayout ll_tab3;
    @Bind(R.id.tv_tab1)
    TextView tv_tab1;
    @Bind(R.id.tv_tab2)
    TextView tv_tab2;
    @Bind(R.id.tv_tab3)
    TextView tv_tab3;
    private CustomPopupWindow customPopupWindow;
    private List<Title> listTitleNames = new ArrayList<Title>(){{
        add(new Title("找老师",true));
        add(new Title("选课程"));
        add(new Title("寻机构"));
    }};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmenttab1, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.ll_tab1)
    public void onClickTab1(){
        if(customPopupWindow == null){
            customPopupWindow = new CustomPopupWindow<Title>(getActivity(),listTitleNames, R.layout.popupwindow_up_list_item, new CustomPopupWindow.PopupListOnItemClick<Title>() {
                @Override
                public void onItemClick(Title title,int position) {
                    tv_tab1.setText(title.getName());
                    for(int i = 0;i < listTitleNames.size();i++){
                        if(i == position){
                            listTitleNames.get(i).setShow(true);
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
        customPopupWindow.showAsDropDown(ll_tab1);
    }
}
