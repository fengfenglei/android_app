package com.mhl.shop.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.main.Data;
import com.mhl.shop.main.MyBaseActivity;
import com.mhl.shop.me.adapter.ReturnDetailAdapter;
import com.mhl.shop.me.been.ReturnDetail;
import com.mhl.shop.shopcart.CartGoodListView;
import com.mhl.shop.shopdetails.GoodsDetailsActivity;
import com.mhl.shop.shopdetails.SupplierActivity;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.mhl.shop.wheel.DialogView;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-12-20 18:13
 * 描述：退款详情
 */
public class ReturnDetailActivity extends MyBaseActivity {

    @Bind(R.id.title_left_imageview)
    ImageView titleLeftImageview;
    @Bind(R.id.title_center_textview)
    TextView titleCenterTextview;
    @Bind(R.id.order_number)
    TextView orderNumber;
    @Bind(R.id.order_state)
    TextView orderState;
    @Bind(R.id.item_image)
    ImageView itemImage;
    @Bind(R.id.shop_name)
    TextView shopName;
    @Bind(R.id.guige)
    TextView guige;
    @Bind(R.id.big_money)
    TextView bigMoney;
    @Bind(R.id.shop_count)
    TextView shopCount;
    @Bind(R.id.return_info)
    CartGoodListView returnInfo;
    @Bind(R.id.head_supplier_view)
    RelativeLayout head_supplier_view;
    @Bind(R.id.write_order_supplier_icon)
    CircleImageView write_order_supplier_icon;
    @Bind(R.id.write_order_supplier_name)
    TextView write_order_supplier_name;
    @Bind(R.id.return_order_detail)
    Button return_order_detail;
    @Bind(R.id.write_Logistics_return)
    Button write_Logistics_return;
    @Bind(R.id.view_Logistics_return)
    Button view_Logistics_return;
    @Bind(R.id.refund_return)
    Button refund_return;
    @Bind(R.id.shop_ly)
    LinearLayout shop_ly;
    @Bind(R.id.all_ly)
    LinearLayout all_ly;
    private String pkId,supplierId,goodsId;
    private ReturnDetailAdapter adapter;
    private ReturnDetail returnDetail;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_return_detail);
        ButterKnife.bind(this);
        initView();
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        initData(pkId);
    }
    private void initView() {
        Intent getIntent = getIntent();
        pkId = getIntent.getStringExtra("pkId");
        supplierId = getIntent.getStringExtra("supplierId");
        goodsId= getIntent.getStringExtra("goodsId");
        titleCenterTextview.setText("退款详情");
        returnInfo.setFocusable(false);//设置顶部在ScrollView

    }



    private void initData(String pkId) {
        OkGo.post(Urls.URL_ORDER_RETURN_DETAIL)//
                .tag(this)
                .params("orderDetailId", pkId)
                .execute(new DialogCallback<LzyResponse<ReturnDetail>>(ReturnDetailActivity.this, true) {
                             @Override
                             public void onSuccess(LzyResponse<ReturnDetail> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse, call, response);
                                 if(returnDetail!=null){
                                     returnDetail=null;
                                 }
                                 if (lzyResponse.code == 200) {
                                     returnDetail =lzyResponse.data;
                                     setData(lzyResponse.data);
                                     all_ly.setVisibility(View.VISIBLE);
                                 } else {
                                     T.showShort(ReturnDetailActivity.this, lzyResponse.info);
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

    private void setData(ReturnDetail data) {

          orderNumber.setText(data.getOrderReturnInfo().getReturnNo()+"");
        if(data.getOrderReturnInfo().getReturnStatus()==31){
            orderState.setText("待供应商审核");
            return_order_detail.setVisibility(View.VISIBLE);
            refund_return.setVisibility(View.VISIBLE);
            view_Logistics_return.setVisibility(View.GONE);
            write_Logistics_return.setVisibility(View.GONE);
        }else if(data.getOrderReturnInfo().getReturnStatus()==32){
            orderState.setText("待平台审核");
            return_order_detail.setVisibility(View.VISIBLE);
            refund_return.setVisibility(View.GONE);
            view_Logistics_return.setVisibility(View.GONE);
            write_Logistics_return.setVisibility(View.GONE);
        }else if(data.getOrderReturnInfo().getReturnStatus()==61){
            orderState.setText("退款成功");
            return_order_detail.setVisibility(View.VISIBLE);
            refund_return.setVisibility(View.GONE);
            view_Logistics_return.setVisibility(View.GONE);
            write_Logistics_return.setVisibility(View.GONE);
        }else if(data.getOrderReturnInfo().getReturnStatus()==41){
            orderState.setText("待供应商审核");
            return_order_detail.setVisibility(View.VISIBLE);
            refund_return.setVisibility(View.VISIBLE);
            view_Logistics_return.setVisibility(View.GONE);
            write_Logistics_return.setVisibility(View.GONE);
        }else if(data.getOrderReturnInfo().getReturnStatus()==42){
            orderState.setText("待会员退货");
            return_order_detail.setVisibility(View.VISIBLE);
            write_Logistics_return.setVisibility(View.VISIBLE);
            refund_return.setVisibility(View.GONE);
            view_Logistics_return.setVisibility(View.GONE);
        }else if(data.getOrderReturnInfo().getReturnStatus()==43){
            orderState.setText("待供应商收货");
            return_order_detail.setVisibility(View.VISIBLE);
            view_Logistics_return.setVisibility(View.VISIBLE);
            refund_return.setVisibility(View.GONE);
            write_Logistics_return.setVisibility(View.GONE);
        }else if(data.getOrderReturnInfo().getReturnStatus()==44){
            orderState.setText("待平台审核");
            return_order_detail.setVisibility(View.VISIBLE);
            view_Logistics_return.setVisibility(View.VISIBLE);
            refund_return.setVisibility(View.GONE);
            write_Logistics_return.setVisibility(View.GONE);
        }else if(data.getOrderReturnInfo().getReturnStatus()==62){
            orderState.setText("退货退款成功");
            return_order_detail.setVisibility(View.VISIBLE);
            view_Logistics_return.setVisibility(View.VISIBLE);
            refund_return.setVisibility(View.GONE);
            write_Logistics_return.setVisibility(View.GONE);
        }else if(data.getOrderReturnInfo().getReturnStatus()==38){
            orderState.setText("供应商拒绝退款");
            return_order_detail.setVisibility(View.VISIBLE);
            refund_return.setVisibility(View.GONE);
            view_Logistics_return.setVisibility(View.GONE);
            write_Logistics_return.setVisibility(View.GONE);
        }else if(data.getOrderReturnInfo().getReturnStatus()==39){
            orderState.setText("会员撤销退款");
            return_order_detail.setVisibility(View.VISIBLE);
            refund_return.setVisibility(View.GONE);
            view_Logistics_return.setVisibility(View.GONE);
            write_Logistics_return.setVisibility(View.GONE);
        }else if(data.getOrderReturnInfo().getReturnStatus()==49){
            orderState.setText("会员撤销退货退款");
            return_order_detail.setVisibility(View.VISIBLE);
            refund_return.setVisibility(View.GONE);
            view_Logistics_return.setVisibility(View.GONE);
            write_Logistics_return.setVisibility(View.GONE);
        }else if(data.getOrderReturnInfo().getReturnStatus()==48){
            orderState.setText("供应商拒绝退货退款");
            return_order_detail.setVisibility(View.VISIBLE);
            refund_return.setVisibility(View.GONE);
            view_Logistics_return.setVisibility(View.GONE);
            write_Logistics_return.setVisibility(View.GONE);
        }
        Glide.with(this).load(Urls.URL_PHOTO + "/file/v3/download-" + data.getIdentificationId() + "-100-100.jpg"
        ).into(write_order_supplier_icon);
        write_order_supplier_name.setText(data.getOrderReturnInfo().getSupplierIdName());
        Glide.with(this).load(Urls.URL_PHOTO+"/file/v3/download-"+data.getOrderReturnInfo().getGoodsIdPic()+"-200-200.jpg"
        ).into(itemImage);
        shopName.setText(data.getOrderReturnInfo().getGoodsIdName());
        bigMoney.setText("¥"+ ToolsUtils.Tow(data.getOrderReturnInfo().getGoodsIdPrice()+""));
        guige.setText(data.getOrderReturnInfo().getGoodsSpec());
        shopCount.setText("x"+data.getOrderReturnInfo().getGoodsCount()+"");

//                                       if (adapter == null) {
                                         adapter = new ReturnDetailAdapter(ReturnDetailActivity.this, data.getReturnLogList(),data);
                                         returnInfo.setAdapter(adapter);
                                         adapter.notifyDataSetChanged();
//                                     } else {
//                                         adapter.notifyDataSetChanged();
//                                     }
    }
    @OnClick({R.id.title_left_imageview, R.id.shop_ly, R.id.head_supplier_view, R.id.return_order_detail
            , R.id.write_Logistics_return, R.id.view_Logistics_return, R.id.refund_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_left_imageview:
                finish();
                break;
            case R.id.shop_ly://跳转商品详情
                Intent intent2 = new Intent();
                intent2.putExtra("goodsid",goodsId);
                intent2.setClass(this, GoodsDetailsActivity.class);
                startActivity(intent2);

                break;
            case R.id.head_supplier_view://跳转供应商
                Intent intent1 = new Intent(this,SupplierActivity.class);
                intent1.putExtra("supplierId",supplierId);
                startActivity(intent1);

                break;
            case R.id.return_order_detail:
                Intent intent = new Intent();
                intent.putExtra("PkId", pkId);//PkId
                intent.setClass(this, OrderDetailActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.write_Logistics_return:
                Intent intent3 = new Intent();
                intent3.putExtra("ReturnNo", returnDetail.getOrderReturnInfo().getReturnNo()+"");
                intent3.putExtra("PkId", returnDetail.getOrderReturnInfo().getPkId()+"");
                intent3.setClass(this, WriteLogisticsActivity.class);
                startActivity(intent3);
                break;
            case R.id.view_Logistics_return:
                Intent intent9 = new Intent();
                intent9.putExtra("pkId", returnDetail.getPkId());
                intent9.putExtra("type","2");
                intent9.setClass(this, LogisticsActivity.class);
                startActivity(intent9);
                break;
            case R.id.refund_return:
                DialogView dialogView2 = new DialogView(this, R.style.myDialog,"确定撤销退款 ？", new DialogView.OnYesButtonListener() {

                    @Override
                    public void YesButtonListener()
                    {
                        canleReturn(returnDetail.getOrderReturnInfo().getPkId()+"");
                    }

                }, null);
                dialogView2.show();
                break;
        }
    }
    Data body;
    private void canleReturn(String s) {
        String URL = null;
     if(returnDetail.getOrderReturnInfo().getReturnType()==1){
         URL=Urls.URL_ORDER_RETURN_CANCEL;
     }else  if(returnDetail.getOrderReturnInfo().getReturnType()==2){
         URL=Urls.URL_ORDER_RETURN_CANCEL_GOODS;
     }
        OkGo.post(URL)//
                .tag(this)
                .params("returnInfoId",s)
                .execute(
//                        new DialogCallback<LzyResponse<Null>>(this,true) {
//                             @Override
//                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
//                                 if(lzyResponse.code==200){
//
//                                     initData(pkId);
//                                 }else {
//                                     T.showShort(ReturnDetailActivity.this, "撤销退款失败");
//                                 }
//                             }
//                             @Override
//                             public void onError(Call call, Response response, Exception e) {
//                                 super.onError(call, response, e);
//                                 T.showShort(ReturnDetailActivity.this, e.getMessage());
//                             }
//                         }
                        new StringDialogCallback(ReturnDetailActivity.this, true) {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                body=(Data) GsonUtils.fromJson(s,
                                        Data.class);
                                if(body.getCode()==200){
                                    if(returnDetail!=null){
                                        returnDetail=null;
                                    }
                                    initData(pkId);

                                }else{
                                    T.showShort(ReturnDetailActivity.this, "撤销退款失败");
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
}
