package com.mhl.shop.me;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.AllListView;
import com.mhl.shop.main.MainActivity;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.main.MyBaseRegistFragment;
import com.mhl.shop.me.adapter.CollectSupplierAdapter;
import com.mhl.shop.me.adapter.CollectSupplierNoAdapter;
import com.mhl.shop.me.been.CollectSupplier;
import com.mhl.shop.shopcart.CartGoodListView;
import com.mhl.shop.utils.IntentUtils;
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
 * 时间；2016-11-28 18:18
 * 描述：收藏的供应商
 */
public class CollectSupplierFragment extends MyBaseRegistFragment implements AllListView.setOnRefreshListener,AllListView.setLOnRefreshListener, MeInterface.OnMyAllCollectSupplierListener  {
    @Bind(R.id.lv_coupon)
    AllListView lvCoupon;
    @Bind(R.id.no_cooupon)
    ScrollView noCooupon;
    @Bind(R.id.no_lv)
    CartGoodListView no_lv;
    @Bind(R.id.go)
    TextView go;
    private List<CollectSupplier.CollectionSuppliersBean> list;
    private List<CollectSupplier.RecommendSuppliers> list_like;
    private CollectSupplierAdapter adapter;
    private CollectSupplierNoAdapter adapter_no;
    //当前可见的listviewitem的位置
    private int lastItem;
    private int page=1;//加载的页数
    public CollectSupplierFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_collect_supplier, container, false);
        ButterKnife.bind(this, view);
        lvCoupon.setOnRefreshListener(this);
        lvCoupon.setLOnRefreshListener(this);
        MeInterface.setOnMyAllCollectSupplierListener(this);
        go.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        no_lv.setFocusable(false);//设置顶部在ScrollView （禁止CartGoodListView的焦点）
        return view;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
        initData(true, 1);
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
        OkGo.post(Urls.URL_GONG_GOODS)//
                .tag(this)
                .params("page",page)
                .params("rows", 15)
                .execute(new DialogCallback<LzyResponse<CollectSupplier>>(getActivity(),b) {
                             @Override
                             public void onSuccess(LzyResponse<CollectSupplier> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (page==1){
                                     if (list!=null){
                                         list.clear();
                                     }
                                     if (list_like!=null){
                                         list_like.clear();
                                     }
                              if (lzyResponse.data.getFlag()==1){//有收藏列表
                                  if(lzyResponse.data.getCollectionSuppliers()!=null){
                                  list=lzyResponse.data.getCollectionSuppliers();
                                  adapter = new CollectSupplierAdapter(getActivity(), list);
                                  lvCoupon.setAdapter(adapter);
                                  lvCoupon.setOnRefreshComplete();
                                  noCooupon.setVisibility(View.GONE);
                                  lvCoupon.setVisibility(View.VISIBLE);
                                  }
                              }else if(lzyResponse.data.getFlag()==2){
                                  list_like=lzyResponse.data.getRecommendSuppliers();
                                  adapter_no = new CollectSupplierNoAdapter(getActivity(), list_like);
                                  no_lv.setAdapter(adapter_no);
                                  adapter_no.notifyDataSetChanged();

                                  noCooupon.setVisibility(View.VISIBLE);
                                  lvCoupon.setVisibility(View.GONE);
                                  no_lv.setFocusable(false);
                              }

                                 }else {
                                     if(null!=lzyResponse.data){
                                         if(null!=lzyResponse.data.getCollectionSuppliers()){
                                     if (lzyResponse.data.getCollectionSuppliers().size()>0){
                                         list.addAll(lzyResponse.data.getCollectionSuppliers());
                                         adapter.notifyDataSetChanged();
                                     }else {
                                         T.showShort(getActivity(),"没有更多数据了");
                                     }
                                         }else {
                                             T.showShort(getActivity(),"没有更多数据了");
                                         }
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
    @OnClick(R.id.go)
    public void onClick() {
        IntentUtils.startActivity(getActivity(), MainActivity.class);
        MyApplication.setTag(0);
        MyApplication.setType(1);
    }
    @Override
    public void OnMyAllCollectSupplierRefresh(String order_stutas, int pageNum) {
        initData(true, 1);
    }
}
