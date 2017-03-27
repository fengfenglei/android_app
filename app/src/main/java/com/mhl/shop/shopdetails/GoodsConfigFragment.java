package com.mhl.shop.shopdetails;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Build;
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
 * 图文详情里的规格参数的Fragment
 */
public class GoodsConfigFragment extends MyBaseRegistFragment {
    public WebView wv_detail;

    private WebSettings webSettings;
    private LayoutInflater inflater;
    private String goodsId;
    @SuppressLint("ValidFragment")
    public GoodsConfigFragment() {
//        goodsId=goodsid;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_item_detail_web, null);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); }
        String url = Urls.URL_BASE+"/goods-detail-param.html?goodsId="+goodsId;
        wv_detail = (WebView) rootView.findViewById(R.id.wv_detail);
        wv_detail.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); 默认的处理方式，WebView变成空白页
                handler.proceed();
                //handleMessage(Message msg); 其他处理
            }
        });
        wv_detail.getSettings().setJavaScriptEnabled(true);
        wv_detail.setFocusable(false);
        wv_detail.loadUrl(url);
        webSettings = wv_detail.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setBlockNetworkImage(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv_detail.setWebViewClient(new GoodsConfigFragment.GoodsDetailWebViewClient());

    }

    private class GoodsDetailWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webSettings.setBlockNetworkImage(false);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }
}
