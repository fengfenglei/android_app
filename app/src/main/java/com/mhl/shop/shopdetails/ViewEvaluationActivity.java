package com.mhl.shop.shopdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.AllListView;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.been.Evaluation;
import com.mhl.shop.shopdetails.adapter.EvaluationAdapter;
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
 * 时间；2016-12-12 14:20
 * 描述：商品评价界面
 */
public class ViewEvaluationActivity extends MyBaseActivity implements AllListView.setOnRefreshListener, AllListView.setLOnRefreshListener {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    private int page = 1;//加载的页数
    private static AllListView lv_list;
    private String goodsId,url,k;//商品id
    private List<Evaluation.RowsBean> list;
    private EvaluationAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_evaluation);
        ButterKnife.bind(this);
        lv_list = (AllListView) findViewById(R.id.lv_list);
        initView();
        initData(true, page);
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
    //初始化视图
    private void initView() {
        Intent getIntent = getIntent();
         goodsId = getIntent.getStringExtra("goodsId");
          url = getIntent.getStringExtra("url");

        if(Urls.URL_USER_EVALYATE.equals(url)){
           k="orderDetailId" ;
        }else {
            k="goodsId" ;

        }
        titleCenterTextview.setText("评价");
    }

    //加载数据
    private void initData(boolean b, final int page) {
        OkGo.post(url)//goodsId
                .tag(this)
                .params(k,goodsId)
                .params("page",page)
                .params("rows", 15)
                .execute(new DialogCallback<LzyResponse<Evaluation>>(this,b) {
                             @Override
                             public void onSuccess(LzyResponse<Evaluation> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (page==1){
                                     if (list!=null){
                                         list.clear();
                                     }
                                     list = lzyResponse.data.getRows();
                                     if (list != null) {
                                         if (list.size() < 1) {

                                         } else {
                                             adapter = new EvaluationAdapter(ViewEvaluationActivity.this, list);
                                             lv_list.setAdapter(adapter);
                                             lv_list.setOnRefreshComplete();
                                             titleCenterTextview.setText("评价（"+list.size()+""+"）");
                                         }
                                     }
                                 }else {
                                     if (lzyResponse.data.getRows().size()>0){
                                         list.addAll(lzyResponse.data.getRows());
                                         adapter.notifyDataSetChanged();
                                     }else {
                                         T.showShort(ViewEvaluationActivity.this,"没有更多数据了");
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
        initData(false,1);
        page=1;
    }

    //上拉
    @Override
    public void onLoadingMore() {
        initData(false, ++page);
    }

    @OnClick(R.id.title_left_imageview)
    public void onClick() {
        finish();
    }
}
