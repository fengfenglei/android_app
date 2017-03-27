package com.mhl.shop.shopdetails.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.mhl.shop.R;
import com.mhl.shop.callback.DialogCallback;
import com.mhl.shop.callback.StringDialogCallback;
import com.mhl.shop.login.LoginActivity;
import com.mhl.shop.login.LzyResponse;
import com.mhl.shop.login.been.Null;
import com.mhl.shop.main.Data;
import com.mhl.shop.main.MyApplication;
import com.mhl.shop.me.MeInterface;
import com.mhl.shop.shopdetails.SubmitOrderActivity;
import com.mhl.shop.shopdetails.adapter.ShopInfoAdapter;
import com.mhl.shop.shopdetails.been.Details;
import com.mhl.shop.shopdetails.been.OldInfo;
import com.mhl.shop.shopdetails.been.WantInfo;
import com.mhl.shop.utils.GsonUtils;
import com.mhl.shop.utils.T;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者：lff
 * 时间；2016-12-9 18:11
 * 描述：规格数量的选择
 */
public class GoodsPopWindow extends PopupWindow implements PopupWindow.OnDismissListener,View.OnClickListener, ShopInfoAdapter.OnItemShopClickListener {
    private PopupWindow			popupWindow;
    private Context			context;
    private RecyclerView			shopping_item;
    private TextView			pop_add_in_cart,kucun,money,pop_lijigoumai,goods_tv_price_figure,pop_reduce,pop_add;
    private ImageView iv_adapter_grid_pic,pop_del;
    private ImageView iv_goods_collection; // 收藏星星图标
    private TextView iv_goods_collection_text; // 收藏星星文字
    private int isGoodCollection = 0; // 判断商品是否收藏 1:已经收藏过了 2:未收藏
    List<String> idlist=new ArrayList<>();//更新规格的集合
    List<String> list=new ArrayList<>();//规格分类的集合
    List<String> wantidlist=new ArrayList<>();//最后的集合
    List<OldInfo.RowsBean> oldlist=new ArrayList<>();//最开始后台返回的集合
    public LinearLayout iv_goods_collection_layout;
    private ShopInfoAdapter mShopinfoAdapter;
    private List<WantInfo> want_List;
    private String goodsid;
    private String pkId,guige;
    private String specNum;
    private String specPrice;
    private EditText pop_edit;
    private AdapterView.OnItemClickListener listener;



    public static interface onDissminssPopWindowClickListener {
        public void DissminssClickListener(String guige,String guigeid,String editCount);
    }

