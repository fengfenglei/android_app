package com.mhl.shop.me;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.main.Share;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.common.SocializeConstants;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：lff
 * 时间；2017-1-6 15:32
 * 描述：推广中心
 */
public class ExtensionCenterActivity extends MyBaseActivity {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.title_right_textview)
    TextView titleRightTextview;
    private WebView mWebView;
    private String url;//uRL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extension_center);
        ButterKnife.bind(this);
        init();

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
    private void init() {

        url = Urls.URL_BASE+"/app-promoted.html";
        titleCenterTextview.setText("推广中心");
        titleRightTextview.setVisibility(View.VISIBLE);
        titleRightTextview.setText("我的推广");

        mWebView = (WebView) findViewById(R.id.webview);
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
        mWebView.loadUrl(url);


    }

    @OnClick(R.id.title_left_imageview)
    public void onClick() {
        finish();
    }
    @OnClick({R.id.title_right_textview, R.id.go_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_right_textview://我的推广中心
                Intent activeIntent = new Intent(ExtensionCenterActivity.this,MyExtensionCenterActivity.class);
                startActivity(activeIntent);
                break;
            case R.id.go_pay:
                Log.d("YOUMENG", SocializeConstants.SDK_VERSION);
                Share share = new Share("http://pp.myapp.com/ma_icon/0/icon_12142988_1484814911/96","http://a.app.qq.com/o/simple.jsp?pkgname=com.mhl.shop",//应用宝下载链接
                        "我的推广","卖货郎商城", ExtensionCenterActivity.this);
                share.goShare();
                break;
        }
    }
}
