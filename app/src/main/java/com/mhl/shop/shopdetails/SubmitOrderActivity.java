package com.mhl.shop.shopdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.test.espresso.core.deps.guava.base.Joiner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.SettingPayPwdActivity;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.ManageAdressActivity;
import com.mhl.shop.me.MeInterface;
import com.mhl.shop.me.OrderMainActivity;
import com.mhl.shop.me.myview.PayPasswordView;
import com.mhl.shop.shopdetails.adapter.SubmitOrderAdapter;
import com.mhl.shop.shopdetails.been.Submit;
import com.mhl.shop.shopdetails.been.SumbitOk;
import com.mhl.shop.utils.Constant;
import com.mhl.shop.utils.DialogWidget;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.mhl.shop.wheel.PasswordDialogView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-12-13 16:44
 * 描述：提交订单
 */
public class SubmitOrderActivity extends MyBaseActivity implements SubmitOrderAdapter.OnItemClickListener, MeInterface.OnMyPasswordListener {
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.title_parentlayout)
    RelativeLayout titleParentlayout;
    @Bind(R.id.write_order_true_menoy)
    TextView writeOrderTrueMenoy;
    @Bind(R.id.write_order_commit)
    Button writeOrderCommit;
    @Bind(R.id.rel)
    RelativeLayout rel;
    @Bind(R.id.write_order_username)
    TextView writeOrderUsername;
    @Bind(R.id.write_order_phone)
    TextView writeOrderPhone;
    @Bind(R.id.write_order_user_adress)
    TextView writeOrderUserAdress;
    @Bind(R.id.write_order_adress_zero)
    TextView writeOrderAdressZero;
    @Bind(R.id.write_order_edit_address)
    LinearLayout writeOrderEditAddress;
    @Bind(R.id.write_order_shop_item)
    RecyclerView writeOrderShopItem;
    @Bind(R.id.tv_coupons)
    TextView tvCoupons;
    @Bind(R.id.write_order_usable_coupons)
    TextView writeOrderUsableCoupons;
    @Bind(R.id.write_order_isuse_coupons)
    RelativeLayout writeOrderIsuseCoupons;
    @Bind(R.id.write_order_coupons_item)
    ListView writeOrderCouponsItem;
    @Bind(R.id.tv_hld)
    TextView tvHld;
    @Bind(R.id.write_order_usable_hld)
    TextView writeOrderUsableHld;
    @Bind(R.id.write_order_isuse_hld_switch)
    CheckBox writeOrderIsuseHldSwitch;
    @Bind(R.id.write_order_isuse_hld)
    RelativeLayout writeOrderIsuseHld;
    @Bind(R.id.write_order_usabale_money)
    TextView writeOrderUsabaleMoney;
    @Bind(R.id.write_order_account_money_switch)
    CheckBox writeOrderAccountMoneySwitch;
    @Bind(R.id.write_order_account_money)
    RelativeLayout writeOrderAccountMoney;
    @Bind(R.id.write_order_all_money)
    TextView writeOrderAllMoney;
    @Bind(R.id.freight_all_money)
    TextView freightAllMoney;
    @Bind(R.id.write_order_freight)
    TextView writeOrderFreight;
    @Bind(R.id.write_order_coupons_money)
    TextView writeOrderCouponsMoney;
    @Bind(R.id.coupons_lay)
    RelativeLayout couponsLay;
    @Bind(R.id.write_order_hld_money)
    TextView writeOrderHldMoney;
    @Bind(R.id.hld_lay)
    RelativeLayout hldLay;
    @Bind(R.id.write_order_balance_money)
    TextView writeOrderBalanceMoney;
    @Bind(R.id.balance_lay)
    RelativeLayout balanceLay;
    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.coupons_no)
    TextView coupons_no;
    @Bind(R.id.all_ly)
    LinearLayout allLy;
    private String id, goodsSpecDetailId;
    private SubmitOrderAdapter adapter;
    private List<Submit.UserGoodsCartBean> listshop;
    private Submit list;
    private SumbitOk subok;
    private ArrayAdapter<String> sadapter;
    private List<Submit.UserGetCouponBean> coupons;
    private String couponpkId;//选中的优惠券id，选中的优惠券减免的金额
    private boolean hldisChecked = false;//货郎豆是否选中
    private boolean balanceisChecked = false;//可用余额是否选中
    private Double hldAmount = 0.00;//货郎豆折现后的金额
    private Double balanceAmount = 0.00;//所用的余额金额
    private Double couponAmount = 0.00;//所用的优惠券减免的金额
    private Double allFreight = 0.00;//总运费
    private Double allMoneyFreight = 0.00;//总金额和运费
    private Double allMoney = 0.00;//总金额
    private String addressId = "";//地址id
