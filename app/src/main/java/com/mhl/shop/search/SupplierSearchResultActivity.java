package com.mhl.shop.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.AllListView;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.search.adapter.SupplierSearchAdapter;
import com.mhl.shop.search.been.SupplierMain;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2017-1-11 16:48
 * 描述：供应商搜索结果页面
 */

public class SupplierSearchResultActivity extends MyBaseActivity implements AllListView.setOnRefreshListener, AllListView.setLOnRefreshListener {
    @Bind(R.id.lv_data)
    AllListView lvData;
    @Bind(R.id.no_data)
    LinearLayout noData;
    @Bind(R.id.search_et_search)
    TextView searchEtSearch;
    private int page = 1;//加载的页数
    private List<SupplierMain> list;
    private SupplierSearchAdapter adapter;
    private String supplierkey = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aupplier_search);
        ButterKnife.bind(this);
        initView();
        initData(true, page);
        lvData.setOnRefreshListener(this);
        lvData.setLOnRefreshListener(this);
    }


    private void initView() {
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        supplierkey = intent.getStringExtra("supplierkey");
        searchEtSearch.setText(supplierkey);
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
    @Override
    public void onRefresh() {
        initData(false, 1);
        page = 1;
    }

    @Override
    public void onLoadingMore() {
        initData(false, ++page);
    }

    private void initData(boolean b, final int page) {
        OkGo.post(Urls.URL_UPPLIER_NAME)//
                .tag(this)
                .params("supplierName",supplierkey)
                .params("searchType","2")
                .params("page", page)
                .params("rows", "10")
                .execute(new DialogCallback<LzyResponse<List<SupplierMain>>>(this,b) {
                                  @Override
                                  public void onSuccess(LzyResponse<List<SupplierMain>> lzyResponse, Call call, Response response) {
                                      handleResponse(lzyResponse.data, call, response);

                                      if (page==1){
                                     if (list!=null){
                                         list.clear();
                                     }
                                     list = lzyResponse.data;
                                     if (list != null) {
                                         if (list.size() < 1) {
                                             noData.setVisibility(View.VISIBLE);
                                             lvData.setVisibility(View.GONE);
                                         } else {
                                             adapter = new SupplierSearchAdapter(SupplierSearchResultActivity.this, list);
                                             lvData.setAdapter(adapter);
                                             lvData.setOnRefreshComplete();
                                             lvData.setSelection(0);
                                         }
                                     }
                                 }else {
                                     if (lzyResponse.data.size()>0){
                                         list.addAll(lzyResponse.data);
                                         adapter.notifyDataSetChanged();
                                     }else {
//                                         T.showShort(getActivity(),"没有更多数据了");
                                     }
                                     lvData.hideFooterView();

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

    @OnClick({R.id.search_back_iv, R.id.search_search_bar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back_iv:
                finish();
                break;
            case R.id.search_search_bar:
                finish();
                break;
        }
    }
}
