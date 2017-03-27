package com.mhl.shop.shopdetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bumptech.glide.Glide;
import com.gxz.PagerSlidingTabStrip;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.login.LoginActivity;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.Data;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.main.MyBaseRegistFragment;
import com.mhl.shop.main.Share;
import com.mhl.shop.me.MeInterface;
import com.mhl.shop.shopdetails.adapter.NetworkImageHolderView;
import com.mhl.shop.shopdetails.been.Comment;
import com.mhl.shop.shopdetails.been.Details;
import com.mhl.shop.shopdetails.been.NewInfo;
import com.mhl.shop.shopdetails.been.OldInfo;
import com.mhl.shop.shopdetails.been.Postage;
import com.mhl.shop.shopdetails.been.WantInfo;
import com.mhl.shop.shopdetails.widget.GoodsInfoConsultFragment;
import com.mhl.shop.shopdetails.widget.GoodsPopWindow;
import com.mhl.shop.shopdetails.widget.SlideDetailsLayout;
import com.mhl.shop.utils.Arith;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * item页ViewPager里的商品Fragment
 */
@SuppressLint("ValidFragment")
public class GoodsInfoFragment extends MyBaseRegistFragment implements View.OnClickListener,AdapterView.OnItemClickListener,GoodsPopWindow.onDissminssPopWindowClickListener, SlideDetailsLayout.OnSlideDetailsListener ,MeInterface.OnGoodAddressResultLister, MeInterface.OnMyGoodsListener {
    private PagerSlidingTabStrip psts_tabs;
    private SlideDetailsLayout sv_switch;
    private ScrollView sv_goods_info;
    private FloatingActionButton fab_up_slide;
    public ConvenientBanner vp_item_goods_img;
    private LinearLayout ll_goods_detail, ll_goods_config,ll_goods_three;
    private TextView tv_goods_detail, tv_goods_config;
    private View v_tab_cursor;
    private CircleImageView supplier_img;
    public FrameLayout fl_content,shengxinprice_framelayout;
    public LinearLayout iv_goods_fenxiang_layout,ll_current_goods, ll_pull_up,cuxiao_ly,iv_goods_collection_layout,collection_supplier_layout,join_supplier_layout;
    public TextView tv_goods_title, good_ship_txt,service_ship_txt,logistics_ship_txt,good_pl_content_txt,service_pl_content_txt,commentcount_text,
            praise_percent,comment_nickname,comment_datetime,comment_content,logistics_pl_content_txt,supplier_text_name,comment_number_value_text,
            cuxiao_tx,tv_new_price, tv_old_price,goods_tv_sheng, tv_current_goods, tv_fu_title,postage,city,goods_put_in,goods_buy_now;
    public Details details_data;//商品详情的返回数据
    public List<Comment.RowsBean> fisrt_comment;
    private int isGoodCollection = 0; // 判断商品是否收藏 1:已经收藏过了 2:未收藏
    private int isSCollection = 0; // 判断供应商是否收藏 1:已经收藏过了 2:未收藏
    private ImageView iv_goods_collection; // 收藏星星图标
    private TextView iv_goods_collection_text; // 收藏星星文字
    private LinearLayout mGoods_collection_supplier_layout; // 收藏供应商
    private LinearLayout all_good_pl_layout; // 评论区
    private ImageView mGoods_collection_start_img; // 收藏供应商按钮图片
    private TextView mGoods_collection_btn_text; // 收藏供应商按钮文字
    private RatingBar comment_ratingBar_star;
    private  List<String> list;//地区拆分之后集合
    private String cityname;//城市名字
    private String shopcount="1";//商品数量;
    private String shopguige;//商品规格;
    private String shopguigeid;//商品规格id;
    private  boolean isChoiseaddress = false;//是否去选择过地址
    private Button checkmoreCommentLay;
    private  String goodsId;//接收到的商品id
    @SuppressLint("ValidFragment")
    public GoodsInfoFragment(String goodsid, Details data, List<Comment.RowsBean> rows) {
        details_data=data;
        fisrt_comment= rows;
        goodsId=goodsid;
    }

    /** 当前商品详情数据页的索引分别是图文详情、规格参数 */
    private int nowIndex;
    private float fromX;
    public GoodsDetailWebFragment goodsConfigFragment;
    public GoodsInfoWebFragment goodsInfoWebFragment;
    public GoodsInfoConsultFragment goodsInfoConsultFragment;

