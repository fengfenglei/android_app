package com.mhl.shop.me;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.AllListView;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.adapter.CollectAdapter;
import com.mhl.shop.me.been.Collect;
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
 * 时间；2016-11-28 10:51
 * 描述：我的收藏
 */
public class MyCollectActivity extends MyBaseActivity implements AllListView.setOnRefreshListener {

    private static AllListView lvCollect;
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.title_right_textview)
    TextView titleRightTextview;
    @Bind(R.id.my_supplier_collect_go_look)
    Button mySupplierCollectGoLook;
    @Bind(R.id.me_my_collectSupplier_zero)
    LinearLayout meMyCollectSupplierZero;
    private List<Collect.RowsBean> list;
    private CollectAdapter adapter;
    //当前可见的listviewitem的位置
    private int lastItem;
    private int page=1;//加载的页数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        lvCollect = (AllListView) findViewById(R.id.lv_collect);
        lvCollect.setOnRefreshListener(this);
        initView();
        initData(true,page);
        lvCollect.setOnScrollListener(new AllListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if(lastItem == adapter.getCount() - 1 && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                    if(list.size()>=15){
                        //加载更多
//                        initData(true, ++page);
//                    }
//                }
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && lastItem == adapter.getCount()) {
                    // 当滑到底部时自动加载
                    initData(false, ++page);
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //可见的最后item的位置
                lastItem = firstVisibleItem + visibleItemCount - 1;
            }
        });
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
        titleCenterTextview.setText("我的收藏");
        titleRightTextview.setVisibility(View.VISIBLE);
        titleRightTextview.setText("编辑");
    }
    private void initData(boolean b, final int page) {
        OkGo.post(Urls.URL_COLLECTION_GOODS)//
                .tag(this)
                .params("page",page)
                .params("rows", 15)
                .execute(new DialogCallback<LzyResponse<Collect>>(this,b) {
                             @Override
                             public void onSuccess(LzyResponse<Collect> lzyResponse, Call call, Response response) {
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
                                         } else {
//                                             adapter = new CollectAdapter(MyCollectActivity.this, list);
//                                             lvCollect.setAdapter(adapter);
                                             lvCollect.setOnRefreshComplete();
                                             lvCollect.setSelection(0);
                                         }
                                     }
                                 }else {
                                     if (lzyResponse.data.getRows().size()>0){
                                         list.addAll(lzyResponse.data.getRows());
                                         adapter.notifyDataSetChanged();
                                     }else {
//                                         T.showShort(MyCollectActivity.this,"没有更多数据了");
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

    @Override
    public void onRefresh() {
        initData(false,1);
        page=1;
    }

    @OnClick({R.id.title_left_imageview, R.id.title_right_textview, R.id.my_supplier_collect_go_look})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.title_right_textview:
                break;
            case R.id.my_supplier_collect_go_look:
//                IntentUtils.startActivity(MyCollectActivity.this, MainActivity.class);
                MyApplication.setTag(0);
                MyApplication.setType(1);
                finish();
                break;
        }
    }
}
