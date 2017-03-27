package com.mhl.shop.shopdetails.widget;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseRegistFragment;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;


/**
 * 图文详情webview的Fragment
 */
public class GoodsInfoConsultFragment extends MyBaseRegistFragment {
    public ItemWebView mWebView;
    private WebSettings webSettings;
    private LayoutInflater inflater;
    private String mgoodsId;
    @SuppressLint("ValidFragment")
    public GoodsInfoConsultFragment(String goodsid) {
        mgoodsId=goodsid;
    }
    public GoodsInfoConsultFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_item_consult_web, null);
        initWebView(rootView);
        return rootView;
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());

    }
    public void initWebView(View rootView) {

        String url = Urls.URL_BASE+"/goods-detail-consult.html";
        mWebView = (ItemWebView) rootView.findViewById(R.id.wv_detail);
        mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setSupportZoom(false);
//      this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setDomStorageEnabled(true);
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
    }


}

