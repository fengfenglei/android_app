package com.mhl.shop.finance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.finance.adapter.HldAdapter;
import com.mhl.shop.finance.been.Hld;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.AllListView;
import com.mhl.shop.main.MyBaseRegistFragment;
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
 * 描述：未使用优惠券
 */
public class HldFragment03 extends MyBaseRegistFragment implements AllListView.setOnRefreshListener,AllListView.setLOnRefreshListener {
    @Bind(R.id.lv_coupon)
    AllListView lvCoupon;
    @Bind(R.id.no_cooupon)
    LinearLayout noCooupon;

    private List<Hld.RowsBean> list;
    private HldAdapter adapter;
    //当前可见的listviewitem的位置
    private int lastItem;
    private int page=1;//加载的页数
    public HldFragment03() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_hld, container, false);
        ButterKnife.bind(this, view);
        initData(true,page);
        lvCoupon.setOnRefreshListener(this);
        lvCoupon.setLOnRefreshListener(this);
        return view;
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
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
        OkGo.post(Urls.URL_LIST_GOLD)//
                .tag(this)
                .params("page",page)
                .params("rows", 15)
                .params("fundFlows", "0")
                .execute(new DialogCallback<LzyResponse<Hld>>(getActivity(),b) {
                             @Override
                             public void onSuccess(LzyResponse<Hld> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (page==1){
                                     if (list!=null){
                                         list.clear();
                                     }
                                     list = lzyResponse.data.getRows();
                                     if (list != null) {
                                         if (list.size() < 1) {
                                             noCooupon.setVisibility(View.VISIBLE);
                                             lvCoupon.setVisibility(View.GONE);
                                         } else {
                                             adapter = new HldAdapter(getActivity(), list);
                                             lvCoupon.setAdapter(adapter);
                                             lvCoupon.setOnRefreshComplete();
                                             lvCoupon.setSelection(0);
                                         }
                                     }
                                 }else {
                                     if (lzyResponse.data.getRows().size()>0){
                                         list.addAll(lzyResponse.data.getRows());
                                         adapter.notifyDataSetChanged();
                                     }else {
                                         T.showShort(getActivity(),"没有更多数据了");
                                     }
                                     lvCoupon.hideFooterView();

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
}
