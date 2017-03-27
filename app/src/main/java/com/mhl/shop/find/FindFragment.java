package com.mhl.shop.find;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.AllListView;
import com.mhl.shop.main.MyBaseFragment;
import com.mhl.shop.me.MeInterface;
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
 * 时间；2016-11-10 14:03
 * 描述：发现
 */
public class FindFragment extends MyBaseFragment implements AllListView.setOnRefreshListener, AllListView.setLOnRefreshListener, MeInterface.OnMyFindListener {
    @Bind(R.id.title_left_imageview)
    ImageView titleLeftImageview;
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.title_right_imageview)
    ImageView titleRightImageview;
    @Bind(R.id.lv_data)
    AllListView lvData;
    @Bind(R.id.no_data)
    LinearLayout noData;
    @Bind(R.id.net_connecte_fail)
    FrameLayout netConnecteFail;

    private int page = 1;//加载的页数
    private List<Find> list;
    private FindAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find, container,
                false);
        ButterKnife.bind(this, rootView);
        initViews();
        initData(false, page);
        lvData.setOnRefreshListener(this);
        lvData.setLOnRefreshListener(this);
        MeInterface.setOnMyFindListener(this);

        return rootView;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }

    private void initViews() {
        titleCenterTextview.setText("发现");
        titleRightImageview.setVisibility(View.VISIBLE);
        titleLeftImageview.setVisibility(View.GONE);
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
        OkGo.post(Urls.URL_DISCOVER_LIST)//
                .tag(this)
                .params("page", page)
                .params("rows", 10)
                .execute(new DialogCallback<LzyResponse<List<Find>>>(getActivity(), b) {
                             @Override
                             public void onSuccess(LzyResponse<List<Find>> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (lzyResponse.code == 200) {
                                     netConnecteFail.setVisibility(View.GONE);

                                     if (page == 1) {
                                         if (list != null) {
                                             list.clear();
                                         }
                                         list = lzyResponse.data;
                                         if (list != null) {
                                             if (list.size() < 1) {
                                                 noData.setVisibility(View.VISIBLE);
                                                 lvData.setVisibility(View.GONE);
                                             } else {
                                                 adapter = new FindAdapter(getActivity(), list);
                                                 lvData.setAdapter(adapter);
                                                 lvData.setOnRefreshComplete();
                                                 lvData.setSelection(0);
                                             }
                                         }
                                     } else {
                                         if (lzyResponse.data.size() > 0) {
                                             list.addAll(lzyResponse.data);
                                             adapter.notifyDataSetChanged();
                                         } else {
//                                         T.showShort(getActivity(),"没有更多数据了");
                                         }
                                         lvData.hideFooterView();

                                     }
                                 } else {
                                     netConnecteFail.setVisibility(View.VISIBLE);

                                 }
                             }

                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 handleError(call, response, e);
                                 netConnecteFail.setVisibility(View.VISIBLE);

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
    public void OnMyFindRefresh(String order_stutas, int pageNum) {
        initData(false, 1);
        page = 1;
    }

    @OnClick(R.id.net_connecte_fail)
    public void onClick() {
        initData(false, 1);
        page = 1;
    }
}
