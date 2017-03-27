package com.mhl.shop.shopdetails.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mhl.shop.R;
import com.mhl.shop.main.MyBaseAdpter;
import com.mhl.shop.me.been.Evaluation;
import com.mhl.shop.utils.ToolsUtils;
import com.mhl.shop.utils.Urls;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者：lff
 * 时间；2016-11-17 18:04
 * 描述：评价列表
 */
public class EvaluationAdapter extends MyBaseAdpter {

    private final Context context;
    private final List<Evaluation.RowsBean> alist;

    public EvaluationAdapter(Context context, List<Evaluation.RowsBean> list) {
        super();
        this.context = context;
        this.alist = list;
    }




    public int getCount() {
        return alist.size();
    }

    public Object getItem(int position) {
        return alist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.shop_evaluation_item, null);
            holder = new ViewHolder();
            holder.icon = (CircleImageView) convertView.findViewById(R.id.icon);
            holder.phone = (TextView) convertView.findViewById(R.id.phone);
            holder.comment_ratingBar_star = (RatingBar) convertView.findViewById(R.id.comment_ratingBar_star);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.comment = (TextView) convertView.findViewById(R.id.comment);
            holder.shop_info = (TextView) convertView.findViewById(R.id.shop_info);
            holder.reply = (TextView) convertView.findViewById(R.id.reply);
            //得到控件
            holder. mRecyclerView = (RecyclerView) convertView.findViewById(R.id.id_recyclerview_horizontal);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(Urls.URL_PHOTO+"/file/v3/download-"+alist.get(position).getUserIdPic()+"-150-150.jpg").into(holder.icon);
       if(alist.get(position).getIsHiddenUsername()==1){
           holder.phone.setText("匿名用户");
       }else if(alist.get(position).getIsHiddenUsername()==2){
           holder.phone.setText(alist.get(position).getUserIdName());
       }
        if(TextUtils.isEmpty(alist.get(position).getUserEvalContent())){
            holder.comment.setVisibility(View.GONE);
        }else{
            holder.comment.setText(alist.get(position).getUserEvalContent());
            holder.comment.setVisibility(View.VISIBLE);
        }
        if(TextUtils.isEmpty(alist.get(position).getGoodsSpec())){
            holder.shop_info.setVisibility(View.GONE);
        }else{
            holder.shop_info.setText(alist.get(position).getGoodsSpec());
            holder.shop_info.setVisibility(View.VISIBLE);
        }
        if(TextUtils.isEmpty(alist.get(position).getStoreEvalContent())){
            holder.reply.setVisibility(View.GONE);
        }else{
            holder.reply.setText("【供应商回复】 : "+alist.get(position).getStoreEvalContent());
            holder.reply.setVisibility(View.VISIBLE);
        }
        holder.comment_ratingBar_star.setRating(alist.get(position).getGoodsScore());
        holder.time.setText(ToolsUtils.dateToStamp(alist.get(position).getAddTime()));
if(alist.get(position).getEvalPicture()==null || TextUtils.isEmpty(alist.get(position).getEvalPicture())){
    holder.mRecyclerView.setVisibility(View.GONE);
}else {
    List<String> list = Arrays.asList(alist.get(position).getEvalPicture().split(","));
    //设置布局管理器
    if(list.size()>0){
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    holder.mRecyclerView.setLayoutManager(linearLayoutManager);
    //设置适配器
    PhotoAdapter mAdapter = new PhotoAdapter(context, list);
    holder.mRecyclerView.setAdapter(mAdapter);}else {
        holder.mRecyclerView.setVisibility(View.GONE);
    }
}
        return convertView;
    }

    class ViewHolder
    {
        CircleImageView icon;
        TextView phone;
        RatingBar comment_ratingBar_star;
        TextView time;
        TextView comment;
        TextView shop_info;
        TextView reply;
        RecyclerView mRecyclerView;
    }

}
