package com.mhl.shop.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.AllListView;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.adapter.ReturnOrserAdapter;
import com.mhl.shop.me.been.Order;
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
 * 时间；2016-12-20 14:38
 * 描述：退货退款列表
 */
public class ReturnRefundActivity extends MyBaseActivity implements AllListView.setOnRefreshListener,AllListView.setLOnRefreshListener{
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.lv_list)
    AllListView lvList;
    @Bind(R.id.no_data)
    LinearLayout noData;
    private int page=1;//加载的页数
    private List<Order.RowsBean> list;
    private ReturnOrserAdapter adapter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_return_refund);
        ButterKnife.bind(this);
        initView();


    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        initData(true,1);

    }


    private void initView() {
        titleCenterTextview.setText("退货退款");
        lvList.setOnRefreshListener(this);
        lvList.setLOnRefreshListener(this);
    }

    @OnClick(R.id.title_left_imageview)
    public void onClick() {
        finish();
    }
    @Override
    public void onRefresh() {
        initData(false,1);
        page=1;
    }
    @Override
    public void onLoadingMore() {
        initData(false, ++page);
    }
    private void initData(boolean b, int i) {
        OkGo.post(Urls.URL_VIEW_ORDER)//
                .tag(this)
                .params("page",i)
                .params("rows",15)
                .params("orderStatus", "8")
                .execute(new DialogCallback<LzyResponse<Order>>(ReturnRefundActivity.this,b) {
                             @Override
                             public void onSuccess(LzyResponse<Order> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (page==1){
                                     if (list!=null){
                                         list.clear();
                                     }
                                     list = lzyResponse.data.getRows();
                                     if (list != null) {
                                         if (list.size() < 1) {
                                             noData.setVisibility(View.VISIBLE);
                                             lvList.setVisibility(View.GONE);
                                         } else {
                                             if(list!=null) {
                                                 noData.setVisibility(View.GONE);
                                                 lvList.setVisibility(View.VISIBLE);
                                                 adapter = new ReturnOrserAdapter(ReturnRefundActivity.this, list);
                                                 lvList.setAdapter(adapter);
                                                 lvList.setOnRefreshComplete();
                                                 lvList.setSelection(0);
                                             }
                                         }
                                     }
                                 }else {
                                     if (lzyResponse.data.getRows().size()>0){
                                         list.addAll(lzyResponse.data.getRows());
                                         adapter.notifyDataSetChanged();
                                     }else {
//                                         T.showShort(ReturnRefundActivity.this,"没有更多数据了");
                                     }
                                     lvList.hideFooterView();
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
