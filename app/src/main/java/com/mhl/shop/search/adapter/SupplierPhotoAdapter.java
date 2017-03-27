package com.mhl.shop.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.search.been.SupplierMain;
import com.mhl.shop.shopdetails.GoodsDetailsActivity;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;

import java.util.List;

/**
 * 作者：lff
 * 时间；2017-1-9 11:54
 * 描述：、
 */
public class SupplierPhotoAdapter extends
        RecyclerView.Adapter<SupplierPhotoAdapter.ViewHolder>
{
    private LayoutInflater mInflater;
    private List<SupplierMain.GoodsListBean>  mDatas;
    private final Context context;
    private String pkId;
    public SupplierPhotoAdapter(Context context,  List<SupplierMain.GoodsListBean> goodsList)
    {
        mInflater = LayoutInflater.from(context);
        mDatas = goodsList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }

        ImageView icon;
        TextView money;
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public SupplierPhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.supplier_search_item,
                viewGroup, false);
        SupplierPhotoAdapter.ViewHolder viewHolder = new SupplierPhotoAdapter.ViewHolder(view);

        viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
        viewHolder.money = (TextView) view.findViewById(R.id.money);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final SupplierPhotoAdapter.ViewHolder viewHolder, final int i)
    {
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v4/download-"+mDatas.get(i).getPicId()+"-100-100.jpg").placeholder(R.drawable.icon_bg).into(viewHolder.icon);
        viewHolder.money.setText("¥ "+ ToolsUtils.Tow(mDatas.get(i).getShoppingPrice()+""));
        viewHolder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent1 = new Intent(context, GoodsDetailsActivity.class);
                intent1.putExtra("goodsid", mDatas.get(i).getPkId());
                context.startActivity(intent1);
            }
        });
    }
}