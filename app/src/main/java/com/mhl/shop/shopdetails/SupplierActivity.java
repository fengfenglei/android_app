package com.mhl.shop.shopdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.AllListView;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.shopdetails.adapter.SupplierListAdapter;
import com.mhl.shop.shopdetails.been.Supplier;
import com.mhl.shop.shopdetails.been.SupplierHead;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-12-13 14:41
 * 描述：供应商列表
 */
public class SupplierActivity extends MyBaseActivity implements AllListView.setOnRefreshListener, AllListView.setLOnRefreshListener {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.no_data)
    LinearLayout noData;
    private int page = 1;//加载的页数
    private static AllListView lv_list;
    private String supplierId;//供应商id
    private List<Supplier> list;
    private SupplierListAdapter adapter;
    private View vHead;
    private CircleImageView supplier_top_img;
    private TextView supplier_top_titleName, supplier_pl_content_txt, supplier_top_hpNumber, supplier_service_pl_content_txt, supplier_ship_pl_content_txt, supplier_goodinfo_txt, supplier_service_ship_txt, supplier_ship_txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_list);
        ButterKnife.bind(this);
        lv_list = (AllListView) findViewById(R.id.lv_list);
        vHead = View.inflate(this, R.layout.advertisement_default, null);
        lv_list.addHeaderView(vHead);
        initView();
        initData(true, page);
        initDataHead(false, page);
        lv_list.setOnRefreshListener(this);
        lv_list.setLOnRefreshListener(this);

    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    private void initDataHead(boolean b, int page) {
        OkGo.post(Urls.URL_INDEX_SUPPLIER)//
                .tag(this)
                .params("supplierId", supplierId)
                .execute(new DialogCallback<LzyResponse<SupplierHead>>(this, b) {
                             @Override
                             public void onSuccess(LzyResponse<SupplierHead> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);

                                 if (lzyResponse.data != null) {
                                     Glide.with(SupplierActivity.this).load(Urls.URL_PHOTO + "/file/v3/download-" + lzyResponse.data.getIdentificationId() + "-170-170.jpg").into(supplier_top_img);
                                     supplier_top_titleName.setText(lzyResponse.data.getSupplierName());
                                     supplier_top_hpNumber.setText(lzyResponse.data.getSupplierGoodsNum() + "");
                                     supplier_pl_content_txt.setText(lzyResponse.data.getSupplierGoodsScore() + "");
                                     supplier_service_pl_content_txt.setText(lzyResponse.data.getSupplierServiceScore() + "");
                                     supplier_ship_pl_content_txt.setText(lzyResponse.data.getLogisticsServiceScore() + "");

                                     if (Double.parseDouble(lzyResponse.data.getSupplierGoodsScore() + "") < 4) {
                                         supplier_goodinfo_txt.setText("低");
                                     } else if (Double.parseDouble(lzyResponse.data.getSupplierGoodsScore() + "") == 4) {
                                         supplier_goodinfo_txt.setText("持平");
                                     } else {
                                         supplier_goodinfo_txt.setText("高");
                                     }

                                     if (Double.parseDouble(lzyResponse.data.getSupplierServiceScore() + "") < 4) {
                                         supplier_service_ship_txt.setText("低");
                                     } else if (Double.parseDouble(lzyResponse.data.getSupplierServiceScore() + "") == 4) {
                                         supplier_service_ship_txt.setText("持平");
                                     } else {
                                         supplier_service_ship_txt.setText("高");
                                     }

                                     if (Double.parseDouble(lzyResponse.data.getLogisticsServiceScore() + "") < 4) {
                                         supplier_ship_txt.setText("低");
                                     } else if (Double.parseDouble(lzyResponse.data.getLogisticsServiceScore() + "") == 4) {
                                         supplier_ship_txt.setText("持平");
                                     } else {
                                         supplier_ship_txt.setText("高");
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

    //初始化视图
    private void initView() {
        Intent getIntent = getIntent();
        supplierId = getIntent.getStringExtra("supplierId");
        supplier_top_img = (CircleImageView) findViewById(R.id.supplier_top_img);
        supplier_top_titleName = (TextView) findViewById(R.id.supplier_top_titleName);
        supplier_top_hpNumber = (TextView) findViewById(R.id.supplier_top_hpNumber);
        supplier_pl_content_txt = (TextView) findViewById(R.id.supplier_pl_content_txt);
        supplier_service_pl_content_txt = (TextView) findViewById(R.id.supplier_service_pl_content_txt);
        supplier_ship_pl_content_txt = (TextView) findViewById(R.id.supplier_ship_pl_content_txt);

        supplier_goodinfo_txt = (TextView) findViewById(R.id.supplier_goodinfo_txt);
        supplier_service_ship_txt = (TextView) findViewById(R.id.supplier_service_ship_txt);
        supplier_ship_txt = (TextView) findViewById(R.id.supplier_ship_txt);


        titleCenterTextview.setText("供应商");
    }

    //加载数据
    private void initData(boolean b, final int page) {
        OkGo.post(Urls.URL_VIEW_SUPPLIER)//
                .tag(this)
                .params("supplierId", supplierId)
                .params("page", page)
                .params("rows", 20)
                .execute(new DialogCallback<LzyResponse<List<Supplier>>>(this, b) {
                             @Override
                             public void onSuccess(LzyResponse<List<Supplier>> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (page == 1) {
                                     if (list != null) {
                                         list.clear();
                                     }
                                     list = lzyResponse.data;
                                     if (list != null) {
                                         if (list.size() < 1) {
                                             noData.setVisibility(View.VISIBLE);
                                             lv_list.setVisibility(View.GONE);
                                         } else {
                                             adapter = new SupplierListAdapter(SupplierActivity.this, list);
                                             lv_list.setAdapter(adapter);
                                             lv_list.setOnRefreshComplete();
                                         }
                                     }
                                 } else {
                                     if (lzyResponse.data.size() > 0) {
                                         list.addAll(lzyResponse.data);
                                         adapter.notifyDataSetChanged();
                                     } else {
                                         T.showShort(SupplierActivity.this, "没有更多数据了");
                                     }
                                     lv_list.hideFooterView();
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

    //下拉刷新
    @Override
    public void onRefresh() {
        initData(false, 1);
        page = 1;
    }

    //上拉
    @Override
    public void onLoadingMore() {
        if (list.size() < 20) {

        }
        {
            initData(false, ++page);
        }

    }

    @OnClick(R.id.title_left_imageview)
    public void onClick() {
        finish();
    }
}

