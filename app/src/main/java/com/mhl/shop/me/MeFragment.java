package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.finance.MyAllActivationActivity;
import com.mhl.shop.finance.MyAllGiftBalanceActivity;
import com.mhl.shop.finance.MyAllHldActivity;
import com.mhl.shop.finance.TransactionRecordActivity;
import com.mhl.shop.homepage.MessageActivity;
import com.mhl.shop.login.LoginActivity;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.main.MyBaseFragment;
import com.mhl.shop.main.StringDate;
import com.mhl.shop.main.been.LoginData;
import com.mhl.shop.me.been.Me;
import com.mhl.shop.utils.Constant;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.IntentUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-11-10 14:09
 * 描述：个人中心
 */
public class MeFragment extends MyBaseFragment implements MeInterface.OnMyMeListener {


    @Bind(R.id.title_right_imageview)
    ImageView titleRightImageview;

    @Bind(R.id.user_icon)
    CircleImageView userIcon;
    @Bind(R.id.favcount)
    TextView favcount;
    @Bind(R.id.mycollectionlayout)
    LinearLayout mycollectionlayout;
    @Bind(R.id.viewhistory_num)
    TextView viewhistoryNum;
    @Bind(R.id.browserecordlayout)
    LinearLayout browserecordlayout;
    @Bind(R.id.myorder_img)
    ImageView myorderImg;
    @Bind(R.id.myorder_title_text)
    TextView myorderTitleText;
    @Bind(R.id.myorder_img_title_layout)
    LinearLayout myorderImgTitleLayout;
    @Bind(R.id.myorder_tipcontent_text)
    TextView myorderTipcontentText;
    @Bind(R.id.myorder_tipinfo_jt_layout)
    LinearLayout myorderTipinfoJtLayout;
    @Bind(R.id.myOrderLayout)
    LinearLayout myOrderLayout;
    @Bind(R.id.obligation_lin)
    LinearLayout obligationLin;
    @Bind(R.id.obligation_goods_num)
    TextView obligationGoodsNum;
    @Bind(R.id.my_obligation)
    RelativeLayout myObligation;
    @Bind(R.id.receive_lin)
    LinearLayout receiveLin;
    @Bind(R.id.receive_goods_num)
    TextView receiveGoodsNum;
    @Bind(R.id.my_receive)
    RelativeLayout myReceive;
    @Bind(R.id.my_evaluate_img)
    ImageView myEvaluateImg;
    @Bind(R.id.my_evaluate_layout)
    LinearLayout myEvaluateLayout;
    @Bind(R.id.evaluate_goods_num)
    TextView evaluateGoodsNum;
    @Bind(R.id.my_evaluate)
    RelativeLayout myEvaluate;
    @Bind(R.id.my_return_goods_img)
    ImageView myReturnGoodsImg;
    @Bind(R.id.my_return_goods)
    RelativeLayout myReturnGoods;
    @Bind(R.id.financialcenter_img)
    ImageView financialcenterImg;
    @Bind(R.id.financialcenter_title_text)
    TextView financialcenterTitleText;
    @Bind(R.id.financialcenter_img_title_layout)
    LinearLayout financialcenterImgTitleLayout;
    @Bind(R.id.financialcenter_tipcontent_text)
    TextView financialcenterTipcontentText;
    @Bind(R.id.financialcenter_tipinfo_jt_layout)
    LinearLayout financialcenterTipinfoJtLayout;
    @Bind(R.id.ll_transaction_record)
    LinearLayout llTransactionRecord;
    @Bind(R.id.donate_money)
    TextView donateMoney;
    @Bind(R.id.ll_donate_money)
    LinearLayout llDonateMoney;
    @Bind(R.id.active_money)
    TextView activeMoney;
    @Bind(R.id.ll_active_money)
    LinearLayout llActiveMoney;
    @Bind(R.id.withdraw_money)
    TextView withdrawMoney;
    @Bind(R.id.ll_withdraw_money)
    LinearLayout llWithdrawMoney;
    @Bind(R.id.gold)
    TextView gold;
    @Bind(R.id.finance_peat)
    LinearLayout financePeat;
    @Bind(R.id.myorder_jiantou_img)
    ImageView myorderJiantouImg;
    @Bind(R.id.youhuijuan_img)
    ImageView youhuijuanImg;
    @Bind(R.id.youhuijuan_title_txt)
    TextView youhuijuanTitleTxt;
    @Bind(R.id.youhuijuan_img_title_layout)
    LinearLayout youhuijuanImgTitleLayout;
    @Bind(R.id.coupon_number)
    TextView couponNumber;
    @Bind(R.id.youhuijuan_jiantou_img)
    ImageView youhuijuanJiantouImg;
    @Bind(R.id.youhuijuan_tipinfo_jt_layout)
    LinearLayout youhuijuanTipinfoJtLayout;
    @Bind(R.id.ll_my_coupon)
    LinearLayout llMyCoupon;
    @Bind(R.id.promotioncenter_img)
    ImageView promotioncenterImg;
    @Bind(R.id.promotioncenter_title_text)
    TextView promotioncenterTitleText;
    @Bind(R.id.promotioncenter_img_title_layout)
    LinearLayout promotioncenterImgTitleLayout;
    @Bind(R.id.promotioncenter_tipcontent_text)
    TextView promotioncenterTipcontentText;
    @Bind(R.id.promotioncenter_jiantou_img)
    ImageView promotioncenterJiantouImg;
    @Bind(R.id.promotioncenter_tipinfo_jt_layout)
    LinearLayout promotioncenterTipinfoJtLayout;
    @Bind(R.id.promotioncenterlayout)
    LinearLayout promotioncenterlayout;
    @Bind(R.id.helperandrank_img)
    ImageView helperandrankImg;
    @Bind(R.id.helperandrank_title_txt)
    TextView helperandrankTitleTxt;
    @Bind(R.id.return_goods_num)
    TextView returnGoodsNum;
    @Bind(R.id.helperandrank_img_title_layout)
    LinearLayout helperandrankImgTitleLayout;
    @Bind(R.id.helperandrank_tipcontent_text)
    TextView helperandrankTipcontentText;
    @Bind(R.id.helperandrank_jiantou_img)
    ImageView helperandrankJiantouImg;
    @Bind(R.id.helperandrank_tipinfo_jt_layout)
    LinearLayout helperandrankTipinfoJtLayout;
    @Bind(R.id.helperandranklayout)
    LinearLayout helperandranklayout;
    @Bind(R.id.fra_info_layout)
    LinearLayout fraInfoLayout;
    @Bind(R.id.my_gridview)
    GridView myGridview;
    @Bind(R.id.rl_icon_sign)
    RelativeLayout rlIconSign;
    @Bind(R.id.iv_msg_point)
    ImageView ivMsgPoint;
    @Bind(R.id.mhl_notice)
    ImageView mhlNotice;
    @Bind(R.id.home_news)
    RelativeLayout homeNews;
    private Me data;//我的所有信息

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_me, container,
                false);
        ButterKnife.bind(this, rootView);
        initViews();

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

        if (!MyApplication.getApplication().isLogin()) {
            userIcon.setImageResource(R.drawable.my_header_not_login);
            // 没有登陆隐藏数量信息
            obligationGoodsNum.setVisibility(View.GONE);
            receiveGoodsNum.setVisibility(View.GONE);
            evaluateGoodsNum.setVisibility(View.GONE);
            returnGoodsNum.setVisibility(View.GONE);
            viewhistoryNum.setText(0 + "");
            gold.setText(0 + "");
            favcount.setText(0 + "");
            donateMoney.setText("0");
            activeMoney.setText("0");
            withdrawMoney.setText("0");
            couponNumber.setText("0");
            ivMsgPoint.setVisibility(View.INVISIBLE);
        } else {
            initData();
            isNews(false);
        }

    }

    private void initViews() {
        titleRightImageview.setVisibility(View.VISIBLE);

    }

    StringDate stringDate;

    private void isNews(boolean b) {
        if(stringDate!=null){
            stringDate=null;
        }
        // 获取我的未读消息
        OkGo.post(Urls.URL_INFO_NOTREADNOTICE)//
                .tag(this)
                .execute(new StringDialogCallback(getActivity(), false) {
                             @Override
                             public void onSuccess(String s, Call call, Response response) {
                                 handleResponse(s, call, response);
                                 stringDate = (StringDate) GsonUtils.fromJson(s,
                                         StringDate.class);
                                 if (stringDate.code == 200) {
                                     if (Integer.parseInt(stringDate.data) > 0) {
                                         ivMsgPoint.setVisibility(View.VISIBLE);
                                     } else {
                                         ivMsgPoint.setVisibility(View.INVISIBLE);
                                     }
                                 } else {
                                     T.showShort(getActivity(), stringDate.info);
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
        if(data!=null){
            data=null;
        }
// 获取我的主页信息
        OkGo.post(Urls.URL_MINE)//
                .tag(this)
                .execute(new DialogCallback<LzyResponse<Me>>(getActivity(), false) {
                             @Override
                             public void onSuccess(LzyResponse<Me> responseData, Call call, Response response) {
                                 handleResponse(responseData.data, call, response);

                                 if (responseData.data != null) {
                                     data = responseData.data;
                                     setData(responseData.data);
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

    private void setData(Me data) {

        LoginData.setLoginName(data.getLoginName());
        if(data.getAvatar()==null){
            userIcon.setBackgroundResource(R.drawable.icon_bg);
        }else {
            if(!TextUtils.isEmpty(data.getAvatar())){
                Glide.with(MyApplication.getContext()).load(Urls.URL_PHOTO + "/file/v3/download-" + data.getAvatar() + "-200-200.jpg").into(userIcon);
            }else {
//                userIcon.setBackgroundResource(R.drawable.icon_bg);
                userIcon.setImageDrawable(getResources().getDrawable(R.drawable.icon_bg));
            }
        }

        favcount.setText((data.getCollectionGoodsNum()+ data.getCollectionStoreNum()+data.getCollectionArticleNum())+ "");
        viewhistoryNum.setText(data.getBrowseNum() + "");

        if(data.getPendingPaymentNum()>0){
            obligationGoodsNum.setVisibility(View.VISIBLE);
            obligationGoodsNum.setText(data.getPendingPaymentNum() + "");
        }
        else {
            obligationGoodsNum.setVisibility(View.GONE);

        }
        if(data.getNotReceivedNum()>0){
            receiveGoodsNum.setVisibility(View.VISIBLE);
            receiveGoodsNum.setText(data.getNotReceivedNum() + "");
        }
        else {
            receiveGoodsNum.setVisibility(View.GONE);

        }

        if(data.getReceivedNum()>0){
            evaluateGoodsNum.setVisibility(View.VISIBLE);
            evaluateGoodsNum.setText(data.getReceivedNum() + "");
        }  else {
            evaluateGoodsNum.setVisibility(View.GONE);

        }

        if(data.getReturnNum()>0){
            returnGoodsNum.setVisibility(View.VISIBLE);
            returnGoodsNum.setText(data.getReturnNum() + "");
        } else {
            returnGoodsNum.setVisibility(View.GONE);

        }
        donateMoney.setText(ToolsUtils.Tow(data.getGiftBalance() + ""));//赠送金额
        activeMoney.setText(ToolsUtils.Tow(data.getActivationBalance() + ""));//激活金额
        withdrawMoney.setText(ToolsUtils.Tow(data.getAvailableBalance() + ""));//可提现余额
        gold.setText(data.getGold() + "");//货郎豆
        couponNumber.setText(data.getCouponsNum() + "");//优惠券数量
    }

    @OnClick({R.id.user_icon, R.id.title_right_imageview, R.id.ll_withdraw_money, R.id.mycollectionlayout, R.id.ll_my_coupon,
            R.id.browserecordlayout, R.id.myOrderLayout, R.id.my_obligation, R.id.my_receive, R.id.my_evaluate, R.id.my_return_goods
            , R.id.finance_peat, R.id.ll_transaction_record, R.id.ll_active_money, R.id.myMsgaddress
            , R.id.ll_donate_money, R.id.promotioncenterlayout, R.id.helperandranklayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_icon://我的账户
                // 跳转到登陆界面
                if (!MyApplication.getApplication().isLogin()) {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    // 传递标记登陆成功后返回到我的
                    login.putExtra("index", 3);
                    getActivity().startActivity(login);
                } else {
                    Intent intent = new Intent(getActivity(), MyAccountMainAcivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.myMsgaddress://我的地址管理
                // 跳转到登陆界面
                if (!MyApplication.getApplication().isLogin()) {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    // 传递标记登陆成功后返回到我的
                    login.putExtra("index", 3);
                    getActivity().startActivity(login);
                } else {
                    Intent adIntent = new Intent(getActivity(), ManageAdressActivity.class);
                    adIntent.putExtra("adress_type", "userInfo");
                    startActivity(adIntent);
                }
                break;
            case R.id.myOrderLayout://全部订单

                isLogin(Constant.ALL_ORDER);
                break;
            case R.id.my_obligation://待付款

                isLogin(Constant.DAIFUKUAI_ORDER);
                break;
            case R.id.my_receive://待收货

                isLogin(Constant.DASHOUHUO_ORDER);
                break;
            case R.id.my_evaluate://已收货
                isLogin(Constant.DAIPINGJIA_ORDER);
                break;
            case R.id.my_return_goods://退货退款
                if (!MyApplication.getApplication().isLogin()) {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(login);
                } else {
                    IntentUtils.startActivity(getActivity(), ReturnRefundActivity.class);

                }
                break;
            case R.id.ll_my_coupon://我的优惠券
                if (!MyApplication.getApplication().isLogin()) {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(login);
                } else {
                    IntentUtils.startActivity(getActivity(), MyCouponActivity.class);
                }

                break;

            case R.id.browserecordlayout://我的浏览记录
                if (!MyApplication.getApplication().isLogin()) {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(login);
                } else {
                    IntentUtils.startActivity(getActivity(), MyHistoryActivity.class);

                }
                break;
            case R.id.title_right_imageview://设置
                IntentUtils.startActivity(getActivity(), SettingActivity.class);
                break;
            case R.id.ll_transaction_record://交易記錄
                if (!MyApplication.getApplication().isLogin()) {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(login);
                } else {
                    IntentUtils.startActivity(getActivity(), TransactionRecordActivity.class);

                }
                break;
            case R.id.finance_peat://我的货郎豆
                if (!MyApplication.getApplication().isLogin()) {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(login);
                } else {
                    if(data!=null){
                    Intent intent5 = new Intent();
                    intent5.putExtra("hld", data.getGold() + "");
                    intent5.setClass(getActivity(), MyAllHldActivity.class);
                    startActivity(intent5);}
                }

                break;
            case R.id.mycollectionlayout://我的收藏
                if (!MyApplication.getApplication().isLogin()) {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(login);
                } else {
                    IntentUtils.startActivity(getActivity(), MyAllCollectActivity.class);

                }
                break;
            case R.id.ll_donate_money://赠送金额
                if (!MyApplication.getApplication().isLogin()) {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(login);
                } else {
                    if(data!=null){
                    Intent giftIntent = new Intent(getActivity(), MyAllGiftBalanceActivity.class);
                    giftIntent.putExtra("GiftBalance", ToolsUtils.Tow(data.getGiftBalance() + ""));
                    startActivity(giftIntent);}
                }

                break;
            case R.id.ll_withdraw_money://可提现金额
                if (!MyApplication.getApplication().isLogin()) {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    // 传递标记登陆成功后返回到我的
                    getActivity().startActivity(login);
                } else {
                    if(data!=null){
                    Intent withdrawIntent = new Intent(getActivity(), CanWithdrawCashActivity.class);
                    withdrawIntent.putExtra("AvailableBalance", ToolsUtils.Tow(data.getAvailableBalance() + ""));
                    startActivity(withdrawIntent);}
                }
                break;
            case R.id.ll_active_money://激活金额
                if (!MyApplication.getApplication().isLogin()) {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    // 传递标记登陆成功后返回到我的
                    getActivity().startActivity(login);
                } else {
                    // 激活金额
                    if(data!=null){
                    Intent activeIntent = new Intent(getActivity(), MyAllActivationActivity.class);
                    activeIntent.putExtra("activated_money", ToolsUtils.Tow(data.getActivationBalance() + ""));
                    activeIntent.putExtra("activated_in", ToolsUtils.Tow(data.getActivationBalance() + ""));
                    activeIntent.putExtra("activated_no", ToolsUtils.Tow(data.getActivationBalanceDueIn() + ""));
                    startActivity(activeIntent);}
                }
                break;
            case R.id.promotioncenterlayout://推广中心
                if (!MyApplication.getApplication().isLogin()) {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    // 传递标记登陆成功后返回到我的
                    getActivity().startActivity(login);
                } else {
                    Intent activeIntent = new Intent(getActivity(), ExtensionCenterActivity.class);
                    startActivity(activeIntent);
                }
                break;
            case R.id.helperandranklayout://反馈
                Intent activeIntent = new Intent(getActivity(), HelpActivity.class);
                startActivity(activeIntent);
                break;
        }
    }

    private void isLogin(String typeOrder) {
        if (!MyApplication.getApplication().isLogin()) {
            Intent login = new Intent(getActivity(), LoginActivity.class);
            // 传递标记登陆成功后返回到我的
            getActivity().startActivity(login);
        } else {
            Intent intent = new Intent();
            intent.putExtra(Constant.ORDER_STATE, typeOrder);
            intent.setClass(getActivity(), OrderMainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
//通知
    @OnClick(R.id.home_news)
    public void onClick() {
        if (!MyApplication.getApplication().isLogin()) {
            Intent login = new Intent(getActivity(), LoginActivity.class);
            // 传递标记登陆成功后返回到我的
            login.putExtra("index", 3);
            getActivity().startActivity(login);
        } else {
            IntentUtils.startActivity(getActivity(), MessageActivity.class);
        }
    }

    @Override
    public void OnMyMeRefresh(String order_stutas, int pageNum) {

        if (!MyApplication.getApplication().isLogin()) {
            userIcon.setImageResource(R.drawable.my_header_not_login);
            // 没有登陆隐藏数量信息
            obligationGoodsNum.setVisibility(View.GONE);
            receiveGoodsNum.setVisibility(View.GONE);
            evaluateGoodsNum.setVisibility(View.GONE);
            returnGoodsNum.setVisibility(View.GONE);
            viewhistoryNum.setText(0 + "");
            gold.setText(0 + "");
            favcount.setText(0 + "");
            donateMoney.setText("0");
            activeMoney.setText("0");
            withdrawMoney.setText("0");
            couponNumber.setText("0");
            ivMsgPoint.setVisibility(View.INVISIBLE);
        } else {
            initData();
            isNews(false);
        }
    }
}
