package com.mhl.shop.shopcart;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LoginActivity;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.MainActivity;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.main.MyBaseFragment;
import com.mhl.shop.me.MeInterface;
import com.mhl.shop.me.been.Coupon;
import com.mhl.shop.shopdetails.SubmitOrderActivity;
import com.mhl.shop.utils.IntentUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-10 14:10
 * 描述：购物车
 */
public class CartFragment extends MyBaseFragment implements MeInterface.OnMyCartListener {
    @Bind(R.id.title_left_imageview)
    ImageView titleLeftImageview;
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.title_right_textview)
    TextView title_right_textview;
    @Bind(R.id.title_right_imageview)
    ImageView titleRightImageview;
    @Bind(R.id.shopping_cart_item)
    RecyclerView shopping_item;
    @Bind(R.id.content_abate)
    CartGoodListView content_abate;
    @Bind(R.id.shopping_cart_all_chose)
    TextView shopping_cart_all_chose;
    @Bind(R.id.my_login_tv)
    TextView myLoginTv;
    @Bind(R.id.shopping_cart_all_menoy)
    TextView shopping_cart_all_menoy;
    @Bind(R.id.shopping_login_go)
    TextView shoppingLoginGo;
    @Bind(R.id.shopping_cart_login_ray)
    LinearLayout shoppingCartLoginRay;
    @Bind(R.id.shopping_cart_zero)
    LinearLayout shopping_cart_zero;
    @Bind(R.id.relLayout)
    LinearLayout relLayout;
    @Bind(R.id.rel)
    RelativeLayout rel;
    @Bind(R.id.edit)
    RelativeLayout edit;
    @Bind(R.id.list_all)
    ScrollView list_all;
    @Bind(R.id.clear_abate)
    Button clear_abate;
    @Bind(R.id.shopping_cart_go_pay)
    Button shopping_cart_go_pay;
    @Bind(R.id.shopping_cart_edit_delete)
    Button shopping_cart_edit_delete;
    @Bind(R.id.shopping_cart_edit_collect)
    Button shopping_cart_edit_collect;
    @Bind(R.id.shopping_cart_go_looks)
    Button shopping_cart_go_looks;
    List<Cart> list;
    List<Cart> listabate;
    List<Cart> listshop;
    StringBuilder sb_pk;   //选中的pkid拼接的字符串
    StringBuilder sb_goods;   //选中的goodsid拼接的字符串
    ArrayList<Cart> mGoPayList = new ArrayList<>();
    @Bind(R.id.loading_layout)
    FrameLayout loadingLayout;
    @Bind(R.id.net_connecte_fail)
    FrameLayout netConnecteFail;
    private boolean isEdit = false;//默认不是编辑状态
    private boolean is = false;
    private List<NewCartList> newCartList = new ArrayList<>();
    private ShoppingCartManageAdapter adapter;
    private GoodsAbateAdapter adapterabate;
    private Set<String> pkidSet = new HashSet<>();
    private List<String> pkidList = new ArrayList<>();
    private Set<String> goodsidSet = new HashSet<>();
    private List<String> goodsidList = new ArrayList<>();
    private ShopCartAdapter mShopCartAdapter;
    private int mCount, mPosition;
    private boolean mSelect = true;
    private float mTotalPrice1;
    private String goodsid;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cart, container,
                false);
        ButterKnife.bind(this, rootView);
        initViews();
        MeInterface.setOnMyCartListener(this);

        return rootView;
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());

    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {//不可见

        } else { //可见
            appInitData();

        }
    }

    private void appInitData() {
        if (!MyApplication.getApplication().isLogin()) {
            if (list != null) {
                list.clear();
            }
            if (listabate != null) {
                listabate.clear();
            }
            if (listshop != null) {
                listshop.clear();
            }
            shoppingCartLoginRay.setVisibility(View.VISIBLE);
            relLayout.setVisibility(View.GONE);
            title_right_textview.setVisibility(View.GONE);
            shopping_cart_zero.setVisibility(View.GONE);
            list_all.setVisibility(View.GONE);
//            shopping_item.setVisibility(View.GONE);
        } else {
            shoppingCartLoginRay.setVisibility(View.GONE);

            listabate = new ArrayList<>();
            listshop = new ArrayList<>();
            initData();
            shopping_item.setLayoutManager(new LinearLayoutManager(getActivity()));
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setSmoothScrollbarEnabled(true);
            layoutManager.setAutoMeasureEnabled(true);

            shopping_item.setLayoutManager(layoutManager);
            shopping_item.setHasFixedSize(true);
            shopping_item.setNestedScrollingEnabled(false);
            mShopCartAdapter = new ShopCartAdapter(getActivity(), listshop);
            shopping_item.setAdapter(mShopCartAdapter);
            //修改数量接口
            mShopCartAdapter.setOnEditClickListener(new ShopCartAdapter.OnEditClickListener() {
                @Override
                public void onEditClick(int position, int cartid, int count) {
                    mCount = count;
                    mPosition = position;
                }
            });
            //实时监控全选按钮
            mShopCartAdapter.setResfreshListener(new ShopCartAdapter.OnResfreshListener() {
                @Override
                public void onResfresh(boolean isSelect) {
                    mSelect = isSelect;
                    if (isSelect) {
                        Drawable left = getResources().getDrawable(R.drawable.check);
                        shopping_cart_all_chose.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    } else {
                        Drawable left = getResources().getDrawable(R.drawable.not_checked);
                        shopping_cart_all_chose.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    }
                    float mTotalPrice = 0;
                    int mTotalNum = 0;
                    mTotalPrice1 = 0;
                    mGoPayList.clear();
                    pkidSet.clear();
                    pkidList.clear();
                    goodsidSet.clear();
                    goodsidSet.clear();
                    for (int i = 0; i < listshop.size(); i++)
                        if (listshop.get(i).getIsSelect()) {
                            mTotalPrice += Float.parseFloat(String.valueOf(listshop.get(i).getCurrentPrice() * listshop.get(i).getGoodsCount()));
                            mTotalNum += 1;
                            mGoPayList.add(listshop.get(i));
                            pkidSet.add(listshop.get(i).getPkId());//选中的PkId添加到集合
                            goodsidSet.add(listshop.get(i).getGoodsId());//选中的GoodsId添加到集合

                            //pkid的拼装
                            for (String str : pkidSet) {
                                pkidList.add(str);
                            }
                            pkidList = ToolsUtils.removeDuplicateWithOrder(pkidList);
                            sb_pk = new StringBuilder();
                            if (pkidList != null && pkidList.size() > 0) {
                                for (int f = 0, len = pkidList.size(); f < len; f++) {
                                    sb_pk.append(pkidList.get(f));
                                    if (f < len - 1) {
                                        sb_pk.append(",");
                                    }
                                }
                            }

                            // goodsid的拼装
                            for (String str : goodsidSet) {
                                goodsidList.add(str);
                            }
                            goodsidList = ToolsUtils.removeDuplicateWithOrder(goodsidList);
                            sb_goods = new StringBuilder();
                            if (goodsidList != null && goodsidList.size() > 0) {
                                for (int f = 0, len = goodsidList.size(); f < len; f++) {
                                    sb_goods.append(goodsidList.get(f));
                                    if (f < len - 1) {
                                        sb_goods.append(",");
                                    }
                                }
                            }
                            Log.d("222", "sb===" + sb_goods);
                        }
                    mTotalPrice1 = mTotalPrice;
                    shopping_cart_all_menoy.setText(ToolsUtils.Tow(mTotalPrice + ""));//总价：
                    shopping_cart_go_pay.setText("去结算（" + mTotalNum + "）");
                }
            });


            //全选
            shopping_cart_all_chose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelect = !mSelect;
                    if (mSelect) {
                        Drawable left = getResources().getDrawable(R.drawable.check);
                        shopping_cart_all_chose.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                        for (int i = 0; i < listshop.size(); i++) {
                            listshop.get(i).setSelect(true);
                            listshop.get(i).setShopSelect(true);
                        }
                    } else {
                        Drawable left = getResources().getDrawable(R.drawable.not_checked);
                        shopping_cart_all_chose.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                        for (int i = 0; i < listshop.size(); i++) {
                            listshop.get(i).setSelect(false);
                            listshop.get(i).setShopSelect(false);
                        }
                    }
                    mShopCartAdapter.notifyDataSetChanged();

                }
            });


        }
    }


    private void initViews() {
        titleCenterTextview.setText("购物车");
        titleRightImageview.setVisibility(View.VISIBLE);
        titleLeftImageview.setVisibility(View.GONE);
        title_right_textview.setText("编辑");
    }

    @OnClick({R.id.title_right_textview, R.id.clear_abate, R.id.shopping_cart_edit_delete, R.id.shopping_cart_edit_collect,
            R.id.shopping_cart_go_pay, R.id.shopping_cart_go_looks, R.id.shopping_login_go,R.id.net_connecte_fail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_right_textview://编辑
                if (isEdit) {
                    isEdit = false;
                    title_right_textview.setText("编辑");
                    relLayout.setVisibility(View.VISIBLE);//底部按钮显示
                    rel.setVisibility(View.VISIBLE);//正常全选底部显示
                    edit.setVisibility(View.GONE);//编辑底部隐藏
                } else {
                    isEdit = true;
                    title_right_textview.setText("完成");
                    relLayout.setVisibility(View.VISIBLE);//底部按钮显示
                    rel.setVisibility(View.GONE);//正常全选底部隐藏
                    edit.setVisibility(View.VISIBLE);//编辑底部显示
                }
                break;
            case R.id.net_connecte_fail://重新加载
                appInitData();
                break;
            case R.id.clear_abate://清除失效宝贝
                delete(1);
                break;
            case R.id.shopping_cart_edit_delete://删除选中商品
                delete(2);
                break;
            case R.id.shopping_cart_edit_collect://收藏选中商品
                collect(sb_goods);
                break;
            case R.id.shopping_cart_go_pay://去结算
                if (shopping_cart_go_pay.getText().equals("去结算（0）") || shopping_cart_go_pay.getText().equals("")) {
                } else {

                    Intent intent = new Intent();
                    intent.putExtra("id", sb_pk.toString());
                    intent.setClass(getActivity(), SubmitOrderActivity.class);
                    startActivity(intent);

                }
                break;
            case R.id.shopping_login_go://去登录
                Intent intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.shopping_cart_go_looks://去逛逛
                IntentUtils.startActivity(getActivity(), MainActivity.class);
                MyApplication.setTag(0);
                MyApplication.setType(1);
                break;
        }
    }

    private void collect(StringBuilder sb_goods) {
        if (TextUtils.isEmpty(sb_goods.toString())) {
            T.showShort(getActivity(), "请选择要收藏的商品");
        }
        OkGo.post(Urls.URL_GOODS_SHOP)//
                .tag(this)
                .params("goodsIds", sb_goods.toString())
                .execute(new DialogCallback<LzyResponse<Coupon>>(getActivity(), true) {
                             @Override
                             public void onSuccess(LzyResponse<Coupon> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 T.showShort(getActivity(), "您已收藏成功");

                             }

                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 handleError(call, response, e);
                             }
                         }
                );

    }

    private void delete(final int type) {
        String resultId = "";
        if (type == 1) { //清除失效商品
            ArrayList<String> result = new ArrayList<String>();
            for (int k = 0; k < listabate.size(); k++) {
                result.add(listabate.get(k).getPkId() + "");
            }
            StringBuilder sb = new StringBuilder();

            if (result != null) {
                if (result.size() > 0) {
                    for (int i = 0, len = result.size(); i < len; i++) {
                        sb.append("" + result.get(i) + "");
                        if (i < len - 1) {
                            sb.append(",");
                        }
                    }
                    resultId = sb.toString();
                }
            }
        } else if (type == 2) {//删除选中商品
            resultId = sb_pk.toString();
        }
        OkGo.post(Urls.URL_DELETE_SHOP)//
                .tag(this)
                .params("userGoodsCartIds", resultId)
                .execute(new DialogCallback<LzyResponse<Coupon>>(getActivity(), true) {
                             @Override
                             public void onSuccess(LzyResponse<Coupon> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if (type == 1) {
                                     if (listabate != null) {
                                         listabate.clear();
                                         adapterabate.notifyDataSetChanged();
                                         clear_abate.setVisibility(View.GONE);
                                         if (listshop.size() > 0) {
//                                             relLayout.setVisibility(View.VISIBLE);
                                         } else {
                                             relLayout.setVisibility(View.GONE);
                                             shopping_cart_zero.setVisibility(View.VISIBLE);
                                         }
                                     }
                                 } else if (type == 2) {
                                     if (!MyApplication.getApplication().isLogin())
                                         shoppingCartLoginRay.setVisibility(View.VISIBLE);
                                     else {
                                         initData();
                                         mShopCartAdapter.notifyDataSetChanged();
//                                         adapterabate.notifyDataSetChanged();
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

    private void initData() {
        loadingLayout.setVisibility(View.VISIBLE);
        list_all.setVisibility(View.GONE);

        OkGo.post(Urls.URL_CART_LIST)//
                .tag(this)
                .execute(new DialogCallback<LzyResponse<List<Cart>>>(getActivity(), false) {
                             @Override
                             public void onSuccess(LzyResponse<List<Cart>> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if (list != null) {
                                     list.clear();
                                 }
                                 if (listabate != null) {
                                     listabate.clear();
                                 }
                                 if (listshop != null) {
                                     listshop.clear();
                                 }
                                 loadingLayout.setVisibility(View.GONE);

                                 list = lzyResponse.data;
                                 netConnecteFail.setVisibility(View.GONE);

                                 if (list.size() > 0) {

                                     /**
                                      * 检查是否有失效商品（分组）
                                      */
                                     for (int p = 0; p < list.size(); p++) {  // 遍历商品
                                         int goods_status = list.get(p).getStatusFlag();
                                         if (goods_status != 1 || list.get(p).getSpecNum() == 0) {
//                                         // 失效商品添加到已失效的集合
                                             listabate.add(list.get(p));
                                         } else {
                                             // 正常在售商品添加到集合
                                             listshop.add(list.get(p));
                                         }
                                     }
                                     if (listshop.size() > 0) { //有可售商品的话
                                         /**
                                          * 把后台返回过来的组装成我想要的格式
                                          */
//                                         List<Cart> usableList1 = new ArrayList<>();
                                         //指定排序器
                                         Collections.sort(listshop, new Comparator<Cart>() {
                                             @Override
                                             public int compare(Cart o1, Cart o2) {
                                                 return (int) (Long.parseLong(o1.getSupplierId()) - Long.parseLong(o2.getSupplierId()));
                                             }
                                         });


//                                         for (int i = 0; i < listshop.size() -1; i++) {
//                                             listshop.get(i).getSupplierId();
//                                         }

//                                         Log.e("LFF",listshop.toString());
//                                          List<Cart> usableList1 = new ArrayList<>();
//                                          List<NewCartList.Cart> newCartList = new ArrayList<>();
//                                         usableList1.addAll(listshop);
//
//                                         for (int i = 0; i < usableList1.size() -1; i++) {
//                                             String s1 = usableList1.get(i).getSupplierId().toString().trim();
//                                             for (int j = usableList1.size() -1; j > i; j--) {
//                                                 String s2 = usableList1.get(j).getSupplierId().toString().trim();
//                                                 if (s2.equals(s1)) {
//                                                     usableList1.remove(j);
//                                                 }
//                                             }
//                                         }
//
//                                         for (int i = 0; i < usableList1.size(); i++) {
//                                             NewCartList.Cart cartInfo = new NewCartList.Cart();
//                                             cartInfo.setSupplierId(usableList1.get(i).getSupplierId());
//                                             cartInfo.setSupplierIdName(usableList1.get(i).getSupplierIdName());
//                                             newCartList.add(cartInfo);
//                                         }
//                                         List<Cart> cartList = new ArrayList<>();
//                                         for (int i = 0; i < newCartList.size(); i++) {
//                                             String supplierId = newCartList.get(i).getSupplierId();
//
//                                             for (int j = 0; j < listshop.size(); j++) {
//                                                 String supplierId1 = listshop.get(j).getSupplierId();
//
//                                                 if (supplierId.equals(supplierId1)) {
//                                                     Cart cartInfo = listshop.get(j);
//                                                     cartList.add(cartInfo);
//                                                 }
//                                             }
////                                             newCartList.get(i).setGoodsList(cartList);
//                                         }

                                         isSelectFirst(listshop);
//                                         FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(getActivity());

                                         LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                         layoutManager.setSmoothScrollbarEnabled(true);
                                         layoutManager.setAutoMeasureEnabled(true);

                                         shopping_item.setLayoutManager(layoutManager);
                                         shopping_item.setHasFixedSize(true);
                                         shopping_item.setNestedScrollingEnabled(false);
                                         mShopCartAdapter = new ShopCartAdapter(getActivity(), listshop);
                                         shopping_item.setAdapter(mShopCartAdapter);
                                         mShopCartAdapter.notifyDataSetChanged();
////                                       MyLinearLayoutManger mLayoutManager = new MyLinearLayoutManger(getActivity());
//                                         shopping_item.setLayoutManager(mLayoutManager);
//                                         shopping_item.setHasFixedSize(true);
//                                         MyomAdapter mAdapter = new MyomAdapter(getActivity(),newCartList);
//                                         shopping_item.setAdapter(mAdapter);
//                                         adapter = new ShoppingCartManageAdapter(getActivity(), newCartList);
//                                         shopping_item.setAdapter(adapter);
                                         title_right_textview.setVisibility(View.VISIBLE);//显示编辑

                                         if (title_right_textview.getText().equals("编辑")) {
                                             relLayout.setVisibility(View.VISIBLE);//底部按钮显示
                                             rel.setVisibility(View.VISIBLE);//正常全选底部显示
                                             shoppingCartLoginRay.setVisibility(View.GONE);
                                         } else if (title_right_textview.getText().equals("完成")) {
                                             relLayout.setVisibility(View.VISIBLE);//底部按钮显示
                                             rel.setVisibility(View.GONE);//正常全选底部隐藏
                                             edit.setVisibility(View.VISIBLE);//编辑底部显示
                                         }
                                     } else {
                                         title_right_textview.setVisibility(View.GONE);//隐藏编辑
                                         relLayout.setVisibility(View.GONE);
                                         mShopCartAdapter.notifyDataSetChanged();
                                     }


                                     if (listabate.size() > 0) { //有失效商品的话
                                         adapterabate = new GoodsAbateAdapter(getActivity(), listabate);
                                         content_abate.setAdapter(adapterabate);
                                         clear_abate.setVisibility(View.VISIBLE);
                                         content_abate.setVisibility(View.VISIBLE);//显示失效商品列表

                                     } else {
                                         clear_abate.setVisibility(View.GONE);//隐藏清除失效按钮
                                         content_abate.setVisibility(View.GONE);//隐藏失效商品列表
                                     }

                                     list_all.setVisibility(View.VISIBLE);
                                     shopping_cart_zero.setVisibility(View.GONE);
                                 } else {
                                     shopping_cart_zero.setVisibility(View.VISIBLE);
                                     list_all.setVisibility(View.GONE);
                                     relLayout.setVisibility(View.GONE);
                                 }
                             }

                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 handleError(call, response, e);
                                 loadingLayout.setVisibility(View.GONE);
                                 netConnecteFail.setVisibility(View.VISIBLE);

                             }
                         }
                );

    }

    private void isSelectFirst(List<Cart> listshop) {
        if (listshop.size() > 0) {
            listshop.get(0).setIsFirst(1);
            for (int i = 1; i < listshop.size(); i++) {
                if (listshop.get(i).getSupplierId().equals(listshop.get(i - 1).getSupplierId())) {
                    listshop.get(i).setIsFirst(2);
                } else {
                    listshop.get(i).setIsFirst(1);
                }
            }
        }

    }


    @Override
    public void OnMyCartRefresh(String order_stutas, int pageNum) {
        appInitData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
