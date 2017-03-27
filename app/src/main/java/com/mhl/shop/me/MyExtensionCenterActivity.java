package com.mhl.shop.me;

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
import com.mhl.shop.me.adapter.MyEctensionCenterAdapter;
import com.mhl.shop.me.been.MyEctensionCenter;
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
 * 时间；2017-1-6 15:43
 * 描述：我的推广中心
 */
public class MyExtensionCenterActivity extends MyBaseActivity implements AllListView.setOnRefreshListener,AllListView.setLOnRefreshListener {

    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.lv_list)
    AllListView lvList;
    @Bind(R.id.no_data)
    LinearLayout noData;

    private int page=1;//加载的页数
    private  List<MyEctensionCenter> list;
    private MyEctensionCenterAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_extension_center);
        ButterKnife.bind(this);
        initData(true,page);
        lvList.setOnRefreshListener(this);
        lvList.setLOnRefreshListener(this);
        titleCenterTextview.setText("我的推广");
    }
//    //如果使用的是qq或者新浪精简版jar，需要在您使用分享或授权的Activity（fragment不行）中添加如下回调代码：
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
//
//    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
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
    private void initData(boolean b, final int page) {
        OkGo.post(Urls.URL_RECOMMEND_LIST)//
                .tag(this)
                .params("page",page)
                .params("rows", 15)
                .execute(new DialogCallback<LzyResponse<List<MyEctensionCenter>>>(MyExtensionCenterActivity.this,b) {
                             @Override
                             public void onSuccess(LzyResponse<List<MyEctensionCenter>> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (page==1){
                                     if (list!=null){
                                         list.clear();
                                     }
                                     list = lzyResponse.data;
                                     if (list != null) {
                                         if (list.size() < 1) {
                                             noData.setVisibility(View.VISIBLE);
                                             lvList.setVisibility(View.GONE);
                                         } else {
                                             adapter = new MyEctensionCenterAdapter(MyExtensionCenterActivity.this, list);
                                             lvList.setAdapter(adapter);
                                             lvList.setOnRefreshComplete();
                                             lvList.setSelection(0);
                                         }
                                     }
                                 }else {
                                     if (lzyResponse.data.size()>0){
                                         list.addAll(lzyResponse.data);
                                         adapter.notifyDataSetChanged();
                                     }else {
                                         T.showShort(MyExtensionCenterActivity.this,"没有更多数据了");
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
