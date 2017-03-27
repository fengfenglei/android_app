package com.mhl.shop.search;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fyales.tagcloud.library.TagCloudLayout;
import com.ihidea.multilinechooselib.MultiLineChooseLayout;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.search.adapter.MyTagBaseAdapter;
import com.mhl.shop.search.been.HotShop;
import com.mhl.shop.search.been.Shop;
import com.mhl.shop.search.been.SupplierName;
import com.mhl.shop.search.wight.PopMenu;
import com.mhl.shop.search.wight.ScrollViewWithListView;
import com.mhl.shop.search.wight.UserMenu;
import com.mhl.shop.utils.Urls;
import com.mhl.shop.wheel.AlertDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2017-1-9 17:49
 * 描述： 搜索界面
 */
public class SearchActivity extends MyBaseActivity {
    @Bind(R.id.search_et_search)
    EditText searchEtSearch;
    @Bind(R.id.search_iv_search)
    TextView searchIvSearch;
    @Bind(R.id.search_tv_action)
    TextView searchTvAction;
    @Bind(R.id.sly)
    ScrollView sly;
    @Bind(R.id.flowLayout)
    MultiLineChooseLayout hotflowLayout;
    @Bind(R.id.keywordsearchlist_lv)
    ListView keywordsearchlist_lv;
    @Bind(R.id.clearhistory)
    LinearLayout clearhistory;
    @Bind(R.id.hot_ly)
    LinearLayout hot_ly;
    @Bind(R.id.historylist_lv)
    ScrollViewWithListView historylist_lv;
    @Bind(R.id.history_ly)
    LinearLayout history_ly;
    @Bind(R.id.container)
    TagCloudLayout mContainer;
    private static final int USER_SEARCH = 0;
    private UserMenu mMenu;
    private MyTagBaseAdapter mAdapter;
    private ArrayList<String> shopkeyList = new ArrayList<String>();
//    private ArrayList<String> hotkeyList = new ArrayList<String>();
    private ArrayList<String> supplierkeyList = new ArrayList<String>();
    private ArrayAdapter<String> keyAdapter;
    public static final String SEARCH_HISTORY = "search_history";
    protected static final int KEYWORDFROMSEARCH = 11;
    private String[] hisArrays;
    private String classSearch="供应商";//搜索分类
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        //从Intent当中根据key取得value
//        goodsid = intent.getStringExtra("goodsid");
        initDataHotShop();

        initView();
        initDataHotShop();

        initListener();
        initDataHotShop();
    }


    private void initView() {
        initSearchHistory();
    }
    private void initListener() {
        searchEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals("")){
                    if(searchIvSearch.getText().equals("商品")){
                        initDataShop(editable.toString());
                    }else if(searchIvSearch.getText().equals("供应商")){
                        initDataSupplier(editable.toString());
                    }

                }{
                    keywordsearchlist_lv.setVisibility(View.GONE);
                    sly.setVisibility(View.VISIBLE);
                }
            }
        });
        //关键字的点击事件
        keywordsearchlist_lv.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