//    private int gold = 0;
    private boolean isPayPassword = false;//是否设置过支付密码
    private DialogWidget mDialogWidget;
    private TextView tv;
    private PasswordDialogView dialogView2;//去设置支付的密码框
    private List<String> expressTypeList = new ArrayList();
    Map<Integer, String> leaveMessageList = new HashMap<Integer, String>();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);
        ButterKnife.bind(this);
        initView();
        //给优惠券列表设置监听
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
//                 tv = (TextView)view;
//                tv.setTextColor(getResources().getColor(R.color.main_text_tow_color));    //设置颜色
//                tv.setTextSize(14.0f);    //设置大小
                String cardNumber = parent.getItemAtPosition(pos).toString();//传递门店
                if (cardNumber.equals("不使用优惠券")) {
                    couponAmount = 0.00;
                    couponpkId = "";
                    couponsLay.setVisibility(View.GONE);
                } else {
                    if (coupons.get(pos).getCouponAmount() > 0) {
                        couponpkId = coupons.get(pos).getPkId() + "";
                        couponAmount = coupons.get(pos).getCouponAmount();
                        couponsLay.setVisibility(View.VISIBLE);
                        writeOrderCouponsMoney.setText("-¥" + ToolsUtils.Tow(couponAmount + ""));

                        writeOrderIsuseHldSwitch.setChecked(false);
                        hldisChecked = false;
                        writeOrderAccountMoneySwitch.setChecked(false);
                        balanceisChecked = false;
                        hldAmount = 0.00;
                        balanceAmount = 0.00;
                    }
                }
                refreshAmount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        //给货郎豆CheckBox设置事件监听
        writeOrderIsuseHldSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    hldisChecked = true;
                    if(list.getUserBalance().getActivationBalance()<allMoneyFreight*list.getActivationCeiling()){
                        if (list.getUserBalance().getActivationBalance() >= allMoneyFreight - couponAmount) {
                            writeOrderIsuseHldSwitch.setChecked(true);
                            hldisChecked = true;
                            writeOrderAccountMoneySwitch.setChecked(false);
                            balanceisChecked = false;
                        }
                    }else {
                        if (allMoneyFreight*list.getActivationCeiling() >= allMoneyFreight - couponAmount) {
                            writeOrderIsuseHldSwitch.setChecked(true);
                            hldisChecked = true;
                            writeOrderAccountMoneySwitch.setChecked(false);
                            balanceisChecked = false;
                        }

                    }

                    refreshAmount();
                } else {
                    hldisChecked = false;
                    refreshAmount();
                }
            }
        });
        //给可用余额CheckBox设置事件监听
        writeOrderAccountMoneySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    balanceisChecked = true;
                    refreshAmount();
                    if(list.getUserBalance().getAvailableBalance()<allMoneyFreight*list.getAvailableCeiling()){
                        if ((list.getUserBalance().getAvailableBalance()) >= allMoneyFreight - couponAmount) {
                            writeOrderAccountMoneySwitch.setChecked(true);
                            balanceisChecked = true;
                            writeOrderIsuseHldSwitch.setChecked(false);
                            hldisChecked = false;
                        }
                    }else {
                        if (allMoneyFreight*list.getAvailableCeiling() >= allMoneyFreight - couponAmount) {
                            writeOrderAccountMoneySwitch.setChecked(true);
                            balanceisChecked = true;
                            writeOrderIsuseHldSwitch.setChecked(false);
                            hldisChecked = false;
                        }
                    }

                } else {
                    balanceisChecked = false;
                    refreshAmount();
                }
            }
        });
        initData();

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    //各种支付状态选中时金额以及页面的刷新
    private void refreshAmount() {


//        else
        //判断是否使用货郎豆
        if (hldisChecked && !balanceisChecked) {
            hldLay.setVisibility(View.VISIBLE);
//            hldAmount = (double) list.getUserBalance().getGold() / 100;
            hldAmount = list.getUserBalance().getActivationBalance();

            if (hldAmount < (allMoneyFreight - (couponAmount + balanceAmount))) {

                if(list.getUserBalance().getActivationBalance()<allMoneyFreight*list.getActivationCeiling()){
                    writeOrderHldMoney.setText("-¥" + ToolsUtils.Tow(hldAmount + ""));
                }else {
                    writeOrderHldMoney.setText("-¥" + ToolsUtils.Tow(allMoneyFreight*list.getActivationCeiling() + ""));

                }
            } else {
                if(list.getUserBalance().getActivationBalance()<allMoneyFreight*list.getActivationCeiling()){
                    writeOrderHldMoney.setText("-¥" + ToolsUtils.Tow((allMoneyFreight - (couponAmount + balanceAmount)) + ""));
                }else {
                    writeOrderHldMoney.setText("-¥" + ToolsUtils.Tow(allMoneyFreight*list.getActivationCeiling() + ""));

                }
                hldAmount = allMoneyFreight - (couponAmount + balanceAmount);
            }

        } else {
            hldLay.setVisibility(View.GONE);
            hldAmount = 0.00;
        }
        //判断是否使用余额
        if (balanceisChecked && !hldisChecked) {
            balanceLay.setVisibility(View.VISIBLE);
            balanceAmount = list.getUserBalance().getAvailableBalance();
//            balanceAmount = list.getUserBalance().getAvailableBalance() + list.getUserBalance().getActivationBalance();

            if (balanceAmount < (allMoneyFreight - (couponAmount + hldAmount))) {
                balanceAmount = list.getUserBalance().getAvailableBalance();
                if (list.getUserBalance().getAvailableBalance()  > 0) {
                    if(list.getUserBalance().getAvailableBalance()<allMoneyFreight*list.getAvailableCeiling()){
                        writeOrderBalanceMoney.setText("-¥" + ToolsUtils.Tow(balanceAmount + ""));
                    }else {
                        writeOrderBalanceMoney.setText("-¥" + ToolsUtils.Tow(allMoneyFreight*list.getAvailableCeiling() + ""));
                    }
                    }


            } else {
                balanceAmount = allMoneyFreight - (couponAmount + hldAmount);
                if(list.getUserBalance().getAvailableBalance()<allMoneyFreight*list.getAvailableCeiling()){
                    if(list.getUserBalance().getAvailableBalance()<allMoneyFreight*list.getAvailableCeiling()){
                        writeOrderBalanceMoney.setText("-¥" + ToolsUtils.Tow((allMoneyFreight - (couponAmount + hldAmount)) + ""));
                    }else {
                        writeOrderBalanceMoney.setText("-¥" + ToolsUtils.Tow(allMoneyFreight*list.getAvailableCeiling() + ""));
                    }
                }else {

                    writeOrderBalanceMoney.setText("-¥" + ToolsUtils.Tow(allMoneyFreight*list.getAvailableCeiling() + ""));
                }
            }
        } else {
            balanceLay.setVisibility(View.GONE);
            balanceAmount = 0.00;
        }

        if (hldisChecked && balanceisChecked) {
            hldLay.setVisibility(View.VISIBLE);
            balanceLay.setVisibility(View.VISIBLE);
            hldAmount = list.getUserBalance().getActivationBalance();
            balanceAmount = list.getUserBalance().getAvailableBalance() ;
            if(list.getUserBalance().getActivationBalance()<allMoneyFreight*list.getActivationCeiling()){
                writeOrderHldMoney.setText("-¥" + ToolsUtils.Tow(hldAmount + ""));
            }else {
                writeOrderHldMoney.setText("-¥" + ToolsUtils.Tow(allMoneyFreight*list.getActivationCeiling() + ""));

            }
            if ((allMoneyFreight - (couponAmount + hldAmount)) < balanceAmount) {
                balanceAmount = allMoneyFreight - (couponAmount + hldAmount);
                if(list.getUserBalance().getAvailableBalance()<allMoneyFreight*list.getAvailableCeiling()){
                    writeOrderBalanceMoney.setText("-¥" + ToolsUtils.Tow(balanceAmount + ""));

                }else {
                    writeOrderBalanceMoney.setText("-¥" + ToolsUtils.Tow(allMoneyFreight*list.getAvailableCeiling() + ""));
                }

            } else {
//                balanceAmount=;
                if(list.getUserBalance().getAvailableBalance()<allMoneyFreight*list.getAvailableCeiling()){
                    writeOrderBalanceMoney.setText("-¥" + ToolsUtils.Tow(balanceAmount + ""));

                }else {
                    writeOrderBalanceMoney.setText("-¥" + ToolsUtils.Tow(allMoneyFreight*list.getAvailableCeiling() + ""));
                }

            }

//                   return;
        } else {
//            hldLay.setVisibility(View.GONE);
//            hldAmount=0.00;
//            balanceLay.setVisibility(View.GONE);
//            balanceAmount=0.00;
        }
        //各种支付状态变化时 底部虚付款金额的刷新
        if(hldisChecked){
            if(list.getUserBalance().getActivationBalance()<allMoneyFreight*list.getActivationCeiling()){
                hldAmount = list.getUserBalance().getActivationBalance();
            }else {
                hldAmount = allMoneyFreight*list.getActivationCeiling();
            }
        }

        if(balanceisChecked){
            if(list.getUserBalance().getAvailableBalance()<allMoneyFreight*list.getAvailableCeiling()){
                balanceAmount = list.getUserBalance().getAvailableBalance();
            }else {
                balanceAmount = allMoneyFreight*list.getAvailableCeiling();
            }
        }
        if (allFreight > 0) {
            if (couponAmount > 0 && balanceisChecked && hldisChecked) {
                //需付款
                writeOrderTrueMenoy.setText("¥" + ToolsUtils.TowZeor((allMoney + allFreight) - hldAmount - couponAmount - balanceAmount + ""));
            } else if (couponAmount > 0 && balanceisChecked && !hldisChecked) {
                //需付款
                writeOrderTrueMenoy.setText("¥" + ToolsUtils.TowZeor((allMoney + allFreight) - couponAmount - balanceAmount + ""));
            } else if (hldisChecked && couponAmount > 0 && !balanceisChecked) {
                //需付款
                writeOrderTrueMenoy.setText("¥" + ToolsUtils.TowZeor((allMoney + allFreight) - hldAmount - couponAmount + ""));
            } else if (couponAmount > 0) {
                //需付款
                writeOrderTrueMenoy.setText("¥" + ToolsUtils.TowZeor(((allMoney + allFreight) - couponAmount + "")));
            } else if (hldisChecked && !balanceisChecked) {
                //需付款
                writeOrderTrueMenoy.setText("¥" + ToolsUtils.TowZeor((allMoney + allFreight) - hldAmount + ""));
            } else if (balanceisChecked && !hldisChecked) {
                //需付款
                writeOrderTrueMenoy.setText("¥" + ToolsUtils.TowZeor(((allMoney + allFreight) - balanceAmount + "")));
            } else if (hldisChecked && balanceisChecked) {
                //需付款
                writeOrderTrueMenoy.setText("¥" + ToolsUtils.TowZeor((allMoney + allFreight) - hldAmount - balanceAmount + ""));
            } else {
                //需付款
                writeOrderTrueMenoy.setText("¥" + ToolsUtils.TowZeor((allMoney + allFreight) + ""));
            }
        } else {
            if (couponAmount > 0 && balanceisChecked && hldisChecked) {
                //需付款
                writeOrderTrueMenoy.setText("¥" + ToolsUtils.TowZeor((allMoney) - hldAmount - couponAmount - balanceAmount + ""));
            } else if (couponAmount > 0 && balanceisChecked && !hldisChecked) {
                //需付款
                writeOrderTrueMenoy.setText("¥" + ToolsUtils.TowZeor((allMoney) - couponAmount - balanceAmount + ""));
            } else if (hldisChecked && couponAmount > 0 && !balanceisChecked) {
                //需付款
                writeOrderTrueMenoy.setText("¥" + ToolsUtils.TowZeor((allMoney) - hldAmount - couponAmount + ""));
            } else if (couponAmount > 0) {
                //需付款
                writeOrderTrueMenoy.setText("¥" + ToolsUtils.TowZeor(((allMoney) - couponAmount + "")));
            } else if (hldisChecked && !balanceisChecked) {
                //需付款
                writeOrderTrueMenoy.setText("¥" + ToolsUtils.TowZeor((allMoney) - hldAmount + ""));
            } else if (balanceisChecked && !hldisChecked) {
                //需付款
                writeOrderTrueMenoy.setText("¥" + ToolsUtils.TowZeor(((allMoney) - balanceAmount + "")));
            } else if (hldisChecked && balanceisChecked) {
                //需付款
                writeOrderTrueMenoy.setText("¥" + ToolsUtils.TowZeor((allMoney) - hldAmount - balanceAmount + ""));
            } else {
                //需付款
                writeOrderTrueMenoy.setText("¥" + ToolsUtils.TowZeor((allMoney) + ""));
            }
        }
    }


    private void initView() {
        titleCenterTextview.setText("填写订单");
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        MeInterface.setOnMyPasswordListener(this);
//        writeOrderShopItem.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//            }
//            @Override
//            public void onScrollStateChanged(RecyclerView view, int scrollState) {
//                if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
//                    View currentFocus = getCurrentFocus();
//                    if (currentFocus != null) {
//                        currentFocus.clearFocus();
//                    }
//                }
//            }
//        });
    }

    //初始化返回数据
    private void initData() {
        if (listshop != null) {
            listshop.clear();
        }
        if (list != null) {
            list = null;
        }
        OkGo.post(Urls.URL_VIEW_CONFIRM)//
                .tag(this)
                .params("goodsCartIds", id)
                .params("addressId", addressId)
                .execute(new DialogCallback<LzyResponse<Submit>>(this, true) {
                             @Override
                             public void onSuccess(LzyResponse<Submit> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
if(lzyResponse.code==200 && lzyResponse.data!=null){
    allLy.setVisibility(View.VISIBLE);
    listshop = lzyResponse.data.getUserGoodsCart();
    list = lzyResponse.data;
    //指定排序器
    Collections.sort(listshop, new Comparator<Submit.UserGoodsCartBean>() {
        @Override
        public int compare(Submit.UserGoodsCartBean o1, Submit.UserGoodsCartBean o2) {
            return (int) (Long.parseLong(o1.getSupplierId()) - Long.parseLong(o2.getSupplierId()));
        }
    });
    setView(list);
}else {
    Toast.makeText(getApplicationContext(), "请重试！",
            Toast.LENGTH_SHORT).show();
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

    private void setView(Submit data) {
        //地址信息
        if (data.getUserAddress() != null) {
            writeOrderAdressZero.setVisibility(View.GONE);
            writeOrderUsername.setText(data.getUserAddress().getConsignee());
            writeOrderPhone.setText(data.getUserAddress().getMobile());
            writeOrderUserAdress.setText(data.getUserAddress().getProvince() + data.getUserAddress().getCity() + data.getUserAddress().getDistrict() + data.getUserAddress().getStreet() + data.getUserAddress().getVillage() + data.getUserAddress().getAddressInfo());
            addressId = data.getUserAddress().getPkId();
        } else {
            writeOrderAdressZero.setVisibility(View.VISIBLE);
        }
        //是否设置支付密码
        if (data.getPayPwdFlag() == 1) {
            isPayPassword = true;
        } else if (data.getPayPwdFlag() == 2) {
            isPayPassword = false;
        }

        //处理商品的adpter
        LinearLayoutManager layoutManager = new LinearLayoutManager(SubmitOrderActivity.this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);

        writeOrderShopItem.setLayoutManager(layoutManager);
        writeOrderShopItem.setHasFixedSize(true);
        writeOrderShopItem.setNestedScrollingEnabled(false);
        adapter = new SubmitOrderAdapter(SubmitOrderActivity.this, data.getUserGoodsCart(), this);
        writeOrderShopItem.setAdapter(adapter);
        //优惠券
        if (data.getUserGetCoupon().size() > 0) {
            coupons = data.getUserGetCoupon();
            sadapter = new ArrayAdapter<String>(SubmitOrderActivity.this,
                    android.R.layout.simple_spinner_item, getEditSource());
            spinner.setAdapter(sadapter);
        } else {
            couponpkId = "";
            coupons_no.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
        }
        //商品总金额
        for (int i = 0; i < list.getUserGoodsCart().size(); i++) {
            allMoney += list.getUserGoodsCart().get(i).getCurrentPrice() * list.getUserGoodsCart().get(i).getGoodsCount();
        }
        for (int i = 0; i < data.getUserGoodsCart().size(); i++) {
            if (!Double.isNaN(data.getUserGoodsCart().get(i).getFreightMoney())) {
                allFreight += data.getUserGoodsCart().get(i).getFreightMoney();
            }
        }
        if (allFreight > 0) {
            writeOrderFreight.setText("¥" + ToolsUtils.Tow(allFreight + ""));
        } else {
            writeOrderFreight.setText("¥" + 0.00);
        }

        allMoneyFreight = allMoney + allFreight;
        //货郎豆
        if (data.getUserBalance().getActivationBalance() > 0) {
            if(data.getUserBalance().getActivationBalance()<allMoneyFreight*data.getActivationCeiling()){
                tvHld.setText("激活金额   " + "本次可用¥" + ToolsUtils.Tow( data.getUserBalance().getActivationBalance()   + ""));
            }else {
                tvHld.setText("激活金额   " + "共¥" +ToolsUtils.Tow( data.getUserBalance().getActivationBalance() + "")+"，本次可用：¥"+ToolsUtils.Tow(allMoneyFreight*data.getActivationCeiling()+""));
//            gold = data.getUserBalance().getGold();
//            tvHld.setText("货郎豆   " + "可用货郎豆" + data.getUserBalance().getGold() + "个" + ",可抵扣：¥" + (ToolsUtils.Tow(String.valueOf((double) data.getUserBalance().getGold() / 100))));
//            gold = data.getUserBalance().getGold();
            }
        } else {
            tvHld.setText("激活金额   " + "可用：¥0.00");
            writeOrderIsuseHldSwitch.setClickable(false);
        }
        //可用余额
        if (data.getUserBalance().getAvailableBalance()  > 0) {
            if(data.getUserBalance().getAvailableBalance()<allMoneyFreight*data.getAvailableCeiling()){
                writeOrderUsabaleMoney.setText("可提现金额   " + "本次可用¥" + ToolsUtils.Tow( data.getUserBalance().getAvailableBalance()   + ""));
            }else {
                writeOrderUsabaleMoney.setText("可提现金额   " + "共¥" +ToolsUtils.Tow( data.getUserBalance().getAvailableBalance() + "")+"，本次可用：¥"+ToolsUtils.Tow(allMoneyFreight*data.getAvailableCeiling()+""));
            }
        } else {
            writeOrderUsabaleMoney.setText("可提现金额  可用：¥0.00");
            writeOrderAccountMoneySwitch.setClickable(false);
        }

        writeOrderAllMoney.setText("¥" + ToolsUtils.Tow(allMoney + ""));
        //总运费
//        Double sum1 = Double.valueOf(0);
//        for(int i = 0; i <data.getUserGoodsCart().size(); i ++){
//            if (i > 0) {
//                if (data.getUserGoodsCart().get(i).getSupplierId().equals(data.getUserGoodsCart().get(i - 1).getSupplierId())) {
//
//                } else {
//                    allFreight += data.getUserGoodsCart().get(i).getFreightMoney();
//                }
//            }else {
//                allFreight  +=data.getUserGoodsCart().get(i).getFreightMoney();
//            }
//        }

        refreshAmount();
    }

    //拼接优惠券列表显示
    public List<String> getEditSource() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < coupons.size(); i++) {
            list.add("满" + coupons.get(i).getCouponOrderAmount() + "元减" + coupons.get(i).getCouponAmount() + "元");
        }
        list.add("不使用优惠券");
        return list;
    }

    @OnClick({R.id.title_left_imageview, R.id.write_order_commit, R.id.write_order_edit_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.write_order_commit:
                //验证手否有收获地址
                if (writeOrderUsername.getText().equals("") || addressId.equals("")) {
                    T.showShort(SubmitOrderActivity.this, "请选择收货地址");
                    return;
                }
                if (balanceisChecked || hldisChecked) {

                    if (isPayPassword)// 已设置支付密码
                    {
                        // 打开密码框
                        mDialogWidget = new DialogWidget(this, getDecorViewDialog());
                        mDialogWidget.show();
                    } else { // 未设置支付面码
                        dialogView2 = new PasswordDialogView(SubmitOrderActivity.this, R.style.myDialog, "您选择了卖货郎账户金额，为了保障您的账户安全，请设置卖货郎安全支付密码！",
                                new PasswordDialogView.OnYesButtonListener() {
                                    @Override
                                    public void YesButtonListener() {
                                        Intent intent1 = new Intent();
                                        intent1.putExtra("loginName", "");
                                        intent1.setClass(SubmitOrderActivity.this, SettingPayPwdActivity.class);
                                        startActivity(intent1);
                                    }


                                }, null);
                        dialogView2.show();
                    }
                } else {
                    commitOrder("");//提交
                }
                break;
            case R.id.write_order_edit_address:
                Intent intent = new Intent(SubmitOrderActivity.this, ManageAdressActivity.class);
                intent.putExtra("adress_type", "order");
                startActivityForResult(intent, 10);
                break;
        }
    }

    protected View getDecorViewDialog() {

        return PayPasswordView.getInstance(
                SubmitOrderActivity.this,
                new PayPasswordView.OnPayListener() {

                    @Override
                    public void onSurePay(String password) {
                        commitOrder(password);//提交


                    }

                    @Override
                    public void onCancelPay() {
                        mDialogWidget.dismiss();
                        mDialogWidget = null;

                    }

                    @Override
                    public void onBackPay() {
                        Toast.makeText(getApplicationContext(), "返回",
                                Toast.LENGTH_SHORT).show();
                        mDialogWidget.dismiss();
                        mDialogWidget = null;

                        finish();
                    }
                }

        ).getView();

    }

    //提交订单
    private void commitOrder(String password) {
        String isUseBalance,isUseActivationMoney;
        int goldNum;

        if (balanceisChecked) {
            isUseBalance = "1";
        } else {
            isUseBalance = "2";
        }
        if (hldisChecked) {
            isUseActivationMoney = "1";
        } else {
            isUseActivationMoney = "2";
        }

        for (int i = 0; i < list.getUserGoodsCart().size(); i++) {
            expressTypeList.add(list.getUserGoodsCart().get(i).getPkId() + "_" + list.getUserGoodsCart().get(i).getExpressWay());
        }
        String expressTypeInfoStr = Joiner.on(";").join(expressTypeList);
        String leaveMessageStr = Joiner.on(";").join(leaveMessageList.values());
        Log.d("123456", "expressTypeList====" + expressTypeInfoStr);
        Log.d("123456", "leaveMessageList====" + leaveMessageStr);
//        Log.d("123456", "goldNum====" + goldNum);
        Log.d("123456", "password====" + password);

        OkGo.post(Urls.URL_SUBMIT_ORDER)
                .tag(this)
                .params("leaveMessageInfo", leaveMessageStr)//买家留言信息(供应商ID_买家留言;...)
                .params("expressTypeInfo", expressTypeInfoStr)//快递信息(购物车ID_快递类型;...)
                .params("addressId", addressId)//收货地址ID
                .params("couponId", couponpkId)//优惠券ID
                .params("isUseActivationMoney", isUseActivationMoney)// 是否使用激活金额(--1是--2否)
                .params("isUseDepositMoney", isUseBalance)//是否使用余额(--1是--2否)
                .params("payPassword", password)
                .params("orderSource", 2)
                .execute(new DialogCallback<LzyResponse<SumbitOk>>(this, true) {
                             @Override
                             public void onSuccess(LzyResponse<SumbitOk> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 subok = lzyResponse.data;
                                 if (subok != null) {
                                     if (lzyResponse.code == 200 && subok.getIsPaySuccess() == 2) {
                                         Intent mIntent = new Intent(SubmitOrderActivity.this, MyCheckStandActivity.class);
                                         Bundle mBundle = new Bundle();
                                         mBundle.putSerializable("subok", subok);
                                         mIntent.putExtras(mBundle);
                                         mIntent.putExtra("t", "1");
                                         startActivity(mIntent);
                                         finish();
                                     } else if (lzyResponse.code == 200 && subok.getIsPaySuccess() == 1) {
                                         T.showShort(SubmitOrderActivity.this, "支付成功！");
                                         Intent intent = new Intent();
                                         intent.putExtra(Constant.ORDER_STATE, Constant.ALL_ORDER);
                                         intent.setClass(SubmitOrderActivity.this, OrderMainActivity.class);
                                         startActivity(intent);
                                         finish();
                                     } else {
                                         if (!TextUtils.isEmpty(lzyResponse.info)) {
                                             T.showShort(SubmitOrderActivity.this, lzyResponse.info);
                                         } else {
                                             T.showShort(SubmitOrderActivity.this, "提交异常");
                                         }
                                     }
                                     //刷新购物车
                                     if (MeInterface.onMyCartListener != null) {
                                         MeInterface.onMyCartListener.OnMyCartRefresh("", 0);
                                     }
                                 } else {
                                     if (!TextUtils.isEmpty(lzyResponse.info)) {
                                         T.showShort(SubmitOrderActivity.this, lzyResponse.info);
                                     } else {
                                         T.showShort(SubmitOrderActivity.this, "提交异常");
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(data);
        if (resultCode == 10) {
            addressId = data.getStringExtra("addressId");

            if (addressId != null) {
                if (expressTypeList != null) {
                    expressTypeList.clear();
                }
                if (leaveMessageList != null) {
                    leaveMessageList.clear();
                }
                hldAmount = 0.00;//货郎豆折现后的金额
                balanceAmount = 0.00;//所用的余额金额
                couponAmount = 0.00;//所用的优惠券减免的金额
                allFreight = 0.00;//总运费
                allMoneyFreight = 0.00;//总金额和运费
                allMoney = 0.00;//总金额
                initData();// 改变地址后根据地址重新请求数据计算邮费
            }
        }
    }

    @Override
    public void onItemClick(int index, String newPrice) {
        leaveMessageList.put(index, newPrice);
    }

    @Override
    public void OnMyPasswordRefresh(String order_stutas, int pageNum) {
        isPayPassword = true;
        if (dialogView2 != null) {
            dialogView2.dismiss();
        }
    }
}
