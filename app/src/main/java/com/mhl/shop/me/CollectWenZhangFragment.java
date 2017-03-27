package com.mhl.shop.me;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.AllListView;
import com.mhl.shop.main.MainActivity;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.main.MyBaseRegistFragment;
import com.mhl.shop.me.adapter.CollectAdapter;
import com.mhl.shop.me.been.CollectShop;
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
 * 描述：收藏的商品
 */
public class CollectWenZhangFragment extends MyBaseRegistFragment implements AllListView.setOnRefreshListener,AllListView.setLOnRefreshListener {
    @Bind(R.id.lv_coupon)
    AllListView lvCoupon;
    @Bind(R.id.no_cooupon)
    LinearLayout noCooupon;
    @Bind(R.id.go)
    TextView go;
    private List<CollectShop.CollectionGoodsBean> list;
//    private List<CollectShop.ChoicenessGoodsBean> list_like;
    private CollectAdapter adapter;
    //当前可见的listviewitem的位置
    private int lastItem;
    private int page=1;//加载的页数
    public CollectWenZhangFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_collect_wen, container, false);
        ButterKnife.bind(this, view);
//        initData(true,page);
        lvCoupon.setOnRefreshListener(this);
        lvCoupon.setLOnRefreshListener(this);
        go.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线

        return view;
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
        OkGo.post(Urls.URL_COLLECTION_GOODS)//
                .tag(this)
                .params("page",page)
                .params("rows", 15)
                .execute(new DialogCallback<LzyResponse<CollectShop>>(getActivity(),b) {
                             @Override
                             public void onSuccess(LzyResponse<CollectShop> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (page==1){
                                     if (list!=null){
                                         list.clear();
                                     }
//                                     if (list_like!=null){
//                                         list_like.clear();
//                                     }
                              if (lzyResponse.data.getFlag()==1){//有收藏列表
                                  list=lzyResponse.data.getCollectionGoods();
//                                  adapter = new CollectAdapter(getActivity(), list);
//                                  lvCoupon.setAdapter(adapter);
//                                  lvCoupon.setOnRefreshComplete();
//                                  lvCoupon.setSelection(0);
//                                  noCooupon.setVisibility(View.GONE);
//                                  lvCoupon.setVisibility(View.VISIBLE);
                              }else if(lzyResponse.data.getFlag()==2){
//                                  list_like=lzyResponse.data.getChoicenessGoods();
                                  noCooupon.setVisibility(View.VISIBLE);
                                  lvCoupon.setVisibility(View.GONE);
                              }

                                 }else {
                                     if(null!=lzyResponse.data){
                                         if(null!=lzyResponse.data.getCollectionGoods()){
                                     if (lzyResponse.data.getCollectionGoods().size()>0){
                                         list.addAll(lzyResponse.data.getCollectionGoods());
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

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }
}