    private Fragment nowFragment;
    private List<TextView> tabTextList;
    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    public GoodsDetailsActivity activity;
    private LayoutInflater inflater;
    private GoodsPopWindow mPopWindow; // 加入购物车的window
    private View rootView;
    private List<OldInfo.RowsBean> oldList = new ArrayList<>();
    // 一次构造数据
    private Set<NewInfo> newSet = new HashSet<>();
    // 一次构造数据转换
    private List<NewInfo> newList = new ArrayList<>();
    // 二次构造数据（过滤）
    private List<NewInfo> filterList = new ArrayList<>();
    // 最终数据
    private List<WantInfo> wantList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (GoodsDetailsActivity) context;
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        vp_item_goods_img.startTurning(4000);
        MobclickAgent.onResume(getActivity());

    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        vp_item_goods_img.stopTurning();
        MobclickAgent.onPause(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_goods_info, null);
        initView(rootView);
        MeInterface.setOnMyGoodsListener(this);

        initListener();
        initPostage("广东省","深圳市",goodsId);//获取运费
        initData();
        return rootView;
    }

    private void initListener() {
        fab_up_slide.setOnClickListener(this);
        ll_current_goods.setOnClickListener(this);
        ll_pull_up.setOnClickListener(this);
        city.setOnClickListener(this);
        ll_goods_detail.setOnClickListener(this);
        ll_goods_config.setOnClickListener(this);
        ll_goods_three.setOnClickListener(this);
        checkmoreCommentLay.setOnClickListener(this);
        join_supplier_layout.setOnClickListener(this);
        collection_supplier_layout.setOnClickListener(this);
        iv_goods_collection_layout.setOnClickListener(this);
        goods_put_in.setOnClickListener(this);
        goods_buy_now.setOnClickListener(this);
        iv_goods_fenxiang_layout.setOnClickListener(this);
        sv_switch.setOnSlideDetailsListener(this);
        mPopWindow = new GoodsPopWindow(getActivity());
        mPopWindow.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        mPopWindow.setOnDissmissClickListener(this);
    }

    private void initView(View rootView) {
        psts_tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.psts_tabs);
        fab_up_slide = (FloatingActionButton) rootView.findViewById(R.id.fab_up_slide);
        sv_switch = (SlideDetailsLayout) rootView.findViewById(R.id.sv_switch);
        sv_goods_info = (ScrollView) rootView.findViewById(R.id.sv_goods_info);
        v_tab_cursor = rootView.findViewById(R.id.v_tab_cursor);
        vp_item_goods_img = (ConvenientBanner) rootView.findViewById(R.id.vp_item_goods_img);
        fl_content = (FrameLayout) rootView.findViewById(R.id.fl_content);
        shengxinprice_framelayout = (FrameLayout) rootView.findViewById(R.id.shengxinprice_framelayout);
        all_good_pl_layout = (LinearLayout) rootView.findViewById(R.id.all_good_pl_layout);
        ll_current_goods = (LinearLayout) rootView.findViewById(R.id.ll_current_goods);
        ll_pull_up = (LinearLayout) rootView.findViewById(R.id.ll_pull_up);
        iv_goods_collection_layout = (LinearLayout) rootView.findViewById(R.id.iv_goods_collection_layout);
        ll_goods_detail = (LinearLayout) rootView.findViewById(R.id.ll_goods_detail);
        ll_goods_config = (LinearLayout) rootView.findViewById(R.id.ll_goods_config);
        ll_goods_three = (LinearLayout) rootView.findViewById(R.id.ll_goods_three);
        collection_supplier_layout = (LinearLayout) rootView.findViewById(R.id.collection_supplier_layout);
        cuxiao_ly = (LinearLayout) rootView.findViewById(R.id.cuxiao_ly);
        iv_goods_fenxiang_layout = (LinearLayout) rootView.findViewById(R.id.iv_goods_fenxiang_layout);
        join_supplier_layout= (LinearLayout) rootView.findViewById(R.id.join_supplier_layout);
        tv_goods_detail = (TextView) rootView.findViewById(R.id.tv_goods_detail);
        tv_goods_config = (TextView) rootView.findViewById(R.id.tv_goods_config);
        tv_goods_title = (TextView) rootView.findViewById(R.id.tv_goods_title);
        tv_fu_title = (TextView) rootView.findViewById(R.id.tv_fu_title);
        tv_new_price = (TextView) rootView.findViewById(R.id.tv_new_price);
        tv_old_price = (TextView) rootView.findViewById(R.id.tv_old_price);
        tv_current_goods = (TextView) rootView.findViewById(R.id.tv_current_goods);
        goods_tv_sheng = (TextView) rootView.findViewById(R.id.goods_tv_sheng);
        cuxiao_tx = (TextView) rootView.findViewById(R.id.cuxiao_tx);
        comment_number_value_text = (TextView) rootView.findViewById(R.id.comment_number_value_text);
        supplier_text_name = (TextView) rootView.findViewById(R.id.supplier_text_name);
        supplier_img= (CircleImageView) rootView.findViewById(R.id.supplier_img);
        checkmoreCommentLay= (Button) rootView.findViewById(R.id.checkmoreCommentLay);
        goods_put_in= (TextView) rootView.findViewById(R.id.goods_put_in);
        goods_buy_now= (TextView) rootView.findViewById(R.id.goods_buy_now);
        good_pl_content_txt = (TextView) rootView.findViewById(R.id.good_pl_content_txt);
        service_pl_content_txt = (TextView) rootView.findViewById(R.id.service_pl_content_txt);
        logistics_pl_content_txt = (TextView) rootView.findViewById(R.id.logistics_pl_content_txt);
        good_ship_txt= (TextView) rootView.findViewById(R.id.good_ship_txt);
        service_ship_txt= (TextView) rootView.findViewById(R.id.service_ship_txt);
        logistics_ship_txt= (TextView) rootView.findViewById(R.id.logistics_ship_txt);
        iv_goods_collection = (ImageView) rootView.findViewById(R.id.iv_goods_collection); // 收藏图标
        iv_goods_collection_text = (TextView) rootView.findViewById(R.id.iv_goods_collection_text); // 收藏文字
        mGoods_collection_supplier_layout = (LinearLayout) rootView.findViewById(R.id.collection_supplier_layout); // 收藏供应商
        mGoods_collection_start_img = (ImageView) rootView.findViewById(R.id.collection_start_img); // 收藏供应商按钮图片
        mGoods_collection_btn_text = (TextView)rootView. findViewById(R.id.collection_btn_text); // 收藏供应商按钮文字
        commentcount_text = (TextView)rootView. findViewById(R.id.commentcount_text); // 评论数量
        praise_percent = (TextView)rootView. findViewById(R.id.praise_percent); // 好评度
        comment_ratingBar_star= (RatingBar)rootView. findViewById(R.id.comment_ratingBar_star); // 好评度星星
        comment_nickname= (TextView)rootView. findViewById(R.id.comment_nickname); // 评价人
        comment_datetime= (TextView)rootView. findViewById(R.id.comment_datetime); // 评价时间
        comment_content= (TextView)rootView. findViewById(R.id.comment_content); // 评价内容
        city= (TextView)rootView. findViewById(R.id.city); // 地区
        postage= (TextView)rootView. findViewById(R.id.postage); // 运费
        setDetailData();
        setLoopView();

        //设置文字中间一条横线
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        fab_up_slide.hide();

        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
        vp_item_goods_img.setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.drawable.shape_item_index_red});
        vp_item_goods_img.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

    }
