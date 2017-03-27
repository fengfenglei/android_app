package com.mhl.shop.me;

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

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.been.CouponNum;
import com.mhl.shop.me.myview.CustomViewPager;
import com.mhl.shop.me.myview.FragmentAdapter;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-28 17:41
* 描述：优惠券
        */
public class MyCouponActivity extends MyBaseActivity{
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    /**
     * 顶部三个LinearLayout
     */
    private LinearLayout mTab01;
    private LinearLayout mTab02;
    private LinearLayout mTab03;

    /**
     * 顶部的三个TextView
     */
    private TextView all_tv;
    private TextView daifu_tv;
    private TextView daifa_tv;

    /**
     * Tab的那个引导线
     */
    private ImageView mTabLine;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;
    private CustomViewPager mViewPager;
    private FragmentAdapter mAdapter;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    CouponNum data;
    private Resources res;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mycoupon);
        ButterKnife.bind(this);
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
        mViewPager.setCurrentItem(0);

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
    private void initDatas() {
        {// 获取
            OkGo.post(Urls.URL_COUPON_NUM)//
                    .tag(this)
                    .execute(new DialogCallback<LzyResponse<CouponNum>>(this, false) {
                                 @Override
                                 public void onSuccess(LzyResponse<CouponNum> responseData, Call call, Response response) {
                                     handleResponse(responseData.data, call, response);
                                     if(responseData.data!=null){
                                         setData(responseData.data);
                                     }
                                 }
                                 @Override
                                 public void onError(Call call, Response response, Exception e) {
                                     super.onError(call, response, e);
                                     handleError(call, response, e);
                                 }
                             }
                    );
        }
    }

    private void setData(CouponNum data) {
        all_tv.setText("未使用("+(data.getNotUsedNum()+"")+")");
        daifu_tv.setText("已使用("+(data.getAlreadyUsedNum()+"")+")");
        daifa_tv.setText("已过期("+(data.getExpiredNum()+"")+")");
    }

    /**
     * 根据屏幕的宽度，初始化引导线的宽度
     */
    private void initTabLine() {
        mTabLine=(ImageView) findViewById(R.id.id_tab_line);
        titleCenterTextview.setText("我的优惠券");
        //获取屏幕的宽度
        DisplayMetrics outMetrics=new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        screenWidth=outMetrics.widthPixels;

        //获取控件的LayoutParams参数(注意：一定要用父控件的LayoutParams写LinearLayout.LayoutParams)
        LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams) mTabLine.getLayoutParams();
        lp.width=screenWidth/3;//设置该控件的layoutParams参数
        mTabLine.setLayoutParams(lp);//将修改好的layoutParams设置为该控件的layoutParams
    }

    /**
     * 初始化控件，初始化Fragment
     */

    private void initView() {
        all_tv=(TextView) findViewById(R.id.id_all);
        daifu_tv=(TextView) findViewById(R.id.id_daifu);
        daifa_tv=(TextView) findViewById(R.id.id_daifa);


        all_tv.setOnClickListener(new TabOnClickListener(0));
        daifu_tv.setOnClickListener(new TabOnClickListener(1));
        daifa_tv.setOnClickListener(new TabOnClickListener(2));


        fragments.add(new CouponFragment01());
        fragments.add(new CouponFragment02());
        fragments.add(new CouponFragment03());


        mTab01=(LinearLayout) findViewById(R.id.id_tab1);
        mTab02=(LinearLayout) findViewById(R.id.id_tab2);
        mTab03=(LinearLayout) findViewById(R.id.id_tab3);
        initDatas();
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        all_tv.setTextColor(res.getColor(R.color.text_color));
        daifu_tv.setTextColor(res.getColor(R.color.text_color));
        daifa_tv.setTextColor(res.getColor(R.color.text_color));
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
            LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams) mTabLine.getLayoutParams();
            //返回组件距离左侧组件的距离
            lp.leftMargin= (int) ((positionOffset+position)*screenWidth/3);
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
            }
        }
    }
    @OnClick(R.id.title_left_imageview)
    public void onClick() {
        finish();
    }


}
