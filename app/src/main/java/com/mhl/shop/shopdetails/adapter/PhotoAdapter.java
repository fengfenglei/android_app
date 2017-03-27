package com.mhl.shop.shopdetails.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.main.ViewPagerActivity;
import com.mhl.shop.utils.Urls;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：lff
 * 时间；2017-1-9 11:54
 * 描述：图片浏览
 */
public class PhotoAdapter extends
        RecyclerView.Adapter<PhotoAdapter.ViewHolder>
{
    private LayoutInflater mInflater;
    private List<String>  mDatas;
    private final Context context;
    public PhotoAdapter(Context context, List<String>  datats)
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
    public PhotoAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.grid_item,
                viewGroup, false);
        PhotoAdapter.ViewHolder viewHolder = new PhotoAdapter.ViewHolder(view);

        viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final PhotoAdapter.ViewHolder viewHolder, final int i)
    {
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v4/download-"+mDatas.get(i)+"-300-300.jpg").into(viewHolder.icon);

        viewHolder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // 跳转到查看大图界面
                ArrayList<String> ny=new ArrayList<>();
                for (int i = 0; i < mDatas.size(); i++) {
                    ny.add(mDatas.get(i));
                }

                Intent intent = new Intent(context, ViewPagerActivity.class);
                intent.putExtra("position", i);
                intent.putStringArrayListExtra("mainImg", ny);

                context.startActivity(intent);
//                context.overridePendingTransition(R.anim.activity_zoomin, .anim.activity_zoomout);

            }
        });
    }
}