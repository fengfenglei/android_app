package com.mhl.shop.me.myview;

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
import com.mhl.shop.me.MeInterface;
import com.mhl.shop.me.adapter.ArticleAdapter;
import com.mhl.shop.me.adapter.NoArticleAdapter;
import com.mhl.shop.me.been.Article;
import com.mhl.shop.search.wight.ScrollViewWithListView;
import com.mhl.shop.utils.IntentUtils;
import com.mhl.shop.utils.Urls;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-28 18:18
 * 描述：收藏的文章
 */
public class CollectArticleFragment extends MyBaseRegistFragment implements AllListView.setOnRefreshListener, AllListView.setLOnRefreshListener , MeInterface.OnMyAllCollectListener {
    @Bind(R.id.lv_coupon)
    AllListView lvCoupon;

    @Bind(R.id.lv_no)
    ScrollViewWithListView lv_no;
    @Bind(R.id.go)
    TextView go;
    @Bind(R.id.sly)
    ScrollView sly;
    private List<Article.DiscoverListBean> list;
    private List<Article.DiscoverListBean> list_like;
    private ArticleAdapter adapter;
    private NoArticleAdapter mAdapter;

    //当前可见的listviewitem的位置
    private int lastItem;
    private int page = 1;//加载的页数

    public CollectArticleFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_collect_article, container, false);
        ButterKnife.bind(this, view);
        lvCoupon.setOnRefreshListener(this);
        lvCoupon.setLOnRefreshListener(this);
        go.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        MeInterface.setOnMyAllCollectListener(this);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        initData(true, 1);
    }
    @Override
    public void onRefresh() {
        initData(false, 1);
        page = 1;

    }

    @Override
    public void onLoadingMore() {
        initData(false, ++page);
    }

    private void initData(boolean b, final int page) {
        OkGo.post(Urls.URL_ARTICLE_GOODS)//
                .tag(this)
                .params("page", page)
                .params("rows", 10)
                .execute(new DialogCallback<LzyResponse<Article>>(getActivity(), b) {
                             @Override
                             public void onSuccess(LzyResponse<Article> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (page == 1) {
                                     if (list != null) {
                                         list.clear();
                                     }
                                     if (list_like != null) {
                                         list_like.clear();
                                     }
                                     if (lzyResponse.data.getFlag() == 1) { //有收藏列表
                                         if(lzyResponse.data.getDiscoverList()!=null){
                                         list = lzyResponse.data.getDiscoverList();
                                         adapter = new ArticleAdapter(getActivity(), list);
                                         lvCoupon.setAdapter(adapter);
                                         lvCoupon.setOnRefreshComplete();
                                         lvCoupon.setSelection(0);
                                         sly.setVisibility(View.GONE);
                                         lvCoupon.setVisibility(View.VISIBLE);}else {
                                             lvCoupon.setVisibility(View.GONE);
                                             sly.setVisibility(View.VISIBLE);
                                         }
                                     } else if (lzyResponse.data.getFlag() == 2) {
                                         if(lzyResponse.data.getDiscoverList()!=null){
                                         list_like = lzyResponse.data.getDiscoverList();
                                         lvCoupon.setVisibility(View.GONE);
                                         sly.setVisibility(View.VISIBLE);
//                                         //为你推荐
                                          lv_no.setFocusable(false);
                                         mAdapter = new NoArticleAdapter(getActivity(),list_like);
                                         lv_no.setAdapter(mAdapter);}
                                     }

                                 } else {
                                     if (null != lzyResponse.data) {
                                         if (null != lzyResponse.data.getDiscoverList()) {
                                             if (lzyResponse.data.getDiscoverList().size() > 0) {
                                                 list.addAll(lzyResponse.data.getDiscoverList());
                                                 adapter.notifyDataSetChanged();
                                             } else {
//                                                 T.showShort(getActivity(), "没有更多数据了");
                                             }
                                         } else {
//                                             T.showShort(getActivity(), "没有更多数据了");
                                         }
                                     } else {
//                                         T.showShort(getActivity(), "没有更多数据了");
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
        MyApplication.setTag(2);
        MyApplication.setType(1);
    }
    @Override
    public void OnMyAllCollectRefresh(String order_stutas, int pageNum) {
        initData(true, 1);
    }
}
