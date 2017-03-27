package com.mhl.shop.shopdetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.gxz.PagerSlidingTabStrip;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LoginActivity;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.MainActivity;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.shopdetails.adapter.ItemTitlePagerAdapter;
import com.mhl.shop.shopdetails.been.Comment;
import com.mhl.shop.shopdetails.been.Details;
import com.mhl.shop.shopdetails.widget.NoScrollViewPager;
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
 * 时间；2016-12-7 15:17
 * 描述：商品详情主Activity
 */
public class GoodsDetailsActivity extends MyBaseActivity {

    @Bind(R.id.psts_tabs)
    PagerSlidingTabStrip pstsTabs;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.vp_content)
    NoScrollViewPager vpContent;

    private List<Fragment> fragmentList = new ArrayList<>();
    private GoodsInfoFragment goodsInfoFragment;
    private GoodsDetailFragment goodsDetailFragment;
    private String goodsid;//接收到的商品id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        goodsid = intent.getStringExtra("goodsid");
        initData();
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    //获取商品详情
    private void initData() {
        OkGo.post(Urls.URL_DETAILS_SHOP)//
                .tag(this)
                .params("goodsId", goodsid)
                .execute(new DialogCallback<LzyResponse<Details>>(this, true) {
                             @Override
                             public void onSuccess(LzyResponse<Details> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (lzyResponse.data != null) {
                                     //获取评论第一条评论
                                     comment(lzyResponse.data);
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

    @SuppressLint("ValidFragment")
    private void comment(final Details data) {
        OkGo.post(Urls.URL_COMMENT_SHOP)//
                .tag(this)
                .params("goodsId", goodsid)
                .params("page", "1")
                .params("rows", "1")
                .execute(new DialogCallback<LzyResponse<Comment>>(this, true) {
                             @Override
                             public void onSuccess(LzyResponse<Comment> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (lzyResponse.data != null) {
                                     fragmentList.add(goodsInfoFragment = new GoodsInfoFragment(goodsid, data, lzyResponse.data.getRows()));
//                                     fragmentList.add(goodsDetailFragment = new GoodsDetailFragment());
//                                     Log.d("goodsaa","fragmentList");

                                     vpContent.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
                                             fragmentList, new String[]{"商品详情"}));
                                     vpContent.setOffscreenPageLimit(0);
                                     pstsTabs.setViewPager(vpContent);

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

    @OnClick({R.id.ll_back, R.id.go_cart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.go_cart:
                if (!MyApplication.getApplication().isLogin()) { // 如果没登陆
                    // 跳转到登陆界面
                    Intent intent = new Intent(GoodsDetailsActivity.this, LoginActivity.class);
                    intent.putExtra("index", 4);
                    startActivity(intent);
                } else {
                    // 登录传 跳到购物车
                    Intent intent = new Intent(GoodsDetailsActivity.this, MainActivity.class);
                    MyApplication.setTag(3);
                    MyApplication.setType(1);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}
