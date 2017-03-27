package com.mhl.shop.homepage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.homepage.bean.HomeBean;
import com.mhl.shop.main.MyBaseFragment;
import com.umeng.analytics.MobclickAgent;

/**
 * 作者：Administrator
 * 时间；2016-11-11 17:30
 * 描述：
 */
public class HomePageFragment extends MyBaseFragment implements View.OnClickListener{

    private Context mContext;
    private LayoutInflater mInflater;
    private View mRootView; // 根布局
    private LinearLayout mLinearLayout;
    private FrameLayout mNet_connecte_fail;// 无网络时
    private ImageView mhl_notice;// 货郎通知(顶部左边)
    private TextView home_search_tv;// 顶部搜索框
    private LinearLayout home_ll_scan;// 扫一扫按钮
    private ImageView iv_msg_point;
    private HomeListView mHomeListView; // 今日推荐
    private View mHeaderView; // 头布局(除了今日推荐的listview以外的都是头布局)
    private LinearLayout mLLContainer; // 4个活动view的容器
    private HomePictureHolder1 mHomePictureHolder_1;// 首页顶部的轮播图
//    private ShortCutHolder mShortCutHolder; // 首页快捷方式 ;
    private LinearLayout mLLAdvertiseBoard_1; // 轮播图_1的容器(顶部的)
    public HomeBean.DataEntity.HomeConfigEntity mHomeConfigEntity;
    private ShortCutHolder mShortCutHolder; // 首页快捷方式 ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        this.mInflater = LayoutInflater.from(mContext);// 从上下文获取构造器
        mRootView = (ViewGroup) inflater.inflate(R.layout.new_fra_home, container, false);
        initView();

        return mRootView;
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }
    private void initView() {
        mLinearLayout = (LinearLayout) mRootView.findViewById(R.id.ll_home_bg);
        mLinearLayout.getBackground().setAlpha(0);
        // 无网络时
        mNet_connecte_fail = (FrameLayout) mRootView.findViewById(R.id.net_connecte_fail);

        // home页面TitleBar的控件点击按钮
        mhl_notice = (ImageView) mRootView.findViewById(R.id.mhl_notice);
        iv_msg_point = (ImageView) mRootView.findViewById(R.id.iv_msg_point);

        home_search_tv = (TextView) mRootView.findViewById(R.id.home_search_tv);
        home_ll_scan = (LinearLayout) mRootView.findViewById(R.id.home_ll_scan);
        mhl_notice.setOnClickListener(this);
        home_search_tv.setOnClickListener(this);
        home_ll_scan.setOnClickListener(this);

        mHomeListView = (HomeListView) mRootView.findViewById(R.id.home_lv_recommmend);// 找到今日推荐
        mHeaderView = mInflater.inflate(R.layout.header_view_home, null);// 找到头布局(只有一个，包括列表之外的所有)
        mLLContainer = (LinearLayout) mHeaderView.findViewById(R.id.home_ll_container);// 找到首页4个活动view的根布局容器

        mHomePictureHolder_1 = new HomePictureHolder1(mContext);// 第1个轮播图(顶部)
        mLLAdvertiseBoard_1 = (LinearLayout) mHeaderView.findViewById(R.id.lladvertiseboard_1);// 加载轮播图一,注意必须通过头布局mHeaderView去findViewById
        mLLAdvertiseBoard_1.addView(mHomePictureHolder_1.getRootView()); // 添加轮播图
        mShortCutHolder = new ShortCutHolder(mContext);// 首页快捷方式:每日签到,话费充值,集采专区,我要推广
        mLLAdvertiseBoard_1.addView(mShortCutHolder.getRootView());

        mHomeListView.addHeaderView(mHeaderView);// 为listview动态加载头包括轮播图 热门市场
    }

    @Override
    public void onClick(View view) {

    }
}
