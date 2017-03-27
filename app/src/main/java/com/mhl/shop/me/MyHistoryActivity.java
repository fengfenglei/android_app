package com.mhl.shop.me;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.AllListView;
import com.mhl.shop.main.MainActivity;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.adapter.HistoryAdapter;
import com.mhl.shop.me.been.History;
import com.mhl.shop.utils.IntentUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;
import com.mhl.shop.wheel.AlertDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-28 10:51
 * 描述：浏览记录
 */
public class MyHistoryActivity extends MyBaseActivity implements AllListView.setOnRefreshListener,AllListView.setLOnRefreshListener {

    private static AllListView lvCollect;
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.title_right_textview)
    TextView titleRightTextview;
    @Bind(R.id.my_supplier_collect_go_look)
    Button mySupplierCollectGoLook;
    @Bind(R.id.me_my_collectSupplier_zero)
    LinearLayout meMyCollectSupplierZero;
    private List<History.RowsBean> list;
    private HistoryAdapter adapter;
    //当前可见的listviewitem的位置
    private int lastItem;
    private int page=1;//加载的页数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        lvCollect = (AllListView) findViewById(R.id.lv_history);
        initView();
        initData(true,page);
        lvCollect.setOnRefreshListener(this);
        lvCollect.setLOnRefreshListener(this);
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
        titleCenterTextview.setText("浏览记录");
        titleRightTextview.setVisibility(View.VISIBLE);
        titleRightTextview.setText("清空");
    }
    private void initData(boolean b, final int page) {
        OkGo.post(Urls.URL_HISTORY_LIST)//
                .tag(this)
                .params("page",page)
                .params("rows", 15)
                .execute(new DialogCallback<LzyResponse<History>>(this,b) {
                             @Override
                             public void onSuccess(LzyResponse<History> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (page==1){
                                     if (list!=null){
                                         list.clear();
                                     }
                                     list = lzyResponse.data.getRows();
                                     if (list != null) {
                                         if (list.size() < 1) {
                                             meMyCollectSupplierZero.setVisibility(View.VISIBLE);
                                             lvCollect.setVisibility(View.GONE);
                                             titleRightTextview.setVisibility(View.GONE);
                                         } else {
                                             adapter = new HistoryAdapter(MyHistoryActivity.this, list);
                                             lvCollect.setAdapter(adapter);
                                             lvCollect.setOnRefreshComplete();
                                         }
                                     }
                                 }else {
                                     if (lzyResponse.data.getRows().size()>0){
                                         list.addAll(lzyResponse.data.getRows());
                                         adapter.notifyDataSetChanged();
                                     }else {
                                         T.showShort(MyHistoryActivity.this,"没有更多数据了");
                                     }
                                     lvCollect.hideFooterView();
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

    @Override
    public void onRefresh() {
        initData(false,1);
        page=1;
    }
    @Override
    public void onLoadingMore() {
        initData(false, ++page);
    }
    @OnClick({R.id.title_left_imageview, R.id.title_right_textview, R.id.my_supplier_collect_go_look})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.title_right_textview:
                new AlertDialog(this).builder().setTitle("温馨提示")
                        .setMsg("确认要清除浏览记录？")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Delete();

                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
                break;
            case R.id.my_supplier_collect_go_look:
                IntentUtils.startActivity(MyHistoryActivity.this, MainActivity.class);
                MyApplication.setTag(0);
                MyApplication.setType(1);
                finish();
                break;
        }
    }

    private void Delete() {
        OkGo.post(Urls.URL_HISTORY_CLEAR)//
                .tag(this)
                .execute(new DialogCallback<LzyResponse<Null>>(this,true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if(lzyResponse.code==200){
                                     initData(true,page);
//                                     T.showShort(MyHistoryActivity.this, lzyResponse.info);
                                 }else{
                                     T.showShort(MyHistoryActivity.this, lzyResponse.info);
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
