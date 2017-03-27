package com.mhl.shop.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.shopdetails.GoodsDetailsActivity;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;

import java.util.List;

/**
 * 作者：lff
 * 时间；2017-1-9 10:24
 * 描述：
 */
public class GalleryAdapter extends
        RecyclerView.Adapter<GalleryAdapter.ViewHolder>
{
    private LayoutInflater mInflater;
    private List<Home.LangBean> mDatas;
    private final Context context;
    public GalleryAdapter(Context context, List<Home.LangBean> datats)
    {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }

        ImageView  icon;
        TextView shoppingPrice;
        TextView marketPrice;
        LinearLayout item;
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
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.home_grid_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
        viewHolder.marketPrice = (TextView) view.findViewById(R.id.marketPrice);
        viewHolder.shoppingPrice = (TextView) view.findViewById(R.id.shoppingPrice);
        viewHolder.item = (LinearLayout) view.findViewById(R.id.item);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)
    {
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v4/download-"+mDatas.get(i).getPicId()+"-300-300.jpg").placeholder(R.drawable.icon_bg).into(viewHolder.icon);
        viewHolder.marketPrice.setText("¥"+ ToolsUtils.Tow(mDatas.get(i).getMarketPrice()));
        viewHolder.shoppingPrice.setText("¥"+ ToolsUtils.Tow(mDatas.get(i).getShoppingPrice()));
        viewHolder.marketPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("goodsid", mDatas.get(i).getPkId());
                intent.setClass(context, GoodsDetailsActivity.class);
                context.startActivity(intent);
            }
        });
}
}