//绑定数据
    private void initData() {
        fragmentList = new ArrayList<>();
        tabTextList = new ArrayList<>();
        tabTextList.add(tv_goods_detail);
        tabTextList.add(tv_goods_config);
        mPopWindow = new GoodsPopWindow(getActivity());

        tv_goods_title.setText(details_data.getGoodsName());
        if(details_data.getGoodsTitle()==null ){
            tv_fu_title.setVisibility(View.GONE);
        }else {
            if(details_data.getGoodsTitle().equals("") ){
                tv_fu_title.setVisibility(View.GONE);
            }else {
                tv_fu_title.setText(details_data.getGoodsTitle());
                tv_fu_title.setVisibility(View.VISIBLE);
            }
        }
        tv_new_price.setText(ToolsUtils.Tow(details_data.getGoodsSpecDetail().getSpecPrice()+""));
        tv_old_price.setText("¥"+ToolsUtils.Tow(details_data.getGoodsSpecDetail().getMarketPrice()+""));
        if (Arith.sub(details_data.getGoodsSpecDetail().getMarketPrice(),details_data.getGoodsSpecDetail().getSpecPrice())>0){
        goods_tv_sheng.setText("¥"+Arith.sub(details_data.getGoodsSpecDetail().getMarketPrice(),details_data.getGoodsSpecDetail().getSpecPrice())+"");
        }else {
            shengxinprice_framelayout.setVisibility(View.GONE);
        }
        if (details_data.getMinimumAmount()==0&&details_data.getMinimumQuantity()==0){
            cuxiao_ly.setVisibility(View.GONE);
        }else {
            if(details_data.getMinimumAmount()>0){
                cuxiao_tx.setText("本店铺满"+details_data.getMinimumAmount()+""+"元包邮");
            }else if(details_data.getMinimumQuantity()>0){
                cuxiao_tx.setText("本店铺满"+details_data.getMinimumQuantity()+""+"件包邮");
            }
        }
        shopguigeid=details_data.getGoodsSpecDetail().getPkId();
        shopguige=ToolsUtils.listToString(ToolsUtils.match("_"+details_data.getGoodsSpecDetail().getSpecName()+"_"));
        tv_current_goods.setText(shopguige+","+shopcount+"个");
        supplier_text_name.setText(details_data.getSupplierName());
        comment_number_value_text.setText(details_data.getSupplierGoodsNum()+"");
        good_pl_content_txt.setText(details_data.getSupplierGoodsScore()+"");
        service_pl_content_txt.setText(details_data.getSupplierServiceScore()+"");
        logistics_pl_content_txt.setText(details_data.getLogisticsServiceScore()+"");

        Glide.with(this).load(Urls.URL_PHOTO+"/file/v3/download-"+details_data.getIdentificationId()+"-70-70.jpg").into(supplier_img);

        if(details_data.getSupplierGoodsScore()<4){
            good_ship_txt.setText("低");
        }else if(details_data.getSupplierGoodsScore()==4){
            good_ship_txt.setText("持平");
        }else {
            good_ship_txt.setText("高");
        }
        if(details_data.getSupplierServiceScore()<4){
            service_ship_txt.setText("低");
        }else if(details_data.getSupplierServiceScore()==4){
            service_ship_txt.setText("持平");
        }else {
            service_ship_txt.setText("高");
        }

        if(details_data.getLogisticsServiceScore()<4){
            logistics_ship_txt.setText("低");
        }else if(details_data.getLogisticsServiceScore()==4){
            logistics_ship_txt.setText("持平");
        }else {
            logistics_ship_txt.setText("高");
        }
        get_store_status(details_data.getSupplierIsCollect());
        get_shop_status(details_data.getGoodsIsCollect());

          //第一条评论的处理
        if(fisrt_comment.size()>0){
            commentcount_text.setText("("+details_data.getGoodsEvaluateNum()+""+"条）");
            long l=Math.round(details_data.getSatisfactionDegree()*1000);
            praise_percent.setText(((l/10)+"."+(l%10)+"%")+"");

            comment_ratingBar_star.setRating((float) fisrt_comment.get(0).getGoodsScore());
              if(fisrt_comment.get(0).getIsHiddenUsername()==1){
                  comment_nickname.setText("匿名买家");
              }else {
                  comment_nickname.setText(fisrt_comment.get(0).getUserIdName());
              }
            comment_datetime.setText(ToolsUtils.dateToStamp(fisrt_comment.get(0).getAddTime()));
            comment_content.setText(fisrt_comment.get(0).getUserEvalContent());
        }else {
            all_good_pl_layout.setVisibility(View.GONE);
        }
    }
    // 判断商品是否收藏的状态
    private void get_shop_status(int goodsIsCollect) {
        if (!MyApplication.getApplication().isLogin()) {
            iv_goods_collection.setBackgroundResource(R.drawable.collect_icon_normal);
            iv_goods_collection_text.setTextColor(getResources().getColor(R.color.main_text_three_color));
        }else{
            if (goodsIsCollect == 1) {
                isGoodCollection=1;
                iv_goods_collection.setBackgroundResource(R.drawable.collect_icon_pressed);
                iv_goods_collection_text.setTextColor(getResources().getColor(R.color.border_light_color));
            } else {
                isGoodCollection = 2;
                iv_goods_collection.setBackgroundResource(R.drawable.collect_icon_normal);
                iv_goods_collection_text.setTextColor(getResources().getColor(R.color.main_text_three_color));
            }
        }

    }

    //获取运费
    private void initPostage(String province,String citename ,String id) {
        OkGo.post(Urls.URL_POSTAGE_SHOP)//
                .tag(this)
                .params("province",province)
                .params("city",citename)
                .params("goodsId",id)
                .execute(new DialogCallback<LzyResponse<Postage>>(getActivity(),true) {
                             @Override
                             public void onSuccess(LzyResponse<Postage> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if(lzyResponse.data!=null) {
                                         if(lzyResponse.data.getStartPrice()>0){
                                             postage.setText("运费"+ lzyResponse.data.getStartPrice() + "");
                                         }else {
                                             postage.setText("包邮");
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
    // 判断供应商的状态
    private void get_store_status(int number) {
        if (!MyApplication.getApplication().isLogin()) {
            mGoods_collection_start_img.setBackgroundResource(R.drawable.collection_wdj);
            mGoods_collection_btn_text.setTextColor(getResources().getColor(R.color.main_text_tow_color));
            mGoods_collection_btn_text.setText(R.string.collection_supplier_text);
            mGoods_collection_supplier_layout.setBackgroundResource(R.drawable.layout_border);
        }else{
            if (number == 1) {
                mGoods_collection_start_img.setBackgroundResource(R.drawable.collection_dj);
                mGoods_collection_btn_text.setTextColor(getResources().getColor(R.color.border_light_color));
                mGoods_collection_btn_text.setText(R.string.collectioned_supplier_text); // 已经收藏供应商
                mGoods_collection_supplier_layout.setBackgroundResource(R.drawable.layout_border_choice);
                isSCollection=1;
            } else {
                mGoods_collection_start_img.setBackgroundResource(R.drawable.collection_wdj);
                mGoods_collection_btn_text.setTextColor(getResources().getColor(R.color.main_text_tow_color));
                mGoods_collection_supplier_layout.setBackgroundResource(R.drawable.linearlayout_select_down_up);
                mGoods_collection_btn_text.setText(R.string.collection_supplier_text);
                isSCollection=2;
            }
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.city://选择地区
//                startActivity(new Intent(getActivity(), ChooseAddressActivity.class));
                MeInterface.setOnGoodAddressResultLister(this);
                Intent spinIntent = new Intent(getActivity(), GoodsAddressActivity.class);
                spinIntent.putExtra("selectTag", "2"); // 两级联动
                startActivity(spinIntent);
                break;
            case R.id.iv_goods_collection_layout://收藏商品

                if (!MyApplication.getApplication().isLogin())
                {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(login);
                } else {  collectionShop(goodsId);}
                break;
            case R.id.collection_supplier_layout://收藏供应商
                if (!MyApplication.getApplication().isLogin())
                {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(login);
                } else {  collectionSupplier(details_data.getSupplierId());}

                break;
            case R.id.join_supplier_layout://进入供应商
                Intent intent1 = new Intent(getActivity(),SupplierActivity.class);
                intent1.putExtra("supplierId", details_data.getSupplierId());
                startActivity(intent1);
                break;
            case R.id.iv_goods_fenxiang_layout://分享
                Share share = new Share(Urls.URL_BASE_HTTP+"/file/v3/download-" + details_data.getGoodsPics().get(0).getPictureId() + "-150-150.jpg",
                        Urls.URL_SHARE+"/mall/good-detail-"+goodsId+".html",
                        details_data.getGoodsName(),details_data.getGoodsTitle(),getActivity());
                share.goShare();
                break;
            case R.id.checkmoreCommentLay://查看评价
                Intent intent = new Intent(getActivity(),ViewEvaluationActivity.class);
                intent.putExtra("goodsId", goodsId);
                intent.putExtra("url", Urls.URL_VIEW_EVALYATE);
                startActivity(intent);
                break;
            case R.id.goods_put_in://加入购物车
//                if (!MyApplication.getApplication().isLogin())
//                {
//                    Intent login = new Intent(getActivity(), LoginActivity.class);
//                    startActivity(login);
//                } else { add_cart(shopguigeid,shopcount);}
                goodsInfo(goodsId,v);
                break;
            case R.id.goods_buy_now://立即购买
//                           if (!MyApplication.getApplication().isLogin())
//                {
//                    Intent login = new Intent(getActivity(), LoginActivity.class);
//                    startActivity(login);
//                } else {
//                    goShop(shopguigeid);
//                }
                goodsInfo(goodsId,v);
                break;
            case R.id.ll_current_goods://规格选择
                 goodsInfo(goodsId,v);
                break;
            case R.id.ll_pull_up:
                //上拉查看图文详情
                sv_switch.smoothOpen(true);
                break;
            case R.id.fab_up_slide:
                //点击滑动到顶部
                sv_goods_info.smoothScrollTo(0, 0);
                sv_switch.smoothClose(true);
                break;

            case R.id.ll_goods_detail:
                //商品详情tab
                nowIndex = 0;
                scrollCursor();
                Log.d("goodsaa","0");
                switchFragment(nowFragment, goodsInfoWebFragment);
                nowFragment = goodsInfoWebFragment;
                break;

            case R.id.ll_goods_config:
                //规格参数tab
                nowIndex = 1;
                scrollCursor();
                Log.d("goodsaa","1");
                switchFragment(nowFragment, goodsConfigFragment);
                nowFragment = goodsConfigFragment;
                break;
            case R.id.ll_goods_three:
                //商品咨询
                nowIndex = 2;
                scrollCursor();
                Log.d("goodsaa","2");
                switchFragment(nowFragment, goodsInfoConsultFragment);
                nowFragment = goodsInfoConsultFragment;
                break;
            default:
                break;
        }
    }
    //立即购买
    private void goShop(String goodsId) {
        OkGo.post(Urls.URL_CART_BUY)//
                .tag(this)
                .params("goodsSpecId",goodsId)
                .params("goodsCount",shopcount)
                .execute(new StringDialogCallback(getActivity(), true) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Data body=(Data) GsonUtils.fromJson(s,
                                Data.class);
                        if(body.getCode()==200){
                            Intent intent = new Intent();
                            intent.putExtra("id",body.getData());
                            intent.setClass(getActivity(), SubmitOrderActivity.class);
                            startActivity(intent);
                        }else {
                            if(!TextUtils.isEmpty(body.getInfo())){
                                T.showShort(getActivity(),body.getInfo());

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

    //加入购物车
    private void add_cart(String pkId, String s1) {
        Log.d("cart","pkId="+pkId);
        Log.d("cart","s1="+s1);

        OkGo.post(Urls.URL_CART_ADD)//
                .tag(this)
                .params("goodsSpecId",pkId)
                .params("goodsCount",s1)
                .execute(new StringDialogCallback(getActivity(), true) {
                             @Override
                             public void onSuccess(String s, Call call, Response response) {
                                 Data body=(Data) GsonUtils.fromJson(s,
                                         Data.class);
                                 if(body.getCode()==200){
                                     T.showShort(getActivity(),"已添加至您的购物车");
                                 }else {
                                     T.showShort(getActivity(),body.getInfo());                                 }
                             }
                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 handleError(call, response, e);
                             }
                         }
                );

    }
    //获取规格详情
    private void goodsInfo(final String goods_id, final View v) {
        OkGo.post(Urls.URL_INFO_SHOP)
                .tag(this)
                .params("goodsId",goods_id)
                .execute(new DialogCallback<LzyResponse<OldInfo>>(getActivity(),true) {
                             @Override
                             public void onSuccess(LzyResponse<OldInfo> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                 if(oldList!=null) {
                                     oldList.clear();
                                 }
                                 if(newList!=null) {
                                     newList.clear();
                                 }
                                 if(filterList!=null) {
                                     filterList.clear();
                                 }
                                 if(newSet!=null) {
                                     newSet.clear();
                                 }
                                 if(wantList!=null) {
                                     wantList.clear();
                                 }
                                 if(lzyResponse.data!=null) {
                                     if (lzyResponse.code==200){
                                          oldList = lzyResponse.data.getRows();
                                         if (oldList.size() > 0) {
                                             for (int i = 0; i < oldList.size(); i++) {
                                                 if (!TextUtils.isEmpty(oldList.get(i).getSpecName())) {

                                                     if (oldList.get(i).getSpecName().contains("_")) {

                                                         String str1[] = oldList.get(i).getSpecName().split("_");
                                                         for (int j = 0; j < str1.length; j++) {
                                                             NewInfo newInfo = new NewInfo();
                                                             if (str1[j].toString().contains(":")) {
                                                                 String str2[] = str1[j].split(":");
                                                                 for (int k = 0; k < str2.length; k++) {
                                                                     newInfo.setKey(str2[0]);
                                                                     newInfo.setValue(str2[1]);
                                                                 }
                                                             }
                                                             newSet.add(newInfo);
                                                             Log.d("111111", "newSet===" + newSet.size());
                                                             Log.d("111111", "newSet===" + newSet);
                                                         }
                                                     }else {
                                                         NewInfo newInfo = new NewInfo();
                                                         String str3[] = oldList.get(i).getSpecName().split(":");
                                                         for (int k = 0; k < str3.length; k++) {
                                                             newInfo.setKey(str3[0]);
                                                             newInfo.setValue(str3[1]);
                                                         }
                                                         newSet.add(newInfo);
                                                         Log.d("111111", "newSet===" + newSet.size());
                                                         Log.d("111111", "newSet===" + newSet);
                                                     }
                                                 }
                                             }
                                         }

                                         newList.addAll(newSet);
                                         filterList.addAll(newList);

                                         for (int i = 0; i < filterList.size()-1; i++) {
                                             for (int j = filterList.size()-1; j > i; j--) {
                                                 if (filterList.get(i).getKey().equals(filterList.get(j).getKey())) {
//                                                     filterList.remove(newList.get(j));
                                                     filterList.remove(j);
                                                 }
                                             }
                                         }
                                         Log.d("111111", "filterList===" + filterList.size());
                                         Log.d("111111", "filterList===" + filterList);

                                         for (int i = 0; i < filterList.size(); i++) {
                                             WantInfo wantInfo = new WantInfo();
                                             wantInfo.setAttrName(filterList.get(i).getKey());
                                             wantList.add(wantInfo);
                                         }
                                         Log.d("111111", "wantList===" + wantList.size());
                                         Log.d("111111", "wantList===" + wantList);

                                         for (int i = 0; i < wantList.size(); i++) {
                                             String key = wantList.get(i).getAttrName();
                                             Set<String> valueList = new HashSet<>();
                                             for (int j = 0; j < newList.size(); j++) {
                                                 String newKey = newList.get(j).getKey();

                                                 if (key.equals(newKey)) {
                                                     valueList.add(newList.get(j).getValue());
                                                 }
                                             }
                                             wantList.get(i).setAttrList(valueList);
                                         }

                                         Log.d("111111", "wantList===" + wantList.size());
                                         Log.d("111111", "wantList===" + wantList);
                                         mPopWindow.showAsDropDown(v, "",details_data,goods_id,isGoodCollection,wantList,oldList); // 显示加入购物车


//                                          List<NewText> usableList1 = new ArrayList<>();
//                                          List<Spec> newCartList = new ArrayList<>();
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

                                     }else {


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
    //收藏供应商
    private void collectionSupplier(String supplierId) {
        String URI;
        if(isSCollection==1){
         URI=   Urls.URL_SUPPLIER_SHOP_CANCEL;
        }else {
         URI=   Urls.URL_SUPPLIER_SHOP;
        }
        OkGo.post(URI)
                .tag(this)
                .params("supplierId",supplierId)
                .execute(new DialogCallback<LzyResponse<Null>>(getActivity(),true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                                     if (lzyResponse.code==200){
                                         if(isSCollection==1){
                                             get_store_status(2);
                                             isSCollection=2;
                                         }else {
                                             get_store_status(1);
                                             isSCollection=1;
                                         }
                                     }else {

                                         if(isSCollection==1){
                                             T.showShort(getActivity(),"收藏失败");
                                         }else {
                                             T.showShort(getActivity(),"取消收藏失败");
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

    //收藏商品
    private void collectionShop(String goods_id) {
        String URI;
        if(isGoodCollection==1){
            URI=   Urls.URL_DELETE_COLLECTSHOP;
        }else {
            URI=   Urls.URL_COLLECTION_SHOP;
        }
        OkGo.post(URI)//
                .tag(this)
                .params("goodsId",goods_id)
                .execute(new DialogCallback<LzyResponse<Null>>(getActivity(),true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 handleResponse(lzyResponse.data, call, response);
                             if (lzyResponse.code==200){
                                 if(isGoodCollection==1){
                                     get_shop_status(2);
                                     isGoodCollection=2;
                                 }else {
                                     get_shop_status(1);
                                     isGoodCollection=1;
                                 }
                             }else {
                                 if(isGoodCollection==1){
                                     T.showShort(getActivity(),"收藏失败");
                                 }else {
                                     T.showShort(getActivity(),"取消收藏失败");
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

//    /**
//     * 选择地址回调
//     */
//    @Override
//    public void OnAddressResultRefresh(String address, String id)
//    {
//        isChoiseaddress=true;
//
//        list = Arrays.asList(address.split(" "));
//        if(list.size()>=2){
//            city.setText(address);
//                initPostage(list.get(0),list.get(1),goodsId);//获取运费
//
//
//        }
//
//
//    }
    /**
     * 给商品轮播图设置图片路径
     */
    public void setLoopView() {
        List<String> imgUrls = new ArrayList<>();
        if(details_data.getGoodsPics()!=null) {
            for (int j = 0; j < details_data.getGoodsPics().size(); j++) {
                imgUrls.add("http://api.51mhl.com/file/v3/download-" + details_data.getGoodsPics().get(j).getPictureId() + "-800-800.jpg");
//                imgUrls.add(Urls.URL_PHOTO+"/file/v3/download-" + details_data.getGoodsPics().get(j).getPictureId() + "-1000-800.jpg");

            }
            //初始化商品图片轮播
            vp_item_goods_img.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new NetworkImageHolderView();
                }
            }, imgUrls);
        }
    }
    /**
     * 加载完商品详情执行
     */
    public void setDetailData() {
        goodsInfoWebFragment = new GoodsInfoWebFragment(goodsId);
        goodsConfigFragment = new GoodsDetailWebFragment(goodsId);
        goodsInfoConsultFragment = new GoodsInfoConsultFragment(goodsId);

        fragmentList.add(goodsConfigFragment);
        fragmentList.add(goodsInfoWebFragment);
        fragmentList.add(goodsInfoConsultFragment);

        nowFragment = goodsInfoWebFragment;
        fragmentManager = getChildFragmentManager();
        //默认显示商品详情tab
        fragmentManager.beginTransaction().replace(R.id.fl_content, nowFragment).commitAllowingStateLoss();
    }


    @Override
    public void onStatucChanged(SlideDetailsLayout.Status status) {
        if (status == SlideDetailsLayout.Status.OPEN) {
            //当前为图文详情页
            fab_up_slide.show();
            activity.vpContent.setNoScroll(true);
            activity.tvTitle.setVisibility(View.VISIBLE);
            activity.pstsTabs.setVisibility(View.GONE);
        } else {
            //当前为商品详情页
            fab_up_slide.hide();
            activity.vpContent.setNoScroll(false);
            activity.tvTitle.setVisibility(View.GONE);
            activity.pstsTabs.setVisibility(View.VISIBLE);
    }}

    /**
     * 滑动游标
     */
    private void scrollCursor() {
        TranslateAnimation anim = new TranslateAnimation(fromX, nowIndex * v_tab_cursor.getWidth(), 0, 0);
        anim.setFillAfter(true);//设置动画结束时停在动画结束的位置
        anim.setDuration(50);
        //保存动画结束时游标的位置,作为下次滑动的起点
        fromX = nowIndex * v_tab_cursor.getWidth();
        v_tab_cursor.startAnimation(anim);

        //设置Tab切换颜色
        for (int i = 0; i < tabTextList.size(); i++) {
            tabTextList.get(i).setTextColor(i == nowIndex ? getResources().getColor(R.color.red) : getResources().getColor(R.color.black));
        }
    }

    /**
     * 切换Fragment
     * <p>(hide、show、add)
     * @param fromFragment
     * @param toFragment
     */
    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if (nowFragment != toFragment) {
            fragmentTransaction = fragmentManager.beginTransaction();
            if (!toFragment.isAdded()) {    // 先判断是否被add过
                fragmentTransaction.hide(fromFragment).add(R.id.fl_content, toFragment).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到activity中
            } else {
                fragmentTransaction.hide(fromFragment).show(toFragment).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }
    }


    @Override
    public void OnGoodAddressResultInfo(String goodAddress, String id) {
        isChoiseaddress=true;

        list = Arrays.asList(goodAddress.split(" "));
        if(list.size()>=2){
            city.setText(goodAddress);
            initPostage(list.get(0),list.get(1),goodsId);//获取运费


        }
    }

    @Override
    public void DissminssClickListener(String guige, String guigeid, String editCount) {
        shopguigeid=guigeid;
        shopcount=editCount;
        tv_current_goods.setText(guige+","+editCount+"个");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
   //刷新收藏.
    @Override
    public void OnMyGoodsRefresh(String s, int i) {
        get_shop_status(i);
//        if(i==1){
//            get_shop_status(2);
////            isGoodCollection=2;
//        }else {
//            get_shop_status(1);
////            isGoodCollection=1;
//        }
    }
}
