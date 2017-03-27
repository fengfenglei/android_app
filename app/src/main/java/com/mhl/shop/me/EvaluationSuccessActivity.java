package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.AllListView;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.adapter.EvaluationSuccessAdapter;
import com.mhl.shop.me.been.Order;
import com.mhl.shop.shopdetails.ViewEvaluationActivity;
import com.mhl.shop.utils.T;
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
 * 时间；2016-12-23 14:40
 * 描述：评价成功
 */
public class EvaluationSuccessActivity extends MyBaseActivity implements AllListView.setLOnRefreshListener{
    @Bind(R.id.title_left_imageview)
    ImageView titleLeftImageview;
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.view_evaluation)
    Button viewEvaluation;
    @Bind(R.id.ok)
    Button ok;
    @Bind(R.id.lv_list)
    AllListView lvList;
    @Bind(R.id.data_ly)
    LinearLayout dataLy;
    private String pkId;
    private int page=1;//加载的页数
    private List<Order.RowsBean> list;
    private EvaluationSuccessAdapter adapter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_evaluation_success_ctivity);
        ButterKnife.bind(this);

        initView();
        initData(true, page);
        lvList.setLOnRefreshListener(this);
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
    private void initView() {
        titleCenterTextview.setText("评价成功");
        Intent intent = getIntent();
        pkId= intent.getStringExtra("pkId");
    }

    @OnClick({R.id.title_left_imageview, R.id.view_evaluation, R.id.ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.view_evaluation:
                Intent intent = new Intent(EvaluationSuccessActivity.this,ViewEvaluationActivity.class);
                intent.putExtra("goodsId",pkId);
                intent.putExtra("url", Urls.URL_USER_EVALYATE);

                startActivity(intent);
                finish();
                break;
            case R.id.ok:
                finish();
                break;
        }
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
                .params("orderStatus", "4")
                .execute(new DialogCallback<LzyResponse<Order>>(EvaluationSuccessActivity.this,b) {
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
                                             dataLy.setVisibility(View.GONE);
                                         } else {
                                             dataLy.setVisibility(View.VISIBLE);
                                             if(list!=null) {
                                                 adapter = new EvaluationSuccessAdapter(EvaluationSuccessActivity.this, list);
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
                                         T.showShort(EvaluationSuccessActivity.this,"没有更多数据了");
                                     }
                                     lvList.hideFooterView();
                                 }
                             }
                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 handleError(call, response, e);
                                 dataLy.setVisibility(View.GONE);
                             }
                         }
                );
    }
}
