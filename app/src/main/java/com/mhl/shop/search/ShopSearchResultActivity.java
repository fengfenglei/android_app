package com.mhl.shop.search;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.AllListView;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.search.adapter.NoShopListAdapter;
import com.mhl.shop.search.adapter.ShopGirdAdapter;
import com.mhl.shop.search.adapter.ShopSearchAdapter;
import com.mhl.shop.search.been.ShopResult;
import com.mhl.shop.search.wight.ScrollViewWithListView;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.mhl.shop.search.SearchActivity.SEARCH_HISTORY;

/**
 * 作者：lff
 * 时间；2017-1-11 16:48
 * 描述：商品搜索结果页面
 */

public class ShopSearchResultActivity extends MyBaseActivity implements AllListView.setOnRefreshListener, AllListView.setLOnRefreshListener {
    @Bind(R.id.lv_data)
    AllListView lvData;

    @Bind(R.id.search_et_search)
    TextView searchEtSearch;
    @Bind(R.id.change)
    ImageView change;
    @Bind(R.id.search_back_iv)
    ImageView searchBackIv;
    @Bind(R.id.search_iv_search)
    TextView searchIvSearch;
    @Bind(R.id.choise_ly)
    LinearLayout choiseLy;
    @Bind(R.id.search_search_bar)
    FrameLayout searchSearchBar;
    @Bind(R.id.zonghe_text)
    TextView zongheText;
    @Bind(R.id.zonghe_view)
    View zongheView;
    @Bind(R.id.zonghe)
    RelativeLayout zonghe;
    @Bind(R.id.xiaoliang_upanddown)
    ImageView xiaoliangUpanddown;
    @Bind(R.id.xiaoliang_view)
    View xiaoliangView;
    @Bind(R.id.xiaoliang_text)
    TextView xiaoliangText;
    @Bind(R.id.xiaoliang)
    RelativeLayout xiaoliang;
    @Bind(R.id.jiage_upanddown)
    ImageView jiageUpanddown;
    @Bind(R.id.jiage_view)
    View jiageView;
    @Bind(R.id.jiage_text)
    TextView jiageText;
    @Bind(R.id.jiage)
    RelativeLayout jiage;
    @Bind(R.id.set)
    LinearLayout set;
    @Bind(R.id.zonghe_upanddown)
    ImageView zongheUpanddown;
    @Bind(R.id.lv_no)
    ScrollViewWithListView lv_no;
    @Bind(R.id.go)
    TextView go;
    @Bind(R.id.sly)
    ScrollView sly;
    private int page = 1;//加载的页数
    private ShopResult list;
    private ShopSearchAdapter adapter;
    private ShopGirdAdapter adapter1;
    private String shopkey = "",classid,type;
    private int changestyle = 1;
    private int isxiaoliang = 1;
    private int isjiage = 1;
    private String purchaseSortFlag = "";
    private String priceSortFlag = "";
    private int jiage_index = 0;
    private int xiaoliang_index = 0;
    private int zonghe_index = 0;
    private NoShopListAdapter mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_search);
        ButterKnife.bind(this);
        initView();
        initData(true, page);
        lvData.setOnRefreshListener(this);
        lvData.setLOnRefreshListener(this);
        go.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
    }

    private void initView() {
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        shopkey = intent.getStringExtra("shopkey");
        searchEtSearch.setText(shopkey);
        if(intent.getStringExtra("type").equals("class")){
            classid = intent.getStringExtra("classid");
            shopkey="";
        }
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
        OkGo.post(Urls.URL_SHOP_LIST)//
                .tag(this)
                .params("goodsName", shopkey)//商品名称
                .params("classId", classid)//分类id
                .params("purchaseSortFlag", purchaseSortFlag)//销量排序
                .params("priceSortFlag", priceSortFlag)//价格排序
                .params("page", page)
                .params("rows", "10")
                .params("choicenessNum", "6")//为你推荐显示数量
                .execute(new DialogCallback<LzyResponse<ShopResult>>(this, b) {
                             @Override
                             public void onSuccess(LzyResponse<ShopResult> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);

                                 if (page == 1) {
                                     if (list != null) {
                                         list = null
                                         ;
                                     }
                                     list = lzyResponse.data;
                                     if (list != null) {
                                         if (list.getGoodsInfos().size() >0 ) {

                                             changeDisplayStyle();
                                             saveSearchHistory(shopkey);
                                         } else {
                                             sly.setVisibility(View.VISIBLE);
                                             lvData.setVisibility(View.GONE);
                                             set.setVisibility(View.GONE);
                                             change.setVisibility(View.INVISIBLE);
                                             //为你推荐
                                             lv_no.setFocusable(false);
                                             mAdapter = new NoShopListAdapter(ShopSearchResultActivity.this,list.getChoicenesss());
                                             lv_no.setAdapter(mAdapter);
                                         }
                                     }
                                 } else {
                                     if (lzyResponse.data.getGoodsInfos().size() > 0) {
                                         list.getGoodsInfos().addAll(lzyResponse.data.getGoodsInfos());
                                         if(changestyle % 2==0){
                                             adapter1.notifyDataSetChanged();
                                         }else  if(changestyle % 2==1){
                                             adapter.notifyDataSetChanged();

                                         }
                                     } else {
//                                         T.showShort(getActivity(),"没有更多数据了");
                                     }
                                     lvData.hideFooterView();

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

    @OnClick({R.id.search_back_iv, R.id.change, R.id.search_search_bar, R.id.zonghe, R.id.xiaoliang, R.id.jiage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back_iv:
                finish();
                break;
            case R.id.search_search_bar:
                finish();
                break;
            case R.id.change:
                changestyle++;
                changeDisplayStyle();
                break;
            case R.id.zonghe:
                zonghe_index++;
                changeViewColor(zongheText, zongheView, zongheUpanddown,
                        zonghe_index);
                purchaseSortFlag="";
                priceSortFlag="";
                initData(true, 1);
                break;

            case R.id.xiaoliang:
                xiaoliang_index++;
                changeViewColor(xiaoliangText, xiaoliangView,
                        xiaoliangUpanddown, xiaoliang_index);
                switch (xiaoliang_index % 2) {
                    case 0:
                        purchaseSortFlag="true";
                        break;
                    case 1:
                        purchaseSortFlag="false";
                        break;
                    default:
                        break;
                }
                priceSortFlag="";
                initData(true, 1);
                break;
            case R.id.jiage:
                jiage_index++;
                changeViewColor(jiageText, jiageView,
                        jiageUpanddown, jiage_index);
                switch (jiage_index % 2) {
                    case 0:
                        priceSortFlag="true";
                        break;
                    case 1:
                        priceSortFlag="false";
                        break;
                    default:
                        break;
                }
                purchaseSortFlag="";
                initData(true, 1);
                break;
        }
    }

    private void changeDisplayStyle() {
        switch (changestyle % 2) {
            case 0:
                lvData.setVisibility(View.VISIBLE);
                change.setImageResource(R.drawable.square_list);
                adapter1 = new ShopGirdAdapter(ShopSearchResultActivity.this, list.getGoodsInfos());
                lvData.setAdapter(adapter1);
                lvData.setOnRefreshComplete();
                lvData.setSelection(0);
                break;
            case 1:
                lvData.setVisibility(View.VISIBLE);
                change.setImageResource(R.drawable.long_list);
                adapter = new ShopSearchAdapter(ShopSearchResultActivity.this, list.getGoodsInfos());
                lvData.setAdapter(adapter);
                lvData.setOnRefreshComplete();
                lvData.setSelection(0);
                break;
            default:
                break;
        }
    }

    private void changeViewColor(TextView view1, View view2, ImageView view3, int index) {

        zongheText.setTextColor(Color.BLACK);
        xiaoliangText.setTextColor(Color.BLACK);
        jiageText.setTextColor(Color.BLACK);

        zongheView.setVisibility(View.INVISIBLE);
        xiaoliangView.setVisibility(View.INVISIBLE);
        jiageView.setVisibility(View.INVISIBLE);

        jiageUpanddown.setBackgroundResource(R.drawable.hui);
        xiaoliangUpanddown.setBackgroundResource(R.drawable.hui);

        if (view1 != null & view2 != null) {
            view1.setTextColor(Color.RED);
            view2.setVisibility(View.VISIBLE);
        }
        if (view3 != null) {
            switch (index % 2) {
                case 0:
                    view3.setBackgroundResource(R.drawable.shang);
                    break;
                case 1:
                    view3.setBackgroundResource(R.drawable.xia);
                    break;
                default:
                    break;
            }
        }
    }
    /*
    * 保存搜索记录
    */
    public void saveSearchHistory(String text) {
        if (text.length() < 1) {
            return;
        }
        SharedPreferences sp = getSharedPreferences(SEARCH_HISTORY, 0);
        String longhistory = sp.getString(SEARCH_HISTORY, "");
        String[] tmpHistory = longhistory.split(",");
        ArrayList<String> history = new ArrayList<String>(
                Arrays.asList(tmpHistory));
        if (history.size() > 0) {
            int i;
            for (i = 0; i < history.size(); i++) {
                if (text.equals(history.get(i))) {
                    history.remove(i);
                    break;
                }
            }
            history.add(0, text);
        }
        if (history.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < history.size(); i++) {
                sb.append(history.get(i) + ",");
            }
            sp.edit().putString(SEARCH_HISTORY, sb.toString()).commit();
        } else {
            sp.edit().putString(SEARCH_HISTORY, text + ",").commit();
        }
        if (history.size() > 10) {
            history.remove(history.size() - 1);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < history.size(); i++) {
                sb.append(history.get(i) + ",");
            }
            sp.edit().putString(SEARCH_HISTORY, sb.toString()).commit();
        }
    }
}
