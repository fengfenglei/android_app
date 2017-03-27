package com.mhl.shop.me;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.myview.CustomViewPager;
import com.mhl.shop.me.myview.FragmentAdapter;
import com.mhl.shop.utils.Constant;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：lff
 * 时间；2016-12-14 14:02
 * 描述：订单列表
 */
public class OrderMainActivity extends MyBaseActivity {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    /**
     * 顶部三个LinearLayout
     */
    private LinearLayout mTab01;
    private LinearLayout mTab02;
    private LinearLayout mTab03;
    private LinearLayout mTab04;
    private LinearLayout mTab05;
    /**
     * 顶部的三个TextView
     */
    private TextView all_tv;
    private TextView daifu_tv;
    private TextView daifa_tv;
    private TextView daishou_tv;
    private TextView daiping_tv;
    /**
     * Tab的那个引导线
     */
    private ImageView mTabLine;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;
    private String state;
    private CustomViewPager mViewPager;
    private FragmentAdapter mAdapter;
    private List<Fragment> fragments = new ArrayList<Fragment>();

    private Resources res;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        state=intent.getStringExtra(Constant.ORDER_STATE);
        res=getResources();
        initView();
        mViewPager=(CustomViewPager) findViewById(R.id.id_viewpager);
        /**
         * 初始化Adapter
         */
        mAdapter=new FragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new TabOnPageChangeListener());
        initTabLine();
        //指定选择某一页
        mViewPager.setCurrentItem(Integer.parseInt(state));
    }


    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    /**
     * 根据屏幕的宽度，初始化引导线的宽度
     */
    private void initTabLine() {
        mTabLine=(ImageView) findViewById(R.id.id_tab_line);
        titleCenterTextview.setText("我的订单");
        //获取屏幕的宽度
        DisplayMetrics outMetrics=new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        screenWidth=outMetrics.widthPixels;

        //获取控件的LayoutParams参数(注意：一定要用父控件的LayoutParams写LinearLayout.LayoutParams)
        LinearLayout.LayoutParams lp=(android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
        lp.width=screenWidth/5;//设置该控件的layoutParams参数
        mTabLine.setLayoutParams(lp);//将修改好的layoutParams设置为该控件的layoutParams
    }

    /**
     * 初始化控件，初始化Fragment
     */

    private void initView() {
        all_tv=(TextView) findViewById(R.id.id_all);
        daifu_tv=(TextView) findViewById(R.id.id_daifu);
        daifa_tv=(TextView) findViewById(R.id.id_daifa);
        daishou_tv=(TextView) findViewById(R.id.id_daishou);
        daiping_tv=(TextView) findViewById(R.id.id_daiping);

        all_tv.setOnClickListener(new TabOnClickListener(0));
        daifu_tv.setOnClickListener(new TabOnClickListener(1));
        daifa_tv.setOnClickListener(new TabOnClickListener(2));
        daishou_tv.setOnClickListener(new TabOnClickListener(3));
        daiping_tv.setOnClickListener(new TabOnClickListener(4));

        fragments.add(new AllOrderFragment());
        fragments.add(new PendingPaymentFragment());
        fragments.add(new ShippedFragment());
        fragments.add(new ReceivedGoodsFragment());
        fragments.add(new EvaluatedFragment());

        mTab01=(LinearLayout) findViewById(R.id.id_tab1);
        mTab02=(LinearLayout) findViewById(R.id.id_tab2);
        mTab03=(LinearLayout) findViewById(R.id.id_tab3);
        mTab04=(LinearLayout) findViewById(R.id.id_tab4);
        mTab05=(LinearLayout) findViewById(R.id.id_tab5);
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        all_tv.setTextColor(res.getColor(R.color.text_color));
        daifu_tv.setTextColor(res.getColor(R.color.text_color));
        daifa_tv.setTextColor(res.getColor(R.color.text_color));
        daishou_tv.setTextColor(res.getColor(R.color.text_color));
        daiping_tv.setTextColor(res.getColor(R.color.text_color));
    }

    /**
     * 功能：点击主页TAB事件
     */
    public class TabOnClickListener implements View.OnClickListener{
        private int index=0;

        public TabOnClickListener(int i){
            index=i;
        }

        public void onClick(View v) {
            mViewPager.setCurrentItem(index);//选择某一页
        }

    }

    /**
     * 功能：Fragment页面改变事件
     */
    public class TabOnPageChangeListener implements ViewPager.OnPageChangeListener {

        //当滑动状态改变时调用
        public void onPageScrollStateChanged(int state) {

        }

        //当前页面被滑动时调用
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
            LinearLayout.LayoutParams lp=(android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
            //返回组件距离左侧组件的距离
            lp.leftMargin= (int) ((positionOffset+position)*screenWidth/5);
            mTabLine.setLayoutParams(lp);
        }

        //当新的页面被选中时调用
        public void onPageSelected(int position) {
            //重置所有TextView的字体颜色
            resetTextView();
            switch (position) {
                case 0:
                    all_tv.setTextColor(res.getColor(R.color.text_color_red));
                    break;
                case 1:
                    daifu_tv.setTextColor(res.getColor(R.color.text_color_red));
                    break;
                case 2:
                    daifa_tv.setTextColor(res.getColor(R.color.text_color_red));
                    break;
                case 3:
                    daishou_tv.setTextColor(res.getColor(R.color.text_color_red));
                    break;
                case 4:
                    daiping_tv.setTextColor(res.getColor(R.color.text_color_red));
                    break;
            }
        }
    }
    @OnClick(R.id.title_left_imageview)
    public void onClick() {
        finish();
    }

}
