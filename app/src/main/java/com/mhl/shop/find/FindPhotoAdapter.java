package com.mhl.shop.find;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.utils.Urls;

import java.util.List;

/**
 * 作者：lff
 * 时间；2017-1-9 11:54
 * 描述：、
 */
public class FindPhotoAdapter extends
        RecyclerView.Adapter<FindPhotoAdapter.ViewHolder>
{
    private LayoutInflater mInflater;
    private List<String>  mDatas;
    private final Context context;
    private String pkId;
    private String sicon;
    private String title;
    public FindPhotoAdapter(Context context, List<String> datats, String pkId, String icon, String title)
    {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
        this.context = context;
        this.pkId = pkId;
        this.sicon = icon;
        this.title = title;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }

        ImageView icon;
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
    public FindPhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.grid_item,
                viewGroup, false);
        FindPhotoAdapter.ViewHolder viewHolder = new FindPhotoAdapter.ViewHolder(view);

        viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final FindPhotoAdapter.ViewHolder viewHolder, final int i)
    {
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+mDatas.get(i)+"-200-200.jpg").placeholder(R.drawable.icon_bg).into(viewHolder.icon);
        viewHolder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.putExtra("pkId",pkId);
                intent.putExtra("title", title);
                intent.putExtra("icon", sicon);
                intent.setClass(context, FindDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }
}