package com.mhl.shop.main;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.shopdetails.GoodsDetailsActivity;
import com.mhl.shop.utils.Constant;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：lff
 * 时间；2016-11-17 15:24
 * 描述：公用的网页显示
 */
public class WebActivity extends  MyBaseActivity{
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    private WebView mWebView;
    private String url;//uRL
    private String  title="";//标题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abuout_us);
        ButterKnife.bind(this);
        init();

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

        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra(Constant.LBONCLICKURL);
            title=intent.getStringExtra(Constant.TITLE);
            titleCenterTextview.setText(title);
        }
//		if(title.equals("二维码")||title.equals("关于卖货郎")){
//			loadUrl(url);
//			LogUtils.d("abbott", "url=二维码="+url);
//		}else{
//			loadUrl(Common.URL_BASE+url);
//			LogUtils.d("abbott", "url=url="+Common.URL_BASE+url);
//		}


        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setSupportZoom(false);
//      this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setDomStorageEnabled(true);
//        this.mWebView.loadData(url,"text/html","UTF-8");
        // 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

            }
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
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
        mWebView.loadUrl(url);

        //  添加js交互接口
        mWebView.addJavascriptInterface(new MyJava(this), "javaObject");

    }
    @OnClick(R.id.title_left_imageview)
    public void onClick() {
        finish();
    }
    private class MyJava {
        private Context mContext;

        public MyJava(Context context) {
            this.mContext = context;
        }
        // 在js中调用window.javaObject.javaFun(name)，便会触发此方法。
        // api17版本以上加上注解
        @JavascriptInterface
        public void bugNowAPP(String id) {


            Intent intent = new Intent();
            intent.putExtra("goodsid",id.replace("A",""));
            intent.setClass(WebActivity.this, GoodsDetailsActivity.class);
            startActivity(intent);
        }
    }



}
