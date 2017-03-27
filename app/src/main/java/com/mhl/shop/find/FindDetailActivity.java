package com.mhl.shop.find;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LoginActivity;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.main.Share;
import com.mhl.shop.me.MeInterface;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2017-1-10 17:52
 * 描述：内容详情
 */
public class FindDetailActivity extends MyBaseActivity {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.title_right_textview)
    TextView titleRightTextview;
    @Bind(R.id.webview)
    WebView mWebView;
    @Bind(R.id.discTags)
    TextView discTags;
    @Bind(R.id.zan)
    ImageView zan;
    @Bind(R.id.favors)
    TextView favors;
    @Bind(R.id.title_right_imageview)
    ImageView titleRightImageview;
    @Bind(R.id.iv_goods_collection)
    ImageView ivGoodsCollection;
    @Bind(R.id.iv_goods_collection_text)
    TextView ivGoodsCollectionText;
    private String pkId,title,icon;
    private FindDetail mydata;
    private int isCollection = 0; // 判断否收藏 1:已经收藏过了 2:未收藏

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_find_detail);
        ButterKnife.bind(this);
        init();
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
    private void init() {
        titleRightImageview.setVisibility(View.VISIBLE);
        titleRightImageview.setBackgroundResource(R.drawable.found_share);
        Intent intent = getIntent();
        if (intent != null) {
            pkId = intent.getStringExtra("pkId");
            title= intent.getStringExtra("title");
            icon= intent.getStringExtra("icon");
            titleCenterTextview.setText("内容详情");
        }
        titleCenterTextview.setText(title);
        mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setSupportZoom(false);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setDomStorageEnabled(true);
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); 默认的处理方式，WebView变成空白页
//                 //接受证书
                handler.proceed();
                //handleMessage(Message msg); 其他处理
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

        });

    }

    private void initData() {
        OkGo.post(Urls.URL_DISCOVER_DETAIL)//
                .tag(this)
                .params("pkId", pkId)
                .execute(new DialogCallback<LzyResponse<FindDetail>>(FindDetailActivity.this, true) {
                             @Override
                             public void onSuccess(LzyResponse<FindDetail> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                  mydata=lzyResponse.data;
                                 setData(lzyResponse.data);

                             }

                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 handleError(call, response, e);
                             }
                         }
                );
    }

    private void setData(FindDetail data) {
        mWebView.loadUrl(Urls.URL_BASE+"/app-findlist-detail.html?pkId="+data.getPkId());
        //如果账号登陆了
        if (MyApplication.getApplication().isLogin())
        {
            if(data.getFavored()==1){ //已赞
                zan.setImageResource(R.drawable.found_like_click);
            }else { //没有赞
                zan.setImageResource(R.drawable.found_like_native);
            }

        }
        discTags.setText(data.getDiscTags());
        favors.setText(data.getFavors()+"");
        get_shop_status(data.getCollectioned());
    }
    // 判断商品是否收藏的状态
    private void get_shop_status(int goodsIsCollect) {
        if (!MyApplication.getApplication().isLogin()) {
            ivGoodsCollection.setBackgroundResource(R.drawable.collect_icon_normal);
            ivGoodsCollectionText.setTextColor(getResources().getColor(R.color.main_text_three_color));
        }else{
            if (goodsIsCollect == 1) {
                isCollection=1;
                ivGoodsCollection.setBackgroundResource(R.drawable.collect_icon_pressed);
                ivGoodsCollectionText.setTextColor(getResources().getColor(R.color.border_light_color));
            } else {
                isCollection = 2;
                ivGoodsCollection.setBackgroundResource(R.drawable.collect_icon_normal);
                ivGoodsCollectionText.setTextColor(getResources().getColor(R.color.main_text_three_color));
            }
        }

    }
    @OnClick({R.id.title_left_imageview, R.id.title_right_textview, R.id.zan,R.id.title_right_imageview, R.id.iv_goods_collection_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.title_right_textview:

                break;

            case R.id.zan:
                if (!MyApplication.getApplication().isLogin())
                {
                    Intent login = new Intent(FindDetailActivity.this, LoginActivity.class);
                   startActivity(login);

                }
                else
                {
                    if(mydata.getFavored()==1){ //取消赞
//                        alist.get(position).setFavored(2);
//                        holder.zan.setImageResource(R.drawable.found_like_native);
//                        zan(alist.get(position).getPkId(),2);
                    }else { //去赞
                        zan(mydata.getPkId(),1);
                        zan.setImageResource(R.drawable.found_like_click);
                        favors.setText( (Integer.parseInt(favors.getText().toString())+1)+"");
                        zan.startAnimation(AnimationUtils.loadAnimation(
                                FindDetailActivity.this, R.anim.dianzan_anim));
                    }

                }
                break;
            case R.id.title_right_imageview:
                Share share = new Share(icon,
                        Urls.URL_BASE+"/app-findlist-detail.html?pkId="+mydata.getPkId(),
                        title,"卖货郎发现",FindDetailActivity.this);
                share.goShare();
                break;
            case R.id.iv_goods_collection_layout:
                if (!MyApplication.getApplication().isLogin()) { // 如果没登陆
                    // 跳转到登陆界面
                    Intent intent = new Intent(FindDetailActivity.this, LoginActivity.class);
                    intent.putExtra("index", 4);
                    startActivity(intent);

                } else {
                    collection(mydata.getPkId());
                }

                break;
        }
    }
    //收藏商品
    private void collection(String pk_id) {
        int collection;
        if(isCollection==1){
            collection=  2;
        }else {
            collection=  1;
        }
        OkGo.post(Urls.URL_DISCOVER_COLLECTION)//
                .tag(this)
                .params("pkId",pk_id)
                .params("collection",collection+"")
                .execute(new DialogCallback<LzyResponse<Null>>(this,true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (lzyResponse.code==200){
                                     if(isCollection==1){
                                         get_shop_status(2);
                                         isCollection=2;
                                     }else {
                                         get_shop_status(1);
                                         isCollection=1;
                                     }
                                 }else {
                                     if(isCollection==1){
                                         T.showShort(FindDetailActivity.this,"收藏失败");
                                     }else {
                                         T.showShort(FindDetailActivity.this,"取消收藏失败");
                                     }
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
    private void zan(String pkId, int i) {
        OkGo.post(Urls.URL_DISCOVER_FAVOR)//
                .tag(this)
                .params("pkId",pkId)
                .params("favor",i)
                .execute(new DialogCallback<LzyResponse<Null>>(this,false) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (lzyResponse.code==200){
//                                    if(i==1){
//                                        holder.zan.setImageResource(R.drawable.found_like_click);
//                                     }else if(i==2){
//                                        holder.zan.setImageResource(R.drawable.found_like_native);
//                                    }
                                     //刷新
                                     if (MeInterface.onMyFindListener!=null) {
                                         MeInterface.onMyFindListener.OnMyFindRefresh("",0);
                                     }
                                 }else {

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
