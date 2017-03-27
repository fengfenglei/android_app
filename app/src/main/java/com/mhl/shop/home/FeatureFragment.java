package com.mhl.shop.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.home.adpter.FeatureNextAdapter;
import com.mhl.shop.home.been.FeatureNext;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.AllListView;
import com.mhl.shop.main.MyBaseRegistFragment;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2017-1-22 10:43
 * 描述：特色馆二级
 */
public class FeatureFragment extends MyBaseRegistFragment implements AllListView.setOnRefreshListener,AllListView.setLOnRefreshListener {
    private Context mContext;

    public  AllListView lvList;
    private String str,simg,id;
    LinearLayout no_data;
    private int page=1;//加载的页数
    private List<FeatureNext.RowsBean> list;
    private FeatureNextAdapter adapter;
    private View vHead;//头布局
    private LayoutInflater mInflater;
    private View mRootView; // 根布局
    ImageView img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.featurefragment, null);
        this.mInflater = LayoutInflater.from(mContext);// 从上下文获取构造器
        mRootView = (ViewGroup) inflater.inflate(R.layout.featurefragment, container, false);
        //得到数据
        str = getArguments().getString("name");
        id = getArguments().getString("id");
        simg = getArguments().getString("img");
        ButterKnife.bind(this, view);
        lvList = (AllListView) view.findViewById(R.id.lv_list);
        vHead = mInflater.inflate(R.layout.feature_head, null);

        img = (ImageView) vHead.findViewById(R.id.img);
        no_data = (LinearLayout) view.findViewById(R.id.no_data);
        //头布局
       //头布局放入listView中
        lvList.addHeaderView(vHead);
        Glide.with(getActivity()).load(Urls.URL_PHOTO+"/file/v3/download-"+simg+"-980-280.jpg").into(img);
        https://cdn.51mhl.com/file/v1/download-592b37728f874f56a730d4fb6efe251a.jpg
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
        OkGo.post(Urls.APP_FEATURE_DETAIL_LIST)//
                .tag(this)
                .params("page",page)
                .params("rows", 15)
                .params("featureId", id)
                .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                .cacheTime(5000)         // 缓存的过期时间,单位毫秒
                .execute(new DialogCallback<LzyResponse<FeatureNext>>(getActivity(),b) {
                             @Override
                             public void onSuccess(LzyResponse<FeatureNext> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (page==1){
                                     if (list!=null){
                                         list.clear();
                                     }
                                     list = lzyResponse.data.getRows();
                                     if (list != null) {
                                         if (list.size() < 1) {
                                             lvList.setVisibility(View.GONE);
                                             no_data.setVisibility(View.VISIBLE);
                                         } else {
                                             no_data.setVisibility(View.GONE);
                                             lvList.setVisibility(View.VISIBLE);
                                             adapter = new FeatureNextAdapter(getActivity(), list);
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
//                                         T.showShort(getActivity(),"没有更多数据了");
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
