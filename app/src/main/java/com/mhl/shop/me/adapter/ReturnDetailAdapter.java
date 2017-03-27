package com.mhl.shop.me.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mhl.shop.R;
import com.mhl.shop.me.been.ReturnDetail;
import com.mhl.shop.shopdetails.adapter.GridViewAdapter;
import com.mhl.shop.utils.ToolsUtils;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.mhl.shop.utils.UIUtils.getResources;

/**
 * 作者：lff
 * 时间；2016-10-13 14:46
 * 描述：退款详情
 */
public class ReturnDetailAdapter extends BaseAdapter {
    private Context context;
    private List<ReturnDetail.ReturnLogListBean> list;
    private ReturnDetail data;
    public ReturnDetailAdapter(Context context, List<ReturnDetail.ReturnLogListBean> list, ReturnDetail data) {
        super();
        this.context = context;
        this.list = list;
        this.data = data;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_return_detail, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
       holder.time.setText(ToolsUtils.dateToStampLit(list.get(position).getAddTime()));

        if (TextUtils.isEmpty(list.get(position).getOperationInfo())|| null==list.get(position).getOperationInfo()){
            holder.state.setVisibility(View.GONE);
        }else {
            holder.state.setVisibility(View.VISIBLE);
            holder.state.setText(list.get(position).getOperationInfo());
        }
        if (TextUtils.isEmpty(list.get(position).getReturnExplain())|| null==list.get(position).getReturnExplain()){
            holder.info.setVisibility(View.GONE);
        }else {
            holder.info.setVisibility(View.VISIBLE);
            holder.info.setText(list.get(position).getReturnExplain());
        }
        if (TextUtils.isEmpty(list.get(position).getExpressNo())|| null==list.get(position).getExpressNo()){
            holder.logisticsLy.setVisibility(View.GONE);
        }else {
            holder.logisticsLy.setVisibility(View.VISIBLE);
            holder.logisticsName.setText(list.get(position).getExpressCompany());
            holder.logisticsNumber.setText(list.get(position).getExpressNo());
        }
        if (TextUtils.isEmpty(list.get(position).getConsignee())|| null==list.get(position).getConsignee()){
            holder.userLy.setVisibility(View.GONE);
        }else {
            holder.userLy.setVisibility(View.VISIBLE);
            holder.userName.setText(list.get(position).getConsignee()+"  "+list.get(position).getConsigneeTel());
            holder.userAddress.setText(list.get(position).getAddressIdName());
            holder.userZip.setText(list.get(position).getAddressIdZip());
        }
        if(list.get(position).getReturnStatus()==62 || list.get(position).getReturnStatus()==61){
            holder.moneyLy.setVisibility(View.VISIBLE);
            holder.allMoney.setText("¥ "+ToolsUtils.Tow(data.getReturnPayMoney()+""));
//            holder.holdMoney.setText((int)(data.getGoldMoney()*100)+"个");
            holder.jihuoMoney.setText("¥ "+ToolsUtils.Tow(data.getActivationMoney()+""));
            holder.ketixianMoney.setText("¥ "+ToolsUtils.Tow(data.getDepositMoney()+""));
            if (data.getPayType()==1){
                holder.pay_way_ly.setVisibility(View.VISIBLE);
                holder.payMoney.setText("¥ "+ToolsUtils.Tow(data.getPayMoney()+""));
                holder.payWay.setText("微信支付");
            }else if (data.getPayType()==2){
                holder.pay_way_ly.setVisibility(View.VISIBLE);
                holder.payMoney.setText("¥ "+ToolsUtils.Tow(data.getPayMoney()+""));
                holder.payWay.setText("支付宝支付");
            }else if (data.getPayType()==3){
                holder.pay_way_ly.setVisibility(View.VISIBLE);
                holder.payMoney.setText("¥ "+ToolsUtils.Tow(data.getPayMoney()+""));
                holder.payWay.setText("农行支付");
            }else if (data.getPayType()==4){
                holder.pay_way_ly.setVisibility(View.VISIBLE);
                holder.payMoney.setText("¥ "+ToolsUtils.Tow(data.getPayMoney()+""));
                holder.payWay.setText("快钱支付");
            }else if (data.getPayType()==5){
                holder.pay_way_ly.setVisibility(View.VISIBLE);
                holder.payMoney.setText("¥ "+ToolsUtils.Tow(data.getPayMoney()+""));
                holder.payWay.setText("农行对公支付");
            }else {
                holder.pay_way_ly.setVisibility(View.GONE);
            }

        }else{
            holder.moneyLy.setVisibility(View.GONE);
        }
        if(TextUtils.isEmpty(list.get(position).getReturnPic())|| null==list.get(position).getReturnPic()){
             holder.picHs.setVisibility(View.GONE);
        }else {
            holder.picHs.setVisibility(View.VISIBLE);
            List<String> listGrid  = Arrays.asList(list.get(position).getReturnPic().split(","));
            int size = listGrid.size();
            DisplayMetrics dm = new DisplayMetrics();
            dm = getResources().getDisplayMetrics();
            float density  = dm.density;
            int allWidth = (int) (110 * size * density);
            int itemWidth = (int) (80 * density);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    allWidth, LinearLayout.LayoutParams.FILL_PARENT);
            holder.gridview.setLayoutParams(params);
            holder.gridview.setColumnWidth(itemWidth);
            holder.gridview.setHorizontalSpacing(10);
            holder.gridview.setStretchMode(GridView.NO_STRETCH);
            holder.gridview.setNumColumns(size);
            GridViewAdapter adapter = new GridViewAdapter(context,listGrid);
            holder.gridview.setAdapter(adapter);

        }
        return convertView;
    }


    public static class ViewHolder {
        @Bind(R.id.img)
        ImageView img;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.state)
        TextView state;
        @Bind(R.id.info)
        TextView info;
        @Bind(R.id.logistics_name)
        TextView logisticsName;
        @Bind(R.id.logistics_number)
        TextView logisticsNumber;
        @Bind(R.id.logistics_ly)
        LinearLayout logisticsLy;
        @Bind(R.id.user_name)
        TextView userName;
        @Bind(R.id.user_address)
        TextView userAddress;
        @Bind(R.id.user_zip)
        TextView userZip;
        @Bind(R.id.user_ly)
        LinearLayout userLy;
        @Bind(R.id.all_money)
        TextView allMoney;
        @Bind(R.id.hold_money)
        TextView holdMoney;
        @Bind(R.id.jihuo_money)
        TextView jihuoMoney;
        @Bind(R.id.ketixian_money)
        TextView ketixianMoney;
        @Bind(R.id.pay_way)
        TextView payWay;
        @Bind(R.id.pay_money)
        TextView payMoney;
        @Bind(R.id.money_ly)
        LinearLayout moneyLy;
        @Bind(R.id.gridview)
        GridView gridview;
        @Bind(R.id.pic_hs)
        HorizontalScrollView picHs;
        @Bind(R.id.pay_way_ly)
        LinearLayout pay_way_ly;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }



    }
}