//                Intent intent = new Intent(SearchActivity.this,
//                        CategoryGoodsDetailAcitivity.class);
                String text = "";
                if(searchIvSearch.getText().equals("供应商")){
                    text = supplierkeyList.get(arg2);
                    Intent intent = new Intent(SearchActivity.this, SupplierSearchResultActivity.class);
                    intent.putExtra("supplierkey", text);
                    startActivity(intent);
                }else   if(searchIvSearch.getText().equals("商品")){
                    text = shopkeyList.get(arg2);
                    Intent intent = new Intent(SearchActivity.this, ShopSearchResultActivity.class);
                    intent.putExtra("shopkey", text);
                    intent.putExtra("type", "shop");
                    startActivity(intent);
                }
            }
        });
        //历史记录的点击事件
        historylist_lv.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                List<String> list = Arrays.asList(hisArrays);
                String text = list.get(arg2);
                Intent intent = new Intent(SearchActivity.this, ShopSearchResultActivity.class);
                intent.putExtra("shopkey", text);
                intent.putExtra("type", "shop");
                startActivity(intent);
            }
        });
    }

    private void initDataSupplier(String key) {
        OkGo.post(Urls.URL_UPPLIER_NAME)//
                .tag(this)
                .params("supplierName",key)
                .params("searchType","1")
                .params("page", "1")
                .params("rows", "10")

                .execute(new DialogCallback<LzyResponse<List<SupplierName>>>(this,false) {
                             @Override
                             public void onSuccess(LzyResponse<List<SupplierName>> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);

                                 if (supplierkeyList!=null){
                                     supplierkeyList.clear();
                                 }
                                 for (int i = 0; i < lzyResponse.data.size(); i++) {
                                     supplierkeyList.add(lzyResponse.data.get(i).getSupplierName());
                                 }
                                 if (supplierkeyList != null) {
                                     if (supplierkeyList.size() < 1) {
                                         keywordsearchlist_lv.setVisibility(View.GONE);
                                         sly.setVisibility(View.VISIBLE);
                                     } else {
                                         keyAdapter = new ArrayAdapter<String>(SearchActivity.this,
                                                 R.layout.keylistview_item, supplierkeyList);
                                         keywordsearchlist_lv.setAdapter(keyAdapter);
                                         keywordsearchlist_lv.setVisibility(View.VISIBLE);
                                         sly.setVisibility(View.GONE);
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

    @OnClick({R.id.search_back_iv, R.id.choise_ly, R.id.clearhistory,R.id.search_tv_action})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back_iv:
                finish();
                break;
            case R.id.choise_ly:
                initMenu();
                break;
            case R.id.clearhistory:
                new AlertDialog(SearchActivity.this).builder().setTitle("温馨提示")
                        .setMsg("您确定要清空搜索历史吗？")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //清楚历史记录的保存
                                SharedPreferences shareData = SearchActivity.this.getSharedPreferences(
                                        SEARCH_HISTORY, 0);
                                SharedPreferences.Editor editor = shareData.edit();
                                editor.putString(SEARCH_HISTORY,
                                        "");
                                editor.commit();
                                //刷新界面
                                initSearchHistory();
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();

                break;
            case R.id.search_tv_action:
                if(!TextUtils.isEmpty(searchEtSearch.getText().toString())){
                if(searchIvSearch.getText().equals("供应商")){
                    Intent intent = new Intent(SearchActivity.this, SupplierSearchResultActivity.class);
                    intent.putExtra("supplierkey", searchEtSearch.getText().toString());
                    startActivity(intent);
                }else   if(searchIvSearch.getText().equals("商品")){
                    Intent intent = new Intent(SearchActivity.this, ShopSearchResultActivity.class);
                    intent.putExtra("shopkey", searchEtSearch.getText().toString());
                    intent.putExtra("type", "shop");
                    startActivity(intent);
                }}
                break;
        }
    }

    public void initSearchHistory() {
        SharedPreferences sp = getSharedPreferences(
                SEARCH_HISTORY, 0);
        String longhistory = sp.getString(
                SEARCH_HISTORY, "");

        if (longhistory == null || longhistory.length() <= 0) {
            history_ly.setVisibility(View.GONE);
        } else {
            hisArrays = longhistory.split(",");
            if (hisArrays.length < 1) {
                history_ly.setVisibility(View.GONE);
            } else {
                history_ly.setVisibility(View.VISIBLE);
//                for (int i = 0; i < hisArrays.length; i++) {
                    keyAdapter = new ArrayAdapter<String>(SearchActivity.this,
                            R.layout.keylistview_item, hisArrays);
                    historylist_lv.setAdapter(keyAdapter);
                    keywordsearchlist_lv.setVisibility(View.GONE);
                    sly.setVisibility(View.VISIBLE);
                    history_ly.setVisibility(View.VISIBLE);
//                }
            }
        }
    }
    private void initDataHotShop() {
        OkGo.post(Urls.URL_HOT_KEYWORD)//
                .tag(this)
                .execute(new DialogCallback<LzyResponse<List<HotShop>>>(this,false) {
                             @Override
                             public void onSuccess(final LzyResponse<List<HotShop>> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);

//                                 if (hotkeyList!=null){
//                                     hotkeyList.clear();
//                                 }
//                                 for (int i = 0; i < lzyResponse.data.size(); i++) {
//                                     hotkeyList.add(lzyResponse.data.get(i).getKeyword());
//                                 }
                                 if (lzyResponse.data != null) {
                                     if (lzyResponse.data.size() < 1) {
                                         hot_ly.setVisibility(View.GONE);

                                     } else {
                                         hot_ly.setVisibility(View.VISIBLE);
//                                         hotflowLayout.setList(hotkeyList);
                                         mContainer.setVisibility(View.VISIBLE);
                                         mAdapter = new MyTagBaseAdapter(SearchActivity.this,lzyResponse.data);
                                         mContainer.setAdapter(mAdapter);
//                                         mAdapter.notifyDataSetChanged();

                                         //热搜流式布局
                                         mContainer.setItemClickListener(new TagCloudLayout.TagItemClickListener() {
                                             @Override
                                             public void itemClick(int position) {
                                                 Intent intent = new Intent(SearchActivity.this, ShopSearchResultActivity.class);
                                                 intent.putExtra("shopkey",lzyResponse.data.get(position).getKeyword());
                                                 intent.putExtra("type", "shop");
                                                 startActivity(intent);
                                             }
                                         });
//                                         hotflowLayout.setOnItemClickListener(new MultiLineChooseLayout.onItemClickListener() {
//                                             @Override
//                                             public void onItemClick(int position, String text) {
//                                                 Intent intent = new Intent(SearchActivity.this, ShopSearchResultActivity.class);
//                                                 intent.putExtra("shopkey", text);
//                                                 intent.putExtra("type", "shop");
//                                                 startActivity(intent);
//                                             }
//                                         });
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
    private void initDataShop(String key) {
        OkGo.post(Urls.URL_SEARCH_NAME)//
                .tag(this)
                .params("goodsName",key)
                .params("rows", 15)
                .execute(new DialogCallback<LzyResponse<List<Shop>>>(this,false) {
                             @Override
                             public void onSuccess(LzyResponse<List<Shop>> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);

                                     if (shopkeyList!=null){
                                         shopkeyList.clear();
                                     }
                                 for (int i = 0; i < lzyResponse.data.size(); i++) {
                                     shopkeyList.add(lzyResponse.data.get(i).getGoodsName());
                                 }
                                     if (shopkeyList != null) {
                                         if (shopkeyList.size() < 1) {
                                             keywordsearchlist_lv.setVisibility(View.GONE);
                                             sly.setVisibility(View.VISIBLE);
                                         } else {
                                             keyAdapter = new ArrayAdapter<String>(SearchActivity.this,
                                                     R.layout.keylistview_item, shopkeyList);
                                             keywordsearchlist_lv.setAdapter(keyAdapter);
                                             keywordsearchlist_lv.setVisibility(View.VISIBLE);
                                             sly.setVisibility(View.GONE);
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
    private void initMenu() {
        mMenu = new UserMenu(SearchActivity.this);
        mMenu.addItem(classSearch, USER_SEARCH);
        mMenu.setOnItemSelectedListener(new PopMenu.OnItemSelectedListener() {
            @Override
            public void selected(View view, PopMenu.Item item, int position) {
                switch (item.id) {
                    case USER_SEARCH:
                        if(searchIvSearch.getText().equals("供应商")){
                            classSearch="供应商";
                            searchIvSearch.setText("商品");

                        }else   if(searchIvSearch.getText().equals("商品")){
                        classSearch="商品";
                            searchIvSearch.setText("供应商");
                    }
                        searchEtSearch.setText("");
                        break;

                }
            }
        });
        mMenu.showAsDropDown(searchIvSearch);
    }
}