    private static onDissminssPopWindowClickListener m_listener = null;
    public GoodsPopWindow(Context mContext) {
        super(mContext,null);
        this.context = mContext;
        final View view= LayoutInflater.from(context).inflate(R.layout.adapter_popwindow, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置popwindow的动画效果
        popupWindow.setAnimationStyle(R.style.popWindow_anim_style);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOnDismissListener(this);//当popWindow消失时的监听

        ColorDrawable dw = new ColorDrawable(0x88000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);



        money = (TextView) view.findViewById(R.id.money);
        kucun = (TextView) view.findViewById(R.id.kucun);
        pop_del= (ImageView) view.findViewById(R.id.pop_del);
        iv_adapter_grid_pic  = (ImageView) view.findViewById(R.id.iv_adapter_grid_pic);
        iv_goods_collection = (ImageView) view.findViewById(R.id.goods_iv_collection_icon); // 收藏图标
        iv_goods_collection_text = (TextView) view.findViewById(R.id.goods_iv_collection_icon_text); // 收藏文字
        iv_goods_collection_layout = (LinearLayout) view.findViewById(R.id.goods_iv_collection_lay);
        pop_add_in_cart = (TextView) view.findViewById(R.id.pop_add_in_cart);
        pop_add = (TextView) view.findViewById(R.id.pop_add);
        pop_reduce = (TextView) view.findViewById(R.id.pop_reduce);
        pop_lijigoumai = (TextView) view.findViewById(R.id.pop_lijigoumai);
        goods_tv_price_figure= (TextView) view.findViewById(R.id.goods_tv_price_figure); // 已选
        pop_edit = (EditText) view.findViewById(R.id.pop_edit);
        shopping_item  = (RecyclerView) view.findViewById(R.id.shopping_item);
        pop_add_in_cart.setOnClickListener(this);
        iv_goods_collection_layout.setOnClickListener(this);
        pop_add.setOnClickListener(this);
        pop_reduce.setOnClickListener(this);
        pop_lijigoumai.setOnClickListener(this);
        pop_del.setOnClickListener(this);
//        pop_edit.addTextChangedListener(new EditChangedListener());
    }



    /**弹窗显示的位置*/
    public void showAsDropDown(View parent, String s, Details details_data, String goods_id, int isGoodsCollection, List<WantInfo> wantList, List<OldInfo.RowsBean> oldList){
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);   //设置popwindow显示位置
        popupWindow.setFocusable(true);  //获取popwindow焦点
        popupWindow.setOutsideTouchable(true);  //设置popwindow如果点击外面区域，便关闭
        popupWindow.update();
        goodsid= goods_id;
        oldlist= oldList;
        isGoodCollection= isGoodsCollection;
        want_List=wantList;
        pkId=details_data.getGoodsSpecDetail().getPkId();
        money.setText("¥"+details_data.getGoodsSpecDetail().getMarketPrice()+"");
        if( details_data.getGoodsPics().size()>0) {
            Glide.with(context).load(Urls.URL_PHOTO + "/file/v3/download-" + details_data.getGoodsPics().get(0).getPictureId() + "-200-200.jpg").placeholder(R.drawable.icon_bg).into(iv_adapter_grid_pic);
        }
        get_shop_status(isGoodCollection);
        if(list!=null){
            list.clear();
        }
        if(idlist!=null){
            idlist.clear();
        }
        if(wantidlist!=null){
            wantidlist.clear();
        }
        //规格显示
        LinearLayoutManager layoutManager = new      LinearLayoutManager(context);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        shopping_item.setLayoutManager(layoutManager);
        shopping_item.setHasFixedSize(true);
        shopping_item.setNestedScrollingEnabled(false);
        mShopinfoAdapter = new ShopInfoAdapter(context,want_List,  this);
        shopping_item.setAdapter(mShopinfoAdapter);
    }
//    class EditChangedListener implements TextWatcher {
//        private CharSequence temp;//监听前的文本
//        private int editStart;//光标开始位置
//        private int editEnd;//光标结束位置
//        private final int charMaxNum = 10;
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            temp = s;
//            if(Integer.parseInt(s.toString())==1){
//                pop_edit.setEnabled(false);
//            }else {
//                pop_edit.setEnabled(true);
//            }
//        }
//
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//
//        }
//
//
//    };
    private void get_shop_status(int goodsIsCollect) {
        if (goodsIsCollect == 1) {
            isGoodCollection=1;
            iv_goods_collection.setBackgroundResource(R.drawable.collect_icon_pressed);
            iv_goods_collection_text.setTextColor(context.getResources().getColor(R.color.border_light_color));
        } else {
            isGoodCollection = 2;
            iv_goods_collection.setBackgroundResource(R.drawable.collect_icon_normal);
            iv_goods_collection_text.setTextColor(context.getResources().getColor(R.color.main_text_three_color));
        }
    }

