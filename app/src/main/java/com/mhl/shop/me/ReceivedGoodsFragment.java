package com.mhl.shop.me;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.MyBaseRegistFragment;
import com.mhl.shop.me.adapter.OrderALLAdapter;
import com.mhl.shop.me.been.Order;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-28 18:18
 * 描述：所有订单列表（各种父子判断，修改修谨慎）
 */
public class ReceivedGoodsFragment extends MyBaseRegistFragment implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.lv_list)
    RecyclerView lv_list;
    @Bind(R.id.no_data)
    LinearLayout noData;
@Bind(R.id.swipe_refresh)
SwipeRefreshLayout swipeRefresh;
    private List<Order.RowsBean> list;
    private OrderALLAdapter adapter;
    //当前可见的listviewitem的位置
    private int lastItem;
    private int page=1;//加载的页数
    LinearLayoutManager layoutManager;
    public ReceivedGoodsFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order, container, false);
        ButterKnife.bind(this, view);
        initViews();
        lv_list.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItem + 1 == adapter.getItemCount()) {
                    initData(false, ++page);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItem = layoutManager.findLastVisibleItemPosition();
            }

        });
        return view;
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
        initData(true,1);

    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }
    private void initViews() {

        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_red_light, android.R.color.holo_red_light);
        }
    private void initData(boolean b, final int page) {
        OkGo.post(Urls.URL_VIEW_ORDER)//
                .tag(this)
                .params("page",page)
                .params("rows",15)
                .params("orderStatus", "3")
                .execute(new DialogCallback<LzyResponse<Order>>(getActivity(),b) {
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
                                             lv_list.setVisibility(View.GONE);
                                         } else {



                                            if(list!=null) {
                                                 layoutManager = new LinearLayoutManager(getActivity());
                                                layoutManager.setSmoothScrollbarEnabled(true);
                                                layoutManager.setAutoMeasureEnabled(true);

                                                lv_list.setLayoutManager(layoutManager);
                                                lv_list.setHasFixedSize(true);
                                                lv_list.setNestedScrollingEnabled(false);
                                                adapter = new OrderALLAdapter(getActivity(), list);
                                                lv_list.setAdapter(adapter);
                                            }
                                         }
                                     }
                                 }else {
                                     if (lzyResponse.data.getRows().size()>0){
                                         list.addAll(lzyResponse.data.getRows());
                                         adapter.notifyDataSetChanged();
                                     }else {
                                         T.showShort(getActivity(),"没有更多数据了");
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    initData(false, 1);
                    swipeRefresh.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };
}
