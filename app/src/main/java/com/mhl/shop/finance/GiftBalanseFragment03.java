package com.mhl.shop.finance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.finance.adapter.GiftBalanceAdapter;
import com.mhl.shop.finance.been.TransactionRecord;
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
 * 描述：赠送金额全部明细
 */
public class GiftBalanseFragment03 extends MyBaseRegistFragment implements AllListView.setOnRefreshListener,AllListView.setLOnRefreshListener {
    @Bind(R.id.lv_list)
    AllListView lvList;
    @Bind(R.id.no_data)
    LinearLayout noData;

    private List<TransactionRecord.RowsBean> list;
    private GiftBalanceAdapter adapter;
    //当前可见的listviewitem的位置
    private int lastItem;
    private int page=1;//加载的页数
    public GiftBalanseFragment03() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_giftnalance, container, false);
        ButterKnife.bind(this, view);
        initData(true,page);
        lvList.setOnRefreshListener(this);
        lvList.setLOnRefreshListener(this);
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
        OkGo.post(Urls.URL_RECORD_FUND)//
                .tag(this)
                .params("page",page)
                .params("rows", 15)
                .params("fundFlows", "0")//0-支出 1-收入
                .params("fundType", "4")//1-可提现余额 4-赠送金额 6-激活金额
                .params("period", "")//7-最近一周 30-最近一个月 365-最近一年
                .params("userRole", "1")//1-会员 2-服务站 3-运营中心 4-供应商
                .execute(new DialogCallback<LzyResponse<TransactionRecord>>(getActivity(),b) {
                             @Override
                             public void onSuccess(LzyResponse<TransactionRecord> lzyResponse, Call call, Response response) {
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
                                             adapter = new GiftBalanceAdapter(getActivity(), list);
                                             lvList.setAdapter(adapter);
                                             lvList.setOnRefreshComplete();
                                             lvList.setSelection(0);
                                         }
                                     }
                                 }else {
                                     if (lzyResponse.data.getRows().size()>0){
                                         list.addAll(lzyResponse.data.getRows());
                                         adapter.notifyDataSetChanged();
                                     }else {
                                         T.showShort(getActivity(),"没有更多数据了");
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





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