    /** 设置监听 */
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener)
    {
        this.listener = listener;
    }

    public void setOnDissmissClickListener(onDissminssPopWindowClickListener listener){
        this.m_listener  = listener;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.pop_add:  //加号
                if(Integer.parseInt(specNum)==0){
                    T.showShort(context,"库存不足");
                    return;
                }
                if(Integer.parseInt(pop_edit.getText().toString())>=Integer.parseInt(specNum)){
                    T.showShort(context,"库存不足");
                    return;
                }
                pop_edit.setText( (Integer.parseInt(pop_edit.getText().toString())+1)+"" );
                break;
            case R.id.pop_reduce:  //减
                if(Integer.parseInt(specNum)==0){
                    T.showShort(context,"库存不足");
                    return;
                }
                if( Integer.parseInt(pop_edit.getText().toString())==1){
                    T.showShort(context,"最少添加一件");
                    return;
                }
               pop_edit.setText( (Integer.parseInt(pop_edit.getText().toString())-1)+"" );
                break;
            case R.id.pop_del:
                dissmiss();
                break;
            case R.id.goods_iv_collection_lay:  //点击收藏
                if (!MyApplication.getApplication().isLogin())
                {
                    Intent login = new Intent(context, LoginActivity.class);
                    context.startActivity(login);
                } else { collectionShop(goodsid);}

                break;
            case R.id.pop_add_in_cart:   //点击加入购物车

                if (!MyApplication.getApplication().isLogin())
                {
                    Intent login = new Intent(context, LoginActivity.class);
                    context.startActivity(login);
                } else {   add_cart(pkId,pop_edit.getText().toString());}
                break;
            case R.id.pop_lijigoumai:  //立即购买

                if (!MyApplication.getApplication().isLogin())
                {
                    Intent login = new Intent(context, LoginActivity.class);
                    context.startActivity(login);
                } else { goShop(pkId,pop_edit.getText().toString());}
                break;
            default:
                break;
        }
    }
    //立即购买
    private void goShop(String pkId, String num) {
        if(TextUtils.isEmpty(num)){
            T.showShort(context,"请填写购买件数");
            return;
        }else if(Integer.parseInt(num)<=0){
            T.showShort(context,"请填写购买件数");
            return;
        }
        OkGo.post(Urls.URL_CART_BUY)
                .tag(this)
                .params("goodsSpecId",pkId)
                .params("goodsCount",num)
                .execute(new StringDialogCallback((Activity) context, true) {
                             @Override
                             public void onSuccess(String s, Call call, Response response) {
                                 Data body=(Data) GsonUtils.fromJson(s,
                                         Data.class);
                                 if(body.getCode()==200){
                                     Intent intent = new Intent();
                                     intent.putExtra("id",body.getData());
                                     intent.setClass(context, SubmitOrderActivity.class);
                                    context.startActivity(intent);
                                 }else {
                                     if(!TextUtils.isEmpty(body.getInfo())){
                                         T.showShort(context,body.getInfo());
                                     }
                                 }
                             }
                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 T.showShort(context,"立即购买失败");
                             }
                         }
                );

    }
    private void add_cart(String pkId, String s) {
        if(TextUtils.isEmpty(s)){
            T.showShort(context,"请填写购买件数");
            return;
        }else if(Integer.parseInt(s)<=0){
            T.showShort(context,"请填写购买件数");
            return;
        }
        OkGo.post(Urls.URL_CART_ADD)//
                .tag(this)
                .params("goodsSpecId",pkId)
                .params("goodsCount",s)
                .execute(new StringDialogCallback((Activity) context, true) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Data body=(Data) GsonUtils.fromJson(s,
                                Data.class);
                        if(body.getCode()==200){
                            T.showShort(context,"已添加至您的购物车");
                        }else {
                            T.showShort(context,body.getInfo());
                            if(!TextUtils.isEmpty(body.getInfo())){
                                T.showShort(context,body.getInfo());
                            }
                        }
                    }
                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 T.showShort(context,"添加失败");
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
                .execute(new DialogCallback<LzyResponse<Null>>((Activity) context,true) {
                             @Override
                             public void onSuccess(LzyResponse<Null> lzyResponse, Call call, Response response) {
                                 if (lzyResponse.code==200){
                                     if(isGoodCollection==1){
                                         get_shop_status(2);
                                         isGoodCollection=2;
                                     }else {
                                         get_shop_status(1);
                                         isGoodCollection=1;
                                     }
                                     //刷新
                                     if (MeInterface.onMyGoodsListener!=null) {
                                         MeInterface.onMyGoodsListener.OnMyGoodsRefresh("",isGoodCollection);
                                     }
                                 }else {
                                     if(isGoodCollection==1){
                                         T.showShort(context,"收藏失败");
                                     }else {
                                         T.showShort(context,"取消收藏失败");
                                     }
                                 }
                             }
                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                             }
                         }
                );

    }
    @Override
    public void onDismiss() {
        String editCount = pop_edit.getText().toString().trim();  //购买数量
        if (editCount.equals("")) {
            pop_edit.setText("1");
        }
        getDissMissResule();
    }	/**消除弹窗*/
    public void dissmiss(){
        String editCount = pop_edit.getText().toString().trim();  //购买数量
        if (editCount.equals("")) {
            pop_edit.setText("1");
        }
        if(popupWindow != null && popupWindow.isShowing()){
            popupWindow.dismiss();
			getDissMissResule();
        }
    }


    public void getDissMissResule(){
        if(m_listener != null){
            String guigeid = pkId;
            String editCount = pop_edit.getText().toString().trim();  //购买数量
            if (editCount.equals("")) {
                editCount = "1";
            }
            if (!editCount.equals("")) {
                m_listener.DissminssClickListener(guige,guigeid,editCount);
            }
        }
    }
    @Override
    public void onItemShopClick(boolean b, int position, String s, String text) {

        if(b){
            idlist.add(position,s);
            list.add(text);
        }else {
            idlist.set(position,s);
            //替换规格
            for(int i = 0 ;i<idlist.size();i++) {
                String j= idlist.get(i);
                String j2= list.get(i);
                wantidlist.set(i,j2+":"+j);
            }
        }
        //显示的已选规格
        String strf = "";
        for(int i = 0 ;i<idlist.size();i++) {
            String j= idlist.get(i);
            strf+=" “"+j+"” ";
        }
        //添加规格
        for(int i = 0 ;i<idlist.size();i++) {
            String j= idlist.get(i);
            String j2= list.get(i);
            wantidlist.add(j2+":"+j);
        }
        //去掉重复的规格名
        for  ( int  i  =   0 ; i  <  wantidlist.size()  -   1 ; i ++ )   {
            for  ( int  j  =  wantidlist.size()  -   1 ; j  >  i; j -- )   {
                if  (wantidlist.get(j).equals(wantidlist.get(i)))   {
                    wantidlist.remove(j);
                }
            }
        }
//        StringBuilder sb = new StringBuilder();//吧规格和规格名拼装在一起去 查询介个库存
//        if(wantidlist != null && wantidlist.size()>0){
//            for(int i=0,len=wantidlist.size();i<len;i++){
//                sb.append( wantidlist.get(i));
//                if(i < len-1){
//                    sb.append("_");
//                }
//            }
//        }

        for(int i = 0 ;i<oldlist.size();i++) {

            String abc = oldlist.get(i).getSpecName();
            String[] array = abc.split("_");
            List<String> abcList = new ArrayList<>();
            for (String str : array)
            {
                abcList.add(str);
            }
        if(compare(abcList, wantidlist)){
            pkId=oldlist.get(i).getPkId();
            specNum=oldlist.get(i).getSpecNum()+"";
            specPrice=oldlist.get(i).getSpecPrice()+"";
        }
        }
        if(!TextUtils.isEmpty(specNum)){
            if(Integer.parseInt(specNum)>0){
            kucun.setText("库存"+specNum+"件");}else{
                kucun.setText("库存0件");
            }
        }
        if(!TextUtils.isEmpty(specPrice)){
            money.setText("¥"+specPrice);
        }
        Log.d("222", "pkId===" + pkId);
        Log.d("222", "specNum===" + specNum);
        Log.d("222", "specPrice===" + specPrice);
//        Log.d("222", "sb===" + sb);
        Log.d("222", "wantidlist===" + wantidlist);
        Log.d("222", "idlistadd===" + idlist);
        Log.d("222", "listadd===" + list);
         guige=ToolsUtils.listToString(idlist);
        goods_tv_price_figure.setText(strf);

    }
    /**
     * 队列比较
     * @param <T>
     * @param a
     * @param b
     * @return
     */
    public static <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
        if(a.size() != b.size())
            return false;
        Collections.sort(a);
        Collections.sort(b);
        for(int i=0;i<a.size();i++){
            if(!a.get(i).equals(b.get(i)))
                return false;
        }
        return true;
    }
}
